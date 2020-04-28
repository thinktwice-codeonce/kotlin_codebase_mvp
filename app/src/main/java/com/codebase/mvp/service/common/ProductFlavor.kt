package com.codebase.mvp.service.common

enum class ProductFlavor {

    INTEGRATION("integration", "INVALID"),
    STAGING("staging", "INVALID"),
    PRODUCTION("production", "INVALID"),
    LIVE("live", "INVALID");

    private var id: String? = null
    private var url: String? = null

    constructor(id: String, url: String){
        this.id = id
        this.url = url
    }

    fun getFlavor(flavor: String): ProductFlavor {
        for (productFlavor in ProductFlavor.values()) {
            if (flavor == productFlavor.getId()) {
                return productFlavor
            }
        }
        return INTEGRATION
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String): ProductFlavor {
        this.url = url
        return this
    }
}