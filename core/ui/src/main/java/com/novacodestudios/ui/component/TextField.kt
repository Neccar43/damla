package com.novacodestudios.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Composable
fun SipTextField(
    modifier: Modifier = Modifier,
    input: TextInput<String>,
    onValueChange: (String) -> Unit,
    label: String? = null,
    keyboardType:KeyboardType=KeyboardType.Text,
    leadingIcon:ImageVector?=null,
    trailingIcon:ImageVector?=null,
    readOnly: Boolean = false,
) {
    TextField(
        modifier = modifier,
        value = input.value ?: "",
        onValueChange = onValueChange,
        isError = input.error.isNotNull(),
        label = if (label.isNotNull()) { { Text(text = label) } } else null,
        supportingText = if (input.error.isNotNull()){ {Text(text = input.error)}} else null,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        leadingIcon = if (leadingIcon.isNotNull()){{ Icon(leadingIcon,null) }}else null,
        trailingIcon=if (trailingIcon.isNotNull()){{ Icon(trailingIcon,null) }}else null,
        readOnly = readOnly,
    )
}

@Composable
fun SipOutlinedTextField(
    modifier: Modifier = Modifier,
    input: TextInput<String>,
    onValueChange: (String) -> Unit,
    label: String? = null,
    keyboardType:KeyboardType=KeyboardType.Text,
    leadingIcon:ImageVector?=null,
    trailingIcon:ImageVector?=null,
    readOnly:Boolean=false,
    singleLine:Boolean=false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = input.value ?: "",
        onValueChange = onValueChange,
        isError = input.error.isNotNull(),
        label = if (label.isNotNull()) { { Text(text = label) } } else null,
        supportingText = if (input.error.isNotNull()){ {Text(text = input.error)}} else null,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        leadingIcon = if (leadingIcon.isNotNull()){{ Icon(leadingIcon,null) }}else null,
        trailingIcon=if (trailingIcon.isNotNull()){{ Icon(trailingIcon,null) }}else null,
        readOnly = readOnly,
        shape = MaterialTheme.shapes.large,
        singleLine =singleLine ,
        //enabled = !readOnly,

    )
}

@Composable
fun SipEmailField(
    modifier: Modifier = Modifier,
    input: TextInput<String>,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = input.value ?:"",
        onValueChange = onValueChange,
        label = { Text(text = "Email") },
        isError = input.error.isNotNull(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        trailingIcon = if (input.error.isNotNull()) { { Icon(Icons.Default.Error, contentDescription = null) } } else null,
        supportingText = if (input.error.isNotNull()) { { Text(text = input.error) } } else null,
        shape = MaterialTheme.shapes.large,
        /*colors = OutlinedTextFieldDefaults.colors().copy(
            focusedContainerColor = TextFieldDefaults.colors().focusedContainerColor,
            unfocusedContainerColor = TextFieldDefaults.colors().unfocusedContainerColor,
            disabledContainerColor = TextFieldDefaults.colors().disabledContainerColor,
        )*/
    )
}

@Composable
fun SipPasswordField(
    modifier: Modifier = Modifier,
    input: TextInput<String>,
    onValueChange: (String) -> Unit,
) {
    var passwordVisible by remember{ mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = input.value?:"",
        onValueChange = onValueChange,
        label = { Text("Şifre") },
        isError = input.error.isNotNull(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { passwordVisible=!passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = if (input.error.isNotNull()) { { Text(text = input.error) } } else null,
        shape = MaterialTheme.shapes.large,
    )
}

@OptIn(ExperimentalContracts::class)
fun Any?.isNotNull():Boolean{
    contract {
        returns(true) implies (this@isNotNull !=null)
    }
    return this!=null
}

