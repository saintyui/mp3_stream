package com.saintpress.mp3_stream.mp3;

import com.saintpress.mp3_stream.mp3.dto.Mp3Dto;
import com.saintpress.mp3_stream.mp3.repository.JpaMp3Repository;
import com.saintpress.mp3_stream.mp3.service.Mp3Service;
import lombok.RequiredArgsConstructor;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping("/mp3")
@RequiredArgsConstructor
public class Mp3Controller {
    //C:\mp3_test

    @Autowired
    Mp3Service mp3Service;

    @Autowired
    JpaMp3Repository jpaMp3Repository;

    @GetMapping(value="/list")
    public List<Mp3Dto> mp3List() {
        return jpaMp3Repository.findAllByOrderByIdAsc();
    }

    @GetMapping(value="/scan")
    public String mp3ListSave() throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        List<Mp3Dto> mp3List = mp3Service.fileList();

        try{
            jpaMp3Repository.deleteAllInBatch();
            jpaMp3Repository.saveAll(mp3List);
        } catch (Exception e){
            return "code2";
        }
        return "code1";
    }

    @GetMapping(value = "/stream/{idx}")
    public ResponseEntity <StreamingResponseBody> stream(@PathVariable String idx) {
       Mp3Dto mp3Info = jpaMp3Repository.findById(Long.valueOf(idx));
       String route = mp3Info.getRoute_string();

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
