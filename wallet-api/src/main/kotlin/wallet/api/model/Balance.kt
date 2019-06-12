package wallet.api.model

import java.math.BigDecimal

data class Balance(
    val userId: Long,
    val amountEUR: BigDecimal,
    val amountUSD: BigDecimal,
    val amountGBP: BigDecimal
)

