package com.cs5481.news.discovery.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document


@Document(indexName = "kama_sample_news_index_2")
data class News(
        @Id
        var _id: String,
        var title: String,
        var url: String,
        var date: String,
        var keywords: List<String>,
        var category: List<String>,
        var title_embedding: Map<String, String>,
        var model_id: String,
)