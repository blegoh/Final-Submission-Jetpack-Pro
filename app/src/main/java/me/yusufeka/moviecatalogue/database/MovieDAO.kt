package me.yusufeka.moviecatalogue.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.yusufeka.moviecatalogue.movies.model.MovieDetail

@Dao
interface MovieDAO {

    @Query("SELECT * FROM moviedetail ORDER BY id ASC")
    fun getAll(): DataSource.Factory<Int, MovieDetail>

    @Query("SELECT * FROM moviedetail WHERE id = :id")
    suspend fun findById(id: Int): List<MovieDetail>

    @Insert
    suspend fun insertAll(vararg moviedetail: MovieDetail)

    @Delete
    suspend fun delete(moviedetail: MovieDetail)

}