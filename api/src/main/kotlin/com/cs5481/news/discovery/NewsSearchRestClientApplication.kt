package com.cs5481.news.discovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NewsSearchRestClientApplication

fun main(args: Array<String>) {
	runApplication<NewsSearchRestClientApplication>(*args)
}
