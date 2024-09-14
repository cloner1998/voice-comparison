package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
class VoiceCompareApplication

fun main(args: Array<String>) {
	runApplication<VoiceCompareApplication>(*args)
	println("i just started")
}
