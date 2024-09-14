package com.example.demo.voiceservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Paths
import javax.sound.sampled.*

@Component
class VoiceRecorder {

    @Autowired
    lateinit var resourceLoader: ResourceLoader

    fun recordVoice(durationInSeconds: Int, outputFileName: String) {

        // I can not understand signed and bigEndian but see it betters to be true!!
        val format = AudioFormat(44100f, 16, 1, true, false)
        val info = DataLine.Info(TargetDataLine::class.java, format)

        if (!AudioSystem.isLineSupported(info)) {
            throw IllegalStateException("Line not supported")
        }

        val line = AudioSystem.getLine(info) as TargetDataLine
        line.open(format)
        line.start()

        println("Recording started...")

        val out = ByteArrayOutputStream()
        // number of samples per second * number of bytes in each frame
        //this buffer size ensure that creat a large enough of ot hold one second of audio data
        val bufferSize = format.sampleRate.toInt() * format.frameSize
        //It serves as a temporary storage for audio data as it's being read from the input line.
        val buffer = ByteArray(bufferSize)

        try {
            var bytesRead = 0
            val start = System.currentTimeMillis()
            while (System.currentTimeMillis() - start < durationInSeconds * 1000) {
                bytesRead = line.read(buffer, 0, buffer.size)
                out.write(buffer,0,bytesRead)
                println(1)
            }
        } finally {
            line.stop()
            line.close()
        }

        println("Recording finished.")

        val audioBytes = out.toByteArray()
        println(audioBytes.size)


        saveToResources(audioBytes, format, outputFileName)
    }

    private fun saveToResources(audioInputStream: ByteArray, format: AudioFormat, fileName: String) {

        val filePath = Paths.get("E:\\spring\\voice-compare\\voice-compare\\src\\main\\resources", fileName)

        val audionInputForWrite = AudioInputStream(
            ByteArrayInputStream(audioInputStream),
            format,
            audioInputStream.size.toLong() / format.frameSize
        )

        AudioSystem.write(audionInputForWrite, AudioFileFormat.Type.WAVE, filePath.toFile())
        println("Voice saved to: ${filePath.toAbsolutePath()}")
    }
}