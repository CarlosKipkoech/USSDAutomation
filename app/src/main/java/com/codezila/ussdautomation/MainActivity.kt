package com.codezila.ussdautomation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val CALL_PHONE_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDialUSSDClick(view: View) {
        // Check if the CALL_PHONE permission is granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, proceed with the call
            dialUSSDCode()
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE_REQUEST_CODE
            )
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult (
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the call
                dialUSSDCode()
            } else {
                // Permission denied, inform the user
                // You may want to display a message or take appropriate action
            }
        }
    }

    private fun dialUSSDCode() {
        // Replace "your_ussd_code" with the actual USSD code you want to run
        val ussdCode = "*544*1*10#"

        // Encode the USSD code, including the special characters
        val encodedUSSDCode = Uri.encode(ussdCode)

        // Create the USSD code URI
        val ussdUri = Uri.parse("tel:$encodedUSSDCode")

        // Create an Intent with the ACTION_CALL action and the USSD URI
        val dialUSSDIntent = Intent(Intent.ACTION_CALL, ussdUri)

        // Start the phone dialer activity
        startActivity(dialUSSDIntent)

    }

    // Function to request accessibility permission
    fun requestAccessibilityPermission(view: View) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }
}