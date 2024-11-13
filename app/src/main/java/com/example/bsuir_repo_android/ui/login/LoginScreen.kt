package com.example.bsuir_repo_android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bsuir_repo_android.R
import com.example.bsuir_repo_android.viewModel.AuthState
import com.example.bsuir_repo_android.viewModel.UserViewModel

enum class FieldType {
    Email,
    Username,
    Password,
    PasswordConfirm,
}

@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory),
) {
    val authViewModel = AuthState.current

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
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
                        FormHeader(text = stringResource(id = R.string.signing_in))
                        Spacer(modifier = Modifier.height(16.dp))
                        FormTextField(
                            labelValue = stringResource(id = R.string.email),
                            FieldType.Email,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FormTextField(
                            labelValue = stringResource(id = R.string.password),
                            fieldType = FieldType.Password,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        FormButton(
                            buttonText = stringResource(id = R.string.sign_in),
                            onClick = {
                                authViewModel.authoriseUser(
                                    userAuth =
                                        LoginRequest(
                                            email = userViewModel.uiState.value.email,
                                            password = userViewModel.uiState.value.password,
                                        ),
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FormString(
                            textValue = stringResource(id = R.string.forgot_password),
                            linkValue =
                                stringResource(
                                    id = R.string.restore,
                                ),
                            onClick = { navController.navigate(ScreenRoute.Auth.SignupScreen) },
                        )
                    }
                }
            }

            else -> {}
        }*/
    }
}

@Composable
fun FormTextField(
    labelValue: String,
    fieldType: FieldType,
) {
    var textValue = remember { mutableStateOf("") }

    TextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        },
        label = { Text(text = labelValue, modifier = Modifier.height(20.dp)) },
        visualTransformation =
            if (fieldType.equals(FieldType.Password) || fieldType.equals(FieldType.PasswordConfirm)) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
        keyboardOptions =
            if (fieldType.equals(FieldType.Password) || fieldType.equals(FieldType.PasswordConfirm)) {
                KeyboardOptions(keyboardType = KeyboardType.Password)
            } else if (fieldType.equals(FieldType.Email)) {
                KeyboardOptions(keyboardType = KeyboardType.Email)
            } else {
                KeyboardOptions(keyboardType = KeyboardType.Unspecified)
            },
        modifier =
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = colorResource(id = R.color.field))
                .wrapContentHeight()
                .fillMaxWidth(0.8f),
    )
}

@Composable
fun FormHeader(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.field),
        modifier = Modifier.wrapContentSize(),
    )
}

@Composable
fun FormButton(
    buttonText: String,
    onClick: () -> Unit,
) {
    Button(
        modifier =
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.8f),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button),
                contentColor = colorResource(id = R.color.form),
            ),
        onClick = onClick,
    ) {
        Text(text = buttonText, fontSize = 18.sp)
    }
}

@Composable
fun FormString(
    textValue: String,
    linkValue: String,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.8f),
    ) {
        Text(text = textValue + " ", color = colorResource(id = R.color.field), fontSize = 18.sp)
        Text(
            text = linkValue,
            color = colorResource(id = R.color.background),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onClick() },
        )
    }
}

/*@Preview
@Composable
fun loginScreenPreview() {
    LoginScreen()
}*/
