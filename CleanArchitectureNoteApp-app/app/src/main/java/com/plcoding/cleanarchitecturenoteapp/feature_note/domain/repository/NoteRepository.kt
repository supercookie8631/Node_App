package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

// Model 部分
// NoteRepository 是一個介面，它定義了對 Note 數據操作的各種方法。
// 這個介面不依賴於任何具體的數據源，可以通過不同的實現對接到不同的數據源，例如 NoteRepositoryImpl 就是一個對接到 Room 數據庫的實現。
// 這種設計使得數據源可以輕鬆地被替換，並使得對這些方法的測試更加容易。
// 取得資料的interface，取決於對接於哪個資料，方便測試，現在是對接於NoteRepositoryImpl

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}
