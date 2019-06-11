package wallet.server.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import wallet.server.entity.UserWallet

@Repository
interface UserWalletRepository : CrudRepository<UserWallet, Long> {
}