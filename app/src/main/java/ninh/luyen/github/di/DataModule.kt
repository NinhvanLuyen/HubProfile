package ninh.luyen.github.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ninh.luyen.github.data.ProfileRepository
import ninh.luyen.github.data.ProfileRepositorySource
import ninh.luyen.github.data.UnsplashDataSource
import ninh.luyen.github.data.UnsplashRepository
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: ProfileRepository): ProfileRepositorySource

    @Binds
    @Singleton
    abstract fun provideUnsplashRepository(dataRepository: UnsplashRepository): UnsplashDataSource

}
