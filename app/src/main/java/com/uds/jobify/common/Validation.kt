package com.uds.jobify.common

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Validation {
    companion object {
        fun emptyField(data: TextInputEditText, error: TextInputLayout, message: String): Boolean {
            return if (data.text.toString().isEmpty()) {
                error.error = message
                false
            } else {
                error.isErrorEnabled = false
                true
            }

        }

        fun FieldWithLength(
            data: TextInputEditText,
            error: TextInputLayout,
            message: String,
            length: Int
        ): Boolean {
            return if (data.text.toString().isEmpty()) {
                emptyField(data, error, "Input Field can't be Empty")
                false
            } else if (data.text.toString().length < length) {
                error.error = message
                false
            } else {
                error.isErrorEnabled = false
                true
            }
        }

        fun emailField(
            data: TextInputEditText,
            error: TextInputLayout,
            emptymessage: String,
            message: String
        ): Boolean {
            return if (data.text.toString().isEmpty()) {
                error.error = emptymessage
                false
            } else {
                error.isErrorEnabled = false
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(data.text.toString()).matches()) {

                    error.isErrorEnabled = false
                    true
                } else {
                    error.error = message
                    false
                }
            }

        }


    }
}