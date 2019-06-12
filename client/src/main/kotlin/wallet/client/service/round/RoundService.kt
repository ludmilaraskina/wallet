package wallet.client.service.round

import org.springframework.stereotype.Service
import wallet.client.service.round.RoundProvider
import javax.annotation.PostConstruct

@Service
class RoundService(private val roundProvider: RoundProvider) {

    fun startRound(userId: Long) {
        roundProvider.getRound().start(userId)
    }

}