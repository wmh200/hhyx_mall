package hust.wang.hhyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.seckill.SeckillDetail;
import hust.wang.hhyx.entity.seckill.SeckillSession;
import hust.wang.hhyx.mapper.SeckillSessionMapper;
import hust.wang.hhyx.service.SeckillDetailService;
import hust.wang.hhyx.service.SeckillSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangmh
 * @Date 2021/1/29 8:32 下午
 **/
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {
    @Autowired
    SeckillSessionMapper seckillSessionMapper;

    @Autowired
    SeckillDetailService seckillDetailService;

    /**
     * 获取最近三天的数据
     * @return
     */
    @Override
    public List<SeckillSession> getLatest3DaySession() {
        // 计算最近三天
        List<SeckillSession> sessions = seckillSessionMapper.selectList(new QueryWrapper<SeckillSession>().between("start_time", startTime(), endTime()));
        if(sessions != null && sessions.size()>0){
            List<SeckillSession> list = list().stream().map(session -> {
                Integer sessionId = session.getId();
                List<SeckillDetail> seckillLists = seckillDetailService.list(new QueryWrapper<SeckillDetail>().eq("promotion_session_id", sessionId));
                session.setRelations(seckillLists);
                return session;
            }).collect(Collectors.toList());
            return list;
        }
        return null;
    }
    private String startTime(){
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime startTime = LocalDateTime.of(now, min);
        String format = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }

    private String endTime(){
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(2);
        LocalTime max = LocalTime.MAX;
        LocalDateTime endTime = LocalDateTime.of(localDate, max);
        String format = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }
}
