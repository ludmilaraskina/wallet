package wallet.server.service

import wallet.api.model.Balance
import wallet.api.model.DepositRequest
import wallet.api.model.WithdrawRequest

interface WalletService {

    fun deposit(depositRequest: DepositRequest)

    fun withdraw(withdrawRequest: WithdrawRequest)

    fun getBalance(userId: Long): Balance
}