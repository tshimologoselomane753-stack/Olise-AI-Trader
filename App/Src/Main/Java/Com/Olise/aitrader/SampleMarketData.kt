
package com.olise.aitrader

object SampleMarketData {

    fun generateCandles(
        startPrice: Double,
        count: Int = 100
    ): List<MarketCandle> {

        require(startPrice > 0)
        require(count >= 1)

        var previousClose = startPrice

        return (0 until count).map { index ->

            val open = previousClose

            // Artificial price movement for testing only
            val movement =
                kotlin.math.sin(index * 0.7) * 0.002

            val close = open * (1 + movement)

            val high = maxOf(open, close) * 1.001
            val low = minOf(open, close) * 0.999

            previousClose = close

            MarketCandle(
                timestamp = index * 30L * 60L * 1000L,
                open = open,
                high = high,
                low = low,
                close = close,
                volume = 1000.0
            )
        }
    }
}
