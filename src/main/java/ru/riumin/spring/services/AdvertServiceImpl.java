package ru.riumin.spring.services;

import org.springframework.stereotype.Service;
import ru.riumin.spring.domain.Advert;
import ru.riumin.spring.repositories.AdvertRepository;

import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;

    public AdvertServiceImpl(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @Override
    public Advert findAdvertById(Long id) {
        return advertRepository.findById(id);
    }

    @Override
    public List<Advert> findAllAdverts() {
        return advertRepository.findAll();
    }

    @Override
    public Long saveAdvert(Advert advert) {
        advertRepository.save(advert);
        return advert.getId();
    }

}
