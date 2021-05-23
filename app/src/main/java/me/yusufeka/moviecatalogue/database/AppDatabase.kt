package me.yusufeka.moviecatalogue.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail

@Database(entities = [MovieDetail::class, SeriesDetail::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO

    abstract fun seriesDAO(): SeriesDAO
}