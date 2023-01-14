package com.example.okhttppractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    //https://www.digitalocean.com/community/tutorials/okhttp-android-example-tutorial

    //GET
    var url = "https://reqres.in/api/users/2"

    //POST
    var postUrl = "https://reqres.in/api/users/"
    var postBody = """{
    "name": "morpheus",
    "job": "leader"
}"""

    val JSON: MediaType = MediaType.parse("application/json; charset=utf-8")!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //GET
        try {
            run()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //POST
        try {
            postRequest(postUrl, postBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //GET
    @Throws(IOException::class)
    fun run() {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException?) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                val myResponse = response.body()!!.string()
                Log.i("mLog", "$myResponse")

                val txt = (JSONObject(myResponse).getJSONObject("data").get("avatar")).toString()
                Log.i("mLog", "$txt")
//                runOnUiThread { txtString.setText(myResponse) }
            }
        })
    }

    //POST
    @Throws(IOException::class)
    fun postRequest(postUrl: String?, postBody: String?) {
        val client = OkHttpClient()
        val body = RequestBody.create(JSON, postBody)
        val request = Request.Builder().url(postUrl).post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException?) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                Log.i("mLog", "${response.body()!!.string()}")
            }
        })
    }

}