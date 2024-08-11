package com.erivando.comprasonline.base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


abstract class BaseViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String?>()
    val successMessage = MutableLiveData<String?>()

    fun setLoading(isLoading:Boolean){
        this.isLoading.value = isLoading
    }
    fun setError(erroMessage:String?){
        this.errorMessage.value = erroMessage
    }
    fun setSucceMwssage(succeMessage:String?){
        this.successMessage.value = succeMessage
        setLoading(false)
    }
    fun clearMessages(clearMessage:String?) {
        this.errorMessage.value = null
        this.successMessage.value = null
    }
    fun <T> handleApiCall(apiCall: suspend () -> T, result: MutableLiveData<T>) {
        viewModelScope.launch {
            try {
                setLoading(true)
                result.value = apiCall()
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
}


