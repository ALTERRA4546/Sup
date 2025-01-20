package com.example.supabase200125

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.JacksonSerializer

class SupabaseManager {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://tzelwlwbnsivolmalxxc.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InR6ZWx3bHdibnNpdm9sbWFseHhjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzczNjY5MzIsImV4cCI6MjA1Mjk0MjkzMn0.bSA-46yuZkr80IQFSBdFpQVtrfwzZLoRIXkSf109G-Y"
    ) {
        //install(Auth)
        install(Postgrest)
        defaultSerializer = JacksonSerializer()
    }

    suspend fun GetAccounts():List<Account> {
        return supabase.from("account").select().decodeList<Account>()
        //return supabase.postgrest["account"].select().decodeList()
    }

    suspend fun PostAccount(Name: String, Email: String, Password: String ) {
        supabase.postgrest["account"].insert(
            mapOf(
                "Name" to Name,
                "Email" to Email,
                "Password" to Password
            )
        )

        /*val acc = Account(id, Name, Email, Password)
        supabase.from("account").insert(acc)*/
    }
}

data class Account(
    val id : Int,
    val Name : String,
    val Email : String,
    val Password : String
)