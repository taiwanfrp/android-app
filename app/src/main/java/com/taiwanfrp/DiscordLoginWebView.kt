package com.taiwanfrp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DiscordLoginWebView(
    onClose: () -> Unit,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(true) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var finished by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webView = this

                    WebView.setWebContentsDebuggingEnabled(true)

                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                    cookieManager.setAcceptThirdPartyCookies(this, true)

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        setSupportMultipleWindows(true)
                        javaScriptCanOpenWindowsAutomatically = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        cacheMode = WebSettings.LOAD_DEFAULT

                        useWideViewPort = true
                        loadWithOverviewMode = true
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = false

                        userAgentString =
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                                    "(KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onCreateWindow(
                            view: WebView,
                            isDialog: Boolean,
                            isUserGesture: Boolean,
                            resultMsg: android.os.Message
                        ): Boolean {
                            val newWebView = WebView(context)
                            newWebView.apply {
                                val popupCookieManager = CookieManager.getInstance()
                                popupCookieManager.setAcceptCookie(true)
                                popupCookieManager.setAcceptThirdPartyCookies(this, true)

                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.setSupportMultipleWindows(true)
                                settings.javaScriptCanOpenWindowsAutomatically = true

                                webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(
                                        v: WebView,
                                        request: WebResourceRequest
                                    ): Boolean {
                                        val url = request.url.toString()
                                        if (url.contains("taiwanfrp.me") || url.contains("/oauth2/authorize")) {
                                            CookieManager.getInstance().flush()
                                            webView?.loadUrl(url)
                                            (newWebView.parent as? ViewGroup)?.removeView(newWebView)
                                            return true
                                        }
                                        return false
                                    }
                                }

                                webChromeClient = object : WebChromeClient() {
                                    override fun onCloseWindow(window: WebView) {
                                        (window.parent as? ViewGroup)?.removeView(window)
                                    }
                                }
                            }

                            val transport = resultMsg.obj as WebView.WebViewTransport
                            transport.webView = newWebView
                            resultMsg.sendToTarget()

                            (view.parent as? ViewGroup)?.addView(
                                newWebView, ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            )
                            return true
                        }

                        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                            android.util.Log.d(
                                "WEB_JS",
                                "${consoleMessage.message()} @${consoleMessage.lineNumber()}"
                            )
                            return true
                        }
                    }

                    webViewClient = object : WebViewClient() {
                        private fun checkLoginSuccess(url: String) {
                            if (finished) return

                            val uri = Uri.parse(url)
                            val host = uri.host ?: ""
                            val path = uri.path ?: "/"
                            val isTargetHost = host.contains("taiwanfrp.me")
                            val isFinalPath = path == "/" || path == "" || path.contains("callback")
                            if (!isTargetHost || !isFinalPath) return
                            val cookieManager = CookieManager.getInstance()
                            val cookieApi =
                                cookieManager.getCookie("https://api.taiwanfrp.me") ?: ""
                            val cookieRoot = cookieManager.getCookie("https://taiwanfrp.me") ?: ""
                            val combinedCookie =
                                if (cookieApi.isNotEmpty() && cookieRoot.isNotEmpty()) {
                                    if (cookieApi.contains(cookieRoot)) cookieApi else "$cookieApi; $cookieRoot"
                                } else {
                                    cookieApi.ifEmpty { cookieRoot }
                                }

                            android.util.Log.d("AUTH_DEBUG", "Final Check URL: $url")
                            android.util.Log.d("AUTH_DEBUG", "Combined Cookie: $combinedCookie")

                            // 更嚴格的 Token 判定：必須包含常見的 Session 關鍵字且長度足夠
                            val hasSession =
                                combinedCookie.contains("connect.sid", ignoreCase = true) ||
                                        combinedCookie.contains("session", ignoreCase = true) ||
                                        combinedCookie.length > 40

                            if (hasSession) {
                                android.util.Log.d("AUTH_SUCCESS", "Valid Session found! Closing.")
                                finished = true
                                RetrofitClient.cookie = combinedCookie
                                scope.launch {
                                    CookieStore.save(context, combinedCookie)
                                    onSuccess()
                                }
                            }
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView,
                            request: WebResourceRequest
                        ): Boolean {
                            val url = request.url.toString()
                            checkLoginSuccess(url)
                            return false
                        }

                        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            checkLoginSuccess(url)
                        }

                        override fun onPageFinished(view: WebView, url: String) {
                            super.onPageFinished(view, url)
                            loading = false
                            CookieManager.getInstance().flush()
                            checkLoginSuccess(url)

                            view.evaluateJavascript(
                                "(function(){ return document.body ? document.body.innerText : ''; })();"
                            ) { result ->
                                try {
                                    val bodyText =
                                        (JSONTokener(result).nextValue() as? String)?.trim() ?: ""
                                    if (bodyText.startsWith("{") && bodyText.contains("\"error\"")) {
                                        val json = JSONObject(bodyText)
                                        val rawError = json.optString(
                                            "error",
                                            context.getString(R.string.error_unknown)
                                        )
                                        val friendlyMessage = when {
                                            rawError.contains(
                                                "Rate limit exceeded",
                                                ignoreCase = true
                                            ) ->
                                                context.getString(R.string.error_rate_limit)

                                            else -> rawError
                                        }
                                        loading = false
                                        onError(friendlyMessage)
                                        return@evaluateJavascript
                                    }
                                } catch (_: Exception) {
                                }
                            }
                            view.evaluateJavascript(
                                """
                                (function() {
                                    function fixLayout() {
                                        var url = window.location.href;
                                        var h = window.innerHeight + 'px';
                                        if (url.indexOf('/oauth2/authorize') !== -1) {
                                            document.documentElement.style.minHeight = h;
                                            document.documentElement.style.height = 'auto';
                                            document.documentElement.style.overflowY = 'auto';
                                            document.body.style.minHeight = h;
                                            document.body.style.height = 'auto';
                                            document.body.style.overflowY = 'auto';
                                            var app = document.getElementById('app-mount');
                                            if (app) {
                                                app.style.minHeight = h;
                                                app.style.height = 'auto';
                                                app.style.display = 'flex';
                                                app.style.flexDirection = 'column';
                                            }
                                            var containers = document.querySelectorAll('[class*="container"], [class*="modal"], [class*="focusLock"], [data-mana-component="modal"]');
                                            containers.forEach(function(el) {
                                                el.style.setProperty('width', '100%', 'important');
                                                el.style.setProperty('max-width', 'none', 'important');
                                                el.style.setProperty('min-height', h, 'important');
                                                el.style.setProperty('height', 'auto', 'important');
                                                el.style.setProperty('max-height', 'none', 'important');
                                                el.style.setProperty('transform', 'none', 'important');
                                                el.style.setProperty('margin', '0', 'important');
                                                el.style.setProperty('position', 'relative', 'important');
                                                el.style.setProperty('top', '0', 'important');
                                                el.style.setProperty('left', '0', 'important');
                                            });
                                            var scrollers = document.querySelectorAll('[class*="body__"], [class*="scrollerBase"], [class*="bodyInner"], [class*="content__"], [class*="captchaContainer"]');
                                            scrollers.forEach(function(el) {
                                                el.style.setProperty('max-height', 'none', 'important');
                                                el.style.setProperty('height', 'auto', 'important');
                                                el.style.setProperty('overflow', 'visible', 'important');
                                                el.style.setProperty('padding', '10px', 'important');
                                                el.style.setProperty('display', 'block', 'important');
                                            });
                                            var iframes = document.querySelectorAll('iframe');
                                            iframes.forEach(function(el) {
                                                el.style.setProperty('width', '100%', 'important');
                                                el.style.setProperty('max-width', '100%', 'important');
                                            });
                                            var spacers = document.querySelectorAll('[class*="bodySpacer"]');
                                            spacers.forEach(function(el) {
                                                el.style.setProperty('display', 'none', 'important');
                                            });
                                        } 
                                        else if (url.indexOf('/login') !== -1 || url.indexOf('/verify') !== -1) {
                                            document.documentElement.style.overflowY = 'auto';
                                            document.body.style.overflowY = 'auto';
                                            var inputs = document.querySelectorAll('input');
                                            inputs.forEach(function(input) {
                                                input.style.maxWidth = '100%';
                                            });
                                        }
                                    }
                                    fixLayout();
                                    window.addEventListener('resize', fixLayout);
                                    setTimeout(fixLayout, 500);
                                    setTimeout(fixLayout, 2000);
                                    var observer = new MutationObserver(fixLayout);
                                    observer.observe(document.body, { childList: true, subtree: true });
                                })();
                                """.trimIndent(),
                                null
                            )

                            checkLoginSuccess(url)
                        }

                        override fun onReceivedError(
                            view: WebView,
                            request: WebResourceRequest,
                            error: WebResourceError
                        ) {
                            android.util.Log.e("WEB_ERROR", error.description.toString())
                            onError(error.description.toString())
                        }

                        override fun onReceivedHttpError(
                            view: WebView,
                            request: WebResourceRequest,
                            errorResponse: WebResourceResponse
                        ) {
                            android.util.Log.e(
                                "WEB_HTTP_ERROR",
                                "${request.url} -> ${errorResponse.statusCode}"
                            )
                        }
                    }

                    loadUrl("https://api.taiwanfrp.me/api/v1/auth/discord/login")
                }
            }
        )

        if (loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

        Row(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { webView?.reload() },
                modifier = Modifier.background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(Icons.Default.Refresh, null, tint = Color.White)
            }
            IconButton(
                onClick = onClose,
                modifier = Modifier.background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(Icons.Default.Close, null, tint = Color.White)
            }
        }
    }
}