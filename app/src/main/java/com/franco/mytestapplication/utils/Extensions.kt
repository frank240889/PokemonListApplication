package com.franco.mytestapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.franco.mytestapplication.domain.model.remote.detail.Ability
import com.franco.mytestapplication.domain.model.remote.detail.Type

/**
 * Provides some helper methods.
 */

const val EMPTY_STRING = ""

fun String?.orDefault(default: String = EMPTY_STRING) = this ?: default

fun ImageView.loadUrlImage(url: String? = "") {
        Glide
            .with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun List<Type>.toSingleTextLineType() = this.joinToString { it.type?.name.orEmpty() }

fun List<Ability>.toSingleTextLineAbility() = this.joinToString { it.ability?.name.orEmpty() }