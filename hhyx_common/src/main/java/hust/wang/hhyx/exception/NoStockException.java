package hust.wang.hhyx.exception;

/**
 * @Author wangmh
 * @Date 2021/1/25 6:37 下午
 **/
public class NoStockException extends RuntimeException{
    private String skuId;
    public NoStockException(String skuId){
        super("商品id："+skuId+"；没有足够库存了");
    }
    public String getSkuId(){
        return skuId;
    }
    public void setSkuId(String skuId){
        this.skuId = skuId;
    }
}
