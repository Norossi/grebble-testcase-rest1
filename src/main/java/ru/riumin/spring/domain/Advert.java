package ru.riumin.spring.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Advert {

    private long id;

    @NotEmpty(message = "Please, enter the advert's name.")
    @Size(max = 200, message = "Advert's name should not have more than 200 characters.")
    private String name;

    @Size(min = 1, max = 3, message = "Advert should have at least 1 photo and 3 photo at most.")
    private List<String> photos;

    @Size(max = 1000, message = "Advert's description should not have more than 1000 characters.")
    private String description;

    private int price;

    private Date postDate;

}
