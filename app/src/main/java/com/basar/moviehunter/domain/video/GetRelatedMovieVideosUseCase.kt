package com.basar.moviehunter.domain.video

import com.basar.moviehunter.data.model.MovieVideosResponse
import com.basar.moviehunter.data.remote.repository.VideoRepository
import com.basar.moviehunter.domain.uimodel.RelatedMovieVideoUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRelatedMovieVideosUseCase @Inject constructor(
    val repository: VideoRepository
) : UseCase<GetRelatedMovieVideosUseCase.Params, RelatedMovieVideoUI>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<RelatedMovieVideoUI> {
        return repository.getRelatedVideoList(movieId = params.movieId).map(::response2UI)
    }

    private fun response2UI(response: MovieVideosResponse): RelatedMovieVideoUI {
        return object : Mapper<MovieVideosResponse, RelatedMovieVideoUI>() {
            override fun map(value: MovieVideosResponse): RelatedMovieVideoUI {
                with(value) {
                    return RelatedMovieVideoUI(
                        results
                    )
                }
            }
        }.map(response)
    }
}