package com.paligot.jsonforms.kotlin.panes.address.geocode

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import java.net.URLEncoder

class GeocodeApi(
    private val client: HttpClient,
    private val baseUrl: String = "https://maps.googleapis.com/maps/api",
) {
    suspend fun geocode(query: String): Geocode =
        coroutineScope {
            val encodeQuery = URLEncoder.encode(query, "utf-8")
            val apiKey = ""
            return@coroutineScope client
                .get("$baseUrl/geocode/json?address=$encodeQuery&key=$apiKey")
                .body()
        }

    companion object {
        fun create(enableNetworkLogs: Boolean): GeocodeApi =
            GeocodeApi(
                client =
                    HttpClient(CIO.create()) {
                        install(ContentNegotiation) {
                            json(
                                Json {
                                    isLenient = true
                                    ignoreUnknownKeys = true
                                },
                            )
                        }
                        if (enableNetworkLogs) {
                            install(Logging) {
                                logger = Logger.SIMPLE
                                level = LogLevel.INFO
                            }
                        }
                    },
            )
    }
}
