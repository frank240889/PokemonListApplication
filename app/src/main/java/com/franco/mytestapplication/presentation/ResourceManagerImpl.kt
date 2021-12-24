package com.franco.mytestapplication.presentation

import android.content.Context
import com.franco.mytestapplication.common.ResourceManager
import javax.inject.Inject

/**
 *Implementation of ResourceManager
 */
class ResourceManagerImpl @Inject constructor(
    private val context: Context
): ResourceManager() {

    override fun getString(id: Int) = context.getString(id)
}