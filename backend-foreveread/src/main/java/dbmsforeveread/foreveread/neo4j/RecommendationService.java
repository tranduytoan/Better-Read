package dbmsforeveread.foreveread.neo4j;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final Neo4jClient neo4jClient;

    public RecommendationService(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public List<Map<String, Object>> getRecommendations(Long bookId) {
        String query = "MATCH (b1:Book) WHERE b1.book_id = $bookId " +
                "OPTIONAL MATCH (b1)-[:IN_CATEGORY]->(c:Category)<-[:IN_CATEGORY]-(b2:Book) " +
                "OPTIONAL MATCH (b1)-[:WRITTEN_BY]->(a:Author)<-[:WRITTEN_BY]-(b3:Book) " +
                "OPTIONAL MATCH (b1)<-[:PURCHASED]-(u:User)-[:PURCHASED]->(b4:Book) " +
                "WITH b1, COLLECT(DISTINCT b2) + COLLECT(DISTINCT b3) + COLLECT(DISTINCT b4) AS recommendedBooks " +
                "UNWIND recommendedBooks AS book " + "WITH b1, book, COUNT(*) AS score " +
                "WHERE book <> b1 " +
                "RETURN book.book_id AS bookId, book.title AS title, book.image_url AS imageUrl " +
                "ORDER BY score DESC " +
                "LIMIT 10";

        return executeQuery(query, Map.of("bookId", bookId));
    }

    private List<Map<String, Object>> executeQuery(String query, Map<String, Object> parameters) {
        return neo4jClient.query(query)
                .bindAll(parameters)
                .fetch()
                .all()
                .stream()
                .map(record -> {
                    Map<String, Object> bookData = Map.of(
                            "bookId", record.get("bookId"),
                            "title", record.get("title"),
                            "imageUrl", record.get("imageUrl")
                    );
                    return bookData;
                })
                .collect(Collectors.toList());
    }
}