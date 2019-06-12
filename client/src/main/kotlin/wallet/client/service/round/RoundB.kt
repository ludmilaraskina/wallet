package wallet.client.service.round

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import wallet.api.model.Currency
import wallet.api.model.DepositRequest
import wallet.api.model.WithdrawRequest
import wallet.client.service.WalletServer
import java.math.BigDecimal

@Service
class RoundB(@Value("\${wallet.server.url}") val walletServerUrl: String,
             val walletServer: WalletServer = WalletServer.create(walletServerUrl)) : Round {

    override fun start(userId: Long) {
        // Withdraw 100 GBP
        walletServer.withdraw(WithdrawRequest(userId, BigDecimal(100), Currency.GBP)).execute()
        // Deposit 300 GPB
        walletServer.deposit(DepositRequest(userId, BigDecimal(300), Currency.GBP)).execute()
        // Withdraw 100 GBP
        walletServer.withdraw(WithdrawRequest(userId, BigDecimal(100), Currency.GBP)).execute()
        // Withdraw 100 GBP
        walletServer.withdraw(WithdrawRequest(userId, BigDecimal(100), Currency.GBP)).execute()
        // Withdraw 100 GBP
        walletServer.withdraw(WithdrawRequest(userId, BigDecimal(100), Currency.GBP)).execute()
    }
}