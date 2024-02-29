package com.margarin.commonpregnancy.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.commonpregnancy.data.local.model.TermDbModel

@Dao
interface PregnancyDao {

    @Query("SELECT * FROM term WHERE id=0")
    fun getTimeOfStartPregnancy(): TermDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTimeOfStartPregnancy(termDbModel: TermDbModel)

}