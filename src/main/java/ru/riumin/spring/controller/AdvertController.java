package ru.riumin.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;
import ru.riumin.spring.service.AdvertService;

import java.util.List;

@RestController
@RequestMapping("/adverts")
public class AdvertController {

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping
    public Page<AdvertPageModel> getAllAdverts(@RequestParam(name = "page", required = false,
                                              defaultValue = "0") int page,
                                               @RequestParam(name = "sortBy", required = false,
                                              defaultValue = "postDate") String sortBy,
                                               @RequestParam(name = "direction", required = false,
                                              defaultValue = "desc") String direction) {
        return advertService.findAllPage(page, sortBy, direction);
    }

    @GetMapping("/{id}")
    public AdvertModel getAdvertById(@PathVariable Long id,
                                     @RequestParam(name = "fields", required = false) List<String> fields) {
        return advertService.findAdvertById(id, fields);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveAdvert(@RequestBody AdvertModel advertModel) {
        return advertService.saveAdvert(advertModel);
    }

    @GetMapping("/hello")
    public String hello() {
        return "HELLO";
    }
}
