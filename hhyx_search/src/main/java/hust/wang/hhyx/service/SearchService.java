package hust.wang.hhyx.service;

import hust.wang.hhyx.entity.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/31 3:20 下午
 **/
public interface SearchService {
    boolean save(List<SkuEsModel> skuEsModels) throws IOException;
    List<SkuEsModel> getProductsByKeyword(String keyword);
}
