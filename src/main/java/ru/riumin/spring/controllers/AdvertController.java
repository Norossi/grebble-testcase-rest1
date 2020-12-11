package ru.riumin.spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.riumin.spring.domain.Advert;
import ru.riumin.spring.services.AdvertService;

import java.util.List;

@RestController
@RequestMapping("/adverts")
public class AdvertController {

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping
    public List<Advert> getAllAdverts() {
        return advertService.findAllAdverts();
    }

    @GetMapping("/{id}")
    public Advert getAdvertById(@PathVariable Long id) {
        return advertService.findAdvertById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveAdvert(@RequestBody Advert advert) {
        return advertService.saveAdvert(advert);
    }
}
