package com.demo.touchwallet.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.demo.touchwallet.entity.SeedEntity

@Dao
interface SeedDao {
    @Transaction
    @Insert(onConflict = IGNORE)
    fun persistSeeds(vararg seed: SeedEntity)

    @Transaction
    @Query("select * from tbl_seeds limit 1")
    fun retrieveSeed(): SeedEntity?

    @Transaction
    @Delete
    fun destroySeeds(vararg seed: SeedEntity)

    @Transaction
    @Query("delete from tbl_seeds")
    fun destroyAllSeeds()
}