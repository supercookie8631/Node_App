package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

// NoteOrder 是一個密封類，它用於表示對 Note 列表的排序方式。
// 每一個子類都有一個 orderType 屬性，表示排序的方向，即升序或降序。
sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)
    // copy 方法返回一個新的 NoteOrder 物件，其 orderType 屬性與當前物件的 orderType 屬性相同。
    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            // 根據當前物件的具體類型，返回一個相同類型的新物件。
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
