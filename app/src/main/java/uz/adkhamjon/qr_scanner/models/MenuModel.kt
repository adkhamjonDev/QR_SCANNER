package uz.adkhamjon.qr_scanner.models

class MenuModel{
    var image:Int?=null
    var label:String?=null
    constructor()
    constructor(image: Int?, label: String?) {
        this.image = image
        this.label = label
    }
}