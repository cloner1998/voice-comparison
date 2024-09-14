package voice.cmp.voiceservice

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import org.springframework.stereotype.Service
import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.spi.AudioFileReader
import kotlin.math.abs
import kotlin.math.min

@Service
class VoiceComparison(
    val audioFileReader: AudioFileReader
) {

    fun compareVoice(voicePath1: String, voicePath2: String): Double {
        val streamData1 = audioFileReader.getAudioInputStream(File(voicePath1))
        val streamData2 = audioFileReader.getAudioInputStream(File(voicePath2))
        return calculateSimilarityByAbsoluteValue(streamData1, streamData2)

    }

    private fun calculateSimilarityByAbsoluteValue(stream1: AudioInputStream, stream2: AudioInputStream): Double {
        val dataByte1 = stream1.readAllBytes()
        val dataByte2 = stream2.readAllBytes()

        val minSize = min(dataByte1.size, dataByte2.size)
        var difference = 0.0

        for (i in 0 until minSize) {
            difference += abs(dataByte1[i].toInt() - dataByte2[i].toInt())
        }

        val maxDifference = 256.0
        val similarityPercentage = (1 - (difference)/maxDifference) * 100

        return similarityPercentage
    }

    private fun calculateSimilarityByCorrelation(stream1: AudioInputStream, stream2: AudioInputStream): Double {

        val correlation1 = stream1.readAllBytes().toDoubleSamples()
        val correlation2 = stream2.readAllBytes().toDoubleSamples()

        val correlationPercentage = ((PearsonsCorrelation().correlation(correlation1, correlation2)) + 1) * 50

        return correlationPercentage
    }

    //for converting byte array to double array
    private fun ByteArray.mapPairsToDoubles(block: (Byte, Byte) -> Double)
            = DoubleArray(size / 2){ i -> block(this[2 * i], this[2 * i + 1]) }

    fun ByteArray.toDoubleSamples() = mapPairsToDoubles{ a, b ->
        (a.toInt() and 0xFF or (b.toInt() shl 8)).toDouble()
    }


}