package com.example.supabase200125

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AccountAdapter (private val lifecycleOwner: LifecycleOwner, context: Context, private val resource: Int, private val items: List<Account>)
    : ArrayAdapter<Account>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val account = items[position]

        view.findViewById<TextView>(R.id.textId).text = account.id.toString()
        view.findViewById<TextView>(R.id.textName).text = account.Name
        view.findViewById<TextView>(R.id.textEmail).text = account.Email
        view.findViewById<TextView>(R.id.textPassword).text = account.Password

        view.setOnClickListener {
            val intent = Intent(context, EditAccountActivity::class.java)
            intent.putExtra("account_id", account.id)
            context.startActivity(intent)
        }

        view.setOnLongClickListener {
            val layout = LayoutInflater.from(context).inflate(R.layout.delete_account_dialog, null)
            val builder = AlertDialog.Builder(context).setView(layout)
            val show = builder.show()
            show.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val yesButton = layout.findViewById<Button>(R.id.yesDelete)
            val noButton = layout.findViewById<Button>(R.id.noDelete)
            val textMessage = layout.findViewById<TextView>(R.id.textMessageDelete)


            //textMessage.text = "@string/delete_text" + " " + account.Name + "?"

            yesButton.setOnClickListener {
                show.dismiss()
                DeleteAccount(lifecycleOwner, context, account)
            }

            noButton.setOnClickListener {
                show.dismiss()
            }

            true
        }

        return view
    }

    fun DeleteAccount(lifecycleOwner: LifecycleOwner, context: Context, account: Account) {
        var sup = SupabaseManager()

        lifecycleOwner.lifecycleScope.launch {
            sup.DeleteAccount(account.id)
            (context as Activity).recreate()
        }
    }
}