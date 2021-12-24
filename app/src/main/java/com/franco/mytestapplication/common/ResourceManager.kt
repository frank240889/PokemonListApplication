package com.franco.mytestapplication.common

import androidx.annotation.StringRes

/**
* Resource manager to access string resources without using context directly.
*/
abstract class ResourceManager {

    abstract fun getString(@StringRes id: Int): String
}