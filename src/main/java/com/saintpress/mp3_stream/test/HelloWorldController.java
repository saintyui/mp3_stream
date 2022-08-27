package com.saintpress.mp3_stream.test;

import com.saintpress.mp3_stream.mp3.dto.Mp3Dto;
import com.saintpress.mp3_stream.mp3.repository.JpaMp3Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloWorldController {

    @GetMapping("hello")
    public List<String> Hello(){
        return Arrays.asList("안녕하세요", "Hello");
    }
}
