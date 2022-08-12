package com.saintpress.mp3_stream.mp3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/mp3")
@RequiredArgsConstructor
public class Mp3Controller {
    //C:\mp3_test

    @GetMapping(value="/list")
    public List<String> mp3List(){
        return fileList();
    }

    public List<String> fileList(){
        //C:\mp3_test
        File dir = new File("C:/mp3_test");
        List<String> fileList;
        fileList = Arrays.asList(Objects.requireNonNull(dir.list((f, name) -> name.endsWith(".mp3"))));

        return fileList;
    }


}
