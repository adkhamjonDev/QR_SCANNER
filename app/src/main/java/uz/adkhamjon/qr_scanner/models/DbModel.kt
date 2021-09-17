package uz.adkhamjon.qr_scanner.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "scanner")
data class DbModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var date:String,
    var image:ByteArray,
    var data:String
):Serializable