package com.example.bsuir_repo_android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bsuir_repo_android.R
import com.example.bsuir_repo_android.ui.navigation.ScreenRoute
import com.example.bsuir_repo_android.viewModel.AuthState
import com.example.bsuir_repo_android.viewModel.UserViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory),
) {
    val authViewModel = AuthState.current
    val state by authViewModel.state.collectAsState()
    val apiState by authViewModel.apiState.collectAsState()
    val savedUser by userViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = apiState) {
        if (apiState.isSuccess) {
            navController.navigate(ScreenRoute.Main.MainScreen.route)
        }

        if (!apiState.error.isNullOrEmpty()) {
        }
    }

    Scaffold { padding ->

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.background)),
        ) {
            Text(
                text = "${savedUser.email}, ${savedUser.username}, ${savedUser.password}, ${savedUser.password_confirm}",
                modifier = Modifier.matchParentSize(),
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = colorResource(id = R.color.form))
                        .wrapContentSize()
                        .padding(20.dp),
            ) {
                FormHeader(text = stringResource(id = R.string.signing_up))
                Spacer(modifier = Modifier.height(16.dp))
                UserTextField(
                    value = state.email ?: "",
                    onValueChange = authViewModel::onEmailChanges,
                    placeholder = "Enter Email",
                    error = apiState.emailEmpty,
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserTextField(
                    value = state.username ?: "",
                    onValueChange = authViewModel::onUsernameChanges,
                    placeholder = "Enter Username",
                    error = apiState.usernameEmpty,
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserTextField(
                    value = state.password ?: "",
                    onValueChange = authViewModel::onPasswordChanges,
                    placeholder = "Enter Password",
                    error = apiState.passwordEmpty,
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserTextField(
                    value = state.passwordConfirm ?: "",
                    onValueChange = authViewModel::onPasswordConfirmChanges,
                    placeholder = "Enter Password Confirmation",
                    error = apiState.passwordConfirmEmpty,
                )
                Spacer(modifier = Modifier.height(16.dp))

                FormButton(buttonText = stringResource(id = R.string.sign_up), onClick = {
                    authViewModel.authUser()
                })
                Spacer(modifier = Modifier.height(8.dp))
                FormString(
                    textValue = stringResource(id = R.string.already_signed_up),
                    linkValue =
                        stringResource(
                            id = R.string.sign_in,
                        ),
                    onClick = {
                        navController.navigate(ScreenRoute.Auth.LoginScreen.route)
                    },
                )
            }
        }
    }
    /*Surface(
        modifier = Modifier.fillMaxSize(),
    ) {*/
    // val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

        /*when (authViewModel.authUiState) {
            is AuthUiState.Error -> ErrorScreen()
            is AuthUiState.Loading -> CircularProgressIndicator()
            is AuthUiState.Undefined -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.background)),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier =
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = colorResource(id = R.color.form))
                            .wrapContentSize()
                            .padding(20.dp),
                    ) {
                        FormHeader(text = stringResource(id = R.string.signing_up))
                        Spacer(modifier = Modifier.height(16.dp))

                        FormTextField(
                            labelValue = stringResource(id = R.string.email),
                            fieldType = FieldType.Email,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FormTextField(
                            labelValue = stringResource(id = R.string.username),
                            fieldType = FieldType.Username,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FormTextField(
                            labelValue = stringResource(id = R.string.password),
                            fieldType = FieldType.Password,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FormTextField(
                            labelValue = stringResource(id = R.string.repeat_password),
                            fieldType = FieldType.PasswordConfirm,
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        FormButton(buttonText = stringResource(id = R.string.sign_up), onClick = {
                            authViewModel.registerUser(
                                userReg =
                                    UserReg(
                                        email = userViewModel.uiState.value.email,
                                        username = userViewModel.uiState.value.username,
                                        password = userViewModel.uiState.value.password,
                                        password_confirm = userViewModel.uiState.value.password_confirm,
                                    ),
                            )
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                        FormString(
                            textValue = stringResource(id = R.string.already_signed_up),
                            linkValue =
                                stringResource(
                                    id = R.string.sign_in,
                                ),
                            onClick = {
                                navController.navigate(ScreenRoute.Auth.LoginScreen.route)
                            },
                        )
                    }
                }
            }

            else -> {
            }
        }*/
    // }
}

@Composable
fun UserTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            placeholder = {
                Text(text = placeholder)
            },
            onValueChange = onValueChange,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
        )

        if (error != null) {
            Text(text = error, color = Color.Red)
        }
    }
}
