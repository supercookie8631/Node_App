package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

// 指示該類別是一個 Android 入口點，用於自動進行相關的依賴注入和初始化操作。
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        // 在活動創建時調用。
        super.onCreate(savedInstanceState)
        setContent {
            // 設定此活動的內容視圖。
            CleanArchitectureNoteAppTheme {
                // 使用我們定義的主題。
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    // 建立一個表面，其背景色為主題的背景色。
                    val navController = rememberNavController()
                    // 創建一個 NavController，用於控制導航。
                    NavHost(
                        navController = navController, startDestination = Screen.NotesScreen.route
                        // 創建一個 NavHost，並設定開始目的地為筆記螢幕。
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            // 定義一個名為 "Screen.NotesScreen.route" 的路由，當我們在導航器中導航到這個路由時，
                            // 就會執行此處的函數體，這裡我們讓它呈現 NotesScreen 這個 Composable 函數所定義的 UI 界面。

                            NotesScreen(navController = navController)
                            // 呼叫 NotesScreen Composable 函數，並將 navController 作為參數傳入。在 NotesScreen 函數內，
                            // 我們會用到 navController 來控制導航，例如當用戶點擊某個筆記時，我們可以用 navController 導航到該筆記的詳細資訊頁面。
                        }
                        composable(
                            // 定義一個帶有兩個參數 "noteId" 和 "noteColor" 的路由
                            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    // 為 "noteId" 參數設置類型和預設值
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor") {
                                    // 為 "noteColor" 參數設置類型和預設值
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            // 從路由參數中取出 "noteColor"，並作為參數傳給 AddEditNoteScreen
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController, noteColor = color
                            )
                            // 當導航到這個路由時，將顯示 AddEditNoteScreen Composable 函數所定義的 UI 界面，並將取得的 "noteColor" 參數傳入
                        }
                    }
                }
            }
        }
    }
}
