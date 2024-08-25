package tj.example.zavodteplic.utils

sealed class Resource<T>(val data : T? = null,val message : String? = null,val recreateViewModel: Boolean = false) {
    class Success<T>(data : T) : Resource<T>(data)
    class Loading<T>(data : T? = null) : Resource<T>(data)
    class Error<T>(message: String? = null, data: T? = null, recreateViewModel : Boolean = false) : Resource<T>(data,message,recreateViewModel)
}
