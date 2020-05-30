package com.anjelikabog.search_for_operations_on_the_account.rabbitControler

import com.anjelikabog.search_for_operations_on_the_account.repo.OperationsRepository
import com.anjelikabog.search_for_operations_on_the_account.repo.PersonsRepository
import org.springframework.amqp.core.Queue
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@Component
class Sender(
        val template: RabbitTemplate,
        val queue: Queue,
        val personsRepository: PersonsRepository
)
{
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send(){
        personsRepository.findAllByStatus("new").forEach{
            val message = it.account
            template.convertAndSend(queue.getName(), message!!)
            it.status="received"
            personsRepository.save(it)
            println("[x] Sent account '$message'")
        }

    }
}

@Component
@RabbitListener(queues = ["account"])
class Receiver(
        val operationRepository: OperationsRepository
) {
    @RabbitHandler
    fun receive(
            messageIn: String
    ) {
        println("[x] Received account'$messageIn'")
        operationRepository.findAllByAccountOrderByIdOperations(messageIn).forEach {
            println(it.operation)
        }
    }
}

@Configuration
@EnableScheduling
class TutorialConfiguration
{
    @Bean
    fun account() = Queue("account")


}
