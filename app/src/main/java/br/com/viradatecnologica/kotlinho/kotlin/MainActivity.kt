package br.com.viradatecnologica.kotlinho.kotlin

import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import feign.Feign
import feign.gson.GsonDecoder

class MainActivity : AppCompatActivity() {

    private var btnSearch: Button? = null
    private var edUserid: EditText? = null

    private var tvName: TextView? = null
    private var tvUsername: TextView? = null
    private var tvEmail: TextView? = null
    private var tvStreet: TextView? = null
    private var tvCity: TextView? = null
    private var tvZipcode: TextView? = null
    private var tvLat: TextView? = null
    private var tvLng: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configPolicy()

        initComponents()

        configBtnOnClick()
    }


    private fun configBtnOnClick() {
        btnSearch?.setOnClickListener(View.OnClickListener {
            val user = object : AsyncTask<Int, Void, User>() {

                public override fun doInBackground(vararg params: Int?): User? {
                    try {
                        return Feign.builder()
                                .decoder(GsonDecoder())
                                .target(UserRequets::class.java, "http://jsonplaceholder.typicode.com")
                                .getUser(params[0])

                    } catch (e: Exception) {
                        e.printStackTrace()
                        return null
                    }

                }

            }?.doInBackground(edUserid?.text.toString().toInt())

            if (user == null) {
                Toast.makeText(
                        this@MainActivity,
                        "Usuário ${edUserid?.text} não encontrado!",
                        Toast.LENGTH_SHORT).show()

                clearTextViews()

            } else {

                setTextViews(user)
            }
        })
    }


    private fun clearTextViews() {
        tvName?.text = ""
        tvUsername?.text = ""
        tvEmail?.text = ""
        tvStreet?.text = ""
        tvCity?.text = ""
        tvZipcode?.text = ""
        tvLat?.text = ""
        tvLng?.text = ""
    }

    private fun setTextViews(user: User) {
        tvName?.text = user.name
        tvUsername?.text = user.username
        tvEmail?.text = user.email

        val address = user.address
        tvStreet?.text = address?.street
        tvCity?.text = address?.city
        tvZipcode?.text = address?.zipcode

        tvLat?.text = address?.geo?.lat?.toString()
        tvLng?.text = address?.geo?.lng?.toString()
    }

    private fun initComponents() {
        btnSearch = findViewById(R.id.btnSearch) as Button
        edUserid = findViewById(R.id.etUserid) as EditText

        tvName = findViewById(R.id.tvName) as TextView
        tvUsername = findViewById(R.id.tvUsername) as TextView
        tvEmail = findViewById(R.id.tvEmail) as TextView
        tvStreet = findViewById(R.id.tvStreet) as TextView
        tvCity = findViewById(R.id.tvCity) as TextView
        tvZipcode = findViewById(R.id.tvZipcode) as TextView
        tvLat = findViewById(R.id.tvLat) as TextView
        tvLng = findViewById(R.id.tvLng) as TextView
    }

    private fun configPolicy() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}
