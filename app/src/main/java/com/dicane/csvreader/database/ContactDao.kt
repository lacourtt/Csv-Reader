package com.dicane.csvreader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicane.csvreader.model.Contact

@Dao
interface ContactDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert(item: Contact)

     @Query("SELECT * FROM contact")
     suspend fun getAllContacts(): List<Contact>

     @Query("DELETE FROM contact WHERE id = :id")
     suspend fun deleteById(id: Int)

     @Update
     suspend fun update(item: Contact)
}