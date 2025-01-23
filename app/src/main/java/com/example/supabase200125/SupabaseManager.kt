package com.example.supabase200125

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.JacksonSerializer

class SupabaseManager {
    val supabase = createSupabaseClient(
        supabaseUrl = "",
        supabaseKey = ""
    ) {
        //install(Auth)
        install(Postgrest)
        defaultSerializer = JacksonSerializer()
    }

    suspend fun GetAccounts():List<Account> {
        return supabase.from("account").select().decodeList<Account>()
        //return supabase.postgrest["account"].select().decodeList()
    }

    suspend fun GetSimpleAccount(id: Int): Account {
        return  supabase.from("account").select() {
            filter {
                Account::id eq id
            }
        }.decodeSingle()
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

    suspend fun UpdateAccount (id: Int, Name: String, Email: String, Password: String) {
        supabase.from("account").update (
            mapOf(
                "Name" to Name,
                "Email" to Email,
                "Password" to Password
            )
        )
        {
            filter {
                Account::id eq id
            }
        }
    }

    suspend fun DeleteAccount (id: Int) {
        supabase.from("account").delete {
            filter {
                Account::id eq id
            }
        }
    }
}

data class Account(
    val id : Int,
    val Name : String,
    val Email : String,
    val Password : String
)