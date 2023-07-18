package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note

/***
 * 學習筆記
 * 這個 NoteDatabase 類是一個抽象類，代表了應用程式的數據庫
 * 我們定義了一個抽象方法 noteDao，用於獲取 NoteDao 的實例，進行對 Note 表的各種操作。RoomDatabase 會幫我們實現這個方法，只需要調用它即可。
 * 這裡的 @Database 註解指定了這個數據庫的一些屬性，例如它包含的表（entities）和版本號（version）。這些屬性將會被 Room 框架使用，來建立和管理數據庫。
 * 另外，companion object 中的 DATABASE_NAME 是數據庫名稱。當創建數據庫實例時，會使用這個名稱。
*/


// @Database 是一個標記 Room 數據庫的註解。
// entities = [Note::class] 表示此數據庫包含一個名為 Note 的實體（即資料表）。
// version = 1 表示數據庫的版本號。每當數據庫結構有變動時，版本號需要增加以觸發資料庫的升級邏輯。
// 資料庫
@Database(
    //目前只有一個資料表
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {
    // abstract val noteDao: NoteDao 是一個抽象方法，它會被 Room 框架實現。
    // 這個方法返回一個 NoteDao，讓我們能夠進行資料庫操作。
    abstract val noteDao: NoteDao

    // companion object 定義靜態方法或屬性。
    // 定義了一個常量 DATABASE_NAME，表示我們的數據庫名稱。
    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}