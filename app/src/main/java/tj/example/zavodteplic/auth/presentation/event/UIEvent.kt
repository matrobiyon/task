package tj.example.zavodteplic.auth.presentation.event

sealed class UIEvent {
    data class ShowSnackbar(val message : String) : UIEvent()
}
