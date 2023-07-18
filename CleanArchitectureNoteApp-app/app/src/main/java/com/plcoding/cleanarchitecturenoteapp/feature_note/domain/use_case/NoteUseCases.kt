package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

/***
 * 學習筆記
 * Model 部分
 * 打包UseCases給ViewModel用
 */

// NoteUseCases 是一個數據類，它包含了所有對 Note 數據進行操作的 use case。
// 每一個 use case 都是一個物件，它封裝了一種特定的數據操作，例如獲取所有的 Note、刪除一個 Note、添加一個新的 Note 或獲取一個特定的 Note。
data class NoteUseCases(
    val getNotes: GetNotes,

    val deleteNote: DeleteNote,

    val addNote: AddNote,

    val getNote: GetNote
)
