package dbmsforeveread.foreveread.SearchEngine.controller;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import dbmsforeveread.foreveread.SearchEngine.BookSearchRequest;
import dbmsforeveread.foreveread.SearchEngine.domain.Product;
import dbmsforeveread.foreveread.SearchEngine.repository.ProductRepository;
import dbmsforeveread.foreveread.SearchEngine.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository elasticSearchQuery;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<String> createOrUpdateDocument(@RequestBody Product product) throws IOException {
        String response = elasticSearchQuery.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> bulk( @RequestBody List<Product> product) throws IOException {
        String response = elasticSearchQuery.bulkSave(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getDocumentById( @PathVariable String productId) throws IOException {
        Product product =elasticSearchQuery.findDocById(productId);
        log.info("Product Document has been successfully retrieved.");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteDocumentById( @PathVariable String productId) throws IOException {
        String message = elasticSearchQuery.deleteDocById(productId);
        log.info("Product Document has been successfully deleted. Message: {}", message);
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() throws IOException {
        List<Product> products = elasticSearchQuery.findAll();
        log.info("No of Product Documents has been successfully retrieved: {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        try {
            Product insertedProduct = elasticSearchQuery.insertProduct(product);
            return new ResponseEntity<>(insertedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/matchSearch/{title}")
    public ResponseEntity<List<Product>> searchProductsByName(@PathVariable String title) {
        try {
            List<Product> products = productService.searchProductsByName(title);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fuzzySearch/{approximateProductName}")
    List<Product> fuzzySearch( @PathVariable String approximateProductName ) throws IOException {
        SearchResponse<Product> searchResponse = productService.fuzzySearch(approximateProductName);
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        System.out.println(hitList);
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit :hitList){
            productList.add(hit.source());
        }
        return productList;
    }

    @GetMapping("/autoSuggest/{partialProductName}")
    List<String> autoSuggestProductSearch(@PathVariable String partialProductName) throws IOException {
        try {
            SearchResponse<Product> searchResponse = productService.autoSuggestProduct(partialProductName);
            List<Hit<Product>> hitList = searchResponse.hits().hits();
            List<Product> productList = new ArrayList<>();
            for (Hit<Product> hit : hitList) {
                productList.add(hit.source());
            }
            List<String> listOfProductNames = new ArrayList<>();
            for (Product product : productList) {
                listOfProductNames.add(product.getTitle());
            }
            return listOfProductNames;
        }  catch (IOException e) {
            log.error("Error occurred while retrieving auto-suggestions", e);
            return (List<String>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}








