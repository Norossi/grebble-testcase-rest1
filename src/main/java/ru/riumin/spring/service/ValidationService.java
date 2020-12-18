package ru.riumin.spring.service;

import ru.riumin.spring.domain.AdvertModel;

import java.util.List;

public interface ValidationService {

    List<String> validateAdvert(AdvertModel advertModel);

}
