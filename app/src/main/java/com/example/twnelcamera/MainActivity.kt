package com.example.twnelcamera

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.twnelcamera.databinding.ActivityMainBinding
import com.mcxiaoke.koi.ext.longToast
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraController: CameraController
    private lateinit var permissionHandler: PermissionHandler
    private lateinit var faceDetectorHelper: FaceDetectorHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cameraController = CameraController(previewView, this)
        permissionHandler = PermissionHandler(this, cameraController)
        faceDetectorHelper = FaceDetectorHelper()
        permissionHandler.askCameraPermissions()
        btnTakePicture.setOnClickListener {
            spin_kit.visibility = View.VISIBLE
            cameraController.takePhoto { uri ->
                showCapturedImage(previewView.bitmap!!)
                faceDetectorHelper.detectFace(this, uri) { isFace ->
                    val msg = if (isFace) R.string.face_detected else R.string.no_face_detected
                    longToast(msg)
                }
            }
        }
        btnTryAgain.setOnClickListener {
            tryAgain()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraController.shutDownCamera()
    }


    private fun showCapturedImage(bitmap: Bitmap) {
        spin_kit.visibility = View.GONE
        previewView.visibility = View.GONE
        ivPhotoPreview.visibility = View.VISIBLE
        ivPhotoPreview.setImageBitmap(bitmap)
    }

    private fun tryAgain() {
        previewView.visibility = View.VISIBLE
        ivPhotoPreview.visibility = View.GONE
    }


}