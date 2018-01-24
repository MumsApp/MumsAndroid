package com.mumsapp.android.di.modules

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.mumsapp.android.MainApplication
import com.mumsapp.android.util.KeyboardHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: MainApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = context.baseContext;

    @Provides
    @Singleton
    fun provideInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    @Singleton
    internal fun provideKeyboardHelper(imm: InputMethodManager): KeyboardHelper {
        return KeyboardHelper(imm)
    }
}