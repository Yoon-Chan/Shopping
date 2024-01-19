package com.example.shopping.model

import com.example.domain.model.BannerList
import com.example.shopping.delegate.BannerDelegate

class BannerListVM(model: BannerList,private val bannerDelegate: BannerDelegate): PresentationVM<BannerList>(model) {

    fun openBannerList(bannerId: String){
        bannerDelegate.openBanner(bannerId)
        sendBannerListLog()
    }

    fun sendBannerListLog() {

    }
}