package ru.riumin.spring.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class AdvertPageMapper implements RowMapper<AdvertPageModel> {

    @Override
    public AdvertPageModel mapRow(ResultSet resultSet, int i) throws SQLException {
        Array photos;
        AdvertPageModel advertModel = new AdvertPageModel();
        advertModel.setId(resultSet.getLong(AdvertModel.ID_COLUMN_NAME));
        advertModel.setTitle(resultSet.getString(AdvertModel.TITLE_COLUMN_NAME));
        advertModel.setPrice(resultSet.getInt(AdvertModel.PRICE_COLUMN_NAME));
        advertModel.setPostDate(resultSet.getTimestamp(AdvertModel.POSTDATE_COLUMN_NAME).toLocalDateTime());
        photos = resultSet.getArray(AdvertModel.PHOTOS_COLUMN_NAME);
        advertModel.setMainPhoto((Arrays.asList((String[]) photos.getArray())).get(0));
        return advertModel;
    }
}
