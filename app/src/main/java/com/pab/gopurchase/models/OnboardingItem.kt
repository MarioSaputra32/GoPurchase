package com.pab.gopurchase.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingItem(
    @DrawableRes val imageRes: Int,
    @StringRes val descriptionRes: Int
)
