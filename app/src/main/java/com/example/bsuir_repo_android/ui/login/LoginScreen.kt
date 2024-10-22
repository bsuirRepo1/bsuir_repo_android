package com.example.bsuir_repo_android.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bsuir_repo_android.R
import com.example.bsuir_repo_android.model.navigation.ScreenRoute

@Composable
fun LoginScreen(navController: NavHostController) {
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
                FormHeader(text = stringResource(id = R.string.signing_in))
                Spacer(modifier = Modifier.height(16.dp))
                FormTextField(labelValue = stringResource(id = R.string.email))
                Spacer(modifier = Modifier.height(8.dp))
                FormTextField(labelValue = stringResource(id = R.string.password))
                Spacer(modifier = Modifier.height(16.dp))
                FormButton(
                    buttonText = stringResource(id = R.string.sign_in),
                    onClick = { navController.navigate(ScreenRoute.ProfileScreenRoute.route) },
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormString(
                    textValue = stringResource(id = R.string.forgot_password),
                    linkValue =
                        stringResource(
                            id = R.string.restore,
                        ),
                    onClick = {},
                )
            }
        }
    }
}

@Composable
fun FormTextField(labelValue: String) {
    var textValue by remember { mutableStateOf("") }
    TextField(
        value = textValue,
        onValueChange = { textValue = it },
        label = { Text(text = labelValue, modifier = Modifier.height(20.dp)) },
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
