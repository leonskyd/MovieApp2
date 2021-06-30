package com.example.movieapp.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.movieapp.models.WebMovie
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.google.gson.Gson
import java.util.stream.Collectors

object MovieLoader {

    fun loadMovieFromWeb(id: Int?): WebMovie? {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/movie_id?=${id}")
            //("https://api.weather.yandex.ru/v2/informers?lat=${
               // lat}&lon=${lon}")

            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.addRequestProperty("API Key (v3 auth)",
                        "01141597fb9a845a9ce999e83b8db575")
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(
                        InputStreamReader(urlConnection.inputStream))
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                }else{
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, WebMovie::class.java)
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect() // dont forget
            }
        } catch(e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var temVariable: String?
        while (reader.readLine().also { temVariable = it } != null) {
            rawData.append(temVariable).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // requires API24, we have 21
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}