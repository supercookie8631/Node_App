package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

// Model 部分
class GetNote(
    private val repository: NoteRepository
) {

    // 操作符重载GetNote.invoke
    suspend operator fun invoke(id: Int): Note? {
        // 調用 repository.getNoteById(id) 將 Note 從數據庫中讀取出。
        return repository.getNoteById(id)
    }
}