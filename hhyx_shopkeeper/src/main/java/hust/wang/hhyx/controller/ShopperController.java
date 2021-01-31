package hust.wang.hhyx.controller;
import hust.wang.hhyx.entity.position.PositionEntity;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/20 11:44 上午
 **/
@RestController
@RequestMapping("shopkeeper")
public class ShopperController {

    @Autowired
    ShopperService shopperService;

    @GetMapping("shopperList")
    public R shopperList(){
        List<ShopKeeper> shopKeepers = shopperService.getShopperList();
        return R.ok().data("shoppers", shopKeepers);
    }

    @PostMapping("saveShopper")
    public R insert() {
        String filePath = "/Users/wangmh/Desktop/chengduTZ.xlsx";
        shopperService.insert(filePath);
        return R.ok();
    }

    /**
     * 根据店铺id获取某一个团长信息
     * @param storeId
     * @return
     */
    @GetMapping("getOne")
    public R getOne(@RequestParam(value = "storeId") String storeId){
        ShopKeeper shopKeeper = shopperService.getOne(storeId);
        return R.ok().data("shopKeeper",shopKeeper);
    }

    /**
     * 根据用户地理位置信息来计算团长距离
     * @param positionEntity
     * @return
     */
    @PostMapping("getListByPois")
    public R getListByPois(@RequestBody PositionEntity positionEntity){
        List<ShopKeeper> shopperList = shopperService.getListByPois(positionEntity);
        return R.ok().data("shopperList",shopperList);
    }



}
