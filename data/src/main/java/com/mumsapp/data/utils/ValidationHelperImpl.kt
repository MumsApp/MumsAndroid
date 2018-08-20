package com.mumsapp.data.utils

import com.mumsapp.domain.utils.ValidationHelper
import java.util.regex.Pattern
import javax.inject.Inject

class ValidationHelperImpl : ValidationHelper {

    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )
    private val PASSWORD_PATTERN = Pattern.compile(
            "^.{4,}\$"
    )

    @Inject
    constructor()

    override fun checkIsNotEmpty(reference: String?): Boolean {
        return reference != null && !reference.isEmpty()
    }

    override fun checkIsNotEmpty(reference: Collection<*>?): Boolean {
        return reference != null && !reference.isEmpty()
    }

    override fun checkIsNotEmpty(obj: Any?): Boolean {
        return obj != null
    }

    override fun checkEmailValid(email: String?): Boolean {

        return checkIsNotEmpty(email) && EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    override fun checkPasswordValid(password: String?): Boolean {
        //conditions:
        //at least 4 characters

        return PASSWORD_PATTERN.matcher(password).matches()
    }

    override fun checkNameValid(name: String?): Boolean {
        //conditions:
        //allowed characters: letters and symbols: `-` and `'`

        if (name == null || name.length < 2) return false

        val array = name.toCharArray()

        for (ch in array) {
            if (!Character.isLetter(ch) && ch != '\'' && ch != '-') {
                return false
            }
        }
        return true
    }

    override fun areFieldsTheSame(first: String?, second: String?): Boolean {
        if(!checkIsNotEmpty(first) || !checkIsNotEmpty(second)) {
            return false
        }

        return first.equals(second)
    }

    override fun checkIsCorrectPrice(price: String?): Boolean {
        return checkIsCorrectPrice(price?.toFloat())
    }

    override fun checkIsCorrectPrice(price: Float?): Boolean {
        return checkIsNotEmpty(price) && price!! > 0
    }
}