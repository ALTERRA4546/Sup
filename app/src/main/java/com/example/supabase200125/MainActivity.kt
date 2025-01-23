package com.example.supabase200125

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var nameTextView : TextView
    lateinit var emailTextView : TextView
    lateinit var passwordTextView : TextView
    lateinit var accountList : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameTextView = findViewById<TextView>(R.id.nameMain)
        emailTextView = findViewById<TextView>(R.id.emailMain)
        passwordTextView = findViewById<TextView>(R.id.passwordMain)
        accountList = findViewById<ListView>(R.id.accountMainList)

        getAccount()
    }

    override fun onResume() {
        super.onResume()
        getAccount()
    }

    fun updateAccount(view: View) {
        getAccount()
    }

    fun sendAccount(view: View) {
        var sup = SupabaseManager()
        lifecycleScope.launch {
            sup.PostAccount(nameTextView.text.toString(), emailTextView.text.toString(), passwordTextView.text.toString())

            getAccount()
        }
    }

    fun getAccount() {
        var sup = SupabaseManager()

        lifecycleScope.launch {
            accountList.adapter = null
            var accounts = sup.GetAccounts()
            var adapter = AccountAdapter(this@MainActivity, this@MainActivity, R.layout.list_account_item, accounts)
            accountList.adapter = adapter
        }
    }
}