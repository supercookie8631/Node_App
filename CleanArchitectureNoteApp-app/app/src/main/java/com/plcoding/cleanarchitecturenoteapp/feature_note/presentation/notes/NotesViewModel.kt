package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

// 使用HiltViewModel註解，讓Hilt可以提供此ViewModel的實例
@HiltViewModel
class NotesViewModel @Inject constructor(
    // 透過構造函數注入NoteUseCases，以便進行筆記的各種操作
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    // 定義一個可變狀態，用來儲存筆記界面的狀態
    private val _state = mutableStateOf(NotesState())
    // 定義一個只讀狀態，讓界面可以觀察筆記界面的狀態
    val state: State<NotesState> = _state

    // 定義一個變量，用來儲存最近被刪除的筆記
    private var recentlyDeletedNote: Note? = null

    // 定義一個Job，用來取消正在進行的getNotes操作
    private var getNotesJob: Job? = null

    // 在初始化ViewModel時，獲取筆記
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    // 定義一個方法，用來處理來自界面的各種事件
    fun onEvent(event: NotesEvent) {
        when (event) {
            // 當收到Order事件時，獲取排列過後的筆記
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            // 當收到DeleteNote事件時，刪除筆記
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            // 當收到RestoreNote事件時，恢復筆記
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            // 當收到ToggleOrderSection事件時，切換排序部分的可見性
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    // 獲取筆記
    private fun getNotes(noteOrder: NoteOrder) {
        // 取消正在進行的getNotes操作，因為getNotes操作是異步的，可能需要一段時間才能完成。
        // 假設用戶在短時間內快速切換排序方式，那麼每次切換都會調用getNotes，如果不取消前一次的操作，那麼可能會有多個操作同時進行
        getNotesJob?.cancel()
        // 開始一個新的getNotes操作
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                // 每當獲取到筆記時，更新狀態
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            // 在viewModelScope中啟動協程，以便在ViewModel被清理時取消操作
            .launchIn(viewModelScope)
    }
}
