package com.anjelikabog.search_for_operations_on_the_account.repo

import com.anjelikabog.search_for_operations_on_the_account.modelDataBase.*
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
interface PersonsRepository : JpaRepository<PersonsDB, Long>{
    fun findAllByStatus(status: String): List<PersonsDB>
}

@Repository
interface HobbyRepository : JpaRepository<HobbyDB, Long>

@Repository
interface OperationsRepository : JpaRepository<OperationsDB, Long>{
    fun findAllByAccountOrderByIdOperations(account: String): List<OperationsDB>
}
