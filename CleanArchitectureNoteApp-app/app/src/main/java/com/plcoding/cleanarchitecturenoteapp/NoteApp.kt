package com.plcoding.cleanarchitecturenoteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/***
 * @HiltAndroidApp 是一個 Hilt 的註解，它用來標記一個應用的 Application 類
 * 當我們在 Application 類上添加 @HiltAndroidApp 註解後，Hilt 會在這個類中生成一些必要的組件來提供依賴注入
 * 這樣，我們就可以在整個應用中使用 Hilt 提供的依賴注入功能了。
 */

@HiltAndroidApp
class NoteApp : Application()