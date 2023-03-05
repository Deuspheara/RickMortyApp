package fr.deuspheara.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fr.deuspheara.rickandmorty.data.api.CharactersApi
import fr.deuspheara.rickandmorty.data.models.ResultCharacter

class CharactersPagingSource(
    private val charactersApi: CharactersApi
): PagingSource<Int, ResultCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultCharacter> {
        val page = params.key ?: 1
        return try {
            val response = charactersApi.getCharacterPaging(page)
            val results = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (results.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}