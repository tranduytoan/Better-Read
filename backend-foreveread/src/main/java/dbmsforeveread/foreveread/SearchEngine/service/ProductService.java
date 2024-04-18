package dbmsforeveread.foreveread.SearchEngine.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import dbmsforeveread.foreveread.SearchEngine.BookSearchRequest;
import dbmsforeveread.foreveread.SearchEngine.domain.Product;
import dbmsforeveread.foreveread.SearchEngine.util.ElasticSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ProductService {


    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public ProductService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }


//    public List<Product> searchProductsByName(String title) {
//        try {
//            SearchRequest request = new SearchRequest.Builder()
//                    .index("products")
//                    .query(q -> q
//                            .match(m -> m
//                                    .field("title")
//                                    .query(title)
//                            )
//                    ).build();
//
////            SearchResponse<Product> response = elasticsearchClient.search(request, Product.class);
////            List<Product> products = new ArrayList<>();
////            for (Hit<Product> hit : response.hits().hits()) {
////                products.add(hit.source());
////            }
////            return products;
//            SearchResponse<Product> response = elasticsearchClient.search(request, Product.class);
//            return response.hits().hits().stream()
//                    .map(Hit::source)
//                    .toList();
//        } catch (IOException e) {
//            throw new RuntimeException("Error searching in Elasticsearch", e);
//        }
//    }

    public Page<Product> searchProductsByName(String title, Pageable pageable) {
        try {
            SearchRequest request = new SearchRequest.Builder()
                    .index("products")
                    .query(q -> q
                            .match(m -> m
                                    .field("title")
                                    .query(title)
                            )
                    )
                    .from(pageable.getPageNumber() * pageable.getPageSize())
                    .size(pageable.getPageSize())
                    .build();

            SearchResponse<Product> response = elasticsearchClient.search(request, Product.class);
            List<Product> products = response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

            return new PageImpl<>(products, pageable, response.hits().total().value());
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

//    public Page<Product> searchProducts(BookSearchRequest request, Pageable pageable) {
//        try {
//            SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
//                    .index("products");
//
//            // Build the query based on the search request
//            Query query = buildQuery(request);
//
//            // Add the query to the search request
//            searchRequestBuilder.query(query);
//
//            // Add pagination
//            searchRequestBuilder.from(pageable.getPageNumber() * pageable.getPageSize());
//            searchRequestBuilder.size(pageable.getPageSize());
//
//            // Add highlighting
//            searchRequestBuilder.highlight(h -> h
//                    .fields("title", f -> f.preTags("<mark>").postTags("</mark>"))
//                    .fields("author", f -> f.preTags("<mark>").postTags("</mark>"))
//            );
//
//            // Execute the search request
//            SearchResponse<Product> response = elasticsearchClient.search(searchRequestBuilder.build(), Product.class);
//            List<Product> products = response.hits().hits().stream()
//                    .map(hit -> {
//                        Product product = hit.source();
//                        // Apply highlighting to the title and author fields
//                        if (hit.highlight() != null) {
//                            if (hit.highlight().get("title") != null) {
//                                product.setTitle(hit.highlight().get("title").get(0));
//                            }
//                            if (hit.highlight().get("author") != null) {
//                                product.setAuthor(hit.highlight().get("author").get(0));
//                            }
//                        }
//                        return product;
//                    })
//                    .collect(Collectors.toList());
//
//            return new PageImpl<>(products, pageable, response.hits().total().value());
//        } catch (IOException e) {
//            throw new RuntimeException("Error searching products", e);
//        }
//    }
    public Page<Product> searchProducts(BookSearchRequest request, Pageable pageable) {
        try {
            SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                    .index("products");

            Query query = buildQuery(request);
            searchRequestBuilder.query(query);

            searchRequestBuilder.from(pageable.getPageNumber() * pageable.getPageSize());
            searchRequestBuilder.size(pageable.getPageSize());

            SearchResponse<Product> response = elasticsearchClient.search(searchRequestBuilder.build(), Product.class);

            List<Product> products = response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

            return new PageImpl<>(products, pageable, response.hits().total().value());
        } catch (IOException e) {
            throw new RuntimeException("Error searching products", e);
        }
    }


    private Query buildQuery(BookSearchRequest request) {
        List<Query> must = new ArrayList<>();
        List<Query> should = new ArrayList<>();
        List<Query> filter = new ArrayList<>();

        if (StringUtils.hasText(request.getTitle())) {
            must.add(MatchQuery.of(m -> m.field("title").query(request.getTitle()).operator(Operator.And))._toQuery());
        }

        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            RangeQuery.Builder rangeQueryBuilder = new RangeQuery.Builder().field("price");
            if (request.getMinPrice() != null) {
                rangeQueryBuilder.gte(JsonData.of(request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                rangeQueryBuilder.lte(JsonData.of(request.getMaxPrice()));
            }
            filter.add(rangeQueryBuilder.build()._toQuery());
        }

//        if (!CollectionUtils.isEmpty(request.getCategories())) {
//            filter.add(TermsQuery.of(t -> t.field("category").terms(t2 -> t2.value(request.getCategories())))._toQuery());
//        }

        if (StringUtils.hasText(request.getAuthor())) {
            should.add(MatchQuery.of(m -> m.field("author").query(request.getAuthor()))._toQuery());
        }

        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder()
                .must(must)
                .filter(filter)
                .should(should)
                .minimumShouldMatch("1");

        Query query = boolQueryBuilder.build()._toQuery();
        log.info("Generated Elasticsearch query: {}", query);

                return query;
    }

    private void addSorting(SearchRequest.Builder searchRequestBuilder, Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                searchRequestBuilder.sort(s -> s
                        .field(f -> f
                                .field(order.getProperty())
                                .order(order.isAscending() ? SortOrder.Asc : SortOrder.Desc)
                        )
                );
            }
        }
    }
}