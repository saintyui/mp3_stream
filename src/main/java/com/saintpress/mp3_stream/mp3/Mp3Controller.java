package com.saintpress.mp3_stream.mp3;

import com.saintpress.mp3_stream.mp3.service.Mp3Service;
import lombok.RequiredArgsConstructor;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/mp3")
@RequiredArgsConstructor
public class Mp3Controller {
    //C:\mp3_test

    @GetMapping(value="/list")
    public List<HashMap<String, Object>> mp3List() throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        return Mp3Service.fileList();
    }


    @GetMapping(value = "/stream/{idx}")
    public ResponseEntity <StreamingResponseBody> stream(@PathVariable String idx) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        List<HashMap<String, Object>> fileList = Mp3Service.fileList();
        String route = "";


        for (HashMap<String, Object> map: fileList){
            if(Objects.equals(String.valueOf(map.get("id")), idx)){
                //System.out.println("id: " + idx);
                route = (String) map.get("route");
            }
        }

        //System.out.println("경로TEST: " + route);
        File file = new File(route);
        if(!file.isFile()){
            return ResponseEntity.notFound().build();
        }

        StreamingResponseBody stream = outputStream -> FileCopyUtils.copy(Files.newInputStream(file.toPath()), outputStream);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "audio/mpeg");
        responseHeaders.add("Accept-Ranges", "bytes");
        responseHeaders.add("Content-Length", Long.toString(file.length()));

        return ResponseEntity.ok().headers(responseHeaders).body(stream);
    }
}
