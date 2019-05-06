package com.coden.starslicer.gameplay.spawner

data class GrowthResolver(val init: Float, private val rate: Float, private val type: GrowthType) {
    constructor(init: Int, rate: Int, type: GrowthType):this(init.toFloat(), rate.toFloat(), type)

    enum class GrowthType{ POLYNOMIAL, EXPONENTIAL}

    fun resolve(n: Int) = when (type){
        GrowthType.POLYNOMIAL -> init + n * rate
        GrowthType.EXPONENTIAL -> init * Math.pow(rate.toDouble(), n.toDouble()).toFloat()
    }

}