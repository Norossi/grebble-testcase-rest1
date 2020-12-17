package ru.riumin.spring.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvertPageModel {

    private long id;
    private String title;
    private String mainPhoto;
    private int price;
    private LocalDateTime postDate;

}
