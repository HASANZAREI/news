package com.example.news.di

import android.app.Application
import androidx.room.Room
import com.example.news.data.local.NewsDao
import com.example.news.data.local.NewsDataBase
import com.example.news.data.local.NewsTypeConvertor
import com.example.news.data.manager.LocalUserManagerImpl
import com.example.news.data.remote.NewsApi
import com.example.news.data.repository.NewsRepoImpl
import com.example.news.domain.manger.LocalUserManger
import com.example.news.domain.repository.NewsRepo
import com.example.news.domain.usecases.app_entry.AppEntryUsesCases
import com.example.news.domain.usecases.app_entry.ReadAppEntry
import com.example.news.domain.usecases.app_entry.SaveAppEntry
import com.example.news.domain.usecases.news.DeleteArticle
import com.example.news.domain.usecases.news.GetNews
import com.example.news.domain.usecases.news.NewsUseCases
import com.example.news.domain.usecases.news.SearchNews
import com.example.news.domain.usecases.news.SelectArticle
import com.example.news.domain.usecases.news.SelectArticles
import com.example.news.domain.usecases.news.UpsertArticle
import com.example.news.util.Constans
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUsesCases(
        localUserManger: LocalUserManger
    ) = AppEntryUsesCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepo(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepo =
        NewsRepoImpl(newsApi = newsApi, newsDao = newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        repo: NewsRepo
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(repo),
            searchNews = SearchNews(repo),
            upsertArticle = UpsertArticle(repo),
            deleteArticle = DeleteArticle(repo),
            selectArticles = SelectArticles(repo),
            selectArticle = SelectArticle(repo)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDataBase(application: Application): NewsDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDataBase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDataBase: NewsDataBase): NewsDao = newsDataBase.newsDao

}

















