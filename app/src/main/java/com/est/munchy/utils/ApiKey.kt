package com.est.munchy.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

class CryptoHelper @Inject constructor(
    private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPrefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun storeApiKey(apiKey: String) {
        sharedPrefs.edit().putString("ENCRYPTED_API_KEY", apiKey).apply()
    }

    fun getApiKey(): String? {
        return sharedPrefs.getString("ENCRYPTED_API_KEY", null)
    }

    companion object {
        private const val KEY_ALIAS = "MunchApp_Key"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"

        init {
            createKeyIfNeeded()
        }

        private fun createKeyIfNeeded() {
            val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
                load(null)
            }

            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    KEYSTORE_PROVIDER
                ).apply {
                    init(
                        KeyGenParameterSpec.Builder(
                            KEY_ALIAS,
                            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                        )
                            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                            .setKeySize(256)
                            .build()
                    )
                    generateKey()
                }
            }
        }
    }
}