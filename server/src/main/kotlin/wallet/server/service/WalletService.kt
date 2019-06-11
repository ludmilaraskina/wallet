package wallet.server.service

import wallet.server.model.Balance
import wallet.server.model.DepositRequest
import wallet.server.model.WithdrawRequest

interface WalletService {

    fun deposit(depositRequest: DepositRequest)

    fun withdraw(withdrawRequest: WithdrawRequest)

    fun getBalance(userId: Long): Balance
}