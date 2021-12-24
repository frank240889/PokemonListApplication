package com.franco.mytestapplication.domain.model.remote.detail


import com.google.gson.annotations.SerializedName

data class VersionGroupDetail(
    @SerializedName("level_learned_at")
    val levelLearnedAt: Int? = 0,
    @SerializedName("move_learn_method")
    val moveLearnMethod: MoveLearnMethod? = MoveLearnMethod(),
    @SerializedName("version_group")
    val versionGroup: VersionGroup? = VersionGroup()
)