package ru.riumin.spring.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdvertModel {

    private long id;
    public static final String ID_COLUMN_NAME = "id";

    @NotBlank(message = "Please, enter the advert's title.")
    @Size(min = 2, max = 200, message = "Advert's title should not have more than 200 characters.")
    private String title;
    public static final String TITLE_COLUMN_NAME = "title";

    @NotEmpty(message = "Please, attach at least 1 photo.")
    @Size(min = 1, max = 3, message = "Advert should have at least 1 photo and 3 photo at most.")
    private List<String> photos;
    public static final String PHOTOS_COLUMN_NAME = "photos";

    @Size(max = 1000, message = "Advert's description should not have more than 1000 characters.")
    private String description;
    public static final String DESCRIPTION_COLUMN_NAME = "description";

    @Min(value = 0, message = "Please, enter the product's price.")
    private int price;
    public static final String PRICE_COLUMN_NAME = "price";

    private LocalDateTime postDate;
    public static final String POSTDATE_COLUMN_NAME = "post_date";
}
