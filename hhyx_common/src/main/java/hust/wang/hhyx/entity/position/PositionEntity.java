package hust.wang.hhyx.entity.position;

import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/21 5:12 下午
 **/
@Data
public class PositionEntity {
    public String provinceName;
    public String cityName;
    public Double lat;
    public Double lng;
}
