package com.cs5481.news.discovery.service

import com.cs5481.news.discovery.model.News
import com.cs5481.news.discovery.repository.NewsRepository
import com.cs5481.news.discovery.util.getDefaultPagin
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.apache.http.util.EntityUtils
import org.elasticsearch.client.Request
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.io.ResourceLoader
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class SearchService(val elasticClient: RestHighLevelClient,
                    val repository: NewsRepository,
                    val resourceLoader: ResourceLoader) {

    private val json: String;
    private val countJson: String;
    private val gson: Gson = Gson();

    init {
        json = this.loadJSON("classpath:query.json")
        countJson = this.loadJSON("classpath:countQuery.json")
    }

    fun getAllNew(pageNumber: Int?, size: Int?): Page<News> {
        val pageable: Pageable = getDefaultPagin(pageNumber, size)
        return repository.findAll(pageable)
    }

    fun getById(id: String): News {
        return repository.findBy_id(id)
    }

    fun searchByKeywords(keywords: String, pageNumber: Int?, size: Int?): List<News> {
        val request = Request("GET", "/kama_sample_news_index_2/_search")
        request.setJsonEntity(
                json.replace("%user_input%", '"' + keywords + '"')
                        .replace("%from%", pageNumber.toString())
                        .replace("%size%", size.toString())
        )
        val response = elasticClient.lowLevelClient.performRequest(request)
        val res = EntityUtils.toString(response.entity)
        val jsonObject = gson.fromJson(res, JsonObject::class.java)
        val listOfNews = jsonObject.getAsJsonObject("hits").getAsJsonArray("hits")
        val list: MutableList<News> = ArrayList()
        val action: (JsonElement) -> Unit = {
            val news = gson.fromJson(it.asJsonObject.getAsJsonObject("_source"), News::class.java)
            news._id = it.asJsonObject.get("_id").asString
            list.add(news)
        }
        listOfNews.forEach(action = action)

//        val pageable: Pageable = getDefaultPagin(pageNumber, size)
        return list
    }

    fun countByKeywords(keywords: String): Int {
        val request = Request("GET", "/kama_sample_news_index_2/_search")
        request.setJsonEntity(
                countJson.replace("%user_input%", '"' + keywords + '"')
        )
        val response = elasticClient.lowLevelClient.performRequest(request)
        val res = EntityUtils.toString(response.entity)
        val jsonObject = gson.fromJson(res, JsonObject::class.java)
        return jsonObject.getAsJsonObject("hits").getAsJsonObject("total")["value"].asInt
    }


    private fun loadJSON(filePath: String): String {
        try {
            val resource = resourceLoader.getResource(filePath)
            return resource.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}