package com.android.furnitureplace.coroutines

import kotlin.coroutines.experimental.CoroutineContext

interface Executor {
    val io: CoroutineContext
    val ui: CoroutineContext
    val net: CoroutineContext
}