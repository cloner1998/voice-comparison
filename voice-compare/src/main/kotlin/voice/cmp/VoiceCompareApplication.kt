package voice.cmp

import voice.cmp.voiceservice.VoiceRecorder
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import voice.cmp.voiceservice.VoiceComparison

@SpringBootApplication
class VoiceCompareApplication {
    @Bean
    fun run(voiceComparison: VoiceComparison) = CommandLineRunner {
        val path1: String = "E:\\spring\\voice-compare\\voice-compare\\src\\main\\resources\\testvoice1.wav"
        val path2: String = "E:\\spring\\voice-compare\\voice-compare\\src\\main\\resources\\testvoice2.wav"
        println(voiceComparison.compareVoice(path1, path2))
    }
}

fun main(args: Array<String>) {
    runApplication<VoiceCompareApplication>(*args)

}
