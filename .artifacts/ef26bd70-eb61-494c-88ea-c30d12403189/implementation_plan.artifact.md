# 登入功能與 WebView 渲染最終修復計畫

解決 Discord 登入頁面空白渲染問題，並優化頻率限制報錯提示。

## 提議的變更

### [MODIFY] [MainActivity.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/MainActivity.kt)
- **錯誤解析優化**：在 `checkAuth` 中加入對所有 4xx 錯誤的深度 JSON 解析，優先提取 `"error"` 或 `"detail"` 欄位，確保 `Rate limit exceeded` 能被顯示。

### [MODIFY] [DiscordLoginWebView.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/DiscordLoginWebView.kt)
- **渲染修復**：
    - 強制關閉 WebView 的深色模式強制縮放。
    - 設定實心白色背景。
    - 使用純桌機版 User-Agent (`Mozilla/5.0 (Windows NT 10.0; Win64; x64)...`)。
- **UI 優化**：
    - 調整頂部按鈕，使用 `Surface` 建立一個懸浮的導覽小列。
    - 修正按鈕位置，確保在各機型下都能正常點擊。

## 驗證計畫
- **錯誤顯示**：手動觸發多次登入，確認紅框框顯示 `Rate limit exceeded`。
- **畫面渲染**：確認 WebView 顯示 Discord 的登入表單。
