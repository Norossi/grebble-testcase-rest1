package ru.riumin.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;
import ru.riumin.spring.service.AdvertService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Long saveAdvert(@Valid @RequestBody AdvertModel advertModel) {
        return advertService.saveAdvert(advertModel);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
