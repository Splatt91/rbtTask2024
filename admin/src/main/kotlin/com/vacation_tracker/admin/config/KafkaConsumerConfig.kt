package com.vacation_tracker.admin.config

import com.vacation_tracker.admin.model.Employee
import com.vacation_tracker.admin.model.UsedVacationDays
import com.vacation_tracker.admin.model.VacationDays
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper
import org.springframework.kafka.support.serializer.JsonDeserializer


@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Bean
    fun multiTypeConverter(): RecordMessageConverter {
        val converter = StringJsonMessageConverter()
        val typeMapper = DefaultJackson2JavaTypeMapper()
        typeMapper.typePrecedence = Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID
        typeMapper.addTrustedPackages("com.vacation-tracker.admin.model")
        val mappings: MutableMap<String, Class<*>> = HashMap()
        mappings["Employee"] = Employee::class.java
        mappings["UsedVacationDays"] = UsedVacationDays::class.java
        mappings["VacationDays"] = VacationDays::class.java
        typeMapper.idClassMapping = mappings
        converter.typeMapper = typeMapper
        return converter
    }

    @Bean
    fun multiTypeConsumerFactory(): ConsumerFactory<String, Any> {
        val props = hashMapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "com.vacation-tracker.admin.model",
            JsonDeserializer.TYPE_MAPPINGS to
                    "Employee:com.vacation_tracker.admin.model.Employee," +
                    "UsedVacationDays:com.vacation_tracker.admin.model.UsedVacationDays," +
                    "VacationDays:com.vacation_tracker.admin.model.VacationDays"
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = multiTypeConsumerFactory()
        factory.setRecordMessageConverter(multiTypeConverter())
        return factory
    }
}