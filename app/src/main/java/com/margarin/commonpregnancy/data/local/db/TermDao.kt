package com.margarin.commonpregnancy.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.commonpregnancy.data.local.model.TermDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TermDao {

    @Query("SELECT * FROM term WHERE id=0")
    fun getTimeOfStartPregnancy(): Flow<TermDbModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTimeOfStartPregnancy(termDbModel: TermDbModel)

    @Query("SELECT EXISTS (SELECT * FROM term WHERE id=0 LIMIT 1)")
    suspend fun checkIsConfigured() : Boolean

}