package com.novacodestudios.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SipButton(modifier: Modifier = Modifier,text:String,onClick:()->Unit) {
    Button(
        modifier = modifier,
        onClick = onClick
    ){
        Text(text=text)
    }
}

@Composable
fun SipOutLinedButton(modifier: Modifier = Modifier,text:String,onClick:()->Unit) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick
    ){
        Text(text=text)
    }
}

@Composable
fun SipTextButton(modifier: Modifier = Modifier, text: String,onClick: () -> Unit) {
    TextButton(modifier = modifier, onClick = onClick,){
        Text(text=text)
    }
}
