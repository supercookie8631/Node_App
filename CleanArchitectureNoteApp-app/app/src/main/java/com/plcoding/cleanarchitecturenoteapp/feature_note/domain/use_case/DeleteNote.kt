package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

// Model 部分
class DeleteNote(
    private val repository: NoteRepository
) {

    // 操作符重载DeleteNote.invoke
    suspend operator fun invoke(note: Note) {
        // 調用 repository.deleteNote(note) 將 Note 從數據庫中刪除。
        repository.deleteNote(note)
    }
}
