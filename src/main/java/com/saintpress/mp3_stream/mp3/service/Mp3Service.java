package com.saintpress.mp3_stream.mp3.service;

import com.saintpress.mp3_stream.mp3.dto.Mp3Dto;
import com.saintpress.mp3_stream.mp3.repository.JpaMp3Repository;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@ConfigurationProperties( prefix = "file" )
public class Mp3Service {

    @Autowired
    static
    JpaMp3Repository jpaMp3Repository;

    //static일 경우 그냥 @Value를 그냥 받을 수 없기에 메서드 함수를 만들어줘야 한다.
    @Value("${file.path}")
    public void setRouteKey(String key){
        pathKey = key;
    }

    //메서드 함수 결과를 받아서 전달한다.
    private static String pathKey;

    public static List<Mp3Dto> fileList() throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        //C:\mp3_test
        //Path dir = Paths.get("C:/mp3_test");

        Path dir = Paths.get(pathKey);
        List<Path> fileList;

        Stream<Path> walk = Files.walk(dir);
        fileList = walk
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(".mp3"))
                .collect(Collectors.toList());

        List<Mp3Dto> mp3List = new ArrayList<>();

        Long id = 0L;

        for(Path path: fileList){
            id++;
            Mp3Dto mp3Dto = new Mp3Dto();
            File route = new File(path.toUri());
            String route_string = route.toString().replace("\\", "/");

            MP3File mp3 = (MP3File) AudioFileIO.read(route);
            Tag tag = mp3.getTag();
            String title;
            String artist;
            String album;
            if (tag == null){
                title = "무제";
                artist = "알 수 없는 가수";
                album = "알 수 없는 앨범";
            } else {
                title = tag.getFirst(FieldKey.TITLE);
                artist = tag.getFirst(FieldKey.ARTIST);
                album = tag.getFirst(FieldKey.ALBUM);
            }


            mp3Dto.setTitle(title);
            mp3Dto.setArtist(artist);
            mp3Dto.setAlbum(album);
            mp3Dto.setRoute_string(route_string);
            mp3Dto.setId(id);

            //System.out.println(mp3Map);
            mp3List.add(mp3Dto);
        }

        return mp3List;
    }

}
