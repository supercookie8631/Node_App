package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * 學習筆記
 * 這個 AppModule 類使用 Hilt 依賴注入框架提供應用程序所需的各種物件實例。
 * 這使得你可以在任何需要這些物件的地方輕鬆地獲取它們，而不需要手動創建實例，這大大簡化了你的程式碼並提高了可測試性和可維護性。
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // 此方法是用來提供 NoteDatabase 的單例實例。在整個應用程序中，只會有一個 NoteDatabase 的實例。
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        // Room.databaseBuilder 用來構建一個 Room 數據庫。
        // 所需參數@NonNull Context context, @NonNull Class<T> klass, @NonNull String name
        // app 參數是應用程序的 Context，它是用來訪問數據庫的路徑和其他信息的。
        // NoteDatabase::class.java 是要創建的數據庫的類。
        // NoteDatabase.DATABASE_NAME 是的數據庫的名稱。
        // build 創建數據庫的。
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    // 此方法是用來提供 NoteRepository 的單例實例。
    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        // NoteRepositoryImpl 是 NoteRepository 的實現類，它需要一個 NoteDao 物件來訪問數據庫。
        // 從提供的 NoteDatabase 實例中獲得 NoteDao。
        return NoteRepositoryImpl(db.noteDao)
    }

    // 此方法是用來提供 NoteUseCases 的單例實例。
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        // NoteUseCases 是一個用來執行所有與筆記相關的業務邏輯操作的類。
        // 它需要一個 NoteRepository 的實例來訪問數據庫。
        // 這裡創建一個 NoteUseCases 的實例，並將所有需要的 use cases（GetNotes，DeleteNote，AddNote，GetNote）傳遞給它。
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}