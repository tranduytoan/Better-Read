package dbmsforeveread.foreveread.SearchEngine.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


import dbmsforeveread.foreveread.SearchEngine.domain.Product;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class ProductRepository {

    public static final String PRODUCTS = "products";
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public String createOrUpdateDocument( Product product ) throws IOException {
        IndexResponse response= elasticsearchClient.index(i->i
                .index(PRODUCTS)
                .id(product.getId())
                .document(product));

        Map<String,String> responseMessages = Map.of(
                "Created","Document has been created",
                "Updated", "Document has been updated"
        );

        return responseMessages.getOrDefault(response.result().name(),"Error has occurred");

    }

    public Product findDocById(String productId) throws IOException {
        return elasticsearchClient.get(g->g.index(PRODUCTS).id(productId),Product.class).source();
    }

    public String deleteDocById(String productId) throws IOException {
        DeleteRequest deleteRequest = DeleteRequest.of(d->d.index(PRODUCTS).id(productId));
        DeleteResponse response =elasticsearchClient.delete(deleteRequest);

        return new StringBuffer(response.result().name().equalsIgnoreCase("NOT_FOUND")
                ?"Document not found with id"+productId:"Document has been deleted").toString();
    }

    public List<Product> findAll() throws IOException {
        SearchRequest request = SearchRequest.of(s->s.index(PRODUCTS));
        SearchResponse<Product> response = elasticsearchClient.search(request,Product.class);

        List<Product> products = new ArrayList<>();
        response.hits().hits().stream().forEach(object->{
            products.add(object.source());

        });
        return products;

    }

    public String bulkSave(List<Product> products) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();
        products.stream().forEach(product->br.operations(operation->
                operation.index(i->i
                        .index(PRODUCTS)
                        .id(product.getId())
                        .document(product))));

        BulkResponse response =elasticsearchClient.bulk(br.build());
        if(response.errors()){
            return new StringBuffer("Bulk has errors").toString();
        } else {
            return new StringBuffer("Bulk save success").toString();
        }
    }

    public Product insertProduct(Product product) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(PRODUCTS)
                .document(product));

        if (response.result().equals(Result.Created) || response.result().equals(Result.Updated)) {
            // Cập nhật product với ID từ response, nếu ID ban đầu không được cung cấp.
            if (product.getId() == null) {
                product.setId(response.id());
            }
            return product; // Trả về đối tượng Product đã được cập nhật với ID.
        } else {
            throw new RuntimeException("Failed to insert product. Elasticsearch response: " + response.result().name());
        }
    }

        public List<Product> searchWithFilters(Map<String, Object> filters, Double minPrice, Double maxPrice) throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(PRODUCTS)
                .query(q -> q
                        .bool(b -> {
                            List<Query> mustQueries = filters.entrySet().stream()
                                    .map(filter -> {
                                        if (filter.getKey().equalsIgnoreCase("price")) {
                                            return null; // Skip handling price here as it's handled below
                                        } else {
                                            return Query.of(m -> m
                                                    .matchPhrase(mp -> mp
                                                            .field(filter.getKey())
                                                            .query(filter.getValue().toString())
                                                    )
                                            );
                                        }
                                    })
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());

                            // Handling range query for price
                            if (minPrice != null || maxPrice != null) {
                                Query priceRangeQuery = Query.of(r -> r
                                        .range(ro -> ro
                                                .field("price")
                                                .gte(minPrice != null ? JsonData.of(minPrice) : null)
                                                .lte(maxPrice != null ? JsonData.of(maxPrice) : null)
                                        )
                                );
                                mustQueries.add(priceRangeQuery);
                            }

                            b.must(mustQueries);
                            return b;
                        })
                )
        );
        SearchResponse<Product> response = elasticsearchClient.search(searchRequest, Product.class);
        return response.hits().hits().stream()
                .map(hit -> hit.source())
                .collect(Collectors.toList());
    }


}

