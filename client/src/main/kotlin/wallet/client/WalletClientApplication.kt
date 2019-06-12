package wallet.client

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import wallet.client.service.ClientService

@SpringBootApplication
class WalletClientApplication(val clientService: ClientService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val userAmount: Int = args[0]?.toInt() ?: throw IllegalArgumentException()
        val concurrentThreadsPerUser: Int = args[1]?.toInt() ?: throw IllegalArgumentException()
        val roundsPerThread: Int = args[2]?.toInt() ?: throw IllegalArgumentException()

        clientService.start(userAmount, concurrentThreadsPerUser, roundsPerThread)
    }
}

fun main(args: Array<String>) {
    runApplication<WalletClientApplication>(*args)
}