package ru.riumin.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AdvertRepositoryImpl implements AdvertRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<AdvertModel> rowMapper;
    private final RowMapper<AdvertPageModel> rowMapperForPage;

    @Override
    public AdvertModel findById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        List<AdvertModel> queryResult = jdbcTemplate.query(
                "SELECT * FROM adverts WHERE id = :id", param, rowMapper
        );
        return queryResult.isEmpty() ? null : queryResult.get(0);
    }

    @Override
    public Long save(AdvertModel advertModel) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", advertModel.getTitle())
                .addValue("photos", getStringArray(advertModel.getPhotos()))
                .addValue("descr", advertModel.getDescription())
                .addValue("price", advertModel.getPrice())
                .addValue("date", advertModel.getPostDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                "INSERT INTO adverts " +
                        "(" + AdvertModel.ID_COLUMN_NAME + ", " +
                        AdvertModel.TITLE_COLUMN_NAME + ", " +
                        AdvertModel.PHOTOS_COLUMN_NAME + ", " +
                        AdvertModel.DESCRIPTION_COLUMN_NAME + ", " +
                        AdvertModel.PRICE_COLUMN_NAME + ", " +
                        AdvertModel.POSTDATE_COLUMN_NAME + ")" +
                        " VALUES " +
                        "(nextval('adverts_id_sequence'), :title, :photos, :descr, :price, :date)",
                params, keyHolder, new String[]{"id"}
        );
        return keyHolder.getKey().longValue();
    }

    private java.sql.Array getStringArray(List<String> list) {
        java.sql.Array stringArray = null;
        try {
            stringArray = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().
                    createArrayOf("varchar", list.toArray());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stringArray;
    }

    @Override
    public Page<AdvertPageModel> findAllPage(Pageable pageable) {
        Sort.Order order = pageable.getSort().toList().get(0);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("pageSize", pageable.getPageSize())
                .addValue("get_offset", pageable.getOffset());
        List<AdvertPageModel> content = jdbcTemplate.query(
                "SELECT * FROM adverts " +
                        "ORDER BY " + order.getProperty() + " " + order.getDirection().name() +
                        " LIMIT :pageSize " +
                        "OFFSET :get_offset", params, rowMapperForPage
        );
        return new PageImpl<AdvertPageModel>(content, pageable, getTotal());
    }

    private int getTotal() {
        return jdbcTemplate.getJdbcTemplate().queryForObject(
                        "SELECT count(*) FROM adverts" ,Integer.class);
    }
}
