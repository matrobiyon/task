package tj.example.zavodteplic.auth.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tj.example.zavodteplic.auth.data.repository.AuthRepository
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.utils.CoreSharedPreference
import tj.example.zavodteplic.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val sharedPref = CoreSharedPreference(context)

    var isRegisterLoading by mutableStateOf(false)
        private set

    var loggInLoading by mutableStateOf(false)
        private set

    var isRegisterLoaded by mutableStateOf(false)
        private set

    private var _errorEvent = MutableSharedFlow<UIEvent>()
    val errorEvent = _errorEvent.asSharedFlow()
    private var errorEvenJob: Job? = null

    var sendAuthCode = mutableStateOf("")

    fun showSnackbar(message: String) {
        errorEvenJob?.cancel()
        errorEvenJob = viewModelScope.launch {
            _errorEvent.emit(UIEvent.ShowSnackbar(message))
        }
    }

    fun registerUser(name: String, phone: String, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.registerUser(name = name,phone = phone, username =  username).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        isRegisterLoaded = true
                        sharedPref.setAccessToken(result.data?.accessToken)
                        sharedPref.setRefreshToken(result.data?.refreshToken)
                        isRegisterLoading = false

                    }

                    is Resource.Error -> {
                        _errorEvent.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown error"))
                        isRegisterLoading = false
                    }
                    is Resource.Loading -> {
                        isRegisterLoading = true
                    }

                }
            }
        }
    }

    fun sendAuthCode(phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.sendAuthCode("+992935329897").collectLatest { result ->
                Log.d("TAG", "sendAuthCode: scope")
                when (result) {
                    is Resource.Success -> {
                        loggInLoading = false
                    }

                    is Resource.Error -> {
                        loggInLoading = false
                        _errorEvent.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }

                    is Resource.Loading -> {
                        loggInLoading = true
                    }
                }
            }

        }
    }
}