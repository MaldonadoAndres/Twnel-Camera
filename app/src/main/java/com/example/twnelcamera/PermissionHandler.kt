package com.example.twnelcamera

import android.Manifest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX

class PermissionHandler(
    private val owner: AppCompatActivity,
    private val cameraController: CameraController
) {
    fun askCameraPermissions() {
        PermissionX.init(owner).permissions(Manifest.permission.CAMERA)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "El permiso de camara es necesario para continuar",
                    "OK",
                    "Cancelar"
                )

            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "Necesitaras otorgar permisos de manera manual",
                    "OK",
                    "Cancelar"
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    cameraController.startCamera()
                } else {
                    Toast.makeText(
                        owner,
                        "No se aceptaron permisos para la camara",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}