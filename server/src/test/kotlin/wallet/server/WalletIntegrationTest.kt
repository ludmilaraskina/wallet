package wallet.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wallet.server.exception.InsufficientFundsException
import wallet.server.model.Currency
import wallet.server.model.DepositRequest
import wallet.server.model.WithdrawRequest
import java.math.BigDecimal


const val BASE_URL = "/wallet"
const val DEPOSIT = "$BASE_URL/deposit"
const val BALANCE = "$BASE_URL/balance"
const val WITHDRAW = "$BASE_URL/withdraw"


@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [WalletApplication::class]
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class WalletControllerIntegrationTest {

    @Autowired
    private lateinit var mvc: MockMvc

    private val PATH = "/wallet"

    private val mapper = jacksonObjectMapper()
    private val userId = 1L

    @Test
    fun integrationTest() {
        logger.info { "1. Make a withdrawal of USD 200 for user with id 1. Must return \"insufficient_funds\"" }
        withdrawExpectInsufficientFunds(WithdrawRequest(1, BigDecimal(200), Currency.USD))

        logger.info { "2. Make a deposit of USD 100 to user with id 1. " }
        deposit(DepositRequest(1, BigDecimal(100), Currency.USD))

        logger.info { "3. Check that all balances are correct " }
        mvc.perform(get("$BALANCE/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.amountEUR").value(0))
            .andExpect(jsonPath("$.amountUSD").value(100))
            .andExpect(jsonPath("$.amountGBP").value(0))

        logger.info { "4. Make a withdrawal of USD 200 for user with id 1. Must return \"insufficient_funds\". " }
        withdrawExpectInsufficientFunds(WithdrawRequest(1, BigDecimal(200), Currency.USD))

        logger.info { "5. Make a deposit of EUR 100 to user with id 1. " }
        deposit(DepositRequest(1, BigDecimal(100), Currency.EUR))

        logger.info { "6. Check that all balances are correct " }
        mvc.perform(get("$BALANCE/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.amountEUR").value(100))
            .andExpect(jsonPath("$.amountUSD").value(100))
            .andExpect(jsonPath("$.amountGBP").value(0))

        logger.info { "7. Make a withdrawal of USD 200 for user with id 1. Must return \"insufficient_funds\". " }
        withdrawExpectInsufficientFunds(WithdrawRequest(1, BigDecimal(200), Currency.USD))

        logger.info { "8. Make a deposit of USD 100 to user with id 1. " }
        deposit(DepositRequest(1, BigDecimal(100), Currency.USD))

        logger.info { "9. Check that all balances are correct " }
        mvc.perform(get("$BALANCE/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.amountEUR").value(100))
            .andExpect(jsonPath("$.amountUSD").value(200))
            .andExpect(jsonPath("$.amountGBP").value(0))

        logger.info { "10. Make a withdrawal of USD 200 for user with id 1. Must return \"ok\". " }
        withdraw(WithdrawRequest(1, BigDecimal(200), Currency.USD))

        logger.info { "11. Check that all balances are correct " }
        mvc.perform(get("$BALANCE/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.amountEUR").value(100))
            .andExpect(jsonPath("$.amountUSD").value(0))
            .andExpect(jsonPath("$.amountGBP").value(0))

        logger.info { "12. Make a withdrawal of USD 200 for user with id 1. Must return \"insufficient_funds\". " }
        withdrawExpectInsufficientFunds(WithdrawRequest(1, BigDecimal(200), Currency.USD))
    }

    private fun deposit(depositRequest: DepositRequest) {
        mvc.perform(post(DEPOSIT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(depositRequest))
        ).andExpect(status().isOk)
    }

    private fun withdraw(withdrawRequest: WithdrawRequest) {
        mvc.perform(post(WITHDRAW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(withdrawRequest))
        ).andExpect(status().isOk)
    }

    private fun withdrawExpectInsufficientFunds(withdrawRequest: WithdrawRequest) {
        try {
            withdraw(withdrawRequest)
            fail()
        } catch (e: Exception) {
            if(e.cause is InsufficientFundsException) {
                logger.info { "Expected: insufficient funds" }
            }
            else throw e
        }
    }

    companion object : KLogging()
}