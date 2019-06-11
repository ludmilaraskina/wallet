package wallet.server.controller

import wallet.server.service.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import wallet.server.model.Balance
import wallet.server.model.DepositRequest
import wallet.server.model.WithdrawRequest

@RestController
@RequestMapping("/wallet")
class WalletController(val walletService: WalletService) {

    @PostMapping("/deposit")
    fun deposit(@RequestBody depositRequest: DepositRequest) {
        walletService.deposit(depositRequest)
    }

    @PostMapping("/withdraw")
    fun withdraw(@RequestBody withdrawRequest: WithdrawRequest) {
        walletService.withdraw(withdrawRequest)
    }

    @GetMapping("/balance/{userId}")
    fun getBalance(@PathVariable("userId") userId: Long): ResponseEntity<Balance> {
        val balance = walletService.getBalance(userId)
        return ResponseEntity.ok().body(balance)
    }
}