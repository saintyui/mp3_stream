package com.saintpress.mp3_stream.mp3.repository;

import com.saintpress.mp3_stream.mp3.dto.Mp3Dto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaMp3Repository extends CrudRepository <Mp3Dto, Integer> {
    List<Mp3Dto> findAllByOrderByIdAsc();

    Mp3Dto findById(Long id);

    void deleteAllInBatch();
}
