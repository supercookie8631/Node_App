package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder

// 定義一個密封類
sealed class NotesEvent {
    // 定義一個數據類，表示排序事件。當用戶選擇了一種新的排序方式時，應該發送這種事件。
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    // 定義一個數據類，表示刪除筆記事件。當用戶選擇刪除一個筆記時，應該發送這種事件。
    data class DeleteNote(val note: Note): NotesEvent()
    // 定義一個對象，表示恢復筆記事件。當用戶選擇恢復一個被刪除的筆記時，應該發送這種事件。
    object RestoreNote: NotesEvent()
    // 定義一個對象，表示切換排序部分的顯示狀態的事件。當用戶選擇展開或收起排序部分時，應該發送這種事件。
    object ToggleOrderSection: NotesEvent()
}
