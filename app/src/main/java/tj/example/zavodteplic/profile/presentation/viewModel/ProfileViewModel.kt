package tj.example.zavodteplic.profile.presentation.viewModel

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.example.zavodteplic.MainApplication
import tj.example.zavodteplic.R
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.profile.data.local.ProfileData
import tj.example.zavodteplic.profile.data.repository.ProfileRepository
import tj.example.zavodteplic.profile.data.repository.ProfileRepositoryProvide
import tj.example.zavodteplic.profile.presentation.model.ProfileDataItem
import tj.example.zavodteplic.profile.presentation.model.ProfileDataState
import tj.example.zavodteplic.profile.presentation.model.body.UploadPhotoBody
import tj.example.zavodteplic.profile.presentation.ui.components.getZodiacZnak
import tj.example.zavodteplic.utils.Resource
import java.io.File


class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    var profileDataM by mutableStateOf(ProfileDataState())
        private set

    var isEditProfileLoading by mutableStateOf(false)
        private set

    var isEditProfileLoaded by mutableStateOf(false)

    var isEditing by mutableStateOf(false)

    init {
        getUserLocally()
    }


    private var _errorEvent = MutableSharedFlow<UIEvent>()
    val errorEvent = _errorEvent.asSharedFlow()

    fun getUserLocally() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getUserLocally()
            withContext(Dispatchers.Main) {
                if (res != null) profileDataM = profileDataM.copy(
                    data = res, isLoading = false
                ) else {
                    getUser()
                }
            }
        }
    }

    suspend fun getUser() {
        repository.getUser().collect { result ->
            when (result) {
                is Resource.Success -> {
                    withContext(Dispatchers.IO) {
                        repository.saveUserLocally(result.data?.profileData?.copy(avatar = "media/avatars/400x400/" + result.data.profileData.avatar))
                        getUserLocally()
                    }
                }

                is Resource.Error -> {
                    if (result.recreateViewModel) {
                        _errorEvent.emit(UIEvent.RecreateViewModel)
                    } else {
                        profileDataM = profileDataM.copy(
                            data = result.data?.profileData,
                            isLoading = false,
                        )
                        _errorEvent.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                }

                is Resource.Loading -> {
                    profileDataM = profileDataM.copy(
                        data = result.data?.profileData, isLoading = true
                    )
                }
            }
        }
    }

    fun saveEdit(
        profileData: ProfileData, file: File? = null
    ) {
        viewModelScope.launch {

            repository.saveEdit(
                profileData.name,
                profileData.username,
                profileData.birthday,
                profileData.city,
                profileData.vk,
                profileData.instagram,
                profileData.status,
                if (file == null) {
                    null
                } else UploadPhotoBody(file.name, encodeImageToBase64(file))
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        withContext(Dispatchers.IO) {
                            repository.saveUserLocally(
                                profileData.copy(avatar = if (file == null) profileDataM.data?.avatar else result.data?.avatars?.bigAvatar)
                            )

                            isEditProfileLoaded = true
                            isEditProfileLoading = false
                        }
                    }

                    is Resource.Error -> {
                        if (result.recreateViewModel) {
                            _errorEvent.emit(UIEvent.RecreateViewModel)
                        } else {
                            _errorEvent.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                            isEditProfileLoading = false
                        }
                    }

                    is Resource.Loading -> {
                        isEditProfileLoading = true
                    }
                }
            }
            getUserLocally()
        }
    }

    fun savePhotoUri(id: Int) {
//        repository.savePhotoUri(id,)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                ProfileViewModel(
                    ProfileRepositoryProvide.provideProfileRepository(application)
                )
            }
        }
    }

}

fun getProfileItems(data: ProfileData): List<ProfileDataItem> {
    val list = mutableListOf<ProfileDataItem>()
    if (data.city != null) list.add(ProfileDataItem(R.drawable.ic_location, "Город", data.city))
    if (data.last != null) list.add(
        ProfileDataItem(
            R.drawable.ic_clock, "Время посещения", data.last
        )
    )
    if (data.vk != null) list.add(ProfileDataItem(R.drawable.ic_vk, "Вконтакте", data.vk))
    if (data.birthday != null) list.add(
        ProfileDataItem(
            R.drawable.ic_calendar, "Дата рождения", data.birthday
        )
    )
    if (data.birthday != null) list.add(
        ProfileDataItem(
            R.drawable.ic_zodiak, "Знак зодиака", getZodiacZnak(
                data.birthday.substringAfterLast("-").toInt(),
                data.birthday.substringAfter("-").substringBeforeLast("-").toInt()
            )?.name ?: ""
        )
    )
    if (data.completedTask != null) list.add(
        ProfileDataItem(
            R.drawable.ic_check, "Задачи", data.completedTask.toString()
        )
    )
    if (data.instagram != null) list.add(
        ProfileDataItem(
            R.drawable.ic_instagram, "Инстаграм", data.instagram
        )
    )
    if (data.created != null) list.add(
        ProfileDataItem(
            R.drawable.ic_calendar, "Создан", data.created.substringBefore("T")
        )
    )

    if (data.status != null) list.add(ProfileDataItem(R.drawable.ic_check, "Статус", data.status))

    return list
}

fun encodeImageToBase64(imageFile: File): String {
    val bytes = imageFile.readBytes()
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}