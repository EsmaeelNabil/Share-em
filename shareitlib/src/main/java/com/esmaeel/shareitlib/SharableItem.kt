package com.esmaeel.shareitlib

public data class SharableItem(
    var data: String = "",
    var pictureUrl: String = "",
    var failOnDownloadFailing: Boolean = false,
    var shareAppLink: Boolean = false,
    var downloadOurAppMessage : String = ""
)