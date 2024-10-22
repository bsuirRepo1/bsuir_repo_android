package com.example.bsuir_repo_android.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bsuir_repo_android.R
import com.example.bsuir_repo_android.model.navigation.ScreenRoute

@Composable
fun SignupScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        var password by remember { mutableStateOf("") }

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

                FormTextField(labelValue = stringResource(id = R.string.email))
                Spacer(modifier = Modifier.height(8.dp))
                FormTextField(labelValue = stringResource(id = R.string.username))
                Spacer(modifier = Modifier.height(8.dp))
                FormTextField(labelValue = stringResource(id = R.string.password))
                Spacer(modifier = Modifier.height(8.dp))
                FormTextField(labelValue = stringResource(id = R.string.repeat_password))
                Spacer(modifier = Modifier.height(16.dp))

                FormButton(buttonText = stringResource(id = R.string.sign_up), onClick = {
                    navController.navigate(ScreenRoute.ProfileScreenRoute.route)
                })
                Spacer(modifier = Modifier.height(8.dp))
                FormString(
                    textValue = stringResource(id = R.string.already_signed_up),
                    linkValue =
                        stringResource(
                            id = R.string.sign_in,
                        ),
                    onClick = {
                        navController.navigate(ScreenRoute.LoginScreen.route)
                        Log.d("Login", "LogIn!")
                    },
                )
            }
        }
    }
}

/*@Preview
@Composable
fun SignupPreview() {
    SignupScreen()
}*/
