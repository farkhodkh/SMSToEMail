package com.example.franksinatra.smstoemail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.franksinatra.smstoemail.services.SMSListenerService
import android.content.ComponentName
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager



class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance: MainActivity
    }

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
        //ComponentInfo{com.example.franksinatra.smstoemail/com.example.franksinatra.smstoemail.services.SMSListenerService}
        val intent: Intent = Intent(this, SMSListenerService::class.java)

        val explicitIntent: Intent = ServiceUtils.convertImplicitIntentToExplicitIntent(intent, this)!!;
        startService(intent)
    }

    object ServiceUtils {
        fun startService(intentUri: String) {
            val implicitIntent = Intent()
            implicitIntent.action = intentUri
            val context = instance
            val explicitIntent = convertImplicitIntentToExplicitIntent(implicitIntent, context)
            if (explicitIntent != null) {
                context.startService(explicitIntent)
            }
        }

        fun convertImplicitIntentToExplicitIntent(implicitIntent: Intent, context: MainActivity): Intent? {
            val pm = context.getPackageManager()
            val resolveInfoList = pm.queryIntentServices(implicitIntent, 0)

            if (resolveInfoList == null || resolveInfoList!!.size != 1) {
                return null
            }
            val serviceInfo = resolveInfoList!!.get(0)
            val component = ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
            val explicitIntent = Intent(implicitIntent)
            explicitIntent.component = component
            return explicitIntent
        }
    }
}
