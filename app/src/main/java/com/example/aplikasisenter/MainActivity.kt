package com.example.aplikasisenter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.aplikasisenter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraManager: CameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Setup Kamera
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        //app title design
        setupAppName()

        //initview
        binding.apply {
            // cek apakah perangkat mempunyai senter
            val hasFlashLight = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

            if (hasFlashLight.not()){
                // pesan ketika perangkat tidak ada senter
                light.isEnabled = false
                Toast.makeText(this@MainActivity, "Perangkat kamu tidak ada senter", Toast.LENGTH_SHORT).show()
            }

            //animasi Listener
            layoutMain.setTransitionListener(object : MotionLayout.TransitionListener{
                override fun onTransitionStarted(motionLayout : MotionLayout? , startId : Int , endId : Int) {}

                override fun onTransitionChange(motionLayout : MotionLayout? , startId : Int , endId : Int , progress : Float) {}

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if (currentId == motionLayout!!.endState){
                        try {
                            val cameraId = cameraManager.cameraIdList[0]
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId, true)
                            }
                        } catch (e: CameraAccessException){
                            e.printStackTrace()
                        } catch (e: ArrayIndexOutOfBoundsException){
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            val cameraId = cameraManager.cameraIdList[0]
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId, false)
                            }
                        } catch (e: CameraAccessException)
                        { e.printStackTrace() }
                        catch (e: ArrayIndexOutOfBoundsException)
                        { e.printStackTrace() }
                    }
                }

                override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}

            })

        }

    }

    // atur custom text
    private fun setupAppName() {
        val spannableString = SpannableString(getString(R.string.flashLight))
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this,R.color.clr_green))

        spannableString.setSpan(colorSpan,8,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.title.text = spannableString
    }


    //Menu Options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.optionmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.petunjukMenu -> {
                val intent = Intent(this@MainActivity, PetunjukActivity::class.java)
                startActivity(intent)
            }
            R.id.tentangSaya -> {
                val intent = Intent(this@MainActivity, TentangActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> {
                finish() // Menutup aktivitas saat ini dan kembali ke halaman sebelumnya
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}