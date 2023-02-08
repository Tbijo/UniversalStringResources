package com.plcoding.universalstringresources

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

// A wrapper class around our Strings.

sealed class UiText {

    // Dynamic String
    // If the API responds with some Error message
    // We do not own that String (we do not decide what is the error message but the API does).
    // So we can not use a string resource.
    // We would have to provide a language code to the API and the API should respond accordingly in that language.
    // Used in repositories.
    data class DynamicString(val value: String): UiText()

    // StringResource - Strings from resources string.xml
    // Should be used in Use Cases.
    // Great way to easily swap with other languages.
    class StringResource(
        // id of a resource String
        @StringRes val resId: Int,
        // and arguments
        // arguments can be passed only when the resource String value has a place holder symbol such as (%d, %s,...)
        vararg val args: Any
    ): UiText()

    // Extracting Strings in Composables
    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    // Extracting Strings in Non-Composables
    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}
