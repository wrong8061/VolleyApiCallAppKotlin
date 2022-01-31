package com.govind8061.birthdayapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.govind8061.birthdayapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    var currentImageUrl: String? =null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(LayoutInflater.from(applicationContext))
        setContentView(binding.root)

        loadMeme()
        binding.btnShare.setOnClickListener(View.OnClickListener {
            val intent=Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,currentImageUrl)
            val chooser=Intent.createChooser(intent,"Check Out This Meme Using ...")
            startActivity(chooser)
        })



        binding.btnNext.setOnClickListener(View.OnClickListener {
            loadMeme()
        })
    }
    fun loadMeme(){
        val url = "https://meme-api.herokuapp.com/gimme/wholesomememes"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                currentImageUrl=response.getString("url")
                Glide.with(this).load(currentImageUrl).into(binding.imgMeme)

            },
            Response.ErrorListener { error ->
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

}