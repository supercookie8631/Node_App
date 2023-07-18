package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// 添加或編輯 Note 的布局
@Composable
fun AddEditNoteScreen(
    // navController 參數用於控制導航。
    navController: NavController,

    // noteColor 參數用於指定 Note 的顏色。
    noteColor: Int,

    // viewModel 參數是一個 AddEditNoteViewModel 的實例，它負責處理此界面的業務邏輯。
    // hiltViewModel() 是一個在 Jetpack Compose 中使用的擴充函數，它用於獲取和當前 Composable 關聯的 ViewModel
    // 如果調用者沒有傳入 AddEditNoteViewModel 的實例，那麼就使用 hiltViewModel() 函數來獲取一個 AddEditNoteViewModel 的實例
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    // 從 ViewModel 中獲取 noteTitle 和 noteContent 的狀態。
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    // 創建一個 scaffoldState 來控制 Scaffold 的狀態，例如顯示 Snackbar。
    val scaffoldState = rememberScaffoldState()

    // 創建一個 noteBackgroundAnimatable 來控制 Note 背景顏色的動畫。
    val noteBackgroundAnimatable = remember {
        Animatable(
            //如果noteColor沒有值的話，使用viewModel.noteColor隨機選擇一個顏色
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    // 創建一個 CoroutineScope 來啟動和管理協程。
    val scope = rememberCoroutineScope()

    // 使用LaunchedEffect來啟動一個協程
    // LaunchedEffect是Jetpack Compose提供的一種效果，當給定的key改變時，它會自動取消並重新啟動協程
    LaunchedEffect(key1 = true) {

        // 這裡從ViewModel收集事件。這是一種異步操作，因為事件可能在任何時候發生
        // collectLatest會等待最新的事件，並且當新的事件到來時取消並重新啟動lambda表達式
        viewModel.eventFlow.collectLatest { event ->

            // 根據收到的事件類型，進行不同的處理
            // 在Kotlin中，when是一種更強大的switch語句，它可以用於匹配各種不同的情況
            when (event) {

                // 如果事件是顯示Snackbar的事件
                // Snackbar是一種臨時出現來顯示訊息的UI元件
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    // 讓scaffold的snackbarHostState來顯示Snackbar
                    // 這裡的message是從事件中取出的
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                // 如果事件是保存筆記的事件
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    // 讓navController導航到上一個頁面
                    // 在Jetpack Compose中，navController用於控制頁面的導航
                    navController.navigateUp()
                }
            }
        }
    }

    // 使用Scaffold建立基本的應用結構，包含了一個浮動按鈕
    Scaffold(
        // 定義一個浮動按鈕，當該按鈕被點擊時，會通知ViewModel進行保存筆記的操作
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                }, backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        }, scaffoldState = scaffoldState
    ) {
        // 在Scaffold內部，創建一個Column，用於垂直地排列界面元素。
        Column(
            // 使用Modifier來調整Column的屬性，如：填充整個可用空間、背景顏色、內部填充等。
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            // 在Column內部，創建一個Row，用於水平地顯示顏色選擇器。
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 為每種顏色創建一個Box，當Box被點擊時，會通知ViewModel進行更改顏色的操作
                Note.noteColors.forEach { color ->
                    // 將color對象轉換為ARGB格式的整數
                    val colorInt = color.toArgb()
                    Box(modifier = Modifier
                        // 設置Box的尺寸為50dp
                        .size(50.dp)
                        // 設置Box的陰影，陰影的大小為15dp，形狀為圓形
                        .shadow(15.dp, CircleShape)
                        // 設置Box的裁剪形狀為圓形
                        .clip(CircleShape)
                        // 設置Box的背景色為當前的顏色
                        .background(color)
                        // 設置Box的邊框，如果當前的顏色與ViewModel中保存的顏色一致，則邊框為黑色，否則為透明。邊框的寬度為3dp，形狀為圓形。
                        .border(
                            width = 3.dp, color = if (viewModel.noteColor.value == colorInt) {
                                Color.Black
                            } else Color.Transparent, shape = CircleShape
                        )
                        // 當Box被點擊時
                        .clickable {
                            // 在協程中，將背景顏色動畫地變換到當前的顏色，動畫時間為500毫秒
                            scope.launch {
                                noteBackgroundAnimatable.animateTo(
                                    targetValue = Color(colorInt), animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            // 通知ViewModel，用戶選擇了一個新的顏色
                            viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                        })
                }
            }
            // 在顏色選擇器下方，創建一個 TransparentHintTextField 用於輸入標題。
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                // text 參數表示文本輸入框的當前內容。
                text = titleState.text,
                // hint 參數表示當文本輸入框內容為空時顯示的提示文字。
                hint = titleState.hint,
                // onValueChange 是一個函數類型的參數，當文本輸入框的內容改變時，它會被調用。
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                // onFocusChange 是一個函數類型的參數，當文本輸入框的焦點狀態改變時，它會被調用。
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                // isHintVisible 參數用於控制是否顯示提示文字。
                isHintVisible = titleState.isHintVisible,
                // singleLine 參數用於控制文本輸入框是否僅顯示單行。
                singleLine = true,
                // textStyle 參數用於指定文本的風格。
                textStyle = MaterialTheme.typography.h5
            )
            // 在標題輸入框下方，創建另一個 TransparentHintTextField 用於輸入內容。
            Spacer(modifier = Modifier.height(16.dp))
            //同上
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
