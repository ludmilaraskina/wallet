package wallet.server.model

import java.math.BigDecimal

data class WithdrawRequest(
    val userId: Long,
    val amount: BigDecimal,
    val currency: Currency
)