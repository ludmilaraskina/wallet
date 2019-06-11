package wallet.server.service.impl

import org.springframework.stereotype.Service
import org.springframework.util.Assert
import wallet.server.converter.WalletConverter
import wallet.server.entity.UserWallet
import wallet.server.exception.InsufficientFundsException
import wallet.server.model.Balance
import wallet.server.model.Currency
import wallet.server.model.DepositRequest
import wallet.server.model.WithdrawRequest
import wallet.server.repository.UserWalletRepository
import wallet.server.service.WalletService
import wallet.server.validation.WalletValidator
import java.math.BigDecimal
import javax.persistence.EntityNotFoundException
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
        val newUserWallet = UserWallet(
            userWallet.userId,
            userWallet.amountEUR + if (depositRequest.currency == Currency.EUR) {
                depositRequest.amount
            } else {
                BigDecimal(0)
            },
            userWallet.amountUSD + if (depositRequest.currency == Currency.USD) {
                depositRequest.amount
            } else {
                BigDecimal(0)
            },userWallet.amountGBP + if (depositRequest.currency == Currency.GBP) {
                depositRequest.amount
            } else {
                BigDecimal(0)
            })
        userWalletRepository.save(newUserWallet)
    }

    override fun withdraw(withdrawRequest: WithdrawRequest) {
        walletValidator.validate(withdrawRequest)
        val userWallet = userWalletRepository.findById(withdrawRequest.userId).get()
        val newUserWallet = UserWallet(
            userWallet.userId,
            userWallet.amountEUR - if (withdrawRequest.currency == Currency.EUR) {
                withdrawRequest.amount
            } else {
                BigDecimal(0)
            },
            userWallet.amountUSD - if (withdrawRequest.currency == Currency.USD) {
                withdrawRequest.amount
            } else {
                BigDecimal(0)
            },userWallet.amountGBP - if (withdrawRequest.currency == Currency.GBP) {
                withdrawRequest.amount
            } else {
                BigDecimal(0)
            })
        userWalletRepository.save(newUserWallet)
    }

    override fun getBalance(userId: Long): Balance {
        return walletConverter.convert(userWalletRepository.findById(userId).get())
    }
}