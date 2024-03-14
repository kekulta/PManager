package com.kekulta.pmanager.list.presentation.ui

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.UserNotAuthenticatedException
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.kekulta.pmanager.R
import com.kekulta.pmanager.databinding.ActivityMainBinding
import com.kekulta.pmanager.list.domain.models.MainOneTimeEvent
import com.kekulta.pmanager.list.domain.viewmodels.MainViewModel
import com.kekulta.pmanager.list.presentation.nav.MainNavGraph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.KeyStore
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModel()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainNavGraph.setup(binding.rootNavFragment.getFragment())



        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    viewModel.onAuthError()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.onAuthSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    viewModel.onAuthFail()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        lifecycleScope.launch {
            viewModel.observeEvents().collect { event ->
                when (event) {
                    is MainOneTimeEvent.SnackBar -> Snackbar.make(
                        binding.root,
                        event.message,
                        LENGTH_LONG
                    ).show()

                    MainOneTimeEvent.RequireAuth -> biometricPrompt.authenticate(promptInfo)
                }
            }
        }
    }

}

sealed class AuthEvent {
    data object AuthSuccess : AuthEvent()
    data object AuthFail : AuthEvent()
    data object AuthError : AuthEvent()
    data object AuthNone : AuthEvent()
}

class AuthManager {
    private val state = MutableStateFlow<AuthEvent>(AuthEvent.AuthNone)

    fun observeState(): Flow<AuthEvent> = state
    fun onSuccess() {
        state.update { AuthEvent.AuthSuccess }
    }

    fun onFail() {
        state.update { AuthEvent.AuthFail }
    }

    fun onError() {
        state.update { AuthEvent.AuthError }
    }
}

class EncryptionManager {
    fun decrypt(ciphertext: String): Result<String> {
        val encryptCipher = getCipher()
        val decryptCipher = getCipher()

        return try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
            encryptCipher.init(
                Cipher.DECRYPT_MODE,
                getSecretKey(),
                IvParameterSpec(encryptCipher.iv)
            )

            val decryptedInfo =
                String(
                    decryptCipher.doFinal(
                        Base64.decode(ciphertext, Base64.DEFAULT)
                    )
                )

            Result.success(decryptedInfo)
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            logcat { "Key is invalid." }
            Result.failure(e)
        } catch (e: UserNotAuthenticatedException) {
            e.printStackTrace()
            logcat { "The key's validity timed out." }
            Result.failure(e)
        }
    }

    fun encrypt(ciphertext: String): Result<String> {
        val encryptCipher = getCipher()

        return try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

            val encryptedInfo: String = Base64.encodeToString(
                encryptCipher.doFinal(
                    ciphertext.toByteArray(Charset.defaultCharset())
                ), Base64.DEFAULT
            )

            Result.success(encryptedInfo)
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            logcat { "Key is invalid." }
            Result.failure(e)
        } catch (e: UserNotAuthenticatedException) {
            e.printStackTrace()
            logcat { "The key's validity timed out." }
            Result.failure(e)
        }
    }

    private fun genKey() {
        generateSecretKey(
            KeyGenParameterSpec.Builder(
                KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        setUserAuthenticationParameters(
                            TIMEOUT,
                            KeyProperties.AUTH_BIOMETRIC_STRONG or KeyProperties.AUTH_DEVICE_CREDENTIAL
                        )
                    } else {
                        setUserAuthenticationValidityDurationSeconds(TIMEOUT)
                    }
                }
                .setUserAuthenticationRequired(true)
                .build()
        )
    }

    private fun generateSecretKey(keyGenParameterSpec: KeyGenParameterSpec) {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"
        )
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null)

        if (!keyStore.isKeyEntry(KEY_NAME)) {
            genKey()
        }

        return keyStore.getKey(KEY_NAME, null) as SecretKey
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7
        )
    }

    companion object {
        const val KEY_NAME = "secret_key"
        const val TIMEOUT = 10 * 60
    }
}
