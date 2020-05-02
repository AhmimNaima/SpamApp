package com.example.spam

import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        read()

    }
    fun read(){

        var cursor: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)

        startManagingCursor(cursor)

        var from = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID )

        var to = intArrayOf(android.R.id.text1)
        var simple : SimpleCursorAdapter = SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,from,to)

        listView.adapter = simple

        btn_send.setOnClickListener{
            val message = editText.text.toString().trim()

            if (message.isEmpty()) {
                return@setOnClickListener
            }

            val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
            if (contacts != null) {
                while (contacts.moveToNext()){
                    val number=contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    var obj=SmsManager.getDefault()
                    obj.sendTextMessage(number,null,message,null,null)
                }
                contacts.close()
                Toast.makeText(this,"le message de publicité est envoyé a tous les contacts",Toast.LENGTH_SHORT).show()
            }
            else{
                  Toast.makeText(this,"il n'exite aucun contact pour lui envoyer le message",Toast.LENGTH_SHORT).show()
            }


        }

    }
}
