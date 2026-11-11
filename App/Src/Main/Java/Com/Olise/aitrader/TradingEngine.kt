
package com.olise.aitrader

data class IndicatorSnapshot(
    val ema12: Double,
    val ema26: Double,
    val rsi14: Double,
    val macd: Double,
    val signal: Double
)

object TradingEngine {

    fun ema(values: List<Double>, period: Int): Double {
        require(period > 0 && values.isNotEmpty())

        val multiplier = 2.0 / (period + 1)
        var result = values.take(period).average()

        for (value in values.drop(period)) {
            result = (value * multiplier) +
                    (result * (1 - multiplier))
        }

        return result
    }

    fun rsi(
        values: List<Double>,
        period: Int = 14
    ): Double {
        require(values.size > period)

        val changes = values.zipWithNext { a, b -> b - a }
        val recent = changes.takeLast(period)

        val gains = recent.filter { it > 0 }.sum() / period
        val losses = recent.filter { it < 0 }.sum().let {
            -it / period
        }

        if (losses == 0.0) {
            return if (gains == 0.0) 50.0 else 100.0
        }

        val rs = gains / losses
        return 100.0 - (100.0 / (1.0 + rs))
    }

    fun calculate(
        closes: List<Double>
    ): IndicatorSnapshot? {
        if (closes.size < 35) return null

        val ema12 = ema(closes, 12)
        val ema26 = ema(closes, 26)
        val rsi14 = rsi(closes)

        val macdValues = (26..closes.size).map { end ->
            val sample = closes.take(end)
            ema(sample, 12) - ema(sample, 26)
        }

        val macd = macdValues.last()
        val signal = ema(macdValues, 9)

        return IndicatorSnapshot(
            ema12 = ema12,
            ema26 = ema26,
            rsi14 = rsi14,
            macd = macd,
            signal = signal
        )
    }
}
