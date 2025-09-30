package com.sampleproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class SampleProjectApplication

fun main(args: Array<String>) {
    runApplication<SampleProjectApplication>(*args)
}
