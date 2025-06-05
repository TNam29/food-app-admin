package com.breens.orderfood.feature_tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.foodadminapp.common.Result
import com.breens.foodadminapp.data.repositories.Repository
import com.breens.foodadminapp.feature_tasks.events.SignInScreenUiEvent
import com.breens.foodadminapp.feature_tasks.side_effects.SignInScreenSideEffects
import com.breens.foodadminapp.feature_tasks.state.SignInScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AccountViewModel @Inject constructor(private val accountRepository: Repository) : ViewModel() {
    private val _stateAccount: MutableStateFlow<SignInScreenUiState> =
        MutableStateFlow(SignInScreenUiState())
    val stateAccount: StateFlow<SignInScreenUiState> = _stateAccount.asStateFlow()

    private val _effectAccount: Channel<SignInScreenSideEffects> = Channel()
    val effectAccount = _effectAccount.receiveAsFlow()

    init {
        sendEvent(SignInScreenUiEvent.LoginUser)
        viewModelScope.launch {
            while (true) {
                delay(5000) // Thực hiện cập nhật mỗi 5 giây
                sendEvent(SignInScreenUiEvent.GetAccount)
            }
        }
    }
    fun sendEvent(event: SignInScreenUiEvent) {
        reduce(oldStateAccount = _stateAccount.value, event = event)
    }
    private fun setEffect(builder: () -> SignInScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effectAccount.send(effectValue) }
    }

    private fun setState(newStateAccount: SignInScreenUiState) {
        _stateAccount.value = newStateAccount
    }




    private fun reduce(oldStateAccount: SignInScreenUiState, event: SignInScreenUiEvent) {
        when (event) {
            SignInScreenUiEvent.GetAccount -> {
                getAccount(oldStateAccount = oldStateAccount)
            }
            SignInScreenUiEvent.LoginUser -> {
                loginUser(oldStateAccount = oldStateAccount)
            }
            is SignInScreenUiEvent.RegisterUser -> {
                registerUser(oldStateAccount = oldStateAccount, email = event.email, password = event.password, firstname = event.firstname, lastname = event.lastname)
            }
            is SignInScreenUiEvent.LoginUser-> {
                loginUser(oldStateAccount = oldStateAccount)
            }
            is SignInScreenUiEvent.LogoutUser-> {
                logoutUser(oldStateAccount = oldStateAccount)
            }
            is SignInScreenUiEvent.OnChangeFirstname -> {
                onChangeFirstname(oldStateAccount = oldStateAccount, firstname = event.firstname)
            }
            is SignInScreenUiEvent.OnChangeLastname -> {
                onChangeLastname(oldStateAccount = oldStateAccount, lastname = event.lastname)
            }
            is SignInScreenUiEvent.OnChangeEmail -> {
                onChangeEmail(oldStateAccount = oldStateAccount, email = event.email)
            }

            is SignInScreenUiEvent.OnChangePassword -> {
                onChangePassword(oldStateAccount = oldStateAccount, password = event.password)
            }


        }
    }



    private fun registerUser(firstname : String, lastname: String, email: String, password: String, oldStateAccount: SignInScreenUiState) {
        viewModelScope.launch {
            setState(oldStateAccount.copy(isLoading = true))

            when (val resultAccount = accountRepository.registerUser(firstName = firstname, lastName = lastname, email = email, password = password)) {
                is com.breens.foodadminapp.common.Result.Failure -> {
                    setState(oldStateAccount.copy(isLoading = false))

                    val errorMessage =
                        resultAccount.exception.message ?: "An error occurred when adding task"
                    setEffect { SignInScreenSideEffects.ShowSnackBarMessage(messageAccount = errorMessage) }
                }

                is com.breens.foodadminapp.common.Result.Success -> {
                    setState(
                        oldStateAccount.copy(
                            isLoading = false,

                            ),
                    )


                    setEffect { SignInScreenSideEffects.ShowSnackBarMessage(messageAccount = "Đăng kí thành công") }
                }
            }
        }
    }
    private fun loginUser( oldStateAccount: SignInScreenUiState) {
        viewModelScope.launch {
            setState(oldStateAccount.copy(isLoading = true))
            val email = oldStateAccount.currentEmail
            val password = oldStateAccount.currentPassword

            when (val resultAccount = accountRepository.loginUser(email = email, password = password)) {
                is com.breens.foodadminapp.common.Result.Failure -> {
                    setState(oldStateAccount.copy(isLoading = false))

                    val errorMessage =
                        resultAccount.exception.message ?: " Đăng nhập thất bại!"
                    /*setEffect { SignInScreenSideEffects.ShowSnackBarMessage(messageAccount = errorMessage) }*/
                }

                is Result.Success -> {
                    val accounts = resultAccount.data
                    setState(
                        oldStateAccount.copy(
                            isLoading = false,
                            accounts = accounts
                            ),
                    )

                    setEffect { SignInScreenSideEffects.NavigateToHome }
                    setEffect { SignInScreenSideEffects.ShowSnackBarMessage(messageAccount = "Đăng nhập thành công") }
                }
            }
        }
    }
    private fun getAccount( oldStateAccount: SignInScreenUiState) {
        viewModelScope.launch {
            when (val resultAccount = accountRepository.getAccount()) {
                is Result.Failure -> {
                }
                is Result.Success -> {
                    val accounts = resultAccount.data
                    setState(
                        oldStateAccount.copy(
                            accounts = accounts
                        ),
                    )

                }
            }

        }
    }
    private fun logoutUser( oldStateAccount: SignInScreenUiState) {
        viewModelScope.launch {
            setState(oldStateAccount.copy(isLoading = true))

            when (val resultAccount = accountRepository.logoutUser()) {
                is Result.Failure -> {
                    setState(oldStateAccount.copy(isLoading = false))

                    val errorMessage =
                        resultAccount.exception.message ?: " Đăng xuất thất bại!"
                    setEffect { SignInScreenSideEffects.ShowSnackBarMessage(messageAccount = errorMessage) }
                }

                is Result.Success -> {
                    setState(
                        oldStateAccount.copy(
                            isLoading = false,
                            accounts = emptyList(),
                            currentEmail = "",
                            currentPassword = ""
                        ),
                    )
                    sendEvent(SignInScreenUiEvent.LoginUser)
                    setEffect { SignInScreenSideEffects.ShowSnackBarMessage(messageAccount = "Đăng xuất thành công") }
                }
            }
        }
    }
    private fun onChangeFirstname(oldStateAccount: SignInScreenUiState, firstname: String) {
        setState(oldStateAccount.copy(currentFirstname = firstname))
    }
    private fun onChangeLastname(oldStateAccount: SignInScreenUiState, lastname: String) {
        setState(oldStateAccount.copy(currentLastName = lastname))
    }

    private fun onChangeEmail(oldStateAccount: SignInScreenUiState, email: String) {
        setState(oldStateAccount.copy(currentEmail = email))
    }

    private fun onChangePassword(oldStateAccount: SignInScreenUiState, password: String) {
        setState(oldStateAccount.copy(currentPassword = password))
    }

}