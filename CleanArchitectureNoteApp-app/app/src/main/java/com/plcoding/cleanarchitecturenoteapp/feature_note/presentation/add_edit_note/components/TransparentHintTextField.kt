package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    // text 參數表示文本輸入框的當前內容。
    text: String,
    // hint 參數表示當文本輸入框內容為空時顯示的提示文字。
    hint: String,
    // modifier 參數用於修改 Composable 的布局或其他視覺屬性。
    modifier: Modifier = Modifier,
    // isHintVisible 參數用於控制是否顯示提示文字。
    isHintVisible: Boolean = true,
    // onValueChange 是一個函數類型的參數，當文本輸入框的內容改變時，它會被調用。
    onValueChange: (String) -> Unit,
    // textStyle 參數用於指定文本的風格。
    textStyle: TextStyle = TextStyle(),
    // singleLine 參數用於控制文本輸入框是否僅顯示單行。
    singleLine: Boolean = false,
    // onFocusChange 是一個函數類型的參數，當文本輸入框的焦點狀態改變時，它會被調用。
    onFocusChange: (FocusState) -> Unit
) {
    // Box 是一個 Composable，它可以包含其他 Composable。
    // 將 BasicTextField 和 Text 放入同一個 Box 中，使得當文本輸入框為空時，提示文字可以顯示在其上方。
    Box(
        modifier = modifier
    ) {
        // BasicTextField 是一個 Composable，它創建一個基本的文本輸入框。
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            //使用 fillMaxWidth 函數來讓文本輸入框填滿其父 Composable 的寬度。
            //使用 onFocusChanged 函數來設定一個回調函數，該函數會在文本輸入框的焦點狀態改變時被調用。
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        // 如果 isHintVisible 為 true，則我們在 Box 中顯示一個 Text Composable，它顯示提示文字。
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}
