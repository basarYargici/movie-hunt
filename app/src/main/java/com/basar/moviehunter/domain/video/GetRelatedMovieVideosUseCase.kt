package com.basar.moviehunter.domain.video

import com.basar.moviehunter.data.model.MovieVideosResponse
import com.basar.moviehunter.data.remote.repository.VideoRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRelatedMovieVideosUseCase @Inject constructor(
    val repository: VideoRepository
) : UseCase<GetRelatedMovieVideosUseCase.Params, MovieVideosResponse>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<MovieVideosResponse> {
        return repository.getRelatedVideoList(movieId = params.movieId)
    }
    // TODO: responseToUI
}