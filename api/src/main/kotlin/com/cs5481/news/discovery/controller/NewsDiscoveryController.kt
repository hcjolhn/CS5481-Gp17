package com.cs5481.news.discovery.controller

import com.cs5481.news.discovery.model.News
import com.cs5481.news.discovery.service.SearchService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/news-discovery/v1/news")
class NewsDiscoveryController(val searchService: SearchService) {
    @GetMapping
    fun getAll(@RequestParam(value = "p", required = false) pageNumber: Int?,
               @RequestParam(value = "s", required = false) size: Int?
    ): MutableIterable<News> {
        return searchService.getAllNew(pageNumber, size)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable(value = "id") id: String): News {
        return searchService.getById(id);
    }

    @GetMapping("/search")
    fun search(@RequestParam(value = "keys") keywords: String,
               @RequestParam(value = "from", required = false) from: Int?,
               @RequestParam(value = "size", required = false) size: Int?): List<News> {
        return searchService.searchByKeywords(keywords, from, size);
    }

    @GetMapping("/count")
    fun search(@RequestParam(value = "keys") keywords: String): Map<String, Int> {
        return mapOf("count" to searchService.countByKeywords(keywords))
    }
}