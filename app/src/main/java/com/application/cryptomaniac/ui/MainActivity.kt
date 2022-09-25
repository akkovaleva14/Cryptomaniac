package com.application.cryptomaniac.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.application.cryptomaniac.network.ApiService
import com.application.cryptomaniac.CryptoRepository
import com.application.cryptomaniac.R
import com.application.cryptomaniac.network.RetrofitClient
import com.application.cryptomaniac.ui.cryptomain.CryptoListFragment


class MainActivity : AppCompatActivity() {
    var apiService: ApiService? = null
  //  var itemId = "bitcoin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService = RetrofitClient.getInstance()

        setContentView(R.layout.activity_main)
    }

    fun getRepository(): CryptoRepository? {
        return apiService?.let { CryptoRepository(it) }
    }


    override fun onResume() {
        super.onResume()
        openFragment(CryptoListFragment())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.activity_main, fragment)
        transaction.commit()
    }
}