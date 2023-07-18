package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note

//便利貼布局
@Composable
fun NoteItem(
    // 定義一個名為note的參數，它是一個Note對象，代表要顯示的筆記
    note: Note,
    modifier: Modifier = Modifier,
    // 定義一個名為cornerRadius的參數，它是一個Dp值，表示圓角的大小
    cornerRadius: Dp = 10.dp,
    // 定義一個名為cutCornerSize的參數，它是一個Dp值，表示剪切角的大小
    cutCornerSize: Dp = 30.dp,
    // 定義一個名為onDeleteClick的參數，它是一個無參數的函數，當用戶點擊刪除按鈕時，這個函數會被調用
    onDeleteClick: () -> Unit
) {
    // 使用Box函數創建一個盒子佈局
    Box(
        // 指定這個盒子佈局的modifier
        modifier = modifier
    ) {
        // 在這個盒子佈局中添加一個Canvas，用於自定義繪圖
        Canvas(modifier = Modifier.matchParentSize()) {
            // 創建一個Path對象，並在其上添加一條路徑
            val clipPath = Path().apply {
                // 從原點(0,0)畫一條線到點(size.width - cutCornerSize, 0)，這是矩形的頂部邊緣
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                // 從當前點畫一條線到點(size.width, cutCornerSize)，這是矩形右上角的剪切邊
                lineTo(size.width, cutCornerSize.toPx())
                // 從當前點畫一條線到點(size.width, size.height)，這是矩形的右側邊緣
                lineTo(size.width, size.height)
                // 從當前點畫一條線到點(0, size.height)，這是矩形的底部邊緣
                lineTo(0f, size.height)
                // 關閉路徑，將當前點連接回原點，完成矩形的左側邊緣
                close()
            }

            // 使用創建的路徑對繪圖進行剪切
            clipPath(clipPath) {
                // 在剪切後的區域中繪製一個帶有圓角的矩形，這個矩形代表筆記項目的背景
                drawRoundRect(
                    // 指定矩形的顏色為筆記的顏色
                    color = Color(note.color),
                    // 指定矩形的大小為Canvas的大小
                    size = size,
                    // 指定矩形的圓角大小
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                // 在矩形的右上角繪製一個更小的帶有圓角的矩形，這個矩形代表剪切角的背景
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        // 在盒子佈局中添加一個垂直佈局
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            // 在垂直佈局中添加一個Text，用於顯示筆記的標題
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // 在兩個Text之間添加一個間隔
            Spacer(modifier = Modifier.height(8.dp))
            // 在垂直佈局中添加另一個Text，用於顯示筆記的內容
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        // 在盒子佈局的右下角添加一個IconButton，用於顯示刪除按鈕
        IconButton(
            // 指定按鈕被點擊時調用的函數
            onClick = onDeleteClick,
            // 指定按鈕的位置為盒子佈局的右下角
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            // 在按鈕中添加一個Icon，用於顯示刪除的圖標
            Icon(
                // 指定圖標的圖片向量為刪除的圖標
                imageVector = Icons.Default.Delete,
                // 指定圖標的內容描述為"Delete note"
                contentDescription = "Delete note",
                // 指定圖標的顏色為主題的表面色
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}