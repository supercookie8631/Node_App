package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Model 部分
// 將Note按照一定的順序排序。
class GetNotes(
    private val repository: NoteRepository
) {

    // 操作符重载GetNotes.invoke
    operator fun invoke(
        //提供一個預設值，沒參數的情況下將 Note 按照日期降序排序。
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        // 調用 repository.getNotes() 獲取所有的 Note
        // 使用 map 操作符對每一個 Note 進行排序
        // 排序的規則由 noteOrder 決定
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                // 如果 noteOrder.orderType 是 OrderType.Ascending，則我們按照升序排序。
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        // 根據 noteOrder 的具體類型，按照標題、時間戳或顏色來排序。
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                // 如果 noteOrder.orderType 是 OrderType.Descending，則我們按照降序排序。
                is OrderType.Descending -> {
                    when(noteOrder) {
                        // 一樣根據 noteOrder 的具體類型，按照標題、時間戳或顏色來排序。
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}
