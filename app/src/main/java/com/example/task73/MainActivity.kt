package com.example.task73

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    var imageId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        val like = findViewById<ImageView>(R.id.like)
        val dislike = findViewById<ImageView>(R.id.dislike)
        val favourites = findViewById<Button>(R.id.favourites)

        val client = HttpClient(OkHttp)

        getRandomImage(client)

        App.instance?.needUpdateData = false

        like.setOnClickListener{
            App.instance?.needUpdateData = true

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response: String = client.post("https://api.thecatapi.com/v1/votes") {
                        headers {
                            append("Content-Type", "application/json")
                            append("x-api-key", "e7e933f7-09f6-43e3-a68a-b8e30c70e434")
                        }
                        body = Json.encodeToString(Vote(imageId, "user_id", 1))
                    }
                } catch(e: Exception) {
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "something went wrong check your internet connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            getRandomImage(client)
        }

        dislike.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response: String = client.post("https://api.thecatapi.com/v1/votes") {
                        headers {
                            append("Content-Type", "application/json")
                            append("x-api-key", "e7e933f7-09f6-43e3-a68a-b8e30c70e434")
                        }
                        body = Json.encodeToString(Vote(imageId, "something_id", 0))
                    }
                } catch(E: Exception) {
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "something went wrong check your internet connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            getRandomImage(client)
        }

        favourites.setOnClickListener{
            val intent = Intent(this@MainActivity, ActivityFavourites::class.java)
            startActivity(intent)
        }
    }

    private fun getRandomImage(client: HttpClient){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = client.get<String>("https://api.thecatapi.com/v1/images/search")
                val catImage =
                    Json { ignoreUnknownKeys = true }.decodeFromString<List<Cat>>(response)
                this@MainActivity.runOnUiThread {
                    val uri: Uri =
                        Uri.parse(catImage[0].url)
                    val image = findViewById<SimpleDraweeView>(R.id.image)
                    image.setImageURI(uri)
                    imageId = catImage[0].id
                }
            } catch(e: Exception) {
                this@MainActivity.runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "something went wrong check your internet connection",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}