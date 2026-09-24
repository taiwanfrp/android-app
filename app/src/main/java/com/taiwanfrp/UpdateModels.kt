package com.taiwanfrp

import com.google.gson.annotations.SerializedName

data class GithubRelease(
    @SerializedName("tag_name") val tagName: String,
    val name: String?,
    val body: String?,
    val assets: List<GithubAsset>,
)

data class GithubAsset(
    val name: String,
    @SerializedName("browser_download_url") val downloadUrl: String,
    val size: Long
)

data class GithubTag(
    val name: String,
    val commit: GithubCommitRef
)

data class GithubCommitRef(
    val sha: String
)

data class GithubCommit(
    val commit: GithubCommitDetail
)

data class GithubCommitDetail(
    val message: String
)
