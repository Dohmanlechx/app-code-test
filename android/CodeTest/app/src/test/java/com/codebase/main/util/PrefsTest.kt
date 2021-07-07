package com.codebase.main.util

import android.content.Context
import android.content.SharedPreferences
import com.codetest.main.util.Prefs
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy

class PrefsTest {
    @Spy
    private lateinit var spyPrefs: Prefs

    @Mock
    private lateinit var mockSharedPrefs: SharedPreferences

    @Mock
    private lateinit var mockSharedPrefsEditor: SharedPreferences.Editor

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        mockSharedPrefs = mock()
        mockSharedPrefsEditor = mock()
        mockContext = mock()

        Mockito.`when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPrefs)
        Mockito.`when`(mockSharedPrefs.edit()).thenReturn(mockSharedPrefsEditor)
        Mockito.`when`(mockSharedPrefsEditor.putString(anyString(), anyString())).thenReturn(mockSharedPrefsEditor)

        spyPrefs = spy(Prefs(context = mockContext))
    }

    @Test
    fun `Create new api key and store if no api key from the local storage`() {
        assertThat(spyPrefs.preferences().getString(Prefs.KEY, null)).isNull()
        spyPrefs.apiKey()
        verify(spyPrefs, times(1)).createNewApiKeyAndStore(eq(mockSharedPrefs))
    }
}