package com.mohanjp.mrcoopertask.presentation.util

fun String?.nullAsEmpty(): String {
    return this ?: ""
}