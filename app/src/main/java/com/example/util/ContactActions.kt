package com.example.util

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.R
import com.example.data.PortfolioData
import java.net.URLEncoder

sealed interface ActionResult {
    data object Success : ActionResult
    data class Feedback(val message: String) : ActionResult
}

object ContactActions {

    fun openDialer(context: Context, phoneNumber: String = PortfolioData.PHONE_NUMBER): ActionResult {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return launchSafely(context, intent, context.getString(R.string.error_no_dialer))
    }

    fun openWhatsApp(
        context: Context,
        phoneE164: String = PortfolioData.WHATSAPP_E164,
        prefilledMessage: String = "Hello Nihal! I viewed your NIHALX portfolio app and would like to connect."
    ): ActionResult {
        val encodedText = runCatching {
            URLEncoder.encode(prefilledMessage, "UTF-8")
        }.getOrDefault("")
        val url = "https://wa.me/$phoneE164?text=$encodedText"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return launchSafely(context, intent, context.getString(R.string.error_no_browser))
    }

    fun openEmail(
        context: Context,
        recipient: String = PortfolioData.EMAIL,
        subject: String = "Project Inquiry via NIHALX Portfolio",
        body: String = "Hello Nihal Sain,\n\nI saw your NIHALX portfolio and would like to discuss:"
    ): ActionResult {
        val uri = Uri.parse(
            "mailto:$recipient" +
                "?subject=${Uri.encode(subject)}" +
                "&body=${Uri.encode(body)}"
        )
        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(intent)
            ActionResult.Success
        } catch (_: ActivityNotFoundException) {
            copyToClipboard(context, "Email", recipient)
            ActionResult.Feedback(context.getString(R.string.error_no_email))
        } catch (_: SecurityException) {
            copyToClipboard(context, "Email", recipient)
            ActionResult.Feedback(context.getString(R.string.error_no_email))
        }
    }

    fun openInstagram(
        context: Context,
        url: String = PortfolioData.INSTAGRAM_URL
    ): ActionResult {
        return openWebUrl(context, url)
    }

    fun openWebUrl(context: Context, rawUrl: String): ActionResult {
        val trimmed = rawUrl.trim()
        val normalized = if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            trimmed
        } else {
            "https://$trimmed"
        }
        val parsed = runCatching { Uri.parse(normalized) }.getOrNull()
        if (parsed == null || parsed.host.isNullOrBlank()) {
            return ActionResult.Feedback(context.getString(R.string.error_invalid_url))
        }
        val intent = Intent(Intent.ACTION_VIEW, parsed).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return launchSafely(context, intent, context.getString(R.string.error_no_browser))
    }

    fun copyToClipboard(context: Context, label: String, text: String): String {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
        return "$label copied: $text"
    }

    private fun launchSafely(
        context: Context,
        intent: Intent,
        fallbackMessage: String
    ): ActionResult {
        return try {
            context.startActivity(intent)
            ActionResult.Success
        } catch (_: ActivityNotFoundException) {
            ActionResult.Feedback(fallbackMessage)
        } catch (_: SecurityException) {
            ActionResult.Feedback(fallbackMessage)
        }
    }
}
