package hust.wang.hhyx.controller;

import hust.wang.hhyx.entity.seckill.vo.SeckillRespVo;
import hust.wang.hhyx.entity.seckill.vo.SeckillVo;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.SeckillService;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/30 9:58 上午
 **/
@RestController
@RequestMapping("seckill")
public class SeckillController {
    @Autowired
    SeckillService seckillService;

    @GetMapping("/currentSeckillSkus")
    public R getCurrentSeckillSkus(){
        List<SeckillRespVo> vos = seckillService.getCurrentSeckillSkus();
        return R.ok().data("currentSeckillSkus",vos);
    }

    @GetMapping("/sgu/seckill/{sguId}")
    public R getSguSeckillInfo(@PathVariable("sguId") String sguId){
        SeckillVo seckillRespVo = seckillService.getSguSeckillInfo(sguId);
        return R.ok().data("seckillInfo",seckillRespVo);
    }

    @GetMapping("kill")
    public R secKill(@RequestParam("killId")String killId
                    , @RequestParam("key") String key
                    , @RequestParam("num") Integer num
                    , HttpServletRequest request){
        String orderNo = seckillService.kill(killId,key,num,request);
        return R.ok().data("orderNo",orderNo);
    }
}
