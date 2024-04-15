//package dbmsforeveread.foreveread.neo4j;
//
//import org.springframework.data.neo4j.core.schema.Id;
//import org.springframework.data.neo4j.core.schema.Node;
//import org.springframework.data.neo4j.core.schema.Property;
//import org.springframework.data.neo4j.core.schema.Relationship;
//
//import java.util.List;
//
//@Node("User")
//public class UserNode {
//    @Id
//    private Long id;
//
//    @Property("name")
//    private String name;
//
//    @Relationship(type = "PURCHASED", direction = Relationship.Direction.OUTGOING)
//    private List<BookNode> purchasedBooks;
//}
