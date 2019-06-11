package wallet.server.converter

import org.springframework.stereotype.Service
import wallet.server.entity.UserWallet
import wallet.server.model.Balance

@Service
class WalletConverter {
    fun convert(userWallet: UserWallet): Balance {
        return Balance(userWallet.userId, userWallet.amountEUR, userWallet.amountUSD, userWallet.amountGBP)
    }
}