package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util

// 定義一個封裝類 Screen，用於表示應用中的不同布局。
sealed class Screen(val route: String) {
    // NotesScreen 代表記事本列表布局，其路由字符串為 "notes_screen"。
    object NotesScreen: Screen("notes_screen")
    // AddEditNoteScreen 代表添加或編輯筆記的布局，其路由字符串為 "add_edit_note_screen"。
    object AddEditNoteScreen: Screen("add_edit_note_screen")
}