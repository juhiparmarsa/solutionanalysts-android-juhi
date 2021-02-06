package com.test.imaginato.database.remote

import androidx.annotation.Keep

@Keep
enum class DataLoader {
    LOADING_ALL,
    LOADING_MORE,
    LOADING_CLOSE
}