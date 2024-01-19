package com.example.shopping.model

import com.example.domain.model.Banner
import com.example.shopping.delegate.BannerDelegate

class BannerVM(model: Banner, bannerDelegate: BannerDelegate) : PresentationVM<Banner>(model),
    BannerDelegate by bannerDelegate {

}