package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

// Model 部分
class AddNote(
    private val repository: NoteRepository
) {
    // @Throws(InvalidNoteException::class) 表示這個方法可能會拋出 InvalidNoteException 異常。
    // InvalidNoteException 是一個自定義方法

    // 操作符重载AddNote.invoke
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("請輸入標題")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("請輸入內容")
        }
        // 調用 repository.insertNote(note) 將其添加到數據庫中。
        repository.insertNote(note)
    }
}
