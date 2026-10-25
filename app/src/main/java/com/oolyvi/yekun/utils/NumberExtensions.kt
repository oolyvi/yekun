package com.oolyvi.yekun.utils

/**
 * return if true 1 else 0 on nsfw part
 */
fun Boolean?.toInt(): Int = if (this == true) 1 else 0