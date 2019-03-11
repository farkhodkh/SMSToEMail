package com.example.franksinatra.smstoemail.utils

import android.os.Bundle
import com.example.franksinatra.smstoemail.BuildConfig
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class FeedbackEmail {
    var params: Bundle = Bundle()

    fun send() {
        var sendMessageRunnable: SendMessageRunnable = SendMessageRunnable()

        sendMessageRunnable.params = params

        var sendAsync: Thread = Thread(sendMessageRunnable)
        sendAsync.start()
    }


    class SendMessageRunnable() : Runnable {
        var params: Bundle? = null

        override fun run() {
            var email: String = params!!.getString("email")
            var subject: String = params!!.getString("subject")
            var context: String = params!!.getString("context")

            val props = Properties()
            props["mail.smtp.host"] = "smtp.mail.ru"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            var session = Session.getDefaultInstance(props,
                object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(BuildConfig.email, BuildConfig.password)
                    }
                })

            try {
                val mm = MimeMessage(session)
                mm.setFrom(InternetAddress(BuildConfig.email))
                mm.addRecipient(Message.RecipientType.TO, InternetAddress(email))
                mm.setSubject(subject)
                val messageBodyPart = MimeBodyPart()
                messageBodyPart.setText(context)
                val multipart = MimeMultipart()

                multipart.addBodyPart(messageBodyPart)
                mm.setContent(multipart)

                try {
                    Transport.send(mm)
                } catch (e: MessagingException) {
                    e.printStackTrace()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } catch (e: MessagingException) {
                e.printStackTrace()
            }


        }

    }
}