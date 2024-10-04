package com.cfh.cfhtaskcompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cfh.cfhtaskcompose.R
import com.cfh.cfhtaskcompose.utils.Consts
import com.cfh.domain.model.Movies

@Composable
fun MovieItem(
    movies: Movies,
    onItemClick: () -> Unit
) {
    AsyncImage(
        model = Consts.IMAGE_URL + movies.posterPath,
        contentDescription = movies.title,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable (
                onClick = onItemClick
            ),
        placeholder = painterResource(id = R.drawable.image_placeholder),
        error = painterResource(id = R.drawable.image_placeholder)
    )
}