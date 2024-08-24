package tj.example.zavodteplic.profile.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import tj.example.zavodteplic.R
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.profile.data.local.ProfileData
import tj.example.zavodteplic.profile.data.repository.ProfileRepository
import tj.example.zavodteplic.profile.presentation.model.ProfileDataItem
import tj.example.zavodteplic.profile.presentation.model.ProfileDataState
import tj.example.zavodteplic.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    var profileData by mutableStateOf(ProfileDataState())
        private set

    var isProfileLoading by mutableStateOf(false)
        private set

    private var _errorEvent = MutableSharedFlow<UIEvent>()
    val errorEvent = _errorEvent.asSharedFlow()

    fun getUser() {
        viewModelScope.launch {
            repository.getUser().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        repository.saveUserLocally(result.data?.profileData)
                        profileData = profileData.copy(
                            data = result.data?.profileData,
                            isLoading = false
                        )

                    }

                    is Resource.Error -> {
                        profileData = profileData.copy(
                            data = result.data?.profileData,
                            isLoading = false,
                        )
                        _errorEvent.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }

                    is Resource.Loading -> {
                        profileData = profileData.copy(
                            data = result.data?.profileData,
                            isLoading = false
                        )
                    }

                    else -> {}
                }
            }
        }
    }

}

fun getProfileItems(data: ProfileData): List<ProfileDataItem> {
    val list = mutableListOf<ProfileDataItem>()
    if (data.city != null) list.add(ProfileDataItem(R.drawable.ic_location, "Город", data.city))
    if (data.last != null) list.add(
        ProfileDataItem(
            R.drawable.ic_clock,
            "Время посещения",
            data.last
        )
    )
    if (data.vk != null) list.add(ProfileDataItem(R.drawable.ic_vk, "Вконтакте", data.vk))
    if (data.birthday != null) list.add(
        ProfileDataItem(
            R.drawable.ic_calendar,
            "Дата рождения",
            data.birthday
        )
    )
    if (data.znakZodiaka != null) list.add(
        ProfileDataItem(
            R.drawable.ic_zodiak,
            "Знак зодиака",
            data.znakZodiaka
        )
    )
    if (data.completedTask != null) list.add(
        ProfileDataItem(
            R.drawable.ic_check,
            "Задачи",
            data.completedTask.toString()
        )
    )
    if (data.instagram != null) list.add(
        ProfileDataItem(
            R.drawable.ic_instagram,
            "Инстаграм",
            data.instagram
        )
    )
    if (data.created != null)
        list.add(
            ProfileDataItem(
                R.drawable.ic_calendar,
                "Создан",
                data.created.substringBefore("T")
            )
        )

    if (data.status != null) list.add(ProfileDataItem(R.drawable.ic_check, "Статус", data.status))

    return list
}