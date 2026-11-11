
package com.olise.aitrader

data class MarketCandle(
    val timestamp: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double = 0.0
) {
    init {
        require(high >= low)
        require(high >= open && high >= close)
        require(low <= open && low <= close)
    }
}

enum class TradeDirection {
    BUY,
    SELL
}

enum class TradeStatus {
    OPEN,
    CLOSED
}

data class PaperTrade(
    val id: String,
    val pair: String,
    val direction: TradeDirection,
    val entryPrice: Double,
    val quantity: Double,
    val openedAt: Long,
    val status: TradeStatus = TradeStatus.OPEN,
    val exitPrice: Double? = null,
    val closedAt: Long? = null
) {
    fun simulatedProfitLoss(): Double? {
        val exit = exitPrice ?: return null

        val difference = when (direction) {
            TradeDirection.BUY -> exit - entryPrice
            TradeDirection.SELL -> entryPrice - exit
        }

        return difference * quantity
    }
}
