package tj.example.zavodteplic.auth.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.example.zavodteplic.auth.data.remote.model.CheckAuthCode
import tj.example.zavodteplic.auth.data.repository.AuthRepository
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.utils.CoreSharedPreference
import tj.example.zavodteplic.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPref: CoreSharedPreference
) : ViewModel() {

    var isRegisterLoading by mutableStateOf(false)
        private set
    var isRegisterLoaded by mutableStateOf(false)

    var isLoggingLoading by mutableStateOf(false)
        private set
    var isLoggingLoaded by mutableStateOf(false)
    var isLoggedSuccess by mutableStateOf(false)

    var isCheckAuthLoading by mutableStateOf(false)
        private set
    var isCheckAuthLoaded by mutableStateOf(false)

    var checkAuthCodeData by mutableStateOf<CheckAuthCode?>(null)

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
            authRepository.registerUser(name = name, phone = phone, username = username)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            isRegisterLoaded = true
                            sharedPref.setAccessToken(result.data?.accessToken)
                            sharedPref.setRefreshToken(result.data?.refreshToken)
                            isRegisterLoading = false

                        }

                        is Resource.Error -> {
                            _errorEvent.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
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
            authRepository.sendAuthCode(phone).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoggedSuccess = true
                        isLoggingLoading = false
                        isLoggingLoaded = true
                    }

                    is Resource.Error -> {
                        isLoggingLoading = false
                        _errorEvent.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }

                    is Resource.Loading -> {
                        isLoggingLoading = true
                    }
                }
            }

        }
    }

    fun checkAuthCode(phone: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.checkAuthCode(phone, code).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        isCheckAuthLoading = false
                        isCheckAuthLoaded = true
                        checkAuthCodeData = result.data
                        sharedPref.setAccessToken(result.data?.accessToken)
                        sharedPref.setRefreshToken(result.data?.refreshToken)
                        sharedPref.setUserId(result.data?.userId?:-1)
                    }

                    is Resource.Error -> {
                        isCheckAuthLoading = false
                        _errorEvent.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }

                    is Resource.Loading -> {
                        isCheckAuthLoading = true
                    }
                }
            }
        }
    }

    fun resetDatas() {
        isRegisterLoaded = false
        isLoggingLoaded = false
        isLoggedSuccess = false
        checkAuthCodeData = null
    }
}