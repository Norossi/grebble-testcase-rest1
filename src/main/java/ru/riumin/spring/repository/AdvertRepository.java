package ru.riumin.spring.repository;

import org.springframework.data.domain.Page;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertRepository {

    AdvertModel findById(Long id);

    List<AdvertModel> findAll();

    Long save(AdvertModel advertModel);

    Page<AdvertPageModel> findAllPage(Pageable pageable);
}
