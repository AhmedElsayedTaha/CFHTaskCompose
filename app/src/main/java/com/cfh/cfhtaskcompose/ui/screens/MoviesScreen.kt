package com.cfh.cfhtaskcompose.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cfh.domain.model.Movies
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import androidx.paging.compose.itemKey
import com.google.gson.Gson


@Composable
fun MoviesScreen(
    movies: LazyPagingItems<Movies>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
){
    val context = LocalContext.current
    LaunchedEffect(key1 = movies.loadState) {
        if(movies.loadState.refresh is LoadState.Error){
            Toast.makeText(context, (movies.loadState.refresh as LoadState.Error).error.message.toString(),
                Toast.LENGTH_LONG).show()
        }

    }
    
    Box(modifier = modifier.fillMaxSize()){
        if(movies.loadState.refresh is LoadState.Loading){
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.Center)
            )
        }else{
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = modifier.fillMaxSize()) {
                items(count = movies.itemCount, key = movies.itemKey{
                    movie -> movie.id
                }){
                    MovieItem(movies = movies[it] ?: Movies()){
                        val movieJson = Gson().toJson(movies[it]) // Convert to JSON
                        val encodedMovieJson = Uri.encode(movieJson)
                        navHostController.navigate("movie_details/${encodedMovieJson}")
                    }
                }

                item {
                    if(movies.loadState.append is LoadState.Loading){
                        CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}