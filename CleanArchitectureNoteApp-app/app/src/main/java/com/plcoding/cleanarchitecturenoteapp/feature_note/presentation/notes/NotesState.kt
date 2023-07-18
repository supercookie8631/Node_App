package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

// 定義一個數據類，儲存筆記界面的狀態
data class NotesState(
    // 定義一個列表，用來儲存所有的筆記，默認為空列表
    val notes: List<Note> = emptyList(),
    // 定義一個NoteOrder，用來儲存筆記的排序方式，默認為按照日期降序排序
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    // 定義一個布爾值，用來表示排序部分是否可見，默認為不可見
    val isOrderSectionVisible: Boolean = false
)
