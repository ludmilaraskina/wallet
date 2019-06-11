package wallet.server.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_wallet")
data class UserWallet(

    @Id
    var userId: Long,

    @Column(name = "amount_EUR")
    val amountEUR: BigDecimal = BigDecimal(0),

    @Column(name = "amount_USD")
    val amountUSD: BigDecimal = BigDecimal(0),

    @Column(name = "amount_GBP")
    val amountGBP: BigDecimal = BigDecimal(0)

) : GeneralEntity()
