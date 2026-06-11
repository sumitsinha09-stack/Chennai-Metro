package com.cmrl.metro.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.cmrl.metro.models.EmergencyContact;
import java.util.List;

@Dao
public interface EmergencyContactDao {
    @Insert
    void insert(EmergencyContact contact);

    @Delete
    void delete(EmergencyContact contact);

    @Query("SELECT * FROM emergency_contacts")
    LiveData<List<EmergencyContact>> getAllContacts();
}
