
package com.olise.aitrader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF101827)
private val Panel = Color(0xFF1B2738)
private val Teal = Color(0xFF20D6B2)
private val Muted = Color(0xFF9AAABD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Navy,
                    surface = Panel,
                    primary = Teal
                )
            ) {
                TraderDashboard()
            }
        }
    }
}

@Composable
fun TraderDashboard() {
    var account by remember { mutableStateOf("DEMO") }
    var pair by remember { mutableStateOf("EUR/USD") }
    var message by remember {
        mutableStateOf("Welcome to Olise AI Trader")
    }

    val pairs = listOf(
        "EUR/USD", "GBP/USD", "USD/JPY",
        "AUD/USD", "USD/CAD", "XAU/USD"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(16.dp)
    ) {
        Text(
            "OLISE AI TRADER",
            color = Teal,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "SMART TRADING DASHBOARD",
            color = Muted,
            fontSize = 11.sp
        )

        Spacer(Modifier.height(20.dp))

        Text("ACCOUNT MODE", color = Muted)

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("DEMO", "REAL").forEach { mode ->
                Button(
                    onClick = {
                        account = mode
                        message = if (mode == "DEMO")
                            "Demo practice mode selected"
                        else
                            "Real account preview - no live orders"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (account == mode) Teal else Panel,
                        contentColor =
                            if (account == mode) Navy else Color.White
                    )
                ) {
                    Text(mode)
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Panel
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    if (account == "DEMO")
                        "VIRTUAL DEMO BALANCE"
                    else
                        "REAL ACCOUNT PREVIEW",
                    color = Muted,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    if (account == "DEMO") "$10,000.00"
                    else "Not connected",
                    color = Teal,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    if (account == "DEMO")
                        "Simulated funds only"
                    else
                        "Broker connection not configured",
                    color = Muted,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("MARKET PAIRS", color = Muted)

        Row(
            modifier = Modifier.horizontalScroll(
                rememberScrollState()
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pairs.forEach { item ->
                FilterChip(
                    selected = pair == item,
                    onClick = { pair = item },
                    label = { Text(item) }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Panel
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    pair,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("TIMEFRAME: 30 MINUTES", color = Muted)

                Spacer(Modifier.height(12.dp))

                Text("TECHNICAL INDICATORS", color = Teal)

                Spacer(Modifier.height(10.dp))

                Text("EMA  •  RSI  •  MACD", color = Color.White)

                Text(
                    "Indicator calculations: coming next",
                    color = Muted,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        if (account == "DEMO") {
            Text("PAPER TRADING", color = Teal)

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        message = "Demo BUY simulation selected"
                    }
                ) {
                    Text("Simulate BUY")
                }

                OutlinedButton(
                    onClick = {
                        message = "Demo SELL simulation selected"
                    }
                ) {
                    Text("Simulate SELL")
                }
            }
        } else {
            Text(
                "Real account is display-only in this version.",
                color = Muted
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            message,
            color = Teal,
            fontSize = 13.sp
        )

        Spacer(Modifier.weight(1f))

        Text(
            "OLISE AI TRADER • DEMO BUILD 1.0",
            color = Muted,
            fontSize = 10.sp
        )
    }
}
