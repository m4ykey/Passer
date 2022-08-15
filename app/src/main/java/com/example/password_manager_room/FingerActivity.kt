package com.example.password_manager_room

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FingerActivity : AppCompatActivity() {

    private var cancellationSignal : CancellationSignal? = null
    private val authenticationCallback : BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@FingerActivity, "Authentication error : $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(this@FingerActivity, MainActivity::class.java))
                finish()
            }
        }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger)

        checkBiometricSupport()

        val btnUnlock : Button = findViewById(R.id.btnUnlock)
        btnUnlock.setOnClickListener {
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle(getString(R.string.lock_screen_application))
                .setDescription(getString(R.string.unlock_to_continue))
                .setNegativeButton(getString(R.string.cancel), this.mainExecutor) { _, _ ->
                    Toast.makeText(this, "Authentication cancelled", Toast.LENGTH_SHORT).show()
                }.build()

            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }

    private fun checkBiometricSupport() : Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure){
            Toast.makeText(this, "Fingerprint authentication has been not enabled in settings", Toast.LENGTH_SHORT).show()
            return false
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Fingerprint authentication permission is not enabled", Toast.LENGTH_SHORT).show()
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }

    private fun getCancellationSignal() : CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(this, "Authentication cancelled by the user", Toast.LENGTH_SHORT).show()
        }
        return cancellationSignal as CancellationSignal
    }
}