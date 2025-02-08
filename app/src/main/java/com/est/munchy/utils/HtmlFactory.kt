/*
 * MIT License
 * 
 * Copyright (c) 2025 Husty9 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.est.munchy.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

suspend fun fetchInstructionsContent(url: String): String = withContext(Dispatchers.IO) {
    val client = getSecureOkHttpClient()
    val request = Request.Builder().url(url).build()

    try {
        withTimeout(9000L) { // 9-second timeout
            val response = client.newCall(request).execute()
            val html = response.body?.string() ?: ""
            val doc = Jsoup.parse(html)

            val selectors = listOf(
                "recipe",
                ".post-content",
                ".entry-content",
                "#main-content",
                ".content",
                "main"
            )

            var content: Element? = null
            for (selector in selectors) {
                content = doc.select(selector).firstOrNull()
                if (content != null) break
            }

            content = content ?: doc.body()

            // Remove common non-content elements
            content?.select("header, nav, footer, .sidebar, .comments, script, style")?.remove()

            // Preserve some HTML structure
            content?.select("br")?.before("\\n")
            content?.select("p")?.before("\\n\\n")

            val formattedContent = content?.text()?.replace("\\n", "\n")?.trim() ?: ""

            val cleanedContent = formattedContent.replaceFirst(Regex("^(.*?\\n){0,5}"), "")

            // Return content if it's long enough, or a fallback message
            if (cleanedContent.length > 50) cleanedContent else "Content not found or too short"
        }
    } catch (e: IOException) {
        e.printStackTrace()
        "Error fetching content: ${e.message}"
    } catch (e: Exception) {
        e.printStackTrace()
        "Unexpected error: ${e.message}"
    }
}

internal fun getSecureOkHttpClient(): OkHttpClient {
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(null as KeyStore?)
    val trustManagers = trustManagerFactory.trustManagers

    // Use TLSv1.3 if available, otherwise fallback to TLSv1.2
    val sslContext = try {
        SSLContext.getInstance("TLSv1.3")
    } catch (e: Exception) {
        SSLContext.getInstance("TLSv1.2")
    }
    sslContext.init(null, trustManagers, null)

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()
}
