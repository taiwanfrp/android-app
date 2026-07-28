# Discord 登入回調流程修正計畫

由於後端流程要求手動觸發 `/api/v1/auth/discord/callback` 來生成 Token 與設定 Cookie，我們需要調整 WebView 的重導向偵測邏輯。

## 流程分析
1. **起點**：`GET /api/v1/auth/discord/login`
2. **Discord 授權**：使用者點擊授權。
3. **返回 App**：Discord 跳轉回 `https://api.taiwanfrp.me/?code=xxx&state=yyy`。
4. **手動驗證**：WebView 必須偵測到 `code`，並載入 `https://api.taiwanfrp.me/api/v1/auth/discord/callback?code=xxx&state=yyy`。
5. **登入完成**：當 `/callback` 處理完畢（並發送 Set-Cookie）後，捕捉 Cookie 並關閉 WebView。

## 待執行任務
- [ ] 修改 `DiscordLoginWebView.kt`：
    - 在 `onPageFinished` (或 `shouldOverrideUrlLoading`) 中偵測 URL 是否包含 `code=`。
    - 如果包含 `code` 且當前 URL **不是** `/callback`，則手動重導向至 `/callback` 端點。
    - 在 `/callback` 執行完畢或跳轉回首頁後，確認 Cookie 已存在並完成登入。

## 預計修改檔案
- [MODIFY] [DiscordLoginWebView.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/DiscordLoginWebView.kt)

## 驗證計畫
### 手動驗證
- 檢查 Logcat 是否出現 `Redirecting to callback...` 訊息。
- 確認最終是否能順利獲取 Session Cookie 並關閉視窗。
