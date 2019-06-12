package wallet.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class WalletApplication

fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)
}

