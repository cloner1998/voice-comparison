package com.example.demo.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.InputStream
import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.spi.AudioFileReader

@Configuration
class AudioReaderConfig {

    @Bean
    fun audioFileReader(): AudioFileReader {
        return object : AudioFileReader() {
            override fun getAudioFileFormat(stream: InputStream?) = AudioSystem.getAudioFileFormat(stream)
            override fun getAudioFileFormat(url: URL) = AudioSystem.getAudioFileFormat(url)
            override fun getAudioFileFormat(file: File?) = AudioSystem.getAudioFileFormat(file)

            override fun getAudioInputStream(file: File) = AudioSystem.getAudioInputStream(file)
            override fun getAudioInputStream(url: URL) = AudioSystem.getAudioInputStream(url)
            override fun getAudioInputStream(inputStream: InputStream) = AudioSystem.getAudioInputStream(inputStream)

        }
    }
}