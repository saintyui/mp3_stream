package com.saintpress.mp3_stream;

import com.saintpress.mp3_stream.mp3.dto.Mp3Dto;
import com.saintpress.mp3_stream.mp3.repository.JpaMp3Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Mp3DtoStreamApplicationTests {


    @Autowired
    private JpaMp3Repository jpaMp3Repository;

    @Test
    public void create(){
        Mp3Dto mp3Dto = new Mp3Dto();

        mp3Dto.setId(1L);
        mp3Dto.setTitle("무제");
        mp3Dto.setArtist("알 수 없는 아티스트");
        mp3Dto.setAlbum("모름");
        mp3Dto.setRoute_string("C/:Test");

        jpaMp3Repository.save(mp3Dto);
    }

    @Test
    public void delete(){
        jpaMp3Repository.deleteAllInBatch();
    }

    @Test
    public void contextLoads() {
        Mp3Dto mp3Dto;
        mp3Dto = jpaMp3Repository.findById(1L);
        System.out.println(mp3Dto);
    }


}
