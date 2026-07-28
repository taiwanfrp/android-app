# 登入流程優化、主題切換與進階功能整合

此計畫將在登入頁面加入免登入查看功能，實作全域主題切換（深色/淺色/系統），並在登入後整合管理功能。

## 使用者審查請求
- **Discord 圖標**：登入按鈕將換上 Discord 官方風格圖標。
- **主題管理**：
    - 在「登入頁面」與「設定頁面」提供主題切換選項：深色模式、淺色模式、跟隨系統。
    - 主題選擇將持久化儲存（重啟 App 後依然保留）。
- **訪客模式**：在登入頁面新增按鈕，允許使用者在未登入狀態下查看伺服器狀態。
- **首頁控制台**：登入後的主頁將顯示使用者資料、隧道列表與節點列表。

## 擬議變更

### [系統架構]

#### [NEW] [ThemeManager]
- 使用 `DataStore` 儲存使用者的主題偏好。
- 實作 `ThemeViewModel` 管理全域主題狀態。

#### [MODIFY] [MainActivity.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwamfrp/MainActivity.kt)
- **多網路客戶端**：
    - `DdnsClient`：連線至 `taiwanfrp.ddns.net` (伺服器即時狀態)。
    - `MainApiClient`：連線至 `api.taiwanfrp.me` (使用者、隧道、節點)。
- **主題整合**：將 `TaiwamfrpTheme` 包裹在最外層，並由 `ThemeViewModel` 驅動。

### [UI 變更]

#### [MODIFY] LoginScreen
- 加入 **Discord 官方風格圖標** 於登入按鈕。
- 新增主題切換按鈕（或 Segmented Button）。
- 新增「📊 查看伺服器狀態」訪客按鈕。

#### [MODIFY] OtherScreen (Settings)
- 新增主題設定區域，讓使用者登入後也能隨時更改。

#### [MODIFY] ServerScreen
- 加入「返回登入」邏輯，確保訪客模式能正確跳回。

#### [MODIFY] HomeScreen (登入後)
- 顯示使用者個人資料（頭像、名稱）。
- 實作「🚀 我的隧道」卡片（API: `/api/v1/tunnels`）。
- 實作「🌐 官方節點」卡片（API: `/api/v1/nodes`）。

## 驗證計畫

### 手動驗證
- **主題測試**：切換至深色模式，確認整個 App（含所有分頁）都正確變色，且重啟 App 後設定依然生效。
- **訪客測試**：點擊訪客按鈕進入伺服器頁面，再點擊返回確認回到登入頁。
- **資料測試**：登入後確認隧道與節點列表能正確顯示 API 回傳的真實資料。
