package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

// 記事本列表布局
// @ExperimentalAnimationApi 是一個在Jetpack Compose中使用的注解，用於標記一個實驗性的動畫功能。這個注解的存在表示該特定的動畫功能是一個實驗性的 API，可能在未來的版本中有所修改或移除。
@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    // 傳入NavController，用於處理頁面之間的導航
    navController: NavController,
    // 傳入NotesViewModel，用於處理業務邏輯
    viewModel: NotesViewModel = hiltViewModel()
) {
    // 從ViewModel獲取狀態
    val state = viewModel.state.value
    // 創建一個ScaffoldState，用於控制Scaffold的狀態
    val scaffoldState = rememberScaffoldState()
    // 創建一個CoroutineScope，用於啟動協程
    val scope = rememberCoroutineScope()

    // 創建一個Scaffold，它提供了一個包含 FloatingActionButton 的基本佈局
    Scaffold(
        // 創建一個 FloatingActionButton，當它被點擊時，將導航到 AddEditNoteScreen
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        // 傳入創建的ScaffoldState
        scaffoldState = scaffoldState
    ) {
        // 在Scaffold內部，創建一個Column，並在其中添加各種Composable來顯示界面元素
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 在Column內部，創建一個Row來顯示筆記清單的標題和排序按鈕
            Row(
                // 設置修飾器(Modifier)，將 Row 元素的寬度設置為父容器的最大寬度，使其填滿整個寬度空間。
                modifier = Modifier.fillMaxWidth(),
                // 指定子元素在水平方向上的排列方式，這裡的 SpaceBetween 表示子元素之間均勻分佈且留有空間。
                horizontalArrangement = Arrangement.SpaceBetween,
                // 指定子元素在垂直方向上的對齊方式，這裡的 CenterVertically 表示將子元素垂直居中對齊。
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 顯示標題
                Text(
                    text = "記事本",
                    style = MaterialTheme.typography.h4
                )
                // 創建一個IconButton，當它被點擊時，將發送一個 ToggleOrderSection 事件到ViewModel，展開或收起排序列表
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        // 使用 Icons.Default.Sort 作為圖示的來源，表示使用預設的排序圖示。
                        imageVector = Icons.Default.Sort,
                        // 提供圖示的內容描述，用於輔助技術和無障礙功能。
                        contentDescription = "Sort"
                    )
                }
            }
            // 創建一個AnimatedVisibility，用於控制排序部分的顯示和隱藏
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                // 在排序部分中，創建一個OrderSection，用於選擇排序方式
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    // 當排序方式被更改時，將發送一個 Order 事件到ViewModel
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            // 在排序部分下方，創建一個Spacer，用於添加間距
            Spacer(modifier = Modifier.height(16.dp))
            // 創建一個LazyColumn，用於顯示筆記清單，等於RecycleView
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    // 為每個筆記創建一個NoteItem
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // 當NoteItem被點擊時，將導航到 AddEditNoteScreen，並將筆記的id和顏色作為參數傳遞過去
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        // 當刪除按鈕被點擊時，將發送一個 DeleteNote 事件到ViewModel
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                // 顯示一個SnackBar，並在用戶點擊復原按鈕時，發送一個 RestoreNote 事件到ViewModel
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "已刪除筆記",
                                    actionLabel = "復原"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    // 在每個NoteItem下方，添加一個Spacer，用於添加間距
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
