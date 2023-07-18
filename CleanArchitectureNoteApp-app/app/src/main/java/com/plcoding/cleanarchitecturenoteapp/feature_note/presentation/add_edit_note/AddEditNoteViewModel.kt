package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel部分

// 使用HiltViewModel註解，讓Hilt自動提供依賴注入。Hilt是一種依賴注入工具，它可以自動地將對象的依賴關係進行管理
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    // 透過依賴注入方式獲取NoteUseCases類別的實例
    private val noteUseCases: NoteUseCases,
    // 透過依賴注入方式獲取SavedStateHandle類別的實例
    savedStateHandle: SavedStateHandle
) : ViewModel() { // 繼承ViewModel基礎類別，使得這個類別成為一個ViewModel

    // 定義一個MutableState，用於儲存筆記的標題。MutableState是Jetpack Compose提供的一種可變的狀態管理器
    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "請輸入標題..."
        )
    )

    // 提供一個只讀的狀態，供外部讀取筆記標題的狀態
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    // 同_noteTitle，也為筆記的內容定義MutableState
    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "請輸入內容"
        )
    )
    // 提供一個只讀的狀態，供外部讀取筆記內容的狀態
    val noteContent: State<NoteTextFieldState> = _noteContent

    //同上
    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    // 定義一個MutableSharedFlow，用於發送UI事件
    private val _eventFlow = MutableSharedFlow<UiEvent>()

    // 提供一個只讀的SharedFlow，供外部讀取UI事件
    val eventFlow = _eventFlow.asSharedFlow()

    // 定義一個可空的變數，用於儲存當前正在編輯的筆記的ID
    private var currentNoteId: Int? = null

    // 在類的初始化時，從savedStateHandle中獲取筆記的ID，並且如果這個ID不為-1，則從noteUseCases中獲取筆記的數據
    init {
        // 從savedStateHandle中讀取名為"noteId"的值
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            // 如果這個值不等於-1，表示這個ViewModel是用於編輯現有的筆記
            if (noteId != -1) {
                // 在ViewModel的協程範疇中啟動一個新的協程
                viewModelScope.launch {
                    // 從noteUseCases中讀取對應的筆記
                    noteUseCases.getNote(noteId)?.also { note ->
                        // 如果能找到對應的筆記，則更新ViewModel的狀態
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            // 更新筆記的標題，並將提示文字隱藏
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            // 更新筆記的內容，並將提示文字隱藏
                            text = note.content,
                            isHintVisible = false
                        )
                        // 更新筆記的顏色
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    // 定義一個方法，用於處理來自UI的事件。這些事件可能是用戶輸入的筆記標題、筆記內容、改變筆記顏色或保存筆記等
    fun onEvent(event: AddEditNoteEvent) {
        // 使用when表達式對不同的事件進行匹配處理
        when (event) {
            // 當用戶輸入標題時
            is AddEditNoteEvent.EnteredTitle -> {
                // 更新_noteTitle的值，將其text屬性設置為用戶輸入的標題
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            // 當標題的焦點狀態發生變化時
            is AddEditNoteEvent.ChangeTitleFocus -> {
                // 更新_noteTitle的值，根據當前的焦點狀態和標題是否為空來決定是否顯示提示
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }

            // 同上
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }

            // 同上
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }

            // 同上
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            // 當用戶儲存筆記時
            is AddEditNoteEvent.SaveNote -> {
                // 在viewModelScope範疇內啟動一個新的協程
                viewModelScope.launch {
                    try {
                        // 嘗試將用戶的筆記添加到數據源中
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        // 如果添加成功，則發送SaveNote事件
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        // 如果添加失敗，則發送ShowSnackbar事件，並顯示錯誤訊息
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    // 定義一個密封類，用於描述所有可能的UI事件。密封類是一種特殊的類，它的所有可能的子類都必須在它的內部或同一個檔案中定義
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}