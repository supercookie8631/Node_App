package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.*
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/***
 * 學習筆記
 * 這裡主要定義了對 Note 資料表的基本操作，有其他資料表則要在創建新的DAO
 */

// @Dao 表示這是一個 Room Database Access Object (DAO)。DAO 是一個介面，定義了一些方法來訪問數據庫。
@Dao
interface NoteDao {

    // @Query 表示該方法是一個數據庫查詢操作。參數是 SQL 查詢語句。
    // getNotes 方法是用來獲取數據庫中所有的 Note 資料。返回一個包含所有 Note 的 Flow。
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    // getNoteById 方法是用來根據 id 獲取指定的 Note。如果找不到對應的 Note，則返回 null。
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    // @Insert 表示這是一個插入操作。onConflict = OnConflictStrategy.REPLACE 表示如果插入的 Note 與已有的 Note 衝突（例如 id 相同），則替換已有的 Note。
    // insertNote 方法是用來將一個新的 Note 插入到數據庫中。
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // @Delete 註解表示這是一個刪除操作。
    // deleteNote 方法是用來從數據庫中刪除一個 Note。
    @Delete
    suspend fun deleteNote(note: Note)
}
