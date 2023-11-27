package com.cs5481.news.discovery.repository

import com.cs5481.news.discovery.model.News

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface NewsRepository : ElasticsearchRepository<News, String> {
    fun findBy_id(id: String): News
}