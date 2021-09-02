package ninh.luyen.github.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ninh.luyen.github.data.local.entites.PhotoEntity

/**
 * Created by luyen_ninh on 20/08/2021.
 */
@Dao
interface PhotoDAO {

    @Query("SELECT * from photos")
    fun getPhotosPaging(): DataSource.Factory<Int, PhotoEntity>

    @Query("SELECT * from photos")
    fun getPhotos(): List<PhotoEntity>

    @Insert(onConflict = REPLACE)
    fun insertPhoto(photo: PhotoEntity)

    @Insert(onConflict = REPLACE)
    fun insertPhotos(vararg photos: PhotoEntity)

    @Query("delete from photos")
    fun deletePhotos()

    @Delete
    fun deletePhoto(photo: PhotoEntity)

}