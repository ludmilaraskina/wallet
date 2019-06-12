package wallet.server.service.impl

import org.springframework.stereotype.Service
import wallet.server.converter.WalletConverter
import wallet.server.entity.UserWallet
import wallet.api.model.Balance
import wallet.api.model.Currency
import wallet.api.model.DepositRequest
import wallet.api.model.WithdrawRequest
import wallet.server.repository.UserWalletRepository
import wallet.server.service.WalletService
import wallet.server.validation.WalletValidator
import java.math.BigDecimal
import javax.transaction.Transactional

@Service
@Transactional
class WalletServiceImpl(val userWalletRepository: UserWalletRepository,
                        val walletValidator: WalletValidator,
                        val walletConverter: WalletConverter) :
    WalletService {
    override fun deposit(depositRequest: DepositRequest) {
        val userWallet = userWalletRepository.findById(depositRequest.userId)
            .orElse(UserWallet(depositRequest.userId))
        userWallet.amountEUR += if (depositRequest.currency == Currency.EUR) depositRequest.amount else BigDecimal(0)
        userWallet.amountUSD += if (depositRequest.currency == Currency.USD) depositRequest.amount else BigDecimal(0)
        userWallet.amountGBP += if (depositRequest.currency == Currency.GBP) depositRequest.amount else BigDecimal(0)
        userWalletRepository.save(userWallet)
    }

    override fun withdraw(withdrawRequest: WithdrawRequest) {
        walletValidator.validate(withdrawRequest)
        val userWallet = userWalletRepository.findById(withdrawRequest.userId).get()
        userWallet.amountEUR -= if (withdrawRequest.currency == Currency.EUR) withdrawRequest.amount else BigDecimal(0)
        userWallet.amountUSD -= if (withdrawRequest.currency == Currency.USD) withdrawRequest.amount else BigDecimal(0)
        userWallet.amountGBP -= if (withdrawRequest.currency == Currency.GBP) withdrawRequest.amount else BigDecimal(0)
        userWalletRepository.save(userWallet)
    }

    override fun getBalance(userId: Long): Balance {
        return walletConverter.convert(userWalletRepository.findById(userId).get())
    }
}