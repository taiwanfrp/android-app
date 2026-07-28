# TaiwanFRP Android App

TaiwanFRP 是一款為 [TaiwanFRP](https://taiwanfrp.me)
免費內網穿透服務設計的程式。透過此應用，使用者可以隨時隨地管理其內網穿透節點與隧道，並即時掌握系統運行狀態。

# 使用此app時建議discord帳戶有開啟2fa驗證

因為驗證機器人過不去:D

## 🚀 功能特色

### 📊 儀表板與狀態監控

- **即時系統狀態**：監控 API、資料庫及 Redis 的運行狀況。
- **統計數據**：快速查看目前在線與總計的節點及隧道數量。
- **官方連結**：快速跳轉至官網、Discord 群組與 GitHub 專案。

### 🛠️ 隧道管理 (Tunnel Management)

- **靈活配置**：支援 TCP、UDP 通訊協定。
- **進階功能**：可選開啟 KCP 加速與 Proxy Protocol v2。
- **自動化**：支援隨機選取遠端連接埠。
- **全方位管理**：建立、編輯、刪除及即時查看隧道運行狀態（在線/離線）。

### 🌐 節點資訊 (Node Info)

- **實時列表**：查看所有可用的伺服器節點及其主機地址、連接埠範圍。
- **免登入查看**：支援訪客模式，無需登入即可查看節點負載與在線狀態。

### 🎨 個人化設定

- **多樣化主題**：內建多款主題色（極致黑、湖水藍、薄荷綠等），支援系統跟隨。
- **多國語言**：支援 **繁體中文** 與 **English**。
- **自定義頻率**：可調整背景數據自動更新頻率。

### 🔐 安全與帳號

- **Discord 整合**：使用 Discord 帳號安全登入。
- **權限管理**：根據使用者身分組自動切換可用功能（如管理員可建立節點）。
- **隱私控制**：支援清除 Discord 登入快取。

## 🛠️ 技術棧

- **UI 框架**：[Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **程式語言**：[Kotlin](https://kotlinlang.org/)
- **網路請求
  **：[Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- **資料序列化**：[Gson](https://github.com/google/gson)
- **非同步處理**：[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & Flow
- **圖片載入**：[Coil](https://coil-kt.github.io/coil/)
- **本地儲存
  **：[Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **WebView**：整合 Discord OAuth2 登入流

## 📦 安裝需求

- **最低版本**：Android 7.0 (API Level 24)
- **建議版本**：Android 13.0 (API Level 33) 或以上

## 🛠️ 開發與建置

1. 複製專案：
   ```bash
   git clone https://github.com/TaiwanFRP/taiwanfrp-android.git
   ```
2. 使用 Android Studio 打開專案。
3. 等待 Gradle 同步完成。
4. 點擊 **Run** 即可部署至模擬器或實體裝置。

## 🤝 貢獻與反饋

如果您發現任何 Bug 或有功能建議，歡迎：

- 提交 [GitHub Issue](https://github.com/TaiwanFRP/taiwanfrp-android/issues)
- 加入 [Discord 伺服器](https://discord.gg/ueGFVVHp85) 與開發者交流

---
**開發者**：臭臭卓的冰箱吉祥物
**專案連結**：[TaiwanFRP GitHub](https://github.com/TaiwanFRP)