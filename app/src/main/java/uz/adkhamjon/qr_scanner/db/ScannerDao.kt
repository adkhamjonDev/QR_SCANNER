package uz.adkhamjon.qr_scanner.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.adkhamjon.qr_scanner.models.DbModel
@Dao
interface ScannerDao {
    @Insert
    fun addScanner(dbModel: DbModel)
    @Query("SELECT * FROM scanner")
    fun getAllScanners():List<DbModel>
    @Delete
    fun removeScanner(dbModel: DbModel)
}