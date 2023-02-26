package com.naminov.smobile.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {
    private var pageSizeLast: Int = 0

    abstract suspend fun loadData(page: Int, pageSize: Int): List<T>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        var page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        if (pageSizeLast != pageSize) {
            page = (pageSizeLast / pageSize) + 1
            pageSizeLast = pageSize
        }

        val data = try {
            loadData(page, pageSize)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

        val prevKey = if (page == 1) null else page - 1
        val nextKey = if (data.size < pageSize) null else page + 1

        return LoadResult.Page(data, prevKey, nextKey)
    }
}