package ru.riumin.spring.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.riumin.spring.domain.AdvertModel;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class AdvertMapper implements RowMapper<AdvertModel> {

    @Override
    public AdvertModel mapRow(ResultSet resultSet, int i) throws SQLException {
        Array photos;
        AdvertModel advertModel = new AdvertModel();
        advertModel.setId(resultSet.getLong(AdvertModel.ID_COLUMN_NAME));
        advertModel.setTitle(resultSet.getString(AdvertModel.TITLE_COLUMN_NAME));
        advertModel.setDescription(resultSet.getString(AdvertModel.DESCRIPTION_COLUMN_NAME));
        advertModel.setPrice(resultSet.getInt(AdvertModel.PRICE_COLUMN_NAME));
        advertModel.setPostDate(resultSet.getTimestamp(AdvertModel.POSTDATE_COLUMN_NAME).toLocalDateTime());
        photos = resultSet.getArray(AdvertModel.PHOTOS_COLUMN_NAME);
        advertModel.setPhotos((Arrays.asList((String[]) photos.getArray())));
        return advertModel;
    }
}
