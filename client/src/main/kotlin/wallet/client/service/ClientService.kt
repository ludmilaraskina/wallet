package wallet.client.service

import mu.KLogging
import org.springframework.stereotype.Service
import wallet.client.service.round.RoundService
import kotlin.concurrent.thread

@Service
class ClientService(val roundService: RoundService) {

    fun start(userAmount: Int, concurrentThreadsPerUser: Int, roundsPerThread: Int) {
/*
        The wallet client CLI parameters
        Users (number of concurrent users emulated)
        Concurrent_threads_per_user (number of concurrent requests a user will make)
        Rounds_per_thread (number of rounds each thread is executing)
*/
        for (userNumber in 0 until userAmount) {
            thread {
                logger.info { "Start user: $userNumber" }
                for (concurrentThreadNumber in 0 until concurrentThreadsPerUser) {
                    logger.info {
                        "Start user action: user = $userNumber, " +
                                "concurrentThreadNumber = $concurrentThreadNumber"
                    }
                    thread {
                        for (roundNumber in 0 until roundsPerThread) {
                            logger.info {
                                "Start round: user = $userNumber, " +
                                        "concurrentThreadNumber = $concurrentThreadNumber, roundNumber = $roundNumber"
                            }
                            roundService.startRound(userNumber.toLong())
                        }
                    }
                }
            }
        }
    }

    companion object : KLogging()
}