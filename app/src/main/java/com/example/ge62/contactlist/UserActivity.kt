package com.example.ge62.contactlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ListView
import java.io.FileInputStream
import java.util.*




/**
 * Created by Ge62 on 27.06.2018.
 */
class UserActivity: AppCompatActivity() {

    private var arrUser: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RefreshData()
        R.drawable.ic_launcher_background
        val button = findViewById<Button>(R.id.addNewUserBtn)
        button.setOnClickListener {
            val intent = Intent(this, AddUser::class.java)
            intent.putExtra("size", arrUser.size)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RefreshData()
    }

    private fun RefreshData() {
        arrUser = ArrayList()
        try {
            val fis: FileInputStream
            fis = openFileInput("usersData.txt")
            val fileContent = StringBuffer("")
            var n: Int = 0
            val buffer = ByteArray(1024)
            n = fis.read(buffer)
            while (n !== -1) {
                fileContent.append(String(buffer, 0, n))
                n = fis.read(buffer)
            }
            var lines = fileContent.lines()
            if (lines.size > 0) {
                for (i in 0 until lines.size) {
                    arrUser.add(User(lines[i], i + 1))
                }
                fis.close()
            }
        }
        catch (E:Exception){

        }
        finally {
            var listView = findViewById<ListView>(R.id.listView)
            listView.adapter = CustomAdapter(applicationContext, arrUser)
        }
        //val lineList = mutableListOf<String>()
    }
}