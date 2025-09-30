package com.sampleproject.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class SampleController {
    @GetMapping
    fun index(): String {
        return "Hehe"
    }
}