package org.example.classwork58.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Song {
    private int id;
    private String name;
    private int duration;
    private Album album;
}
