package com.cs5481.news.discovery.config

import org.apache.http.Header
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.message.BasicHeader
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ResourceLoader
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import java.io.BufferedInputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


@Configuration
class ElasticSearchConfig(var env: Environment, val resourceLoader: ResourceLoader) : AbstractElasticsearchConfiguration() {
    private val isLocal: Boolean = false

    @Bean
    override fun elasticsearchClient(): RestHighLevelClient {
        val restClientBuilder: RestClientBuilder
        if (!isLocal) {
            val cloudId = env.getRequiredProperty("elasticsearch.cloudId", String::class.java)
            val apiKey = env.getRequiredProperty("elasticsearch.apiKey", String::class.java)
            restClientBuilder = RestClient.builder(cloudId)
                    .setHttpClientConfigCallback { httpClientBuilder: HttpAsyncClientBuilder ->
                        httpClientBuilder.setDefaultHeaders(mutableListOf<Header>(
                                BasicHeader("Authorization", "ApiKey $apiKey")
                        ))
                    }
        } else {
            restClientBuilder = getLocalClientBuilder()
        }
        return RestHighLevelClient(restClientBuilder)
    }

    fun getLocalClientBuilder(): RestClientBuilder {
        val credentialsProvider = BasicCredentialsProvider()
        val username = env.getRequiredProperty("elasticsearch.username", String::class.java)
        val password = env.getRequiredProperty("elasticsearch.password", String::class.java)

        credentialsProvider.setCredentials(AuthScope.ANY, UsernamePasswordCredentials(username, password))

        var context: SSLContext? = null
        val ks = KeyStore.getInstance("pkcs12")

        ks.load(null, null)
        val resource = resourceLoader.getResource("classpath:cert/http_ca.crt")
        val bis = BufferedInputStream(resource.inputStream)
        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        val cert = cf.generateCertificate(bis)
        ks.setCertificateEntry("ca", cert)

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(ks)

        context = SSLContext.getInstance("TLS")
        context.init(null, tmf.trustManagers, null)
        return RestClient.builder(
                HttpHost(env.getRequiredProperty("elasticsearch.host", String::class.java),
                        env.getRequiredProperty("elasticsearch.port", Int::class.java),
                        "https")
        ).setHttpClientConfigCallback { httpAsyncClientBuilder: HttpAsyncClientBuilder ->
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                    .setSSLContext(context)
        }
    }
}