package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// View部分
//RadioButton布局
@Composable
fun DefaultRadioButton(
    // 定義一個名為text的參數，它是一個字符串，用於指定單選按鈕旁邊的文本
    text: String,
    // 定義一個名為selected的參數，它是一個布爾值，用於指定單選按鈕是否被選中
    selected: Boolean,
    // 定義一個名為onSelect的參數，它是一個無參數的函數，當單選按鈕被點擊時，這個函數會被調用
    onSelect: () -> Unit,
    // 定義一個名為modifier的參數，它是一個Modifier對象，用於指定這個界面元件的佈局和其他UI相關的屬性
    modifier: Modifier = Modifier
) {
    // 使用Row函數來創建一個水平佈局
    Row(
        // 指定這個水平佈局的modifier
        modifier = modifier,
        // 指定這個水平佈局的子元件在垂直方向上的對齊方式為居中對齊
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 在這個水平佈局中添加一個RadioButton
        RadioButton(
            // 指定這個單選按鈕是否被選中
            selected = selected,
            // 指定這個單選按鈕被點擊時調用的函數
            onClick = onSelect,
            // 指定這個單選按鈕的顏色。當它被選中時，顏色為主題的主色；當它未被選中時，顏色為主題的背景色
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground
            )
        )
        // 在單選按鈕和文本之間添加一個間隔
        Spacer(modifier = Modifier.width(8.dp))
        // 添加一個Text，顯示指定的文本，並使用主題的body1字體風格
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}
