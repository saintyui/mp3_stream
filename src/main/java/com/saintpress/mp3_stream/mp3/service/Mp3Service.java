package com.saintpress.mp3_stream.mp3.service;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
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
public class Mp3Service {
    public static List<HashMap<String, Object>> fileList() throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        //C:\mp3_test
        Path dir = Paths.get("C:/mp3_test");
        List<Path> fileList;

        Stream<Path> walk = Files.walk(dir);
        fileList = walk
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(".mp3"))
                .collect(Collectors.toList());

        List<HashMap<String, Object>> mp3List = new ArrayList<>();
        Long id = 0L;

        for(Path path: fileList){
            id++;
            HashMap<String, Object> mp3Map = new HashMap<>();
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
            mp3Map.put("title", title);
            mp3Map.put("artist", artist);
            mp3Map.put("album", album);
            mp3Map.put("route", route_string);
            mp3Map.put("id", id);
            mp3Map.put("url", "/mp3/stream/" + id);

            //System.out.println(mp3Map);
            mp3List.add(mp3Map);
        }

        return mp3List;
    }
}
