package uz.adkhamjon.qr_scanner.models
import java.io.Serializable
class ScannerModel:Serializable{
    var date:String?=null
    var image:ByteArray?=null
    var data:String?=null
    constructor()
    constructor(date: String?, image: ByteArray?, data: String?) {
        this.date = date
        this.image = image
        this.data = data
    }
}