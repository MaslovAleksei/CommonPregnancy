package com.margarin.commonpregnancy.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.commonpregnancy.data.local.model.TaskDbModel

@Dao
interface TaskDao {


    @Query("SELECT EXISTS (SELECT * FROM tasks WHERE value=:value LIMIT 1)")
    suspend fun checkIsCompleted(value: String) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCompleted(taskDbModel: TaskDbModel)

    @Query("DELETE FROM tasks WHERE value=:value")
    suspend fun removeFromCompleted(value: String)

}