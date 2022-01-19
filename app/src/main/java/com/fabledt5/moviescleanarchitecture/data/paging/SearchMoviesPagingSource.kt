package com.fabledt5.moviescleanarchitecture.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fabledt5.moviescleanarchitecture.data.api.MoviesApi
import com.fabledt5.moviescleanarchitecture.data.api.mapper.toDomain
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class SearchMoviesPagingSource @AssistedInject constructor(
    private val moviesApi: MoviesApi,
    @Assisted private val query: String,
    @Assisted private val includeAdult: Boolean
) : PagingSource<Int, MovieItem>() {

    @AssistedFactory
    interface Factory {
        fun create(
            query: String,
            includeAdult: Boolean
        ): SearchMoviesPagingSource
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        if (query.isBlank()) return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)

        val page: Int = params.key ?: INITIAL_PAGE_NUMBER

        val searchResponse =
            moviesApi.searchMovies(searchQuery = query, page = page, includeAdult = includeAdult)

        return if (searchResponse.isSuccessful) {
            val searchedMovies = checkNotNull(searchResponse.body()).toDomain()
            val nextKey = searchResponse.body()?.let {
                if (it.page < it.totalPages) it.page + 1
                else null
            }
            val prevKey = searchResponse.body()?.let {
                if (it.page == 1) null
                else it.page - 1
            }
            LoadResult.Page(searchedMovies, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(searchResponse))
        }
    }

}