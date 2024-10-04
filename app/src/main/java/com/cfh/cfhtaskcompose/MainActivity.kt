package com.cfh.cfhtaskcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.cfh.cfhtaskcompose.ui.theme.CFHTaskComposeTheme
import com.cfh.cfhtaskcompose.viewmodels.MoviesViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.cfh.cfhtaskcompose.intent.MoviesIntent
import com.cfh.cfhtaskcompose.ui.screens.MovieItemDetails
import com.cfh.cfhtaskcompose.ui.screens.MoviesScreen
import com.cfh.cfhtaskcompose.utils.Consts
import com.cfh.domain.model.Movies
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CFHTaskComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   val viewModel = hiltViewModel<MoviesViewModel>()
                    viewModel.send(MoviesIntent.PopularMoviesIntent(Consts.LANGUAGE_VALUE,false))
                    val movies = viewModel.moviesStateFlow.collectAsLazyPagingItems()
                     AppNavigation(movies = movies,
                         modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun AppNavigation(movies: LazyPagingItems<Movies>,
                      modifier: Modifier) {
        val navController = rememberNavController()
        NavHost(navController = navController,  startDestination = "movies" ){
            composable("movies") {
                MoviesScreen(movies = movies,
                    modifier = modifier,
                    navHostController = navController)
            }
            composable("movie_details/{movieJson}") { backStackEntry ->
                val movieJson = backStackEntry.arguments?.getString("movieJson")
                val movie: Movies = Gson().fromJson(movieJson, Movies::class.java)
                MovieItemDetails(movies = movie,modifier = modifier)
            }
        }

    }
}

