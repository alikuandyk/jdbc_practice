package org.example.classwork58.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Album {
    private int id;
    private String name;
    private Artist artist;
}

