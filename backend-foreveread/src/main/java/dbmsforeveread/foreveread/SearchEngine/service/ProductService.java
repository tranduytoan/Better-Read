package dbmsforeveread.foreveread.SearchEngine.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import dbmsforeveread.foreveread.SearchEngine.domain.Product;
import dbmsforeveread.foreveread.SearchEngine.repository.ProductRepository;
import dbmsforeveread.foreveread.SearchEngine.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public ProductService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }




    public List<Product> searchProductsByName(String title) {
        try {
            SearchRequest request = new SearchRequest.Builder()
                    .index("products")
                    .query(q -> q
                            .match(m -> m
                                    .field("title")
                                    .query(title)
                            )
                    ).build();

            SearchResponse<Product> response = elasticsearchClient.search(request, Product.class);
            List<Product> products = new ArrayList<>();
            for (Hit<Product> hit : response.hits().hits()) {
                products.add(hit.source());
            }
            return products;
        } catch (IOException e) {
            throw new RuntimeException("Error searching in Elasticsearch", e);
        }
    }

    public SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierQuery(approximateProductName);
        SearchResponse<Product> response = elasticsearchClient
                .search(s->s.index("products").query(supplier.get()),Product.class);
        System.out.println("elasticsearch supplier fuzzy query "+supplier.get().toString());
        return response;
    }

    public SearchResponse<Product> autoSuggestProduct(String partialProductName) throws IOException {

        Supplier<Query> supplier = ElasticSearchUtil.createSupplierAutoSuggest(partialProductName);
        SearchResponse<Product> searchResponse  = elasticsearchClient
                .search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println(" elasticsearch auto suggestion query"+supplier.get().toString());
        return searchResponse;
    }

    public List<Product> getProductsByFilters(Map<String, Object> filters, Double minPrice, Double maxPrice) throws IOException {
        return productRepository.searchWithFilters(filters, minPrice, maxPrice);
    }



}