package dbmsforeveread.foreveread.SearchEngine.service;

import dbmsforeveread.foreveread.SearchEngine.domain.Product;

import java.util.List;

public interface RedisServiceElastic {
    void addProductToRedis (Product product);

    void deleteProductFromRedis (String id, String title);

    Product getProductFromRedis (String id, String title);

    void addListProductToRedisWithTitle (List<Product> productList);

    List<Product> getListProductWithTitle (String title);

}
