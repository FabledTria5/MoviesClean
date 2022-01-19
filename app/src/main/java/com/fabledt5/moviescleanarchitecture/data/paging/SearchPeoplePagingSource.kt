package com.fabledt5.moviescleanarchitecture.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fabledt5.moviescleanarchitecture.data.api.MoviesApi
import com.fabledt5.moviescleanarchitecture.data.api.mapper.toDomain
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class SearchPeoplePagingSource @AssistedInject constructor(
    private val moviesApi: MoviesApi,
    @Assisted private val query: String
) : PagingSource<Int, PersonItem>() {

    @AssistedFactory
    interface Factory {
        fun create(
            query: String,
        ): SearchPeoplePagingSource
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }

    override fun getRefreshKey(state: PagingState<Int, PersonItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonItem> {
        if (query.isBlank()) return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)

        val page: Int = params.key ?: INITIAL_PAGE_NUMBER

        val searchResponse =
            moviesApi.searchPeople(searchQuery = query, page = page)

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