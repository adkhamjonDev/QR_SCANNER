package uz.adkhamjon.qr_scanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.adkhamjon.qr_scanner.models.DbModel
import uz.adkhamjon.qr_scanner.models.ScannerModel
import java.util.*


@Database(entities = [DbModel::class],version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun scannerDao():ScannerDao
    companion object{
        private var appDatabase: AppDataBase? = null
        @Synchronized
        fun getInstance(context: Context): AppDataBase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDataBase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}