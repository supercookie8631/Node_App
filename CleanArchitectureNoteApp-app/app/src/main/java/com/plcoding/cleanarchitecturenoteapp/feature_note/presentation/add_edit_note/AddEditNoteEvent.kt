package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

// ViewModel 部分
// AddEditNoteEvent 是一個密封類，它用於表示添加或編輯 Note 時可能發生的所有事件。
sealed class AddEditNoteEvent{
    // EnteredTitle 事件表示用戶輸入了標題。它包含一個 String 類型的 value 屬性，該屬性表示用戶輸入的標題。
    data class EnteredTitle(val value: String): AddEditNoteEvent()

    // ChangeTitleFocus 事件表示標題輸入框的焦點狀態發生了改變。它包含一個 FocusState 類型的 focusState 屬性，該屬性表示新的焦點狀態。
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()

    // EnteredContent 事件表示用戶輸入了內容。它包含一個 String 類型的 value 屬性，該屬性表示用戶輸入的內容。
    data class EnteredContent(val value: String): AddEditNoteEvent()

    // ChangeContentFocus 事件表示內容輸入框的焦點狀態發生了改變。它包含一個 FocusState 類型的 focusState 屬性，該屬性表示新的焦點狀態。
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()

    // ChangeColor 事件表示用戶改變了 Note 的顏色。它包含一個 Int 類型的 color 屬性，該屬性表示新的顏色。
    data class ChangeColor(val color: Int): AddEditNoteEvent()

    // SaveNote 事件表示用戶試圖保存 Note。
    object SaveNote: AddEditNoteEvent()
}
