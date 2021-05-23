package me.yusufeka.moviecatalogue.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail

@Dao
interface SeriesDAO {

    @Query("SELECT * FROM seriesdetail ORDER BY id ASC")
    fun getAll(): DataSource.Factory<Int, SeriesDetail>

    @Query("SELECT * FROM seriesdetail WHERE id = :id")
    suspend fun findById(id: Int): List<SeriesDetail>

    @Insert
    suspend fun insertAll(vararg seriesdetail: SeriesDetail)

    @Delete
    suspend fun delete(seriesdetail: SeriesDetail)

}