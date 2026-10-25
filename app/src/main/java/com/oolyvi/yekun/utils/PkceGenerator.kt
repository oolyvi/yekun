package com.oolyvi.yekun.utils

/**
 * chars generator for authorization
 */
object PkceGenerator {

    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '-' + '.' + '_' + '~'

    fun generateVerifier(length: Int): String {
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}