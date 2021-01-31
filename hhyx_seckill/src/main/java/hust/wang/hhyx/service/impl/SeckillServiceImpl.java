package hust.wang.hhyx.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.seckill.SeckillSession;
import hust.wang.hhyx.entity.seckill.vo.SeckillOrderVo;
import hust.wang.hhyx.entity.seckill.vo.SeckillRespVo;
import hust.wang.hhyx.entity.seckill.vo.SeckillVo;
import hust.wang.hhyx.feign.CouponFeignService;
import hust.wang.hhyx.feign.ProductFeignService;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.SeckillService;
import hust.wang.hhyx.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author wangmh
 * @Date 2021/1/29 8:20 下午
 **/
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CouponFeignService couponFeignService;
    private static final String SESSION_CACHE_PREFIX = "seckill:sessions:";

    private static final String SGUKILL_CACHE_PREFIX = "seckill:sgus";

    private static final String SKU_STOCK_SEMAPHORE = "seckill:stock:"; // +商品随机码
    @Override
    public void uploadSeckillSguLatest3Days() {
        R latest3DaySession = couponFeignService.getLatest3DaySession();
        List<SeckillSession> sessions = JSON.parseArray(latest3DaySession.getData().get("sessions").toString(), SeckillSession.class);
        saveSessionInfos(sessions);
        saveSessionSguInfos(sessions);
    }

    /**
     * 获取当前时间可以参与的秒杀商品信息
     * @return
     */
    @Override
    public List<SeckillRespVo> getCurrentSeckillSkus() {
        //确定当前时间属于哪个场次
        long time = System.currentTimeMillis();
        Set<String> keys = redisUtil.keys(SESSION_CACHE_PREFIX + "*");
        for (String key : keys){
            String[] s = key.replace(SESSION_CACHE_PREFIX, "").split("_");
            long start = Long.parseLong(s[0]);
            long end = Long.parseLong(s[1]);
            if (start <= time && end>=time) {
                //2.获取这个秒杀场次所有需要的所有的商品信息
                List<String> ranges = redisUtil.lRange(key, -100, 100);
                BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SGUKILL_CACHE_PREFIX);
                List<String> list = hashOps.multiGet(ranges);
                if(list != null){
                    List<SeckillRespVo> collect = list.stream().map(item -> {
                        SeckillRespVo seckillRespVo = JSON.parseObject(item, SeckillRespVo.class);
                        seckillRespVo.setRandomCode(null);
                        return seckillRespVo;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }
        return null;
    }

    /**
     * 根据商品id来获取秒杀商品信息
     * @param sguId
     * @return
     */
    @Override
    public SeckillVo getSguSeckillInfo(String sguId) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SGUKILL_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0){
            for (String key : keys){
                String regx = "\\d_"+sguId;
                if (Pattern.matches(regx,key)) {
                    String json = hashOps.get(key);
                    SeckillRespVo seckillRespVo = JSON.parseObject(json, SeckillRespVo.class);
                    SeckillVo seckillVo = new SeckillVo();
                    BeanUtils.copyProperties(seckillRespVo,seckillVo);
                    //处理随机码
                    Long startTime = seckillVo.getStartTime();
                    Long endTime = seckillVo.getEndTime();
                    long now = System.currentTimeMillis();
                    if (now >= startTime && now <= endTime) {
                    }else{
                        seckillVo.setRandomCode(null);
                    }
                    return seckillVo;
                }
            }
        }
        return null;
    }

    /**
     * 秒杀商品
     * @param killId
     * @param key
     * @param num
     * @param request
     * @return
     */
    @Override
    public String kill(String killId, String key, Integer num, HttpServletRequest request) {
        // 获取用户信息
        String accessToken = request.getHeader("accessToken").replace("[","").replace("]","");
        String s1 = redisUtil.get(accessToken);
        String[] s2 = s1.split("-");
        String openId = s2[2];
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SGUKILL_CACHE_PREFIX);
        String s = hashOps.get(key);
        if (!StringUtils.isEmpty(s)){
            //能查出秒杀商品信息
            SeckillRespVo seckillRespVo = JSON.parseObject(s, SeckillRespVo.class);
            String skuId = seckillRespVo.getPromotionSessionId()+"_"+seckillRespVo.getSguId();
            //验证code是否正确
            if (seckillRespVo.getRandomCode() == key && skuId == killId){
                //验证购物数量是否合理
                if (num < seckillRespVo.getSeckillLimit()){
                    String redisKey = openId + "_" + seckillRespVo.getSguId();
                    boolean b = redisUtil.setIfAbsent(redisKey, num.toString());
                    if (b){
                        // 占位成功说明从来没有买过
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + key);
                        try {
                            boolean bool = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                            String timeId = IdWorker.getTimeId();

                            SeckillOrderVo seckillOrderVo = new SeckillOrderVo();
                            seckillOrderVo.setOrderSn(timeId);
                            seckillOrderVo.setOpenId(openId);
                            seckillOrderVo.setPromotionSessionId(seckillRespVo.getPromotionSessionId());
                            seckillOrderVo.setNum(num);
                            seckillOrderVo.setSeckillPrice(seckillRespVo.getSeckillPrice());
                            seckillOrderVo.setSguId(seckillRespVo.getSguId());

                            //发送到消息队列创建订单
                            rabbitTemplate.convertAndSend("order-event-exchange","order.seckill,order",seckillOrderVo);
                            return timeId;
                        } catch (InterruptedException e) {
                            return null;
                        }
                    }else{

                    }

                }else {
                    return null;
                }
            }else {
                return null;
            }
        }else{
            return null;
        }
        return null;
    }

    /**
     * 保存活动信息
     * @param sessions
     */
    private void saveSessionInfos(List<SeckillSession> sessions) {
        sessions.stream().forEach(session -> {
            long createTime = session.getCreateTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SESSION_CACHE_PREFIX+createTime + "_" +endTime;
            //缓存活动信息
            Boolean hasKey = redisUtil.hasKey(key);
            if (!hasKey){
                List<String> collect = session.getRelations().stream().map(item -> item.getPromotionSessionId().toString()+"_"+item.getSguId()).collect(Collectors.toList());
                redisUtil.setList(key,collect);
            }
        });
    }

    /**
     * 保存商品信息
     * @param sessions
     */
    private void saveSessionSguInfos(List<SeckillSession> sessions) {
        sessions.stream().forEach(session->{
            BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SGUKILL_CACHE_PREFIX);
            session.getRelations().stream().forEach(item -> {
                //随机码
                String token = UUID.randomUUID().toString().replace("-", "");
                if(!ops.hasKey(item.getPromotionSessionId().toString()+"_"+item.getSguId())){
                    SeckillRespVo seckillRespVo = new SeckillRespVo();
                    //1.sku基本信息
                    R r = productFeignService.getGoodBySguId(item.getSguId());
                    Good good = JSON.parseObject(r.getData().get("good").toString(), Good.class);
                    seckillRespVo.setGood(good);

                    //2.sku的秒杀信息
                    BeanUtils.copyProperties(item,seckillRespVo);

                    //3.设置上当前活动的秒杀时间
                    seckillRespVo.setStartTime(session.getStartTime().getTime());
                    seckillRespVo.setEndTime(session.getEndTime().getTime());

                    //4.随机码
                    seckillRespVo.setRandomCode(token);
                    String s = JSON.toJSONString(seckillRespVo);
                    ops.put(item.getPromotionSessionId().toString()+"_"+item.getSguId(),s);

                    //5 用分布式信号量来控制：限流   商品可以秒杀的数量作为信号量

                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    semaphore.trySetPermits(item.getSeckillCount());
                }
            });
        });
    }
}
