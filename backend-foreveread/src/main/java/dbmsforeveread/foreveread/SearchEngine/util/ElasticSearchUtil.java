package dbmsforeveread.foreveread.SearchEngine.util;


import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;

public class ElasticSearchUtil {

    // util for fuzzy query
    public static Supplier<Query> createSupplierQuery(String approximateProductName){
        Supplier<Query> supplier = ()->Query.of(q->q.fuzzy(createFuzzyQuery(approximateProductName)));
        return  supplier;
    }
    public static FuzzyQuery createFuzzyQuery(String approximateProductName){
        val fuzzyQuery  = new FuzzyQuery.Builder();
        return  fuzzyQuery.field("name").value(approximateProductName).build();
    }

    // util for auto suggest query
    public static Supplier<Query> createSupplierAutoSuggest(String partialProductName){

        Supplier<Query> supplier = ()->Query.of(q->q.match(createAutoSuggestMatchQuery(partialProductName)));
        return  supplier;
    }
    public static MatchQuery createAutoSuggestMatchQuery(String partialProductName){
        val autoSuggestQuery = new MatchQuery.Builder();
        return  autoSuggestQuery.field("name").query(partialProductName).analyzer("standard").build();

    }
}
