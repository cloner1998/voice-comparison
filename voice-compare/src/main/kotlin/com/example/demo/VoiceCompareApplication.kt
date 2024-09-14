package com.example.demo

import com.example.demo.voiceservice.VoiceRecorder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class VoiceCompareApplication {
    @Bean
    fun run(voiceRecorder: VoiceRecorder) = CommandLineRunner {
        voiceRecorder.recordVoice(5,"sajjad")
    }
}

fun main(args: Array<String>) {
    runApplication<VoiceCompareApplication>(*args)

}
