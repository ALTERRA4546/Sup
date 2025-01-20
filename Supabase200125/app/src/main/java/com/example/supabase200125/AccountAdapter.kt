package com.example.supabase200125

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AccountAdapter (context: Context, private val resource: Int, private val items: List<Account>)
    : ArrayAdapter<Account>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val account = items[position]

        view.findViewById<TextView>(R.id.textId).text = account.id.toString()
        view.findViewById<TextView>(R.id.textName).text = account.Name
        view.findViewById<TextView>(R.id.textEmail).text = account.Email
        view.findViewById<TextView>(R.id.textPassword).text = account.Password

        return view
    }
}