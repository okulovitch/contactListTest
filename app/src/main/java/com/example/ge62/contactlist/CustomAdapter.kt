package com.example.ge62.contactlist
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.user_item_list.view.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by Ge62 on 27.06.2018.
 */
class CustomAdapter(var context: Context, var user:ArrayList<User>):BaseAdapter() {

    private class ViewHolder(row:View?){
        var txtName:TextView
        var ivUser:ImageView
        init{
        this.txtName=row?.findViewById(R.id.txtName)!!
        this.ivUser= row?.findViewById(R.id.ivUser)!!
        }


    }

    private fun loadFromInternalStorage(id:Int): Bitmap? {
        var bitmap:Bitmap?=null
        val cw = ContextWrapper(context)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir

        val mypath = File(directory, id.toString()+".png")

        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
           bitmap= BitmapFactory.decodeStream(fis)


        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fis!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return bitmap
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if(convertView == null){
            var layout =LayoutInflater.from(context)
            view=layout.inflate(R.layout.user_item_list,parent,false)
            viewHolder = ViewHolder(view)
            view.tag=viewHolder

        }else{
           view = convertView
            viewHolder=view.tag as ViewHolder
        }
        var user:User=getItem(position) as User
        viewHolder.txtName.text=user.name
        viewHolder.ivUser.setImageResource(loadFromInternalStorage(user.image)!!)
        return view as View
    }

    override fun getItem(position: Int): Any {
     return user.get(position)
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
      return user.count()
    }
}

private fun ImageView.setImageResource(image: Bitmap) {
  ivUser.setImageBitmap(image)
}
