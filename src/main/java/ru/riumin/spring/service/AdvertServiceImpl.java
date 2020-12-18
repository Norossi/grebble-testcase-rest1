package ru.riumin.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;
import ru.riumin.spring.repository.AdvertRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private static final List<String> EXTRA_FIELDS;
    private static final Map<String, String> AVAILABLE_SORTING_OPTIONS;
    private final AdvertRepository advertRepository;

    static {
        EXTRA_FIELDS  = Arrays.asList("allPhotos", "description");
        AVAILABLE_SORTING_OPTIONS = new HashMap<>();
        AVAILABLE_SORTING_OPTIONS.put("postDate", AdvertModel.POSTDATE_COLUMN_NAME);
        AVAILABLE_SORTING_OPTIONS.put("price", AdvertModel.PRICE_COLUMN_NAME);
    }

    @Override
    public Page<AdvertPageModel> findAllPage(int page, String sortBy, String direction) {
        return advertRepository.findAllPage(PageRequest.of(page, 10, getPageSort(sortBy, direction)));
    }

    private Sort getPageSort(String sortBy, String direction) {
        Sort sort;
        switch (sortBy) {
            case "postDate":
                sortBy = AVAILABLE_SORTING_OPTIONS.get(sortBy);
                break;
            case "price":
                sortBy = AVAILABLE_SORTING_OPTIONS.get(sortBy);
                break;
            default:
                sortBy = AVAILABLE_SORTING_OPTIONS.get("postDate");
                break;
        }
        switch (direction) {
            case "asc":
                sort = Sort.by(sortBy).ascending();
                break;
            case "desc":
                sort = Sort.by(sortBy).descending();
                break;
            default:
                sort = Sort.by(sortBy).descending();
                break;
        }
        return sort;
    }

    @Override
    public Long saveAdvert(AdvertModel advertModel) {
        advertModel.setPostDate(LocalDateTime.now());
        return advertRepository.save(advertModel);
    }

    @Override
    public AdvertModel findAdvertById(Long id, @Nullable List<String> requiredFields) {
        AdvertModel advertModel = advertRepository.findById(id);
        return getAdvertWithRequiredFields(requiredFields, advertModel);
    }

    private AdvertModel getAdvertWithRequiredFields(@Nullable List<String> requiredFields,
                                                    AdvertModel advertModel) {
        advertModel.setPostDate(null);
        if (requiredFields == null) {
            return getDefaultAdvert(advertModel);
        } else {
            if (requiredFields.isEmpty()) {
                return getDefaultAdvert(advertModel);
            }
            else if (requiredFields.containsAll(EXTRA_FIELDS)) {
                return advertModel;
            }
            else if (requiredFields.contains(EXTRA_FIELDS.get(0))) {
                advertModel.setDescription(null);
                return advertModel;
            }
            else if (requiredFields.contains(EXTRA_FIELDS.get(1))) {
                advertModel.setPhotos(Collections.singletonList(advertModel.getPhotos().get(0)));
                return advertModel;
            }
            else {
                return getDefaultAdvert(advertModel);
            }
        }
    }

    private AdvertModel getDefaultAdvert(AdvertModel advertModel) {
        advertModel.setPhotos(Collections.singletonList(advertModel.getPhotos().get(0)));
        advertModel.setDescription(null);
        return advertModel;
    }

}
