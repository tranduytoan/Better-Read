//package dbmsforeveread.foreveread.neo4j;
//
//import org.springframework.data.neo4j.core.Neo4jClient;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class RecommendationService {
//    private final Neo4jClient neo4jClient;
//
//    public RecommendationService(Neo4jClient neo4jClient) {
//        this.neo4jClient = neo4jClient;
//    }
//
//    public List<Map<String, Object>> getRecommendations(Long userId) {
//        String query = "MATCH (u:User)-[:PURCHASED]->(b1:Book)-[:WRITTEN_BY]->(a:Author)<-[:WRITTEN_BY]-(b2:Book) " +
//                "WHERE NOT (u)-[:PURCHASED]->(b2) AND u.id = $userId " +
//                "WITH u, b2, COUNT(*) AS score " +
//                "ORDER BY score DESC " +
//                "RETURN b2.title AS title, b2.isbn AS isbn, b2.publicationDate AS publicationDate, " +
//                "b2.language AS language, b2.pages AS pages, b2.description AS description, " +
//                "b2.price AS price, b2.imageUrl AS imageUrl " +
//                "LIMIT 5";
//
//        return neo4jClient.query(query)
//                .bindAll(Map("userId"))
//                .fetch()
//                .stream()
//                .map(record -> {
//                    Map<String, Object> bookData = new HashMap<>();
//                    bookData.put("title", record.get("title"));
//                    bookData.put("isbn", record.get("isbn"));
//                    bookData.put("publicationDate", record.get("publicationDate"));
//                    bookData.put("language", record.get("language"));
//                    bookData.put("pages", record.get("pages"));
//                    bookData.put("description", record.get("description"));
//                    bookData.put("price", record.get("price"));
//                    bookData.put("imageUrl", record.get("imageUrl"));
//                    return bookData;
//                })
//                .collect(Collectors.toList());
//    }
//}