package wallet.client.service.round

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import wallet.api.model.Currency
import wallet.api.model.DepositRequest
import wallet.api.model.WithdrawRequest
import wallet.client.service.WalletServer
import java.math.BigDecimal

@Service
class RoundC(@Value("\${wallet.server.url}") val walletServerUrl: String,
             val walletServer: WalletServer = WalletServer.create(walletServerUrl)) : Round {

    override fun start(userId: Long) {
        // Get Balance
        walletServer.balance(userId).execute()
        // Deposit 100 USD
        walletServer.deposit(DepositRequest(userId, BigDecimal(100), Currency.USD)).execute()
        // Deposit 100 USD
        walletServer.deposit(DepositRequest(userId, BigDecimal(100), Currency.USD)).execute()
        // Withdraw 100 USD
        walletServer.withdraw(WithdrawRequest(userId, BigDecimal(100), Currency.USD)).execute()
        // Depsoit 100 USD
        walletServer.deposit(DepositRequest(userId, BigDecimal(100), Currency.USD)).execute()
        // Get Balance
        walletServer.balance(userId).execute()
        // Withdraw 200 USD
        walletServer.withdraw(WithdrawRequest(userId, BigDecimal(200), Currency.USD)).execute()
        // Get Balance
        walletServer.balance(userId).execute()
    }
}