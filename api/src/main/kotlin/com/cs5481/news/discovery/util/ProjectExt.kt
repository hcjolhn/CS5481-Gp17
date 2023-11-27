package com.cs5481.news.discovery.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable


inline fun getDefaultPagin(pageNumber: Int?, size: Int?): Pageable = PageRequest.of(pageNumber ?: 0, size ?: 20)