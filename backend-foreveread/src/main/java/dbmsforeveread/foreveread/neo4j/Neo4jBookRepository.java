package dbmsforeveread.foreveread.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Neo4jBookRepository extends Neo4jRepository<BookNode, Long> {
}
