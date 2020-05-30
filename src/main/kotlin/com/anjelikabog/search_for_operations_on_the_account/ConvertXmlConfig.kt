package com.anjelikabog.search_for_operations_on_the_account

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ConvertXmlConfig {

    @Bean
    fun xmlMapper() = XmlMapper()
}