package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.cleanarchitecturenoteapp.ui.theme.*

// @Entity 註解表示這個資料類別對應到數據庫的一個表。這個表的名稱默認為類名，即 "Note"。
// Note 類包含了數據表的所有字段，每個字段都對應到表中的一個列。
@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    // @PrimaryKey 註解表示這個字段是這個表的主鍵。主鍵是一個唯一識別每個表行的值。
    // id 是這個表的主鍵，它是一個可為 null 的整數。如果插入新的 Note 時不指定 id，則 id 會自動設為 null。
    @PrimaryKey val id: Int? = null
) {
    // noteColors 一個顏色的列表。
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

// InvalidNoteException 是一個自定義異常類，用來表示存儲失敗時的錯誤訊息，可以拋出這個異常。
class InvalidNoteException(message: String): Exception(message)
