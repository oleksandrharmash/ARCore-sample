package com.android.furnitureplace.presentation

import com.android.furnitureplace.core.presentation.ViewPresenter

abstract class BaseViewPresenter<out V>(view: V) : ViewPresenter<V>(view)