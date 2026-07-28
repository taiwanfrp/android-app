# 代碼整理、詳情複製強化與全頁面表單優化計畫

本計畫旨在深度優化 App 的交互細節，並將原本侷促的彈窗表單升級為沉浸式的全頁面設計，解決鍵盤遮擋與操作不便的問題。

## 提議變更

### 1. 詳情資訊複製強化 [MODIFY] [MainActivity.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/MainActivity.kt)
- **`DetailRow` 重構**：
    - **全欄位可複製**：移除 `clickable` 參數，使所有詳情欄位點擊後皆可複製內容至剪貼簿。
    - **滿高度圖標**：使用 `IntrinsicSize.Min` 高度限制，讓複製圖標在視覺上填滿該列的高度邊界，增加點擊感。
    - **自動提示**：點擊任何詳情列都會彈出「[欄位名] 已複製」的 Toast。

### 2. 全頁面編輯與創建介面 [MODIFY] [MainActivity.kt](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/java/com/taiwanfrp/MainActivity.kt)
- **捨棄 `AlertDialog`**：將 `Create/EditNodeDialog` 與 `Create/EditTunnelDialog` 改為全頁面覆蓋（Overlay）模式。
- **介面結構**：
    - 使用 `Scaffold` 建立標準標題列（帶有「取消」與「完成」按鈕）。
    - 內容區塊支援 `verticalScroll`，確保在鍵盤彈出時仍可滾動至底部的開關選項。
- **智慧鍵盤控制**：
    - 套用 `Modifier.pointerInput` 捕捉背景點擊。
    - 點擊輸入框以外的任何空白區域時，自動呼叫 `focusManager.clearFocus()` 以收起鍵盤。

### 3. 代碼整理與風格統一
- **移除冗餘註釋**：刪除開發過程中的臨時註釋，保持代碼純淨。
- **統一縮排與結構**：修復嵌套過深的問題，將內部的 Body 函式適度抽離。
- **優化資源引用**：確保所有文字均使用 `stringResource`。

## 驗證計畫

### 手動測試
1. **複製功能測試**：
    - 展開節點或隧道項目。
    - 分別點擊「ID」、「IP」、「描述」、「埠號範圍」等。
    - **預期結果**：每一項點擊後都能成功複製，且複製圖標與文字高度齊平。
2. **全頁面表單測試**：
    - 點擊新增或編輯。
    - **預期結果**：表單應填滿螢幕（或呈現為全螢幕 Dialog）。
    - 點擊輸入框後鍵盤彈出，嘗試滾動底部確認開關是否被遮擋。
    - 點擊表單空白處，確認鍵盤是否正確收起。
