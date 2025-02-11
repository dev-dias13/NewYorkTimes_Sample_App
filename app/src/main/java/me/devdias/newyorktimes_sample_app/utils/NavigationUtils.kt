package me.devdias.newyorktimes_sample_app.utils

import com.google.gson.Gson
import me.devdias.newyorktimes_sample_app.domain.model.Article
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavigationUtils {
    fun Article.toNavigationString(): String {
        val json = Gson().toJson(this)
        return URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
    }

    // Convert encoded string back to Article
    fun String.toArticle(): Article {
        val decoded = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
        return Gson().fromJson(decoded, Article::class.java)
    }
}