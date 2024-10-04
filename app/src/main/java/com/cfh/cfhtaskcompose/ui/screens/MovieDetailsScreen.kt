package com.cfh.cfhtaskcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cfh.cfhtaskcompose.R
import com.cfh.cfhtaskcompose.utils.Consts
import com.cfh.domain.model.Movies

@Composable
fun MovieItemDetails(movies: Movies,
                     modifier: Modifier){
    Column(modifier = modifier.fillMaxSize()
        .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        AsyncImage(model = Consts.IMAGE_URL + movies.backdropPath,
            contentDescription = movies.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            placeholder = painterResource(id = R.drawable.image_placeholder),
            error = painterResource(id = R.drawable.image_placeholder)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = movies.title, fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = movies.overview, fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "${movies.voteAverage}/10",fontSize = 20.sp)
    }
}