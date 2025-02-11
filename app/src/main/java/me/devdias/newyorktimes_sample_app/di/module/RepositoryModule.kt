package me.devdias.newyorktimes_sample_app.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.devdias.newyorktimes_sample_app.data.repository.TopStoriesRepositoryImpl
import me.devdias.newyorktimes_sample_app.domain.repository.TopStoriesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTopStoriesRepository(
        repositoryImpl: TopStoriesRepositoryImpl
    ): TopStoriesRepository
}