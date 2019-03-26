package com.coden.starslicer.util

data class GrowthResolver(val rate: Float, val type: GrowthType) {
    enum class GrowthType{ POLYNOMIAL, EXPONENTIAL}
    fun resolve(init: Float, n: Int) = when (type){
        GrowthType.POLYNOMIAL -> init + n * rate
        GrowthType.EXPONENTIAL-> init * Math.pow(rate.toDouble(), n.toDouble()).toFloat()
    }
}