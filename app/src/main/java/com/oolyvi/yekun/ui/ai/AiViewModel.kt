package com.oolyvi.yekun.ui.ai

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolyvi.yekun.data.repository.DefaultPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiUiState())
    val uiState = _uiState.asStateFlow()


    // send click sound preference
    val isSendSoundPlayed = defaultPreferencesRepository.sendClickSoundPlayed


    init {
        getIsSendSoundPlayed()
    }


    /**
     * send prompt
     */
    fun sendPrompt(prompt: String, bitmap: Bitmap?) {
        if (prompt.isNotEmpty()) {
            addPrompt(prompt, bitmap)

            if (bitmap != null) {
                getResponseWithImage(prompt, bitmap)
            } else {
                getResponse(prompt)
            }
        }
    }


    /**
     * update prompt
     */
    fun updatePrompt(newPrompt: String) {
        _uiState.update {
            it.copy(prompt = newPrompt)
        }
    }


    /**
     * add new prompt to the chat item
     */
    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _uiState.update {
            it.copy(
                chatUiStateList = it.chatUiStateList.toMutableList().apply {
                    add(0, ChatUiState(prompt, bitmap, true))
                },
                prompt = "",
                bitmap = null
            )
        }
    }


    /**
     * get response with text
     */
    private fun getResponse(prompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val chat = ChatData.getResponse(prompt)
            _uiState.update {
                it.copy(
                    chatUiStateList = it.chatUiStateList.toMutableList().apply {
                        add(0, chat)
                    },
                    isLoading = false
                )
            }
        }
    }


    /**
     * get response with image
     */
    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            _uiState.update {
                it.copy(
                    chatUiStateList = it.chatUiStateList.toMutableList().apply {
                        add(0, chat)
                    },
                    isLoading = false
                )
            }
        }
    }


    /**
     * set and get is send sound played value for send button
     */
    fun setIsSendSoundPlayed(value: Boolean) {
        viewModelScope.launch {
            defaultPreferencesRepository.setSendClickSoundPlayed(value)
        }
    }

    private fun getIsSendSoundPlayed() {
        defaultPreferencesRepository.sendClickSoundPlayed
            .onEach { value ->
                _uiState.update { it.copy(isSendSoundPlayed = value) }
            }
            .launchIn(viewModelScope)
    }


    /**
     * show bottom sheet for user
     */
    fun showUserBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(showUserBottomSheet = value)
        }
    }


    /**
     * show bottom sheet for model
     */
    fun showModelBottomSheet(value: Boolean) {
        _uiState.update {
            it.copy(showModelBottomSheet = value)
        }
    }

}