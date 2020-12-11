package ru.riumin.spring.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.riumin.spring.domain.Advert;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
public class AdvertRepository {

    private JdbcTemplate jdbcTemplate;

    public AdvertRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Advert findById(Long id) {
        return jdbcTemplate.queryForObject("select id, name, photos, description, price, postDate " +
                "from Adverts where id=?", (resultSet, rowNum) -> {
                    Array photos;
                    Advert advert = new Advert();
                    advert.setId(resultSet.getLong("id"));
                    advert.setName(resultSet.getString("name"));
                    advert.setDescription(resultSet.getString("description"));
                    advert.setPrice(resultSet.getInt("price"));
                    photos = resultSet.getArray("photos");
                    advert.setPhotos((Arrays.asList((String[]) photos.getArray())));
                    advert.setPostDate(resultSet.getDate("postDate"));
                    return advert;
                }, id);
    }

    public List<Advert> findAll() {
        return null;
    }

    public Long save(Advert advert) {
        return null;
    }
}
