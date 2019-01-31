package com.uharris.wedding.domain.model

data class Wish(
    var user: User = User(),
    var comment: String = "")