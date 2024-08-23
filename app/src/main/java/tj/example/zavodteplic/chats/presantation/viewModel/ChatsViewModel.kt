package tj.example.zavodteplic.chats.presantation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChatsViewModel @Inject constructor() : ViewModel() {

    var shouldNavigateToChatScreen by mutableStateOf(false)

}