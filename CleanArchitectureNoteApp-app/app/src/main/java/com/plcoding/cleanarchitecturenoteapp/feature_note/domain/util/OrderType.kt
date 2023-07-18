package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

// OrderType 是一個密封類，它表示排序的方向，即升序或降序。
sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
