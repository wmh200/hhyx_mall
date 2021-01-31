package hust.wang.hhyx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hust.wang.hhyx.entity.position.PositionEntity;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import hust.wang.hhyx.stock.listener.ShopKeeperListener;
import hust.wang.hhyx.mapper.ShopperMapper;
import hust.wang.hhyx.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangmh
 * @Date 2021/1/20 11:45 上午
 **/
@Service
public class ShopperServiceImpl implements ShopperService {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @Autowired
    ShopperMapper shopperMapper;

    @Override
    public List<ShopKeeper> getShopperList() {
        QueryWrapper<ShopKeeper> queryWrapper = new QueryWrapper<>();
        /**
         * TODO  后面要传入poi点根据poi点的距离来返回附近的团长
         */
        queryWrapper.last("limit 10");
        queryWrapper.isNotNull("avatar");
        List<ShopKeeper> shopKeepers = shopperMapper.selectList(queryWrapper);
        return shopKeepers;
    }

    @Override
    public void insert(String file) {
        String filePath = file;
        ShopKeeperListener shopKeeperListener = new ShopKeeperListener();
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(filePath, ShopKeeper.class, shopKeeperListener);
        ExcelReaderSheetBuilder sheet = excelReaderBuilder.sheet();
        sheet.doRead();
        List<ShopKeeper> shopKeepers = shopKeeperListener.getData();
        shopKeepers.forEach(shopKeeper -> {
            shopperMapper.insert(shopKeeper);
        });

    }

    @Override
    public ShopKeeper getOne(String storeId) {
        QueryWrapper<ShopKeeper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id", storeId);
        ShopKeeper shopKeeper = shopperMapper.selectOne(queryWrapper);
        return shopKeeper;
    }

    @Override
    public List<ShopKeeper> getListByPois(PositionEntity positionEntity) {

        // 根据省份信息获取 对应城市的团长
        String provinceName = positionEntity.getProvinceName();
        String cityName = positionEntity.getCityName();
        Double lat = positionEntity.getLat();
        Double lng = positionEntity.getLng();
        QueryWrapper<ShopKeeper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_name",provinceName);
        queryWrapper.eq("city_name",cityName);
        queryWrapper.isNotNull("avatar");
        List<ShopKeeper> shopKeepers = shopperMapper.selectList(queryWrapper);
        // 每一个团长计算距离
        shopKeepers.forEach(shopKeeper -> {
            double distance = getDistance(shopKeeper.getLng(), shopKeeper.getLat(), lng, lat);
            shopKeeper.setDistance(distance);
        });
        List<ShopKeeper> collect = shopKeepers.stream().sorted(Comparator.comparing(ShopKeeper::getDistance)).collect(Collectors.toList());
        List<ShopKeeper> results = collect.subList(0, 10);
        return results;
    }

    /**
     * 计算两个点坐标的距离
     * @param lat1 纬度1
     * @param lng1 经度1
     * @param lat2 纬度2
     * @param lng2 经度2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 100d) / 100d;
        return s;
    }


}
