package ru.riumin.spring.service;

import org.springframework.data.domain.Page;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;

import java.util.List;

public interface AdvertService {

    AdvertModel findAdvertById(Long id, List<String> fields);

    Long saveAdvert(AdvertModel advertModel);

    Page<AdvertPageModel> findAllPage(int page, String sortBy, String direction);
}
