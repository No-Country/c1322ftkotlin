package com.nocuntry.c1322ftkotlin.Profile

import Post
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class PostViewModel : ViewModel() {
    val posts = mutableListOf<Post>()
}

