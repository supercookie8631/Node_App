package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

//排列布局
@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    // 定義一個名為noteOrder的參數，它是一個NoteOrder對象，表示當前的排序方式
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    // 定義一個名為onOrderChange的參數，它是一個函數，當排序方式變化時，這個函數會被調用
    onOrderChange: (NoteOrder) -> Unit
) {
    // 使用Column函數創建一個垂直佈局
    Column(
        // 指定這個垂直佈局的modifier
        modifier = modifier
    ) {
        // 在垂直佈局中添加一個水平佈局
        Row(
            // 指定這個水平佈局填充其父佈局的寬度
            modifier = Modifier.fillMaxWidth()
        ) {
            // 在水平佈局中添加一個DefaultRadioButton，當它被選中時，將排序方式改為按標題排序
            DefaultRadioButton(
                // 設置單選按鈕的文字
                text = "標題",
                // 檢查當前的排序方式是否是按照標題排序，如果是則將單選按鈕設為選中狀態
                selected = noteOrder is NoteOrder.Title,
                // 當單選按鈕被選擇時，將排序方式更改為按照標題排序，並保持當前的排序類型（升序或降序）
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )

            // 在兩個RadioButton之間添加一個間隔
            Spacer(modifier = Modifier.width(8.dp))
            // 在水平佈局中添加另一個DefaultRadioButton，當它被選中時，將排序方式改為按日期排序
            DefaultRadioButton(
                //同上
                text = "日期",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            // 在兩個RadioButton之間添加一個間隔
            Spacer(modifier = Modifier.width(8.dp))
            // 在水平佈局中添加第三個DefaultRadioButton，當它被選中時，將排序方式改為按顏色排序
            DefaultRadioButton(
                //同上
                text = "顏色",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        // 在兩個水平佈局之間添加一個間隔
        Spacer(modifier = Modifier.height(16.dp))
        // 在垂直佈局中添加另一個水平佈局
        Row(
            // 指定這個水平佈局填充其父佈局的寬度
            modifier = Modifier.fillMaxWidth()
        ) {
            // 在水平佈局中添加一個DefaultRadioButton，當它被選中時，將排序方式改為升序
            DefaultRadioButton(
                // 設置單選按鈕的文字
                text = "升序",
                // 檢查當前的排序類型是否是升序，如果是則將單選按鈕設為選中狀態
                selected = noteOrder.orderType is OrderType.Ascending,
                // 當單選按鈕被選擇時，將排序類型更改為升序
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )

            // 在兩個RadioButton之間添加一個間隔
            Spacer(modifier = Modifier.width(8.dp))
            // 在水平佈局中添加另一個DefaultRadioButton，當它被選中時，將排序方式改為降序
            DefaultRadioButton(
                //同上
                text = "降序",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}