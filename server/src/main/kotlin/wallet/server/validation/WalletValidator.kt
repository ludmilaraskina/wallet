package wallet.server.validation

import org.springframework.stereotype.Service
import wallet.server.exception.InsufficientFundsException
import wallet.api.model.Currency
import wallet.api.model.WithdrawRequest
import wallet.server.repository.UserWalletRepository
import javax.transaction.Transactional

@Service
@Transactional
class WalletValidator(var userWalletRepository: UserWalletRepository) {
    fun validate(withdrawRequest: WithdrawRequest) {
        if(!userWalletRepository.findById(withdrawRequest.userId).isPresent) {
            throw InsufficientFundsException()
        }
        val userWallet = userWalletRepository.findById(withdrawRequest.userId).get()

        if((withdrawRequest.currency == Currency.EUR && userWallet.amountEUR < withdrawRequest.amount)
            || (withdrawRequest.currency == Currency.USD && userWallet.amountUSD < withdrawRequest.amount)
            || (withdrawRequest.currency == Currency.GBP && userWallet.amountGBP < withdrawRequest.amount)) {
            throw InsufficientFundsException()
        }
    }
}