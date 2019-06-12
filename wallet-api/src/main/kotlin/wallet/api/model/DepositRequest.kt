package wallet.api.model

import java.math.BigDecimal

data class DepositRequest(
    val userId: Long,
    val amount: BigDecimal,
    val currency: Currency
)