package com.example.exam

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_persona.*
import org.json.JSONArray
import org.json.JSONObject


class Persona : AppCompatActivity() {

    
    private var jsonArray : JSONArray ? = null
    private var jsonO : JSONObject ? = null
    private var urlmaps: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Quien es?")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona)

        var jsonObj = JSONObject()
        var reponseV : String ? = ""
        val que = Volley.newRequestQueue(this) // declaramos volley para realizar las peticiones http
        val req = JsonObjectRequest(Request.Method.GET, "https://randomuser.me/api/", jsonObj,
            Response.Listener { response ->

                jsonArray = JSONArray(response["results"].toString()) //obtenemos el resultado
                jsonO = jsonArray!!.getJSONObject(0)
                var img = "[" + jsonO!!.getString("picture") + "]" // obtenemos objeto json para las imagenes
                var gender = jsonO!!.getString("gender") //obtenemos el genero
                var name = getName(jsonO!!.getString("name")) //obtenemos y mandamos el jsonstring para obtener el nombre completo de la persona
                var location = getLocation(jsonO!!.getString("location")) //obtemos la ubicacion
                val url : String? = setUrl(jsonO!!.getString("location")) //generamos la url

                urlmaps = url.toString()

                var jsonArrayImage = JSONArray(img)

                var jsonOi = jsonArrayImage.getJSONObject(0)

                Picasso.get().load(jsonOi.getString("medium").toString()).into(imgP) // set imageview

                tbName.setText(name) //asignamos el nombre al edittext
                txtGender.setText(if(gender == "female") "Mujer" else "Hombre")//asignamos el nombre al edittext
                tbMLocation.setText(location)//asignamos el nombre al edittext

                reponseV = jsonO!!.getString("location")

            }, Response.ErrorListener { error: VolleyError ->
                Log.d("Volley Error: ", error.toString())
            })
        que.add(req)

        btnLocation.setOnClickListener{ //ver la ubicacion en googlemaps en el navegador
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query="+urlmaps))
            startActivity(browserIntent)
        }

        Log.d("response pet: ",urlmaps.toString())
    }
    fun setUrl(jsonName: String?) : String?{
        var jsonObj = JSONObject(jsonName )
        var street = JSONObject(jsonObj["street"].toString())

        return street["name"].toString() + "+" + street["number"].toString() + "+" + jsonObj["city"].toString() + "+" + jsonObj["state"].toString() + "+" + jsonObj["country"].toString()
    }
    fun getLocation(jsonName: String?) : String? {
        var jsonObj = JSONObject(jsonName )
        var street = JSONObject(jsonObj["street"].toString())

        urlmaps = street["name"].toString() + "+" + street["number"].toString() + "+" + jsonObj["city"].toString() + "+" + jsonObj["state"].toString() + "+" + jsonObj["postcode"].toString() + "+" + jsonObj["country"].toString()
        return street["name"].toString() + " " + street["number"].toString() + ", " + jsonObj["city"].toString() + ", " + jsonObj["state"].toString() + ", " + jsonObj["postcode"].toString() + ", " + jsonObj["country"].toString()
    }

    fun getName(jsonName: String?) : String? {

        var jsonObj = JSONObject(jsonName )


        return jsonObj["title"].toString() + " " + jsonObj["first"].toString()  + " " + jsonObj["last"].toString()
    }


}