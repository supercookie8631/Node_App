package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

data class NoteTextFieldState(
    // 定義一個名為text的屬性，用於儲存文本字段的當前文本
    // 默認值為空字符串
    val text: String = "",

    // 定義一個名為hint的屬性，用於儲存文本字段的提示文本
    // 默認值為空字符串
    val hint: String = "",

    // 定義一個名為isHintVisible的屬性，用於確定是否應該顯示提示文本
    // 默認值為true，表示默認情況下提示文本是可見的
    val isHintVisible: Boolean = true
)
