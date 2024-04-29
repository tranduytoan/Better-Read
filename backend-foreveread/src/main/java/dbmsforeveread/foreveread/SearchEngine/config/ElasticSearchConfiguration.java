//package com.example.springelasticjavaclient;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticSearchConfiguration
//{
//    @Bean
//    public RestClient getRestClient() {
////        RestClient restClient = RestClient.builder(
////                new HttpHost("localhost", 9200)).build();
////        return restClient;
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("elastic", "LhsFsgfG_KKRe1sWpUq+")); // Thay 'user' và 'password' bằng tên người dùng và mật khẩu của bạn.
//
//        RestClientBuilder builder = RestClient.builder(
//                        new HttpHost("localhost", 9200, "https")) // Sử dụng "https" ở đây
//                .setHttpClientConfigCallback(httpClientBuilder ->
//                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
//
//        return builder.build();
//    }
//
//    @Bean
//    public  ElasticsearchTransport getElasticsearchTransport() {
//        return new RestClientTransport(
//                getRestClient(), new JacksonJsonpMapper());
//    }
//
//
//    @Bean
//    public ElasticsearchClient getElasticsearchClient(){
//        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
//        return client;
//    }
//
//}
//
//
package dbmsforeveread.foreveread.SearchEngine.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticSearchConfiguration {
    @Bean
    public RestClient getRestClient() {

        String usename = "elastic";
        String password = "WbCm4eWc6*cYDUCieNK0";

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(usename, password)); // Thay đổi theo tên người dùng và mật khẩu của bạn.

        try {
            SSLContext sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(new TrustAllStrategy()) // tin tưởng tất cả chứng chỉ
                    .build();

            RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "https"))
                    .setHttpClientConfigCallback(httpClientBuilder -> {
                        httpClientBuilder.setSSLContext(sslContext);
                        httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE); // bỏ qua xác thực hostname
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        return new ElasticsearchClient(getElasticsearchTransport());
    }
}
