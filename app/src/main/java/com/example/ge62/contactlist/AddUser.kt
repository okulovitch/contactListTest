package com.example.ge62.contactlist

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.add_user.*
import kotlinx.android.synthetic.main.user_item_list.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Suppress("DEPRECATION")
/**
 * Created by Ge62 on 28.06.2018.
 */
class AddUser:AppCompatActivity ()  {

    private var btn: Button? = null
    //private var buttonSave:Button?=null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private var lastId=0
    private var bitmap:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user)
      //  val selectImageBtn = findViewById<Button>(R.id.selectImage)
        var buttonSave=findViewById<View>(R.id.addNewUser) as Button?
        btn = findViewById<View>(R.id.selectImage) as Button
        imageview = findViewById<View>(R.id.iv) as ImageView
        btn!!.setOnClickListener { choosePhotoFromGallary() }
        buttonSave!!.setOnClickListener{
            saveUser()
        }
        lastId = intent.getIntExtra("size", -1)


    }




    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }


    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                   bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                  //  val path = saveImage(bitmap)
                    Toast.makeText(this@AddUser, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)
                    //saveData()

                    /////////

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@AddUser, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }

    fun saveUser(){
        lastId++
        if (!saveToInternalStorage(bitmap,lastId)) {
            return
        }
        var text = ""
        if(lastId>1){
            text+="\n"
        }
        text += userTxt.text.toString()
        var fos:FileOutputStream
        fos=openFileOutput("usersData.txt", Context.MODE_APPEND)
        fos.write(text.toByteArray())
        fos.close()
        finish()
    }


    private fun saveToInternalStorage(bitmapImage: Bitmap?,id:Int): Boolean {
        if (bitmapImage == null)
            return false
        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir

        val mypath = File(directory, id.toString()+".png")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage!!.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                fos!!.close()
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
        }
        return false
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        Toast.makeText(this, wallpaperDirectory.toString(), Toast.LENGTH_SHORT).show()
        /////

        val intent = Intent(this@AddUser, UserActivity::class.java)
        val text = txtName.text.toString()
        val bStream = ByteArrayOutputStream()
        // bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        val byteArray = bStream.toByteArray()
        intent.putExtra("text", text)
        intent.putExtra("image",wallpaperDirectory)

        startActivity(intent);

        ////////

        return ""
    }
    fun saveDataTest(){
        val intent = Intent(this@AddUser, UserActivity::class.java)
        val text = txtName.text.toString()
        val bStream = ByteArrayOutputStream()
       // bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        val byteArray = bStream.toByteArray()
        intent.putExtra("text", text)
        intent.putExtra("image",byteArray)

        startActivity(intent);
        finish()
    }




  /*  fun saveData(){



        val intent = Intent(this, UserActivity::class.java)
        var text = txtName.text.toString()
        var image = imageview.toString()
        intent.putExtra("text", text)
        intent.putExtra("image", image)
        startActivity(intent);


    }*/
    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

}

