package com.comunidadedevspace.taskbeats.news.domain.usecase

data class NewsListUseCase(
    val getAllNewsUseCase: GetAllNewsUseCase,
    val getTopNewsUseCase: GetTopNewsUseCase,
    val insertNewsUseCase: InsertNewsUseCase,
    val deleteNewsByIdUseCase: DeleteNewsByIdUseCase,
    val getAllFavoriteNewsUseCase: GetAllFavoriteNewsUseCase
)