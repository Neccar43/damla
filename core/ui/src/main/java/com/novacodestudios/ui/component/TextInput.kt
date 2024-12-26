package com.novacodestudios.ui.component

import android.util.Patterns

data class TextInput<T>(
    val value: T? = null,
    val error: String? = null,

    ) {

}

fun TextInput<String>.validateEmail(): TextInput<String> =
    when {
        value.isNullOrBlank() -> copy(error = "E-mail boş olamaz")
        !Patterns.EMAIL_ADDRESS.matcher(value)
            .matches() -> copy(error = "E-mail adresi geçerli değil")

        else -> copy(error=null)
    }

fun TextInput<String>.validatePassword(): TextInput<String> {
    val PASSWORD_MIN_LENGTH = 6
    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$")


    return when {
        value.isNullOrBlank() -> copy(error = "Şifre boş olamaz")
        value.length<PASSWORD_MIN_LENGTH -> copy(error="Şifre en az $PASSWORD_MIN_LENGTH uzunluğunda olmalıdır")
        !value.matches(regex = passwordRegex)-> copy(error = "Şifre en az bir küçük harf, bir büyük harf, bir rakam ve bir özel karakter içermelidir")
        else -> copy(error=null)
    }
}

fun TextInput<String>.validateConfirmPassword(password:String?): TextInput<String> =
    when{
        value.isNullOrBlank() -> copy(error = "Şifre boş olamaz")
        value!=password -> copy(error = "Şifreler uyuşmuyor")
        else->copy(error=null)
    }

fun <T> TextInput<T>.validateInput(fieldName:String="Alan"): TextInput<T> =
    when {
        value==null || value.toString().isBlank()-> copy(error = "$fieldName boş olamaz")
        else-> copy(error=null)
    }

fun TextInput<String>.validateNumeric(fieldName: String = "Alan"): TextInput<String> {
    return when {
        value.isNullOrBlank() -> copy(error = "$fieldName boş olamaz")
        value.toDoubleOrNull() == null -> copy(error = "$fieldName geçerli bir sayı olmalıdır")
        else -> copy(error = null)
    }
}
fun TextInput<String>.validateStock(fieldName: String = "Stok"): TextInput<String> {
    return when {
        value.isNullOrBlank() -> copy(error = null)
        value.toIntOrNull() == null -> copy(error = "$fieldName geçerli bir tam sayı olmalıdır")
        value.toInt() < 0 -> copy(error = "$fieldName negatif olamaz")
        else -> copy(error = null)
    }
}
