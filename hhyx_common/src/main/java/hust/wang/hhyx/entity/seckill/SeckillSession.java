package hust.wang.hhyx.entity.seckill;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/29 7:14 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_seckill_session")//指定表名
public class SeckillSession {
    /**
     * id
     */
    private Integer id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 秒杀开始时间
     */
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    private Date endTime;
    /**
     * 启用状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private List<SeckillDetail> relations;
}
