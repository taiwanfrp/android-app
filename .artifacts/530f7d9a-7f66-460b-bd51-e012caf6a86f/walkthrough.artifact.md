# Discord 登入 WebView 顯示修正

已更新 `DiscordLoginWebView.kt` 中的 JavaScript 注入邏輯，強制撐開授權對話框容器。

## 變更內容

### [DiscordLoginWebView.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/DiscordLoginWebView.kt)
- **容器撐開修正**：強制讓 Modal 填滿 WebView，解決 436x56 的顯示限制。
- **手動驗證重導向**：
    - 當偵測到 URL 包含 `code=` 且不在回調路徑時，主動跳轉至 `/api/v1/auth/discord/callback`。
    - 這確保了後端能接收到授權碼並核發 Session Cookie。
- **穩定的登入判定**：
    - 結合了路徑檢查（首頁或回調頁）與 Cookie 內容檢查（`session`、`token` 等）。
    - 使用 `finished` 旗標防止重複觸發成功邏輯。

### [MainActivity.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/MainActivity.kt)
- **模型空安全強化**：將 `UserMeResponse` 改為具備空安全 `hashCode()` 的類別，防止在介面切換時發生 `NullPointerException`。
- **資料完整性檢查**：在 `AuthViewModel` 中加入 ID 檢驗，確保登入狀態的有效性。
- **錯誤處理 UI 優化**：
    - 在 `LoginScreen` 的錯誤狀態下，現在會顯示具體的錯誤訊息。
    - 在「點擊重試」旁加入了「登出」按鈕，方便使用者清除損壞的 Session 並重新開始。

## 驗證建議
請重新執行 App 並觀察 Logcat：
1. 看到 `Redirecting to callback...` 訊息代表手動驗證觸發成功。
2. 看到 `Login verified via cookie` 代表成功獲取憑證並登入。
