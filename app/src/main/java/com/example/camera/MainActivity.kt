package com.example.camera

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.camera.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.File

private const val REQUEST_CODE_FOR_IMAGE_CAPTURE = 100
private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var photoFile: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) !=null){
                val dir = externalCacheDir
                val file = File.createTempFile("photo_",".jpg",dir)
                val uri = FileProvider.getUriForFile(this, "$packageName.provider",file)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
                startActivityForResult(intent, REQUEST_CODE_FOR_IMAGE_CAPTURE)
                photoFile = file
        }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_FOR_IMAGE_CAPTURE ->{
                if(resultCode == Activity.RESULT_OK){
                    BitmapFactory.decodeFile(photoFile.absolutePath)?.i(
                        binding.image.setImageBitmap(it)
                    )
                }
            }
        }
    }

}