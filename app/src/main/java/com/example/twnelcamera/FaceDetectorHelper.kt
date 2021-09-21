package com.example.twnelcamera

import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import java.io.IOException

private const val TAG = "FaceDetectorHelper"

class FaceDetectorHelper {

    fun detectFace(
        context: AppCompatActivity,
        uri: Uri,
        detectionFinished: (isFace: Boolean) -> Unit
    ) {
        try {
            val image = InputImage.fromFilePath(context, uri)
            val detector: FaceDetector = FaceDetection.getClient()
            detector.process(image).addOnSuccessListener { faces ->
                detectionFinished(faces.isNotEmpty())
            }.addOnFailureListener { e ->
                Log.e(TAG, "onImageSaved: $e", e)
                detectionFinished(false)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}