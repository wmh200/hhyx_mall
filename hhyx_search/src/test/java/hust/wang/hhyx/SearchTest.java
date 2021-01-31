package hust.wang.hhyx;

import com.alibaba.fastjson.JSON;
import hust.wang.hhyx.config.ElasticSearchConfig;
import hust.wang.hhyx.entity.member.Member;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/31 1:20 下午
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        Member member = new Member();
        member.setNickName("小哈");
        member.setPhone("1111111");
        indexRequest.source(JSON.toJSONString(member), XContentType.JSON);
        restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
    }

    @Test
    public void searchData() throws IOException {
        SearchRequest searchRequest = new SearchRequest("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("nickName","小哈"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1){
            String sourceAsString = hit.getSourceAsString();
            Member member = JSON.parseObject(sourceAsString, Member.class);
            System.out.println(member);
        }

        System.out.println(search);

    }
}
