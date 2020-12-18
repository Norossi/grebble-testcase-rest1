package ru.riumin.spring.service;

import org.springframework.stereotype.Service;
import ru.riumin.spring.domain.AdvertModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {

    private static final String EMPTY_TITLE_ERROR = "Please, enter the advert's title.";
    private static final String OVERSIZED_TITLE_ERROR = "Advert's title should not have more than 200 characters.";
    private static final String OVERSIZED_DESCRIPTION_ERROR = "Advert's description should not have more than 1000 characters.";
    private static final String EMPTY_PHOTOS_ERROR = "Please, attach at least 1 photo.";
    private static final String OVERSIZED_PHOTO_ERROR = "Advert should have at least 1 photo and 3 photo at most.";
    private static final String EMPTY_PRICE_ERROR = "Please, enter the product's price.";

    @Override
    public List<String> validateAdvert(AdvertModel advertModel) {
        List<String> errorList = new ArrayList<>();
        if (advertModel.getTitle() == null || advertModel.getTitle().isEmpty())
            errorList.add(EMPTY_TITLE_ERROR);
        else {
            if (advertModel.getTitle().length() > 200)
            errorList.add(OVERSIZED_TITLE_ERROR);
        }

        if (advertModel.getDescription() != null && advertModel.getDescription().length() > 1000)
            errorList.add(OVERSIZED_DESCRIPTION_ERROR);

        if (advertModel.getPhotos() == null || advertModel.getPhotos().isEmpty())
            errorList.add(EMPTY_PHOTOS_ERROR);
        else {
            if (advertModel.getPhotos().size() > 3)
                errorList.add(OVERSIZED_PHOTO_ERROR);
        }

        if (advertModel.getPrice() == 0)
            errorList.add(EMPTY_PRICE_ERROR);

        return errorList;
    }
}
