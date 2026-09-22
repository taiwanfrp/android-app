package com.taiwanfrp

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebStorage
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AltRoute
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.annotations.SerializedName
import com.taiwanfrp.ui.theme.TaiwamfrpTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okio.sink
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import java.io.File

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class AppTheme {
    Light, Dark, System, Blue, BlueLight, Green, GreenLight, Oled, White
}

enum class AppLanguage {
    Auto, Zh, En
}

class SettingsViewModel(context: Context) : ViewModel() {
    private val THEME_KEY = stringPreferencesKey("app_theme")
    private val REFRESH_INTERVAL_KEY = stringPreferencesKey("refresh_interval")
    private val SINGLE_EXPAND_KEY = stringPreferencesKey("single_expand")
    private val LANGUAGE_KEY = stringPreferencesKey("app_language")
    private val dataStore = context.applicationContext.dataStore

    val themeState = dataStore.data
        .map { preferences ->
            try {
                AppTheme.valueOf(preferences[THEME_KEY] ?: "System")
            } catch (e: Exception) {
                AppTheme.System
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppTheme.System)

    val languageState = dataStore.data
        .map { preferences ->
            try {
                AppLanguage.valueOf(preferences[LANGUAGE_KEY] ?: "Auto")
            } catch (e: Exception) {
                AppLanguage.Auto
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.Auto)

    val refreshInterval = dataStore.data
        .map { it[REFRESH_INTERVAL_KEY]?.toIntOrNull() ?: 60 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 60)

    val singleExpandMode = dataStore.data
        .map { it[SINGLE_EXPAND_KEY]?.toBoolean() ?: true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch { dataStore.edit { it[THEME_KEY] = theme.name } }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch { dataStore.edit { it[LANGUAGE_KEY] = language.name } }
    }

    fun setRefreshInterval(seconds: Int) {
        viewModelScope.launch { dataStore.edit { it[REFRESH_INTERVAL_KEY] = seconds.toString() } }
    }

    fun setSingleExpandMode(enabled: Boolean) {
        viewModelScope.launch { dataStore.edit { it[SINGLE_EXPAND_KEY] = enabled.toString() } }
    }
}

sealed class UpdateState {
    object Idle : UpdateState()
    object Checking : UpdateState()
    data class NewVersionAvailable(val release: GithubRelease) : UpdateState()
    object UpToDate : UpdateState()
    object Downloading : UpdateState()
    data class Error(val message: String) : UpdateState()
}

class UpdateViewModel(private val context: Context) : ViewModel() {
    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState

    fun checkUpdate(currentVersion: String, manual: Boolean = false) {
        viewModelScope.launch {
            if (manual) _updateState.value = UpdateState.Checking
            try {
                val latest = RetrofitClient.updateApi.getLatestRelease()
                val latestVersion = latest.tagName.removePrefix("v")
                if (isNewerVersion(currentVersion, latestVersion)) {
                    _updateState.value = UpdateState.NewVersionAvailable(latest)
                } else if (manual) {
                    _updateState.value = UpdateState.UpToDate
                }
            } catch (e: Exception) {
                if (manual) {
                    _updateState.value =
                        UpdateState.Error(e.message ?: "Failed to check for updates")
                }
            }
        }
    }

    private fun isNewerVersion(current: String, latest: String): Boolean {
        val currentParts = current.split(".").mapNotNull { it.toIntOrNull() }
        val latestParts = latest.split(".").mapNotNull { it.toIntOrNull() }
        val size = maxOf(currentParts.size, latestParts.size)
        for (i in 0 until size) {
            val c = currentParts.getOrElse(i) { 0 }
            val l = latestParts.getOrElse(i) { 0 }
            if (l > c) return true
            if (c > l) return false
        }
        return false
    }

    fun downloadAndInstall(release: GithubRelease) {
        val asset = release.assets.find { it.name.endsWith(".apk") } ?: return
        _updateState.value = UpdateState.Downloading
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = okhttp3.Request.Builder().url(asset.downloadUrl).build()
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) throw Exception("Download failed")

                val apkFile = File(context.externalCacheDir, "update.apk")
                response.body?.source()?.use { source ->
                    apkFile.outputStream().use { output ->
                        source.readAll(output.sink())
                    }
                }
                installApk(apkFile)
                _updateState.value = UpdateState.Idle
            } catch (e: Exception) {
                _updateState.value = UpdateState.Error(e.message ?: "Download failed")
            }
        }
    }

    private fun installApk(file: File) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun dismiss() {
        _updateState.value = UpdateState.Idle
    }
}

enum class AppState {
    Init, Welcome, App
}

class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var nodeViewModel: NodeViewModel
    private lateinit var tunnelViewModel: TunnelViewModel
    private lateinit var updateViewModel: UpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authViewModel = AuthViewModel(applicationContext)
        settingsViewModel = SettingsViewModel(applicationContext)
        nodeViewModel = NodeViewModel(applicationContext)
        tunnelViewModel = TunnelViewModel(applicationContext)
        updateViewModel = UpdateViewModel(applicationContext)

        val versionName = try {
            packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: Exception) {
            "0.0.0"
        }
        updateViewModel.checkUpdate(versionName ?: "0.0.0")

        setContent {
            val appTheme by settingsViewModel.themeState.collectAsState()
            val appLanguage by settingsViewModel.languageState.collectAsState()
            val updateState by updateViewModel.updateState.collectAsState()
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val isForeground = rememberIsAppInForeground()
            val isDark = when (appTheme) {
                AppTheme.Light, AppTheme.BlueLight, AppTheme.GreenLight, AppTheme.White -> false
                AppTheme.Dark, AppTheme.Oled, AppTheme.Blue, AppTheme.Green -> true
                AppTheme.System -> isSystemInDarkTheme()
            }

            LaunchedEffect(isForeground) {
                if (isForeground) {
                    authViewModel.checkAuth(silent = true)
                }
            }

            LaunchedEffect(isDark) {
                enableEdgeToEdge(
                    statusBarStyle = androidx.activity.SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT
                    ) { isDark },
                    navigationBarStyle = androidx.activity.SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT
                    ) { isDark }
                )
            }

            LocaleWrapper(appLanguage) {
                TaiwamfrpTheme(theme = appTheme.name) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                })
                            },
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val loginState by authViewModel.loginState.collectAsState()
                        var isGuestMode by remember { mutableStateOf(false) }
                        var appState by remember { mutableStateOf(AppState.Init) }

                        UpdateDialog(state = updateState, viewModel = updateViewModel)

                        Crossfade(targetState = appState, label = "AppStateTransition") { state ->
                            when (state) {
                                AppState.Init -> {
                                    InitScreen(onInitComplete = {
                                        appState =
                                            if (loginState is LoginState.LoggedIn) AppState.App else AppState.Welcome
                                    })
                                }

                                AppState.Welcome -> {
                                    WelcomeScreen(
                                        authViewModel = authViewModel,
                                        onEnterGuestMode = {
                                            isGuestMode = true
                                            appState = AppState.App
                                        },
                                        onLoginSuccess = {
                                            appState = AppState.App
                                        }
                                    )
                                }

                                AppState.App -> {
                                    if (isGuestMode) {
                                        NodeScreen(
                                            viewModel = nodeViewModel,
                                            isGuest = true,
                                            onBack = {
                                                isGuestMode = false
                                                appState = AppState.Welcome
                                            },
                                            language = appLanguage
                                        )
                                    } else {
                                        Crossfade(
                                            targetState = loginState,
                                            label = "LoginTransition"
                                        ) { lState ->
                                            when (lState) {
                                                is LoginState.LoggedIn -> {
                                                    MainScreen(
                                                        nodeViewModel = nodeViewModel,
                                                        tunnelViewModel = tunnelViewModel,
                                                        authViewModel = authViewModel,
                                                        settingsViewModel = settingsViewModel,
                                                        updateViewModel = updateViewModel
                                                    )
                                                }

                                                LoginState.Suspended, LoginState.Banned, LoginState.Deleted -> {
                                                    AccountStatusScreen(
                                                        state = lState,
                                                        authViewModel = authViewModel,
                                                        nodeViewModel = nodeViewModel,
                                                        tunnelViewModel = tunnelViewModel,
                                                        onBackToWelcome = {
                                                            appState = AppState.Welcome
                                                        }
                                                    )
                                                }

                                                LoginState.Loading -> {
                                                    Box(
                                                        Modifier.fillMaxSize(),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Surface(
                                                            shape = CircleShape,
                                                            color = MaterialTheme.colorScheme.secondaryContainer,
                                                            modifier = Modifier.size(64.dp)
                                                        ) {
                                                            Box(contentAlignment = Alignment.Center) {
                                                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                                                            }
                                                        }
                                                    }
                                                }

                                                else -> {
                                                    LaunchedEffect(Unit) {
                                                        appState = AppState.Welcome
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        authViewModel.checkAuth()
    }
}

// --- Data Models ---
data class UserMeResponse(
    @SerializedName("internal_user_id") val id: String? = null,
    val username: String? = null,
    @SerializedName("internal_account_status") val status: String? = null,
    val avatar: String? = null,
    @SerializedName("discord_id") val discordId: String? = null,
    @SerializedName("mfa_enabled") val mfaEnabled: Boolean? = null,
    val locale: String? = null,
    val email: String? = null,
    val verified: Boolean? = null,
    val permissions: List<String> = emptyList(),
    val roles: List<String> = emptyList()
)

data class TunnelResponse(
    val id: String?,
    val name: String?,
    val description: String?,
    @SerializedName("node_id") val nodeId: Int?,
    val protocol: String?,
    @SerializedName("local_ip") val localIp: String?,
    @SerializedName("local_port") val localPort: Int?,
    @SerializedName("remote_port") val remotePort: Int?,
    @SerializedName("is_kcp_enabled") val isKcpEnabled: Boolean?,
    @SerializedName("is_proxy_protocol_v2_enabled") val isProxyProtocolV2Enabled: Boolean?,
    @SerializedName("is_enabled") val isEnabled: Boolean?,
    val status: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("owner_id") val ownerId: String? = null,
    @SerializedName("user_id") val userId: String? = null
)

data class NodeResponse(
    val id: Int?,
    val name: String?,
    val description: String?,
    val host: String?,
    @SerializedName("port_start") val portStart: Int?,
    @SerializedName("port_end") val portEnd: Int?,
    val status: String?,
    @SerializedName("is_public") val isPublic: Boolean?,
    @SerializedName("owner_id") val ownerId: String? = null
)

data class SystemStatusResponse(
    val api: String?,
    val version: String?,
    val database: String?,
    val redis: String?
)

data class CreateNodeRequest(
    val name: String,
    val description: String,
    val host: String,
    @SerializedName("port_start") val portStart: Int,
    @SerializedName("port_end") val portEnd: Int,
    @SerializedName("is_public") val isPublic: Boolean
)

data class CreateTunnelRequest(
    val name: String,
    val description: String,
    @SerializedName("node_id") val nodeId: Int,
    val protocol: String,
    @SerializedName("local_ip") val localIp: String,
    @SerializedName("local_port") val localPort: Int,
    @SerializedName("remote_port") val remotePort: Int?,
    @SerializedName("is_kcp_enabled") val isKcpEnabled: Boolean,
    @SerializedName("is_proxy_protocol_v2_enabled") val isProxyProtocolV2Enabled: Boolean
)

data class NodeUpdateRequest(
    val status: String?,
    @SerializedName("is_public") val isPublic: Boolean?,
    val name: String? = null,
    val description: String? = null,
    val host: String? = null,
    @SerializedName("port_start") val portStart: Int? = null,
    @SerializedName("port_end") val portEnd: Int? = null
)

data class TunnelUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    @SerializedName("node_id") val nodeId: Int? = null,
    val protocol: String? = null,
    @SerializedName("local_ip") val localIp: String? = null,
    @SerializedName("local_port") val localPort: Int? = null,
    @SerializedName("remote_port") val remotePort: Int? = null,
    @SerializedName("is_kcp_enabled") val isKcpEnabled: Boolean? = null,
    @SerializedName("is_proxy_protocol_v2_enabled") val isProxyProtocolV2Enabled: Boolean? = null,
    @SerializedName("is_enabled") val isEnabled: Boolean? = null
)

// --- API Services ---
interface TaiwanFrpApi {
    @GET("api/v1/users/me")
    suspend fun getUserMe(): UserMeResponse

    @GET("api/v1/tunnels")
    suspend fun getTunnels(): List<TunnelResponse>

    @GET("api/v1/nodes")
    suspend fun getNodes(): List<NodeResponse>

    @POST("api/v1/tunnels")
    suspend fun createTunnel(@retrofit2.http.Body request: CreateTunnelRequest): retrofit2.Response<Unit>

    @POST("api/v1/nodes")
    suspend fun createNode(@retrofit2.http.Body request: CreateNodeRequest): retrofit2.Response<Unit>

    @PATCH("api/v1/tunnels/{id}")
    suspend fun updateTunnel(
        @retrofit2.http.Path("id") id: String,
        @retrofit2.http.Body request: TunnelUpdateRequest
    ): retrofit2.Response<Unit>

    @PATCH("api/v1/nodes/{id}")
    suspend fun updateNode(
        @retrofit2.http.Path("id") id: Int,
        @retrofit2.http.Body request: NodeUpdateRequest
    ): retrofit2.Response<Unit>

    @retrofit2.http.DELETE("api/v1/tunnels/{id}")
    suspend fun deleteTunnel(@retrofit2.http.Path("id") id: String): retrofit2.Response<Unit>

    @retrofit2.http.DELETE("api/v1/nodes/{id}")
    suspend fun deleteNode(@retrofit2.http.Path("id") id: Int): retrofit2.Response<Unit>

    @GET("status")
    suspend fun getSystemStatus(): SystemStatusResponse
}

interface GithubUpdateApi {
    @GET("repos/taiwanfrp/android-app/releases/latest")
    suspend fun getLatestRelease(): GithubRelease
}

object RetrofitClient {
    private const val MAIN_URL = "https://api.taiwanfrp.me/"
    var cookie: String? = null

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            cookie?.let { request.header("Cookie", it) }
            chain.proceed(request.build())
        }
        .build()

    val mainApi: TaiwanFrpApi by lazy {
        Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaiwanFrpApi::class.java)
    }

    val updateApi: GithubUpdateApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubUpdateApi::class.java)
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class LoggedIn(val user: UserMeResponse) : LoginState()
    data class Error(val message: String) : LoginState()
    object Suspended : LoginState()
    object Banned : LoginState()
    object Deleted : LoginState()
}

class AuthViewModel(private val context: Context) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState
    private var lastCheckTime = 0L

    fun setError(message: String) {
        _loginState.value = LoginState.Error(message)
    }

    fun checkAuth(force: Boolean = false, silent: Boolean = false) {
        val currentTime = System.currentTimeMillis()
        if (!force && !silent && currentTime - lastCheckTime < 2000) return
        lastCheckTime = currentTime

        viewModelScope.launch {
            val currentState = _loginState.value
            val isActuallySilent = silent || (!force && currentState is LoginState.LoggedIn)

            if (!isActuallySilent) {
                if (!force && currentState is LoginState.Loading) return@launch
                _loginState.value = LoginState.Loading
            }

            try {
                val savedCookie = CookieStore.get(context)
                if (savedCookie != null) RetrofitClient.cookie = savedCookie

                val user = RetrofitClient.mainApi.getUserMe()
                if (user.id != null) {
                    _loginState.value = LoginState.LoggedIn(user)
                } else if (!isActuallySilent) {
                    _loginState.value =
                        LoginState.Error(context.getString(R.string.error_user_info))
                }
            } catch (e: retrofit2.HttpException) {
                if (isActuallySilent && (e.code() == 401 || e.code() == 403)) {
                    _loginState.value = LoginState.Idle
                } else if (!isActuallySilent) {
                    handleHttpException(e)
                }
            } catch (e: Exception) {
                if (!isActuallySilent) {
                    _loginState.value =
                        LoginState.Error(e.message ?: context.getString(R.string.error_connection))
                }
            }
        }
    }

    private fun handleHttpException(e: retrofit2.HttpException) {
        if (e.code() == 401) {
            _loginState.value = LoginState.Idle
            return
        }

        val errorBody = e.response()?.errorBody()?.string()
        val message = try {
            val jsonObject = com.google.gson.Gson()
                .fromJson(errorBody, com.google.gson.JsonObject::class.java)
            jsonObject.get("error")?.asString ?: jsonObject.get("detail")?.asString
            ?: context.getString(R.string.error_server, e.code())
        } catch (_: Exception) {
            context.getString(R.string.error_server, e.code())
        }

        if (e.code() == 403) {
            when {
                message.contains("suspended", ignoreCase = true) || message.contains(
                    "suspended",
                    ignoreCase = true
                ) ->
                    _loginState.value = LoginState.Suspended

                message.contains("banned", ignoreCase = true) ->
                    _loginState.value = LoginState.Banned

                message.contains("deleted", ignoreCase = true) ->
                    _loginState.value = LoginState.Deleted

                else -> _loginState.value = LoginState.Error(message)
            }
        } else {
            _loginState.value = LoginState.Error(message)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = LoginState.Idle
            RetrofitClient.cookie = null
            CookieStore.clear(context)
        }
    }
}

class NodeViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow<NodeUiState>(NodeUiState.Loading)
    val uiState: StateFlow<NodeUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun clearData() {
        _uiState.value = NodeUiState.Loading
    }

    fun refreshNodes(authViewModel: AuthViewModel? = null) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val nodes = RetrofitClient.mainApi.getNodes()
                _uiState.value = NodeUiState.Success(nodes)
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 401 || e.code() == 403) {
                    authViewModel?.checkAuth(force = true)
                }
                _uiState.value = NodeUiState.Error(
                    e.message ?: context.getString(
                        R.string.error_server,
                        e.code()
                    )
                )
            } catch (e: Exception) {
                _uiState.value =
                    NodeUiState.Error(e.message ?: context.getString(R.string.error_unknown))
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun deleteNode(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.mainApi.deleteNode(id)
                if (response.isSuccessful) {
                    val nodes = RetrofitClient.mainApi.getNodes()
                    _uiState.value = NodeUiState.Success(nodes)
                    onSuccess()
                } else {
                    onError(context.getString(R.string.error_server, response.code()))
                }
            } catch (e: Exception) {
                onError(e.message ?: context.getString(R.string.error_delete_failed))
            }
        }
    }

    fun createNode(request: CreateNodeRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.mainApi.createNode(request)
                if (response.isSuccessful) {
                    refreshNodes()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = try {
                        val json = com.google.gson.Gson()
                            .fromJson(errorBody, com.google.gson.JsonObject::class.java)
                        json.get("detail")?.asString ?: json.get("error")?.asString
                        ?: "HTTP ${response.code()}"
                    } catch (_: Exception) {
                        "HTTP ${response.code()}"
                    }
                    onError(message)
                }
            } catch (e: Exception) {
                onError(e.message ?: context.getString(R.string.error_unknown))
            }
        }
    }

    fun updateNode(
        id: Int,
        request: NodeUpdateRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.mainApi.updateNode(id, request)
                if (response.isSuccessful) {
                    refreshNodes()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = try {
                        val json = com.google.gson.Gson()
                            .fromJson(errorBody, com.google.gson.JsonObject::class.java)
                        json.get("detail")?.asString ?: json.get("error")?.asString
                        ?: "HTTP ${response.code()}"
                    } catch (_: Exception) {
                        "HTTP ${response.code()}"
                    }
                    onError(message)
                }
            } catch (e: Exception) {
                onError(e.message ?: context.getString(R.string.error_unknown))
            }
        }
    }
}

sealed class NodeUiState {
    object Loading : NodeUiState()
    data class Success(val nodes: List<NodeResponse>) : NodeUiState()
    data class Error(val message: String) : NodeUiState()
}

class TunnelViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow<TunnelUiState>(TunnelUiState.Loading)
    val uiState: StateFlow<TunnelUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun clearData() {
        _uiState.value = TunnelUiState.Loading
    }

    fun refreshTunnels(authViewModel: AuthViewModel? = null) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val tunnels = RetrofitClient.mainApi.getTunnels()
                _uiState.value = TunnelUiState.Success(tunnels)
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 401 || e.code() == 403) {
                    authViewModel?.checkAuth(force = true)
                }
                _uiState.value = TunnelUiState.Error(
                    e.message ?: context.getString(
                        R.string.error_server,
                        e.code()
                    )
                )
            } catch (e: Exception) {
                _uiState.value =
                    TunnelUiState.Error(e.message ?: context.getString(R.string.error_unknown))
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun deleteTunnel(id: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.mainApi.deleteTunnel(id)
                if (response.isSuccessful) {
                    val tunnels = RetrofitClient.mainApi.getTunnels()
                    _uiState.value = TunnelUiState.Success(tunnels)
                    onSuccess()
                } else {
                    onError(context.getString(R.string.error_server, response.code()))
                }
            } catch (e: Exception) {
                onError(e.message ?: context.getString(R.string.error_delete_failed))
            }
        }
    }

    fun createTunnel(
        request: CreateTunnelRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.mainApi.createTunnel(request)
                if (response.isSuccessful) {
                    refreshTunnels()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = try {
                        val json = com.google.gson.Gson()
                            .fromJson(errorBody, com.google.gson.JsonObject::class.java)
                        json.get("detail")?.asString ?: json.get("error")?.asString
                        ?: "HTTP ${response.code()}"
                    } catch (_: Exception) {
                        "HTTP ${response.code()}"
                    }
                    onError(message)
                }
            } catch (e: Exception) {
                onError(e.message ?: context.getString(R.string.error_unknown))
            }
        }
    }

    fun updateTunnel(
        id: String,
        request: TunnelUpdateRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.mainApi.updateTunnel(id, request)
                if (response.isSuccessful) {
                    refreshTunnels()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = try {
                        val json = com.google.gson.Gson()
                            .fromJson(errorBody, com.google.gson.JsonObject::class.java)
                        json.get("detail")?.asString ?: json.get("error")?.asString
                        ?: "HTTP ${response.code()}"
                    } catch (_: Exception) {
                        "HTTP ${response.code()}"
                    }
                    onError(message)
                }
            } catch (e: Exception) {
                onError(e.message ?: context.getString(R.string.error_unknown))
            }
        }
    }
}

sealed class TunnelUiState {
    object Loading : TunnelUiState()
    data class Success(val tunnels: List<TunnelResponse>) : TunnelUiState()
    data class Error(val message: String) : TunnelUiState()
}

enum class NavItem(val titleRes: Int, val icon: ImageVector) {
    Home(R.string.home, Icons.Default.Home),
    Node(R.string.nodes, Icons.Default.Dns),
    Tunnel(R.string.tunnels, Icons.AutoMirrored.Filled.AltRoute),
    Other(R.string.settings, Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    nodeViewModel: NodeViewModel,
    tunnelViewModel: TunnelViewModel,
    authViewModel: AuthViewModel,
    settingsViewModel: SettingsViewModel,
    updateViewModel: UpdateViewModel
) {
    val navItems = NavItem.entries
    val pagerState = rememberPagerState(pageCount = { navItems.size })
    val scope = rememberCoroutineScope()
    
    var showProfile by remember { mutableStateOf(false) }
    val loginState by authViewModel.loginState.collectAsState()
    val user = (loginState as? LoginState.LoggedIn)?.user
    val refreshInterval by settingsViewModel.refreshInterval.collectAsState()

    BackHandler(enabled = showProfile) { showProfile = false }

    if (showProfile && user != null) {
        ProfileScreen(
            user = user,
            onBack = { showProfile = false },
            authViewModel = authViewModel,
            nodeViewModel = nodeViewModel,
            tunnelViewModel = tunnelViewModel
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TaiwanFRP") },
                    navigationIcon = {
                        IconButton(onClick = { showProfile = true }) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://cdn.discordapp.com/avatars/${user?.discordId}/${user?.avatar}.png")
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                                error = painterResource(R.drawable.ic_discord)
                            )
                        }
                    },
                    actions = {}
                )
            },
            bottomBar = {
                NavigationBar {
                    navItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(stringResource(item.titleRes), fontSize = 10.sp) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            val singleExpandMode by settingsViewModel.singleExpandMode.collectAsState()
            val appLanguage by settingsViewModel.languageState.collectAsState()
            Box(Modifier.padding(innerPadding)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    beyondViewportPageCount = 1
                ) { page ->
                    when (navItems[page]) {
                        NavItem.Home -> HomeScreen(
                            user,
                            refreshInterval,
                            authViewModel,
                            onNavigate = { target ->
                                val targetIndex = navItems.indexOf(target)
                                if (targetIndex != -1) {
                                    scope.launch { pagerState.animateScrollToPage(targetIndex) }
                                }
                            })

                        NavItem.Node -> NodeScreen(
                            nodeViewModel,
                            user = user,
                            refreshInterval = refreshInterval,
                            singleExpandMode = singleExpandMode,
                            language = appLanguage
                        )

                        NavItem.Tunnel -> TunnelScreen(
                            tunnelViewModel,
                            user = user,
                            refreshInterval = refreshInterval,
                            singleExpandMode = singleExpandMode,
                            language = appLanguage
                        )

                        NavItem.Other -> OtherScreen(
                            settingsViewModel,
                            authViewModel,
                            nodeViewModel,
                            tunnelViewModel,
                            updateViewModel
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: UserMeResponse?,
    refreshInterval: Int,
    authViewModel: AuthViewModel,
    onNavigate: (NavItem) -> Unit
) {
    val context = LocalContext.current
    var tunnels by remember { mutableStateOf<List<TunnelResponse>>(emptyList()) }
    var nodes by remember { mutableStateOf<List<NodeResponse>>(emptyList()) }
    var status by remember { mutableStateOf<SystemStatusResponse?>(null) }
    val scope = rememberCoroutineScope()
    val isForeground = rememberIsAppInForeground()
    var isRefreshing by remember { mutableStateOf(false) }

    val refresh = {
        scope.launch {
            isRefreshing = true
            try {
                tunnels = RetrofitClient.mainApi.getTunnels()
                nodes = RetrofitClient.mainApi.getNodes()
                status = RetrofitClient.mainApi.getSystemStatus()
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 401 || e.code() == 403) {
                    authViewModel.checkAuth(force = true)
                }
            } catch (_: Exception) {
            } finally {
                isRefreshing = false
            }
        }
    }

    LaunchedEffect(refreshInterval, isForeground) {
        if (isForeground) {
            while (true) {
                refresh()
                delay(refreshInterval * 1000L)
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isRefreshing) {
                item {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = stringResource(
                        R.string.welcome_user,
                        user?.username ?: stringResource(R.string.unknown)
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DashboardCard(
                        title = stringResource(R.string.nodes),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .weight(1f)
                            .height(136.dp),
                        onClick = { onNavigate(NavItem.Node) }
                    ) {
                        Column {
                            Text(
                                stringResource(
                                    R.string.label_active,
                                    nodes.count { it.status == "active" }),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                stringResource(R.string.label_total, nodes.size),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    DashboardCard(
                        title = stringResource(R.string.tunnels),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier
                            .weight(1f)
                            .height(136.dp),
                        onClick = { onNavigate(NavItem.Tunnel) }
                    ) {
                        Column {
                            Text(
                                stringResource(
                                    R.string.label_active,
                                    tunnels.count { it.status == "active" }),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                stringResource(R.string.label_total, tunnels.size),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                DashboardCard(
                    title = stringResource(R.string.system_status),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.heightIn(min = 180.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatusIndicator("API", status?.api)
                        StatusIndicator("Database", status?.database)
                        StatusIndicator("Redis", status?.redis)
                        Text("Version: ${status?.version ?: "N/A"}", fontSize = 12.sp)
                    }
                }
            }

            item {
                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            stringResource(R.string.official_links),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(12.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://taiwanfrp.me")
                                        ).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            R.string.error_open_link,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(
                                    painterResource(R.mipmap.ic_launcher_foreground),
                                    null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Unspecified
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(stringResource(R.string.website))
                            }

                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://discord.gg/ueGFVVHp85")
                                        ).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            R.string.error_open_link,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF5865F2),
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_discord),
                                    null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Discord")
                            }

                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://github.com/TaiwanFRP")
                                        ).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            R.string.error_open_link,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(Icons.Default.Code, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(stringResource(R.string.github))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatusIndicator(label: String, value: String?, isStatus: Boolean = true) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isStatus) {
            Box(
                Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (value == "ok" || value == "up") Color.Green else Color.Red)
            )
            Spacer(Modifier.width(4.dp))
        }
        Text("$label: ${value ?: "Unknown"}", fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeScreen(
    viewModel: NodeViewModel,
    user: UserMeResponse? = null,
    refreshInterval: Int = 60,
    singleExpandMode: Boolean = true,
    isGuest: Boolean = false,
    onBack: (() -> Unit)? = null,
    language: AppLanguage = AppLanguage.Auto
) {
    val uiState by viewModel.uiState.collectAsState()
    var expandedNodeId by remember { mutableStateOf<Int?>(null) }
    val expandedNodeIds = remember { mutableStateMapOf<Int, Boolean>() }
    val isForeground = rememberIsAppInForeground()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    var nodeToDelete by remember { mutableStateOf<NodeResponse?>(null) }
    var showCreateNodeDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(refreshInterval, isForeground) {
        if (isForeground) {
            while (true) {
                viewModel.refreshNodes()
                delay(refreshInterval * 1000L)
            }
        }
    }
    BackHandler(enabled = isGuest) { onBack?.invoke() }

    if (nodeToDelete != null) {
        val node = nodeToDelete!!
        AlertDialog(
            onDismissRequest = { nodeToDelete = null },
            title = { Text(stringResource(R.string.delete_confirm_title)) },
            text = {
                Text(
                    stringResource(
                        R.string.delete_confirm_msg,
                        node.name ?: "",
                        node.id.toString()
                    )
                )
            },
            confirmButton = {
                val deleteFailedPattern = stringResource(R.string.delete_failed)
                TextButton(onClick = {
                    node.id?.let { id ->
                        viewModel.deleteNode(
                            id,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    R.string.delete_success,
                                    Toast.LENGTH_SHORT
                                ).show()
                                nodeToDelete = null
                            },
                            onError = { msg ->
                                Toast.makeText(
                                    context,
                                    deleteFailedPattern.format(msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                                nodeToDelete = null
                            }
                        )
                    }
                }) {
                    Text(
                        stringResource(R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    nodeToDelete = null
                }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    if (showCreateNodeDialog) {
        CreateNodeDialog(
            onDismiss = { showCreateNodeDialog = false },
            viewModel = viewModel,
            language = language
        )
    }

    val pullState = rememberPullToRefreshState()
    val density = LocalDensity.current

    val nodeContent = @Composable { padding: PaddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = pullState,
            onRefresh = { viewModel.refreshNodes() },
            modifier = Modifier.padding(padding),
            indicator = {
                CustomCircularIndicator(
                    state = pullState,
                    isRefreshing = isRefreshing,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 4.dp)
                )
            }
        ) {
            val offset = with(density) { pullState.distanceFraction * 80.dp.toPx() }
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer { translationY = offset }) {
                NodeContent(
                    uiState, user, singleExpandMode, expandedNodeId, expandedNodeIds,
                    isGuest = isGuest,
                    viewModel = viewModel,
                    onToggle = { id ->
                        if (singleExpandMode) {
                            expandedNodeId = if (expandedNodeId == id) null else id
                        } else {
                            val current = expandedNodeIds[id ?: -1] ?: false
                            expandedNodeIds[id ?: -1] = !current
                        }
                    },
                    onDelete = { nodeToDelete = it },
                    onAddClick = { showCreateNodeDialog = true },
                    language = language
                )
            }
        }
    }

    if (isGuest) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.node_status_title)) },
                    navigationIcon = {
                        IconButton(onClick = { onBack?.invoke() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                        }
                    },
                    actions = {}
                )
            }
        ) { padding ->
            nodeContent(padding)
        }
    } else {
        nodeContent(PaddingValues(0.dp))
    }
}

@Composable
fun NodeContent(
    uiState: NodeUiState,
    user: UserMeResponse?,
    singleExpandMode: Boolean,
    expandedNodeId: Int?,
    expandedNodeIds: Map<Int, Boolean>,
    isGuest: Boolean,
    viewModel: NodeViewModel,
    onToggle: (Int?) -> Unit,
    onDelete: (NodeResponse) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    language: AppLanguage = AppLanguage.Auto
) {
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier.fillMaxSize()) {
        val context = LocalContext.current
        var nodeToEdit by remember { mutableStateOf<NodeResponse?>(null) }

        if (nodeToEdit != null) {
            EditNodeDialog(
                node = nodeToEdit!!,
                onDismiss = { nodeToEdit = null },
                viewModel = viewModel,
                language = language
            )
        }

        Column(Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(stringResource(R.string.search_nodes)) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(16.dp),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                )
            )

            Box(Modifier.weight(1f)) {
                when (val state = uiState) {
                    is NodeUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    is NodeUiState.Error -> Text(
                        stringResource(R.string.error_prefix, state.message),
                        Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )

                    is NodeUiState.Success -> {
                        val filteredNodes = state.nodes.filter {
                            it.name?.contains(searchQuery, ignoreCase = true) == true ||
                                    it.host?.contains(searchQuery, ignoreCase = true) == true
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            if (filteredNodes.isEmpty()) {
                                item {
                                    Column(
                                        Modifier
                                            .fillParentMaxSize()
                                            .padding(32.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            stringResource(R.string.no_nodes),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            } else {
                                if (!isGuest && user?.permissions?.contains("node.create") == true && searchQuery.isEmpty()) {
                                    item {
                                        OutlinedButton(
                                            onClick = onAddClick,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp)
                                        ) {
                                            Icon(Icons.Default.Add, null)
                                            Spacer(Modifier.width(8.dp))
                                            Text(stringResource(R.string.add_node))
                                        }
                                    }
                                }
                                items(filteredNodes) { node ->
                                    NodeItem(
                                        node = node,
                                        user = user,
                                        isExpanded = if (singleExpandMode) expandedNodeId == node.id else expandedNodeIds[node.id
                                            ?: -1] == true,
                                        onToggle = { onToggle(node.id) },
                                        onDelete = { onDelete(node) },
                                        onEdit = { nodeToEdit = node },
                                        singleExpandMode = singleExpandMode
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NodeItem(
    node: NodeResponse,
    user: UserMeResponse?,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    singleExpandMode: Boolean
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggle() },
        elevation = CardDefaults.cardElevation(if (isExpanded) 4.dp else 1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Dns,
                    null,
                    tint = if (node.status == "active") Color(0xFF008000) else Color.Gray
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        node.name ?: stringResource(R.string.unknown),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("${node.host}", style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    text = when (node.status) {
                        "draft" -> stringResource(R.string.status_draft)
                        "active" -> stringResource(R.string.status_active)
                        else -> stringResource(R.string.status_inactive)
                    },
                    fontWeight = FontWeight.Bold,
                    color = if (node.status == "active") Color(0xFF008000) else Color.Red
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) stringResource(R.string.back) else stringResource(
                        R.string.refresh
                    ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(Modifier.alpha(0.3f))
                    Spacer(Modifier.height(8.dp))
                    DetailRow(
                        stringResource(R.string.id),
                        node.id?.toString() ?: "N/A",
                        copyMessage = stringResource(R.string.copied_id)
                    )

                    if (false) { // 暫時隱藏但保留程式碼
                        DetailRow(
                            stringResource(R.string.label_enabled_status),
                            when (node.status) {
                                "active" -> stringResource(R.string.active)
                                "draft" -> stringResource(R.string.status_draft)
                                else -> stringResource(R.string.disabled)
                            },
                            isCopyable = false,
                            valueColor = if (node.status == "active") Color(0xFF008000) else Color.Red
                        )
                        DetailRow(
                            stringResource(R.string.label_current_status),
                            if (node.status == "active") stringResource(R.string.status_active) else stringResource(
                                R.string.status_inactive
                            ),
                            isCopyable = false,
                            valueColor = if (node.status == "active") Color(0xFF008000) else Color.Red
                        )
                    }

                    DetailRow(
                        stringResource(R.string.host),
                        node.host ?: "N/A",
                        copyMessage = stringResource(R.string.copied_host)
                    )
                    DetailRow(
                        stringResource(R.string.port_range),
                        "${node.portStart} - ${node.portEnd}",
                        copyMessage = stringResource(R.string.copied_port)
                    )
                    DetailRow(
                        stringResource(R.string.is_public),
                        if (node.isPublic == true) stringResource(R.string.yes) else stringResource(
                            R.string.no
                        ),
                        isCopyable = false
                    )
                    if (!node.description.isNullOrBlank()) {
                        DetailRow(
                            stringResource(R.string.description),
                            node.description,
                            copyMessage = stringResource(R.string.copied_desc)
                        )
                    }

                    if (node.ownerId == user?.id) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = onEdit) {
                                Icon(Icons.Default.Edit, null)
                                Spacer(Modifier.width(4.dp))
                                Text(stringResource(R.string.edit))
                            }
                            Spacer(Modifier.width(8.dp))
                            TextButton(onClick = onDelete) {
                                Icon(
                                    Icons.Default.Delete,
                                    null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    stringResource(R.string.delete),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TunnelScreen(
    viewModel: TunnelViewModel,
    user: UserMeResponse? = null,
    refreshInterval: Int = 60,
    singleExpandMode: Boolean = true,
    language: AppLanguage = AppLanguage.Auto
) {
    val uiState by viewModel.uiState.collectAsState()
    var expandedTunnelId by remember { mutableStateOf<String?>(null) }
    val expandedTunnelIds = remember { mutableStateMapOf<String, Boolean>() }
    val isForeground = rememberIsAppInForeground()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    var tunnelToDelete by remember { mutableStateOf<TunnelResponse?>(null) }
    var tunnelToEdit by remember { mutableStateOf<TunnelResponse?>(null) }
    val context = LocalContext.current

    LaunchedEffect(refreshInterval, isForeground) {
        if (isForeground) {
            while (true) {
                viewModel.refreshTunnels()
                delay(refreshInterval * 1000L)
            }
        }
    }

    var showCreateTunnelDialog by remember { mutableStateOf(false) }
    if (showCreateTunnelDialog) {
        CreateTunnelDialog(
            onDismiss = { showCreateTunnelDialog = false },
            viewModel = viewModel,
            language = language
        )
    }

    val pullState = rememberPullToRefreshState()
    val density = LocalDensity.current
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullState,
        onRefresh = { viewModel.refreshTunnels() },
        indicator = {
            CustomCircularIndicator(
                state = pullState,
                isRefreshing = isRefreshing,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 4.dp)
            )
        }
    ) {
        val offset = with(density) { pullState.distanceFraction * 80.dp.toPx() }
        Box(
            Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = offset }) {
            tunnelToEdit?.let { tunnel ->
                EditTunnelDialog(
                    tunnel = tunnel,
                    onDismiss = { tunnelToEdit = null },
                    viewModel = viewModel,
                    language = language
                )
            }
            tunnelToDelete?.let { tunnel ->
                AlertDialog(
                    onDismissRequest = { tunnelToDelete = null },
                    title = { Text(stringResource(R.string.delete_confirm_title)) },
                    text = {
                        Text(
                            stringResource(
                                R.string.delete_confirm_msg,
                                tunnel.name ?: "",
                                tunnel.id.toString()
                            )
                        )
                    },
                    confirmButton = {
                        val deleteFailedPattern = stringResource(R.string.delete_failed)
                        TextButton(onClick = {
                            tunnel.id?.let { id ->
                                viewModel.deleteTunnel(
                                    id,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            R.string.delete_success,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        tunnelToDelete = null
                                    },
                                    onError = { msg ->
                                        Toast.makeText(
                                            context,
                                            deleteFailedPattern.format(msg),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        tunnelToDelete = null
                                    }
                                )
                            }
                        }) {
                            Text(
                                stringResource(R.string.delete),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            tunnelToDelete = null
                        }) { Text(stringResource(R.string.cancel)) }
                    }
                )
            }

            when (val state = uiState) {
                is TunnelUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is TunnelUiState.Error -> Text(
                    stringResource(R.string.error_prefix, state.message),
                    Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )

                is TunnelUiState.Success -> {
                    val tunnelCount = state.tunnels.size
                    val isLimitReached = tunnelCount >= 3

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        item {
                            Text(
                                text = stringResource(R.string.label_tunnel_count, tunnelCount),
                                modifier = Modifier.padding(vertical = 8.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isLimitReached) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }

                        if (state.tunnels.isEmpty()) {
                            item {
                                Column(
                                    Modifier
                                        .fillParentMaxSize()
                                        .padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        stringResource(R.string.no_tunnels),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(Modifier.height(16.dp))
                                    Button(
                                        onClick = { showCreateTunnelDialog = true },
                                        enabled = !isLimitReached
                                    ) {
                                        Icon(Icons.Default.Add, null)
                                        Spacer(Modifier.width(8.dp))
                                        Text(stringResource(R.string.add_tunnel))
                                    }
                                }
                            }
                        } else {
                            item {
                                OutlinedButton(
                                    onClick = { showCreateTunnelDialog = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    enabled = !isLimitReached
                                ) {
                                    Icon(Icons.Default.Add, null)
                                    Spacer(Modifier.width(8.dp))
                                    Text(stringResource(R.string.add_tunnel))
                                }
                            }
                            items(state.tunnels) { tunnel ->
                                TunnelItem(
                                    tunnel = tunnel,
                                    user = user,
                                    isExpanded = if (singleExpandMode) expandedTunnelId == tunnel.id else expandedTunnelIds[tunnel.id
                                        ?: ""] == true,
                                    onToggle = {
                                        if (singleExpandMode) {
                                            expandedTunnelId =
                                                if (expandedTunnelId == tunnel.id) null else tunnel.id
                                        } else {
                                            val current =
                                                expandedTunnelIds[tunnel.id ?: ""] ?: false
                                            expandedTunnelIds[tunnel.id ?: ""] = !current
                                        }
                                    },
                                    onDelete = { tunnelToDelete = tunnel },
                                    onEdit = { tunnelToEdit = tunnel },
                                    singleExpandMode = singleExpandMode
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TunnelItem(
    tunnel: TunnelResponse,
    user: UserMeResponse?,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    singleExpandMode: Boolean
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onToggle() },
        elevation = CardDefaults.cardElevation(if (isExpanded) 4.dp else 1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.AutoMirrored.Filled.AltRoute,
                    null,
                    tint = if (tunnel.status == "active") Color(0xFF008000) else Color.Gray
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        tunnel.name ?: stringResource(R.string.unknown),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "${tunnel.localIp}:${tunnel.localPort}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = when (tunnel.status) {
                        "draft" -> stringResource(R.string.status_draft)
                        "active" -> stringResource(R.string.status_active)
                        else -> stringResource(R.string.status_inactive)
                    },
                    fontWeight = FontWeight.Bold,
                    color = if (tunnel.status == "active") Color(0xFF008000) else Color.Red
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) stringResource(R.string.back) else stringResource(
                        R.string.refresh
                    ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(Modifier.alpha(0.3f))
                    Spacer(Modifier.height(8.dp))
                    DetailRow(
                        stringResource(R.string.id),
                        tunnel.id ?: "N/A",
                        copyMessage = stringResource(R.string.copied_id)
                    )

                    if (false) { // 暫時隱藏但保留程式碼
                        DetailRow(
                            stringResource(R.string.label_current_status),
                            if (tunnel.status == "active") stringResource(R.string.status_active) else stringResource(
                                R.string.status_inactive
                            ),
                            isCopyable = false,
                            valueColor = if (tunnel.status == "active") Color(0xFF008000) else Color.Red
                        )
                    }

                    DetailRow(
                        stringResource(R.string.protocol),
                        tunnel.protocol ?: "N/A",
                        copyMessage = stringResource(R.string.copied_protocol)
                    )
                    DetailRow(
                        stringResource(R.string.local),
                        "${tunnel.localIp}:${tunnel.localPort}",
                        copyMessage = stringResource(R.string.copied_local)
                    )
                    DetailRow(
                        stringResource(R.string.remote_port),
                        tunnel.remotePort?.toString() ?: "Auto",
                        copyMessage = stringResource(R.string.copied_port)
                    )
                    DetailRow(
                        stringResource(R.string.kcp),
                        if (tunnel.isKcpEnabled == true) stringResource(R.string.enabled) else stringResource(
                            R.string.disabled
                        ),
                        isCopyable = false
                    )
                    DetailRow(
                        stringResource(R.string.proxy_v2),
                        if (tunnel.isProxyProtocolV2Enabled == true) stringResource(R.string.enabled) else stringResource(
                            R.string.disabled
                        ),
                        isCopyable = false
                    )
                    if (!tunnel.description.isNullOrBlank()) {
                        DetailRow(
                            stringResource(R.string.description),
                            tunnel.description,
                            copyMessage = stringResource(R.string.copied_desc)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = onEdit) {
                            Icon(Icons.Default.Edit, null)
                            Spacer(Modifier.width(4.dp))
                            Text(stringResource(R.string.edit))
                        }
                        Spacer(Modifier.width(8.dp))
                        TextButton(onClick = onDelete) {
                            Icon(
                                Icons.Default.Delete,
                                null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                stringResource(R.string.delete),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    copyMessage: String = "",
    isCopyable: Boolean = true,
    valueColor: Color = Color.Unspecified
) {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .then(
                if (isCopyable) {
                    Modifier.clickable {
                        clipboard.setText(AnnotatedString(value))
                        if (copyMessage.isNotEmpty()) {
                            Toast.makeText(context, copyMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else Modifier
            )
            .padding(vertical = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (valueColor == Color.Unspecified) MaterialTheme.colorScheme.onSurface else valueColor,
                    fontWeight = if (valueColor != Color.Unspecified) FontWeight.Bold else FontWeight.Normal
                )
            }
            if (isCopyable) {
                Icon(
                    Icons.Default.ContentCopy,
                    null,
                    Modifier
                        .fillMaxHeight()
                        .padding(start = 12.dp)
                        .size(20.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FullScreenForm(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = stringResource(R.string.confirm),
    confirmEnabled: Boolean = true,
    language: AppLanguage = AppLanguage.Auto,
    content: @Composable ColumnScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        LocaleWrapper(language) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(title) },
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    stringResource(R.string.back)
                                )
                            }
                        },
                        actions = {
                            TextButton(
                                onClick = onConfirm,
                                enabled = confirmEnabled
                            ) {
                                Text(confirmText)
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            })
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .imePadding()
                            .navigationBarsPadding()
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                })
                            }
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun InitScreen(onInitComplete: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onInitComplete()
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("TaiwanFRP", fontSize = 45.sp, color = MaterialTheme.colorScheme.primary)
        Text(
            stringResource(R.string.login_subtitle),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(32.dp))
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    authViewModel: AuthViewModel,
    onEnterGuestMode: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val loginState by authViewModel.loginState.collectAsState()
    var showLoginWebView by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "TaiwanFRP",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                stringResource(R.string.login_subtitle),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(48.dp))

            if (loginState is LoginState.Error) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        "⚠️ ${(loginState as LoginState.Error).message}",
                        Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (loginState is LoginState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { showLoginWebView = true },
                    modifier = Modifier
                        .width(380.dp)
                        .height(56.dp)
                ) {
                    Text(stringResource(R.string.login_with_discord))
                    Spacer(Modifier.width(8.dp))
                    Icon(painterResource(R.drawable.ic_discord), null, Modifier.size(24.dp))
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onEnterGuestMode,
                    modifier = Modifier
                        .width(380.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(stringResource(R.string.view_nodes_guest))
                }
            }
        }

        AnimatedVisibility(
            visible = showLoginWebView,
            enter = androidx.compose.animation.slideInHorizontally(initialOffsetX = { it }),
            exit = androidx.compose.animation.slideOutHorizontally(targetOffsetX = { it })
        ) {
            DiscordLoginWebView(
                onClose = { showLoginWebView = false },
                onSuccess = {
                    showLoginWebView = false
                    authViewModel.checkAuth(force = true)
                    onLoginSuccess()
                },
                onError = {
                    showLoginWebView = false
                    authViewModel.setError(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelector(
    currentTheme: AppTheme,
    options: List<AppTheme>,
    onThemeChange: (AppTheme) -> Unit
) {
    FlowRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { theme ->
            FilterChip(
                selected = currentTheme == theme,
                onClick = { onThemeChange(theme) },
                label = {
                    Text(
                        when (theme) {
                            AppTheme.Light -> stringResource(R.string.theme_light)
                            AppTheme.Dark -> stringResource(R.string.theme_dark)
                            AppTheme.System -> stringResource(R.string.theme_system)
                            AppTheme.Blue -> stringResource(R.string.theme_blue)
                            AppTheme.BlueLight -> stringResource(R.string.theme_blue_light)
                            AppTheme.Green -> stringResource(R.string.theme_green)
                            AppTheme.GreenLight -> stringResource(R.string.theme_green_light)
                            AppTheme.Oled -> stringResource(R.string.theme_oled)
                            AppTheme.White -> stringResource(R.string.theme_white)
                        },
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(currentLanguage: AppLanguage, onLanguageChange: (AppLanguage) -> Unit) {
    val options = AppLanguage.entries
    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, language ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onLanguageChange(language) },
                selected = currentLanguage == language,
                label = {
                    Text(
                        when (language) {
                            AppLanguage.Auto -> stringResource(R.string.lang_auto)
                            AppLanguage.Zh -> stringResource(R.string.lang_zh)
                            AppLanguage.En -> stringResource(R.string.lang_en)
                        },
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (onClick != null) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForwardIos,
                        null,
                        Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            content()
        }
    }
}

@Composable
fun OtherScreen(
    settingsViewModel: SettingsViewModel,
    authViewModel: AuthViewModel,
    nodeViewModel: NodeViewModel,
    tunnelViewModel: TunnelViewModel,
    updateViewModel: UpdateViewModel
) {
    val appTheme by settingsViewModel.themeState.collectAsState()
    val appLanguage by settingsViewModel.languageState.collectAsState()
    val refreshInterval by settingsViewModel.refreshInterval.collectAsState()
    val singleExpand by settingsViewModel.singleExpandMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            stringResource(R.string.theme_setting),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            stringResource(R.string.theme_group_basic),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        ThemeSelector(
            appTheme,
            listOf(AppTheme.Light, AppTheme.Dark, AppTheme.System)
        ) { settingsViewModel.setTheme(it) }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.theme_group_dark),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        ThemeSelector(
            appTheme,
            listOf(AppTheme.Blue, AppTheme.Green, AppTheme.Oled)
        ) { settingsViewModel.setTheme(it) }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.theme_group_light),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        ThemeSelector(
            appTheme,
            listOf(AppTheme.BlueLight, AppTheme.GreenLight, AppTheme.White)
        ) { settingsViewModel.setTheme(it) }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            stringResource(R.string.language_setting),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        LanguageSelector(appLanguage, onLanguageChange = { settingsViewModel.setLanguage(it) })

        Spacer(modifier = Modifier.height(32.dp))
        var text by remember(refreshInterval) {
            mutableStateOf(refreshInterval.toString())
        }

        Text(
            stringResource(R.string.refresh_interval),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.all(Char::isDigit)) {
                    text = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        val value = text.toIntOrNull()?.coerceAtLeast(10) ?: 10
                        text = value.toString()
                        settingsViewModel.setRefreshInterval(value)
                    }
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    val value = text.toIntOrNull()?.coerceAtLeast(10) ?: 10
                    text = value.toString()
                    settingsViewModel.setRefreshInterval(value)
                }
            ),
            suffix = { Text("s") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(R.string.single_expand),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            Switch(
                checked = singleExpand,
                onCheckedChange = { settingsViewModel.setSingleExpandMode(it) })
        }

        val context = LocalContext.current
        val cameraManager =
            remember { context.getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        val cameraId = remember {
            try {
                cameraManager.cameraIdList.firstOrNull()
            } catch (e: Exception) {
                null
            }
        }
        var isFlashlightOn by remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.height(24.dp))
        val operationFailedPattern = stringResource(R.string.error_operation_failed)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(R.string.flashlight),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            Switch(
                checked = isFlashlightOn,
                onCheckedChange = { isOn ->
                    try {
                        cameraId?.let {
                            cameraManager.setTorchMode(it, isOn)
                            isFlashlightOn = isOn
                        } ?: Toast.makeText(
                            context,
                            R.string.error_no_flashlight,
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            operationFailedPattern.format(e.message ?: ""),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val versionName = try {
                    context.packageManager.getPackageInfo(context.packageName, 0).versionName
                } catch (e: Exception) {
                    "0.0.0"
                }
                updateViewModel.checkUpdate(versionName ?: "0.0.0", manual = true)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Icon(Icons.Default.Refresh, null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.check_update))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                try {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, R.string.error_open_link, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(Icons.Default.PlayArrow, null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.watch_demo))
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.version, "V2.6"),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = stringResource(R.string.developer),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun AccountStatusScreen(
    state: LoginState,
    authViewModel: AuthViewModel,
    nodeViewModel: NodeViewModel? = null,
    tunnelViewModel: TunnelViewModel? = null,
    onBackToWelcome: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val (title, color) = when (state) {
        LoginState.Suspended -> stringResource(R.string.account_suspended) to Color(0xFFF1C40F) // Yellow
        LoginState.Banned -> stringResource(R.string.account_banned) to MaterialTheme.colorScheme.error
        LoginState.Deleted -> stringResource(R.string.account_deleted) to MaterialTheme.colorScheme.error
        else -> "" to MaterialTheme.colorScheme.error
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            authViewModel = authViewModel,
            onLogout = {
                nodeViewModel?.clearData()
                tunnelViewModel?.clearData()
                onBackToWelcome()
            }
        )
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("TaiwanFRP") },
                navigationIcon = {
                    IconButton(onClick = onBackToWelcome) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    if (state == LoginState.Suspended) Color.Transparent else color.copy(
                        alpha = 0.1f
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Placeholder for 200x200
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            RoundedCornerShape(20.dp)
                        )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = if (state == LoginState.Deleted) 28.sp else 45.sp,
                    lineHeight = if (state == LoginState.Deleted) 36.sp else 52.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = if (state == LoginState.Suspended) MaterialTheme.colorScheme.onBackground else color
                )
                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        try {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://discord.gg/ueGFVVHp85")
                            ).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, R.string.error_open_link, Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Icon(painterResource(R.drawable.ic_discord), null, Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(stringResource(R.string.join_discord))
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(stringResource(R.string.logout))
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    authViewModel: AuthViewModel,
    onLogout: () -> Unit = {}
) {
    var clearDiscordSession by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = { Text(stringResource(R.string.logout_confirm)) },
        text = {
            Column {
                Text(stringResource(R.string.logout_message))
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { clearDiscordSession = !clearDiscordSession }
                ) {
                    Checkbox(
                        checked = clearDiscordSession,
                        onCheckedChange = { clearDiscordSession = it }
                    )
                    Text(
                        stringResource(R.string.clear_discord),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            val context = LocalContext.current
            TextButton(
                onClick = {
                    if (clearDiscordSession) {
                        val cookieManager = CookieManager.getInstance()
                        cookieManager.removeAllCookies(null)
                        cookieManager.flush()

                        WebStorage.getInstance().deleteAllData()
                        WebView(context).apply {
                            clearCache(true)
                            clearFormData()
                            clearHistory()
                        }
                    }
                    onLogout()
                    authViewModel.logout()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.confirm), color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun LocaleWrapper(language: AppLanguage, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = when (language) {
        AppLanguage.Zh -> java.util.Locale.TAIWAN
        AppLanguage.En -> java.util.Locale.ENGLISH
        else -> configuration.locales.get(0)
    }

    val newConfig = android.content.res.Configuration(configuration)
    newConfig.setLocale(locale)
    val localizedContext = context.createConfigurationContext(newConfig)

    CompositionLocalProvider(LocalContext provides localizedContext) {
        content()
    }
}

@Composable
fun CreateNodeDialog(
    onDismiss: () -> Unit,
    viewModel: NodeViewModel,
    language: AppLanguage = AppLanguage.Auto
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var portStart by remember { mutableStateOf("") }
    var portEnd by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }
    var showErrors by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val createFailedPattern = stringResource(R.string.create_failed)
    val errorRequired = stringResource(R.string.error_required)

    FullScreenForm(
        title = stringResource(R.string.create_node_title),
        onDismiss = onDismiss,
        language = language,
        onConfirm = {
            if (name.isBlank() || host.isBlank() || portStart.isBlank() || portEnd.isBlank()) {
                showErrors = true
                return@FullScreenForm
            }
            val request = CreateNodeRequest(
                name,
                description,
                host,
                portStart.toIntOrNull() ?: 0,
                portEnd.toIntOrNull() ?: 0,
                isPublic
            )
            viewModel.createNode(request, onSuccess = {
                Toast.makeText(context, R.string.create_success, Toast.LENGTH_SHORT).show()
                onDismiss()
            }, onError = {
                Toast.makeText(context, createFailedPattern.format(it), Toast.LENGTH_SHORT).show()
            })
        }
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.field_name)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && name.isBlank(),
            supportingText = if (showErrors && name.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.field_description)) },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = host,
            onValueChange = { host = it },
            label = { Text(stringResource(R.string.field_host)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && host.isBlank(),
            supportingText = if (showErrors && host.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = portStart,
            onValueChange = { portStart = it },
            label = { Text(stringResource(R.string.field_port_start)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && portStart.isBlank(),
            supportingText = if (showErrors && portStart.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = portEnd,
            onValueChange = { portEnd = it },
            label = { Text(stringResource(R.string.field_port_end)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && portEnd.isBlank(),
            supportingText = if (showErrors && portEnd.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.field_is_public), Modifier.weight(1f))
            Switch(checked = isPublic, onCheckedChange = { isPublic = it })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTunnelDialog(
    onDismiss: () -> Unit,
    viewModel: TunnelViewModel,
    language: AppLanguage = AppLanguage.Auto
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var localIp by remember { mutableStateOf("127.0.0.1") }
    var localPort by remember { mutableStateOf("") }
    var remotePort by remember { mutableStateOf("") }
    var remotePortError by remember { mutableStateOf(false) }
    var isKcp by remember { mutableStateOf(true) }
    var isProxyV2 by remember { mutableStateOf(false) }
    var selectedProtocol by remember { mutableStateOf("tcp") }
    var showErrors by remember { mutableStateOf(false) }
    val protocols = listOf("tcp", "udp")

    var nodeUiState by remember { mutableStateOf<NodeUiState>(NodeUiState.Loading) }
    var selectedNode by remember { mutableStateOf<NodeResponse?>(null) }
    var expandedNodeMenu by remember { mutableStateOf(false) }
    var expandedProtocolMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val createFailedPattern = stringResource(R.string.create_failed)
    val portRangePattern = stringResource(R.string.field_port_range)
    val errorRequired = stringResource(R.string.error_required)
    val errorSelectionRequired = stringResource(R.string.error_selection_required)

    LaunchedEffect(Unit) {
        try {
            val nodes = RetrofitClient.mainApi.getNodes()
            nodeUiState = NodeUiState.Success(nodes.filter { it.status == "active" })
        } catch (e: Exception) {
            nodeUiState = NodeUiState.Error(e.message ?: "Failed to load nodes")
        }
    }

    FullScreenForm(
        title = stringResource(R.string.create_tunnel_title),
        onDismiss = onDismiss,
        language = language,
        onConfirm = {
            val nodeId = selectedNode?.id
            if (name.isBlank() || nodeId == null || localIp.isBlank() || localPort.isBlank() || remotePort.isBlank()) {
                showErrors = true
                return@FullScreenForm
            }
            if ((selectedProtocol == "tcp" || selectedProtocol == "udp") && remotePort.isBlank()) {
                Toast.makeText(context, "TCP/UDP 協議必須填寫遠端埠號", Toast.LENGTH_SHORT).show()
                return@FullScreenForm
            }
            val request = CreateTunnelRequest(
                name, description, nodeId, selectedProtocol, localIp,
                localPort.toIntOrNull() ?: 0,
                remotePort.toIntOrNull()?.let { if (it == 0) null else it },
                isKcp, isProxyV2
            )
            viewModel.createTunnel(request, onSuccess = {
                Toast.makeText(context, R.string.create_success, Toast.LENGTH_SHORT).show()
                onDismiss()
            }, onError = {
                if (it.contains("remote_port is already in use", ignoreCase = true)) {
                    remotePortError = true
                } else {
                    Toast.makeText(context, createFailedPattern.format(it), Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.field_name)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && name.isBlank(),
            supportingText = if (showErrors && name.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.field_description)) },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expandedNodeMenu,
            onExpandedChange = { expandedNodeMenu = it }
        ) {
            OutlinedTextField(
                value = selectedNode?.let { "${it.name} (ID: ${it.id})" }
                    ?: stringResource(R.string.node_selection_hint),
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.field_node)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedNodeMenu) },
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth(),
                isError = showErrors && selectedNode == null,
                supportingText = if (showErrors && selectedNode == null) {
                    { Text(errorSelectionRequired) }
                } else null
            )
            ExposedDropdownMenu(
                expanded = expandedNodeMenu,
                onDismissRequest = { expandedNodeMenu = false }) {
                if (nodeUiState is NodeUiState.Success) {
                    (nodeUiState as NodeUiState.Success).nodes.forEach { node ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        "${node.name} | ID: ${node.id}" ?: "Unknown",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "description: ${node.description} | Host: ${node.host}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            onClick = {
                                selectedNode = node
                                expandedNodeMenu = false
                            }
                        )
                    }
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedProtocolMenu,
            onExpandedChange = { expandedProtocolMenu = it }
        ) {
            OutlinedTextField(
                value = selectedProtocol,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.field_protocol)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProtocolMenu) },
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedProtocolMenu,
                onDismissRequest = { expandedProtocolMenu = false }) {
                protocols.forEach { proto ->
                    DropdownMenuItem(
                        text = { Text(proto) },
                        onClick = { selectedProtocol = proto; expandedProtocolMenu = false })
                }
            }
        }

        OutlinedTextField(
            value = localIp,
            onValueChange = { localIp = it },
            label = { Text(stringResource(R.string.field_local_ip)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && localIp.isBlank(),
            supportingText = if (showErrors && localIp.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = localPort,
            onValueChange = { localPort = it },
            label = { Text(stringResource(R.string.field_local_port)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && localPort.isBlank(),
            supportingText = if (showErrors && localPort.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = remotePort,
            onValueChange = {
                remotePort = it
                remotePortError = false
            },
            label = { Text(stringResource(R.string.field_remote_port)) },
            placeholder = {
                selectedNode?.let {
                    Text(
                        portRangePattern.format(
                            it.portStart ?: 0,
                            it.portEnd ?: 0
                        )
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = remotePortError || (showErrors && remotePort.isBlank()),
            supportingText = {
                if (remotePortError) {
                    Text(stringResource(R.string.error_remote_port_in_use))
                } else if (showErrors && remotePort.isBlank()) {
                    Text(errorRequired)
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    selectedNode?.let { node ->
                        val start = node.portStart ?: 0
                        val end = node.portEnd ?: 0
                        if (start <= end && end > 0) {
                            remotePort = (start..end).random().toString()
                            remotePortError = false
                        }
                    }
                }) {
                    Icon(Icons.Default.Shuffle, stringResource(R.string.random_port))
                }
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.kcp), Modifier.weight(1f))
            Switch(checked = isKcp, onCheckedChange = { isKcp = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.proxy_v2), Modifier.weight(1f))
            Switch(checked = isProxyV2, onCheckedChange = { isProxyV2 = it })
        }
    }
}

@Composable
fun EditNodeDialog(
    node: NodeResponse,
    onDismiss: () -> Unit,
    viewModel: NodeViewModel,
    language: AppLanguage = AppLanguage.Auto
) {
    var name by remember { mutableStateOf(node.name ?: "") }
    var description by remember { mutableStateOf(node.description ?: "") }
    var host by remember { mutableStateOf(node.host ?: "") }
    var portStart by remember { mutableStateOf(node.portStart?.toString() ?: "") }
    var portEnd by remember { mutableStateOf(node.portEnd?.toString() ?: "") }
    var isPublic by remember { mutableStateOf(node.isPublic ?: true) }
    var showErrors by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val updateFailedPattern = stringResource(R.string.update_failed)
    val errorRequired = stringResource(R.string.error_required)

    val isChanged by remember(name, description, host, portStart, portEnd, isPublic) {
        androidx.compose.runtime.derivedStateOf {
            name != (node.name ?: "") ||
                    description != (node.description ?: "") ||
                    host != (node.host ?: "") ||
                    portStart != (node.portStart?.toString() ?: "") ||
                    portEnd != (node.portEnd?.toString() ?: "") ||
                    isPublic != (node.isPublic ?: true)
        }
    }

    FullScreenForm(
        title = stringResource(R.string.edit_node_title),
        onDismiss = onDismiss,
        confirmEnabled = isChanged,
        language = language,
        onConfirm = {
            if (name.isBlank() || host.isBlank() || portStart.isBlank() || portEnd.isBlank()) {
                showErrors = true
                return@FullScreenForm
            }
            val request = NodeUpdateRequest(
                status = node.status,
                isPublic = isPublic,
                name = name,
                description = description,
                host = host,
                portStart = portStart.toIntOrNull(),
                portEnd = portEnd.toIntOrNull()
            )
            node.id?.let { id ->
                viewModel.updateNode(id, request, onSuccess = {
                    Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show()
                    onDismiss()
                }, onError = {
                    Toast.makeText(context, updateFailedPattern.format(it), Toast.LENGTH_SHORT)
                        .show()
                })
            }
        }
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.field_name)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && name.isBlank(),
            supportingText = if (showErrors && name.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.field_description)) },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = host,
            onValueChange = { host = it },
            label = { Text(stringResource(R.string.field_host)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && host.isBlank(),
            supportingText = if (showErrors && host.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = portStart,
            onValueChange = { portStart = it },
            label = { Text(stringResource(R.string.field_port_start)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && portStart.isBlank(),
            supportingText = if (showErrors && portStart.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = portEnd,
            onValueChange = { portEnd = it },
            label = { Text(stringResource(R.string.field_port_end)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && portEnd.isBlank(),
            supportingText = if (showErrors && portEnd.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.field_is_public), Modifier.weight(1f))
            Switch(checked = isPublic, onCheckedChange = { isPublic = it })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTunnelDialog(
    tunnel: TunnelResponse,
    onDismiss: () -> Unit,
    viewModel: TunnelViewModel,
    language: AppLanguage = AppLanguage.Auto
) {
    var name by remember { mutableStateOf(tunnel.name ?: "") }
    var description by remember { mutableStateOf(tunnel.description ?: "") }
    var localIp by remember { mutableStateOf(tunnel.localIp ?: "127.0.0.1") }
    var localPort by remember { mutableStateOf(tunnel.localPort?.toString() ?: "") }
    var remotePort by remember { mutableStateOf(tunnel.remotePort?.toString() ?: "") }
    var remotePortError by remember { mutableStateOf(false) }
    var isKcp by remember { mutableStateOf(tunnel.isKcpEnabled ?: false) }
    var isProxyV2 by remember { mutableStateOf(tunnel.isProxyProtocolV2Enabled ?: false) }
    var selectedProtocol by remember { mutableStateOf(tunnel.protocol ?: "tcp") }
    var showErrors by remember { mutableStateOf(false) }
    val protocols = listOf("tcp", "udp")

    var nodeUiState by remember { mutableStateOf<NodeUiState>(NodeUiState.Loading) }
    var selectedNodeId by remember { mutableStateOf(tunnel.nodeId) }
    var expandedNodeMenu by remember { mutableStateOf(false) }
    var expandedProtocolMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val updateFailedPattern = stringResource(R.string.update_failed)
    val portRangePattern = stringResource(R.string.field_port_range)
    val errorRequired = stringResource(R.string.error_required)
    val errorSelectionRequired = stringResource(R.string.error_selection_required)

    val isChanged by remember(
        name,
        description,
        localIp,
        localPort,
        remotePort,
        isKcp,
        isProxyV2,
        selectedProtocol,
        selectedNodeId
    ) {
        androidx.compose.runtime.derivedStateOf {
            name != (tunnel.name ?: "") ||
                    description != (tunnel.description ?: "") ||
                    localIp != (tunnel.localIp ?: "127.0.0.1") ||
                    localPort != (tunnel.localPort?.toString() ?: "") ||
                    remotePort != (tunnel.remotePort?.toString() ?: "") ||
                    isKcp != (tunnel.isKcpEnabled ?: false) ||
                    isProxyV2 != (tunnel.isProxyProtocolV2Enabled ?: false) ||
                    selectedProtocol != (tunnel.protocol ?: "tcp") ||
                    selectedNodeId != tunnel.nodeId
        }
    }

    LaunchedEffect(Unit) {
        try {
            val nodes = RetrofitClient.mainApi.getNodes()
            nodeUiState = NodeUiState.Success(nodes.filter { it.status == "active" })
        } catch (e: Exception) {
            nodeUiState = NodeUiState.Error(e.message ?: "Failed to load nodes")
        }
    }

    FullScreenForm(
        title = stringResource(R.string.edit_tunnel_title),
        onDismiss = onDismiss,
        confirmEnabled = isChanged,
        language = language,
        onConfirm = {
            val nodeId = selectedNodeId
            if (name.isBlank() || nodeId == null || localIp.isBlank() || localPort.isBlank() || remotePort.isBlank()) {
                showErrors = true
                return@FullScreenForm
            }
            val request = TunnelUpdateRequest(
                name = name,
                description = description,
                nodeId = nodeId,
                protocol = selectedProtocol,
                localIp = localIp,
                localPort = localPort.toIntOrNull(),
                remotePort = remotePort.toIntOrNull(),
                isKcpEnabled = isKcp,
                isProxyProtocolV2Enabled = isProxyV2
            )
            tunnel.id?.let { id ->
                viewModel.updateTunnel(id, request, onSuccess = {
                    Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show()
                    onDismiss()
                }, onError = {
                    if (it.contains("remote_port is already in use", ignoreCase = true)) {
                        remotePortError = true
                    } else {
                        Toast.makeText(context, updateFailedPattern.format(it), Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        }
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.field_name)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && name.isBlank(),
            supportingText = if (showErrors && name.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.field_description)) },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expandedNodeMenu,
            onExpandedChange = { expandedNodeMenu = it }
        ) {
            val selectedNode =
                (nodeUiState as? NodeUiState.Success)?.nodes?.find { it.id == selectedNodeId }
            OutlinedTextField(
                value = selectedNode?.let { "${it.name} (ID: ${it.id})" } ?: "ID: $selectedNodeId",
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.field_node)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedNodeMenu) },
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth(),
                isError = showErrors && selectedNodeId == null,
                supportingText = if (showErrors && selectedNodeId == null) {
                    { Text(errorSelectionRequired) }
                } else null
            )
            ExposedDropdownMenu(
                expanded = expandedNodeMenu,
                onDismissRequest = { expandedNodeMenu = false }) {
                if (nodeUiState is NodeUiState.Success) {
                    (nodeUiState as NodeUiState.Success).nodes.forEach { node ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(node.name ?: "Unknown", fontWeight = FontWeight.Bold)
                                    Text(
                                        "ID: ${node.id} | Host: ${node.host}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            onClick = {
                                selectedNodeId = node.id
                                expandedNodeMenu = false
                            }
                        )
                    }
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedProtocolMenu,
            onExpandedChange = { expandedProtocolMenu = it }
        ) {
            OutlinedTextField(
                value = selectedProtocol,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.field_protocol)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProtocolMenu) },
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedProtocolMenu,
                onDismissRequest = { expandedProtocolMenu = false }) {
                protocols.forEach { proto ->
                    DropdownMenuItem(
                        text = { Text(proto) },
                        onClick = { selectedProtocol = proto; expandedProtocolMenu = false })
                }
            }
        }

        OutlinedTextField(
            value = localIp,
            onValueChange = { localIp = it },
            label = { Text(stringResource(R.string.field_local_ip)) },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && localIp.isBlank(),
            supportingText = if (showErrors && localIp.isBlank()) {
                { Text(errorRequired) }
            } else null
        )
        OutlinedTextField(
            value = localPort,
            onValueChange = { localPort = it },
            label = { Text(stringResource(R.string.field_local_port)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = showErrors && localPort.isBlank(),
            supportingText = if (showErrors && localPort.isBlank()) {
                { Text(errorRequired) }
            } else null
        )

        val currentNode =
            (nodeUiState as? NodeUiState.Success)?.nodes?.find { it.id == selectedNodeId }
        OutlinedTextField(
            value = remotePort,
            onValueChange = {
                remotePort = it
                remotePortError = false
            },
            label = { Text(stringResource(R.string.field_remote_port)) },
            placeholder = {
                currentNode?.let {
                    Text(
                        portRangePattern.format(
                            it.portStart ?: 0,
                            it.portEnd ?: 0
                        )
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = remotePortError || (showErrors && remotePort.isBlank()),
            supportingText = {
                if (remotePortError) {
                    Text(stringResource(R.string.error_remote_port_in_use))
                } else if (showErrors && remotePort.isBlank()) {
                    Text(errorRequired)
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    currentNode?.let { node ->
                        val start = node.portStart ?: 0
                        val end = node.portEnd ?: 0
                        if (start <= end && end > 0) {
                            remotePort = (start..end).random().toString()
                            remotePortError = false
                        }
                    }
                }) {
                    Icon(Icons.Default.Shuffle, stringResource(R.string.random_port))
                }
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.kcp), Modifier.weight(1f))
            Switch(checked = isKcp, onCheckedChange = { isKcp = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.proxy_v2), Modifier.weight(1f))
            Switch(checked = isProxyV2, onCheckedChange = { isProxyV2 = it })
        }
    }
}

@Composable
fun UpdateDialog(state: UpdateState, viewModel: UpdateViewModel) {
    val context = LocalContext.current
    when (state) {
        UpdateState.Checking -> {
            Dialog(onDismissRequest = {}) {
                Card {
                    Column(
                        Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(16.dp))
                        Text(stringResource(R.string.update_checking))
                    }
                }
            }
        }

        UpdateState.UpToDate -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, R.string.update_uptodate, Toast.LENGTH_SHORT).show()
                viewModel.dismiss()
            }
        }

        is UpdateState.NewVersionAvailable -> {
            AlertDialog(
                onDismissRequest = { viewModel.dismiss() },
                title = { Text(stringResource(R.string.update_available)) },
                text = {
                    Column {
                        Text(stringResource(R.string.update_new_version, state.release.tagName))
                        state.release.body?.let {
                            Spacer(Modifier.height(8.dp))
                            Text(it, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.downloadAndInstall(state.release) }) {
                        Text(stringResource(R.string.update_now))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.dismiss() }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }

        UpdateState.Downloading -> {
            Dialog(onDismissRequest = {}) {
                Card {
                    Column(
                        Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(16.dp))
                        Text(stringResource(R.string.update_downloading))
                    }
                }
            }
        }

        is UpdateState.Error -> {
            AlertDialog(
                onDismissRequest = { viewModel.dismiss() },
                title = { Text(stringResource(R.string.update_failed)) },
                text = { Text(state.message) },
                confirmButton = {
                    TextButton(onClick = { viewModel.dismiss() }) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            )
        }

        UpdateState.Idle -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TaiwamfrpTheme {}
}

@Composable
fun rememberIsAppInForeground(): Boolean {
    val lifecycleOwner = LocalLifecycleOwner.current
    var isForeground by remember { mutableStateOf(true) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            isForeground = when (event) {
                Lifecycle.Event.ON_RESUME -> true
                Lifecycle.Event.ON_PAUSE -> false
                else -> isForeground
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    return isForeground
}

@Composable
fun ProfileScreen(
    user: UserMeResponse,
    onBack: () -> Unit,
    authViewModel: AuthViewModel,
    nodeViewModel: NodeViewModel,
    tunnelViewModel: TunnelViewModel
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            authViewModel = authViewModel,
            onLogout = {
                nodeViewModel.clearData()
                tunnelViewModel.clearData()
            }
        )
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(R.string.profile_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = "https://cdn.discordapp.com/avatars/${user.discordId}/${user.avatar}.png",
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            user.username ?: "Unknown",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        val statusText = when (user.status?.lowercase()) {
                            "active" -> stringResource(R.string.active)
                            "draft" -> stringResource(R.string.status_draft)
                            "suspended" -> stringResource(R.string.account_suspended)
                            "banned" -> stringResource(R.string.account_banned)
                            "deleted" -> stringResource(R.string.account_deleted)
                            else -> user.status ?: stringResource(R.string.unknown)
                        }
                        Text(
                            stringResource(R.string.profile_status, statusText),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                InfoItem(
                    stringResource(R.string.profile_discord_id),
                    user.discordId ?: stringResource(R.string.na)
                )
                InfoItem(
                    stringResource(R.string.profile_email),
                    user.email ?: stringResource(R.string.na)
                )
                InfoItem(
                    stringResource(R.string.profile_locale),
                    user.locale ?: stringResource(R.string.na)
                )
                InfoItem(
                    stringResource(R.string.profile_verified),
                    if (user.verified == true) stringResource(R.string.yes) else stringResource(R.string.no)
                )
                InfoItem(
                    stringResource(R.string.profile_mfa),
                    if (user.mfaEnabled == true) stringResource(R.string.yes) else stringResource(R.string.no)
                )

                Spacer(Modifier.height(24.dp))
                Text(
                    stringResource(R.string.profile_roles),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (user.roles.isEmpty()) {
                        Text(
                            stringResource(R.string.na),
                            style = MaterialTheme.typography.bodySmall
                        )
                    } else {
                        user.roles.forEach { role ->
                            AssistChip(onClick = {}, label = { Text(role, fontSize = 10.sp) })
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.profile_permissions),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    user.permissions.forEach { perm ->
                        AssistChip(onClick = {}, label = { Text(perm, fontSize = 10.sp) })
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextButton(onClick = {}, enabled = false) {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            stringResource(R.string.delete_acc),
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                        )
                    }
                    TextButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            stringResource(R.string.logout),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCircularIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(52.dp)
            .graphicsLayer {
                val showFraction = state.distanceFraction.coerceIn(0f, 1f)
                alpha = showFraction
                translationY = (showFraction - 1f) * 40.dp.toPx()
            },
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shadowElevation = 6.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isRefreshing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .graphicsLayer {
                            rotationZ = state.distanceFraction * 180f
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
