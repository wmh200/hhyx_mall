package hust.wang.hhyx.service;

import hust.wang.hhyx.entity.position.PositionEntity;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import java.util.List;
/**
 * @Author wangmh
 * @Date 2021/1/20 11:45 上午
 **/
public interface ShopperService {
    List<ShopKeeper> getShopperList();

    void insert(String file);

    ShopKeeper getOne(String storeId);

    List<ShopKeeper> getListByPois(PositionEntity positionEntity);
}
