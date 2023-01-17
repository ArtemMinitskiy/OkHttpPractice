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
    private var url = "https://reqres.in/api/users/2"

    //POST
    private var postUrl = "https://reqres.in/api/users/"
    private var postBody = """{
    "name": "morpheus",
    "job": "leader"
}"""

    //POST
    private var putUrl = "https://reqres.in/api/users/2"
    private var putBody = """{
    "name": "morpheus",
    "job": "zion resident"
}"""

    private val JSON: MediaType = MediaType.parse("application/json; charset=utf-8")!!

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

        //PUT
        try {
            putRequest(putUrl, putBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //PUT
        try {
            deleteRequest(putUrl, putBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //PATCH
        try {
            patchRequest(putUrl, putBody)
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("mLog", "GET Error: $e")
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body()!!.string()
                Log.i("mLog", "GET $myResponse")

                val txt = (JSONObject(myResponse).getJSONObject("data").get("avatar")).toString()
                Log.i("mLog", "GET $txt")
//                runOnUiThread { txtString.setText(myResponse) }
            }
        })
    }

    //POST
    @Throws(IOException::class)
    fun postRequest(postUrl: String, postBody: String) {
        val client = OkHttpClient()
        val body = RequestBody.create(JSON, postBody)
        val request = Request.Builder().url(postUrl).post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("mLog", "POST Error: $e")
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i("mLog", "POST ${response.body()!!.string()}")
            }
        })
    }

    //PUT
    @Throws(IOException::class)
    fun putRequest(postUrl: String, putBody: String) {
        val client = OkHttpClient()
        val body = RequestBody.create(JSON, putBody)
        val request = Request.Builder().url(postUrl).put(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("mLog", "PUT Error: $e")
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i("mLog", "PUT ${response.body()!!.string()}")
            }
        })
    }

    //DELETE
    @Throws(IOException::class)
    fun deleteRequest(postUrl: String, postBody: String) {
        val client = OkHttpClient()
        val body = RequestBody.create(JSON, postBody)
        val request = Request.Builder().url(postUrl).delete(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("mLog", "DELETE Error: $e")
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i("mLog", "DELETE ${response.code()}")
            }
        })
    }

    //PATCH
    @Throws(IOException::class)
    fun patchRequest(patchUrl: String, patchBody: String) {
        val client = OkHttpClient()
        val body = RequestBody.create(JSON, patchBody)
        val request = Request.Builder().url(patchUrl).patch(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("mLog", "PATCH Error: $e")
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i("mLog", "PATCH ${response.body()!!.string()}")
            }
        })
    }

}