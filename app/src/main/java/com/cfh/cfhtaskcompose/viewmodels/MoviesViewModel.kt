package com.cfh.cfhtaskcompose.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cfh.cfhtaskcompose.intent.MoviesIntent
import com.cfh.cfhtaskcompose.utils.ApiState
import com.cfh.domain.model.Movies
import com.cfh.domain.usecases.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val moviesIntentMutableLiveData = MutableLiveData<MoviesIntent>()
    private val moviesIntentObserver = Observer<MoviesIntent> {
        when (it) {
            is MoviesIntent.PopularMoviesIntent -> getPopularMovies(it.language,it.includeAdult)
        }
    }
    init {
        moviesIntentMutableLiveData.observeForever(moviesIntentObserver)
    }

    internal fun send(moviesIntent: MoviesIntent) {
        moviesIntentMutableLiveData.value = moviesIntent
    }

    private val _moviesStateFlow = MutableStateFlow<PagingData<Movies>>(PagingData.empty())
    val moviesStateFlow = _moviesStateFlow


    private fun getPopularMovies(language: String,includeAdult: Boolean) {

        viewModelScope.launch {
            try {
                moviesUseCase.execute(language,includeAdult)
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _moviesStateFlow.value = pagingData
                    }
            } catch (e: Exception) {
                Log.e("MoviesViewModel", "Error fetching movies: ${e.message}", e)
                _moviesStateFlow.emit(PagingData.empty())
            }
        }
    }

    fun removeObserver(){
        moviesIntentMutableLiveData.removeObserver(moviesIntentObserver)
    }
}