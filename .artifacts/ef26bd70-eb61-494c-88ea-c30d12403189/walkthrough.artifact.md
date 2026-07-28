# 登入功能完成說明

我已完成 TaiwanFRP App 的登入流程實作，確保用戶在登入後能保持登入狀態，且登出功能能正確清除資訊。

## 變更摘要

### 1. 登入持久化 (Login Persistence)
- **CookieStore**: 使用 Jetpack DataStore 儲存登入成功的 Session Cookie。
- **AuthViewModel**:
    - 在啟動時 (`checkAuth`) 會自動嘗試從儲存空間讀取 Cookie 並套用到 API 客戶端。
    - 登出時會清除儲存的 Cookie 與記憶體中的快取。

### 2. Discord WebView 登入優化
- **網址自動載入**: 開啟登入介面後會自動載入 `/api/v1/auth/discord/login` 起始登入流程。
- **自動偵測登入成功**: 當 WebView 回跳到 API 根路徑且包含 `session` Cookie 時，自動完成登入、儲存 Cookie 並關閉 WebView。

### 3. 架構調整
- 為 `AuthViewModel` 增加 `Context` 參數（傳入 `applicationContext`），以支援持久化儲存操作。
- 修正了 `MainActivity` 與 Compose 預覽中的初始化邏輯。

## 測試建議

1. **首次登入**: 點擊「使用 Discord 登入」，完成流程後應能看到您的用戶名稱。
2. **自動重連**: 關閉 App 並重新開啟，應能直接進入主頁，不需再次登入。
3. **登出測試**: 在設定頁面點擊「登出」，確認 App 回到登入頁，且重新啟動後依然維持登出狀態。

> [!TIP]
> 伺服器狀態頁面保持原樣，您可以隨時點擊「查看伺服器狀態」進行免登入查看。
