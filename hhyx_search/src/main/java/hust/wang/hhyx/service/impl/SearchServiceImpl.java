package hust.wang.hhyx.service.impl;
import com.alibaba.fastjson.JSON;
import hust.wang.hhyx.config.ElasticSearchConfig;
import hust.wang.hhyx.constant.EsConstant;
import hust.wang.hhyx.entity.es.SkuEsModel;
import hust.wang.hhyx.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangmh
 * @Date 2021/1/31 3:20 下午
 **/
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    /**
     * 保存所有的sku数据到es中
     * @param skuEsModels
     */
    @Override
    public boolean save(List<SkuEsModel> skuEsModels) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for(SkuEsModel skuEsModel:skuEsModels){
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSguId());
            String s = JSON.toJSONString(skuEsModel);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        //TODO 如果批量错误
        boolean b = bulk.hasFailures();
        BulkItemResponse[] items = bulk.getItems();
        List<String> collect = Arrays.stream(items).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成：{}",collect);
        return b;
    }

    /**
     * 根据关键字来查询
     * @param keyword
     * @return
     */
    @Override
    public List<SkuEsModel> getProductsByKeyword(String keyword) {
        SearchRequest searchRequest = new SearchRequest(EsConstant.PRODUCT_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("sguName",keyword));
        searchSourceBuilder.sort("stock",SortOrder.DESC).sort("sellNum", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = null;
        List<SkuEsModel> result = new ArrayList<>();
        try {
            search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1){
            String sourceAsString = hit.getSourceAsString();
            SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
            result.add(skuEsModel);
        }
        return result;
    }
}
