package wallet.client.service.round

import org.springframework.stereotype.Service

@Service
class RoundProvider(private val roundA: RoundA, private val roundB: RoundB, private val roundC: RoundC) {

    fun getRound(): Round {
        return roundA //TODO
    }
}