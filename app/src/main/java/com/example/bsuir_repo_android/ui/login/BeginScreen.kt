package com.example.bsuir_repo_android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bsuir_repo_android.R
import com.example.bsuir_repo_android.model.navigation.ScreenRoute

@Composable
fun BeginScreen(navController: NavHostController) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
        ) {
            Text(
                text = stringResource(id = R.string.begin_welcome),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier.weight(3f),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
            ) {
                FormButton(
                    buttonText = stringResource(id = R.string.sign_up),
                    onClick = { navController.navigate(ScreenRoute.SignupScreen.route) },
                )
                Spacer(modifier = Modifier.height(16.dp))
                FormButton(
                    buttonText = stringResource(id = R.string.sign_in),
                    onClick = { navController.navigate(ScreenRoute.LoginScreen.route) },
                )
            }
        }
    }
}

/*@Preview
@Composable
fun BeginScreenPreview() {
    BeginScreen()
}*/
