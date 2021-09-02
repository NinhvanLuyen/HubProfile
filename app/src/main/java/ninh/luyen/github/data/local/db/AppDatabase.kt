package ninh.luyen.github.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ninh.luyen.github.data.local.dao.PhotoDAO
import ninh.luyen.github.data.local.dao.RemoteKeysDao
import ninh.luyen.github.data.local.entites.PhotoEntity
import ninh.luyen.github.data.local.entites.RemoteKeys

@Database(entities = [PhotoEntity::class, RemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDAO(): PhotoDAO
    abstract fun remoteKeyDAO():RemoteKeysDao
}

