package com.oolyvi.yekun.ui.ai

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.oolyvi.yekun.utils.Constants.GEMINI_API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

/**
 * chat ui state
 */
@Immutable
data class ChatUiState(
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser: Boolean
)


/**
 * ai ui state
 */
@Immutable
data class AiUiState(
    val chatUiStateList: MutableList<ChatUiState> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val showUserBottomSheet: Boolean? = false,
    val showModelBottomSheet: Boolean? = false,
    val isSendSoundPlayed: Boolean = false
)


/**
 * chat data to handle just prompt and prompt with image
 */
object ChatData {

    // selected image state initially
    val uriState = MutableStateFlow("")


    /**
     * response with just text
     */
    suspend fun getResponse(prompt: String): ChatUiState {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash", apiKey = GEMINI_API_KEY
        )
        try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }
            return ChatUiState(
                prompt = response.text ?: "",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return ChatUiState(
                prompt = e.message ?: "",
                bitmap = null,
                isFromUser = false
            )
        }
    }


    /**
     * response with text and image
     */
    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap): ChatUiState {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash", apiKey = GEMINI_API_KEY
        )
        try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }
            return ChatUiState(
                prompt = response.text ?: "",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return ChatUiState(
                prompt = e.message ?: "",
                bitmap = null,
                isFromUser = false
            )
        }
    }


    /**
     * get bitmap util on AI screen
     */
    @Composable
    fun getAiBitmap(): Bitmap? {
        val uri = uriState.collectAsState().value
        val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .size(Size.ORIGINAL)
                .build()
        ).state

        if (imageState is AsyncImagePainter.State.Success) {
            return imageState.result.drawable.toBitmap()
        }

        return null

    }

}