package ru.riumin.spring.services;

import ru.riumin.spring.domain.Advert;

import java.util.List;

public interface AdvertService {

    Advert findAdvertById(Long id);

    List<Advert> findAllAdverts();

    Long saveAdvert(Advert advert);
}
