package com.saintpress.mp3_stream.mp3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mp3")
@NoArgsConstructor
@Data
public class Mp3Dto {
    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private String album;

    @Column
    private String route_string;
}
