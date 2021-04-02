package com.suchao.learn.redis.cases.set;

import com.suchao.learn.redis.service.ProductSearchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * 商品搜索案例
 *
 * @author suchao
 * @date 2021/4/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_060_ProductSearchTest {

    @Autowired
    private ProductSearchService productSearchService;

    @Test
    public void productSearch() {
        productSearchService.addProduct(1, Arrays.asList("手机", "iphone", "潮流"));
        productSearchService.addProduct(2, Arrays.asList("手机", "huawei", "潮流"));
        productSearchService.addProduct(3, Arrays.asList("手机", "oppo", "潮流"));

        Set<String> set = productSearchService.searchProduct(Arrays.asList("手机", "潮流"));
        Assert.assertArrayEquals(new String[]{"1", "2", "3"}, set.toArray(new String[0]));
        set.forEach(log::info);
    }
}
