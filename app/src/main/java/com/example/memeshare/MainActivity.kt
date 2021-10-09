package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme() {
        // meme loading shows progress bar
        val loading : ProgressBar = findViewById(R.id.progressBar)
loading.visibility = View.VISIBLE
        // now when a new meme loads and the progress bar stops just when it loads
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val url = it.getString("url")

                val memeImageView = findViewById<ImageView>(R.id.memeImageView)
                Glide.with(this).load(url).listener(object : RequestListener<Drawable>
                {
                    override fun onLoadFailed( // when meme fails to load show progress bar
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady( // when meme loads stop the progress bar
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                       loading.visibility = View.GONE
                        return false
                    }


                }
                )

                    .into(memeImageView)
            },
            {

            }

        )
        queue.add(jsonObjectRequest)
    }



    fun shareMeme(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"check this meme"
        )
        // create chooser from which app to send
        val chooser = Intent.createChooser(intent, "Share meme using ...")
        startActivity(chooser)
    }

    fun nextMeme(view: android.view.View) {
        loadMeme()
    }
}





