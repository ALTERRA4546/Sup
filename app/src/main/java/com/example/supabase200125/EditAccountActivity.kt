package com.example.supabase200125

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class EditAccountActivity : AppCompatActivity() {
    lateinit var idTextView : TextView
    lateinit var nameTextView : TextView
    lateinit var emailTextView : TextView
    lateinit var passwordTextView : TextView
    var accountId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        accountId = intent.getIntExtra("account_id", -1)

        idTextView = findViewById<TextView>(R.id.idEdit)
        nameTextView = findViewById<TextView>(R.id.nameEdit)
        emailTextView = findViewById<TextView>(R.id.emailEdit)
        passwordTextView = findViewById<TextView>(R.id.passwordEdit)

        if(accountId != -1) {
            getAccount(accountId)
        }
    }

    fun getAccount(id: Int) {
        var sup = SupabaseManager()

        lifecycleScope.launch {
            var data =  sup.GetSimpleAccount(id)

            idTextView.text = data.id.toString()
            nameTextView.text = data.Name
            emailTextView.text = data.Email
            passwordTextView.text = data.Password
        }
    }

    fun updateAccount(view: View) {
        var sup = SupabaseManager()

        lifecycleScope.launch {
            sup.UpdateAccount(accountId, nameTextView.text.toString(), emailTextView.text.toString(), passwordTextView.text.toString())
            finish()
        }
    }
}