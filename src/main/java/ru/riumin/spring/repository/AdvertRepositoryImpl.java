package ru.riumin.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.riumin.spring.domain.AdvertModel;
import ru.riumin.spring.domain.AdvertPageModel;
import org.springframework.data.domain.Pageable;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdvertRepositoryImpl implements AdvertRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<AdvertModel> rowMapper;
    private final RowMapper<AdvertPageModel> rowMapperForPage;

    public AdvertRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<AdvertModel> rowMapper,
                                RowMapper<AdvertPageModel> rowMapperForPage) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.rowMapperForPage = rowMapperForPage;
    }

    @Override
    public AdvertModel findById(Long id) {
        List<AdvertModel> queryResult = jdbcTemplate.query(
                "SELECT * FROM adverts WHERE id = " + id.toString(), rowMapper
        );
        return queryResult.isEmpty() ? null : queryResult.get(0);
    }

    @Override
    public List<AdvertModel> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM adverts", rowMapper);
    }

    @Override
    public Page<AdvertPageModel> findAllPage(Pageable pageable) {
        Sort.Order order = pageable.getSort().toList().get(0);
        List<AdvertPageModel> content = jdbcTemplate.query(
                "SELECT * FROM adverts ORDER BY " + order.getProperty() + " " +
                        order.getDirection().name() + " LIMIT " + pageable.getPageSize() +
                        " OFFSET " + pageable.getOffset(), rowMapperForPage
        );
        return new PageImpl<AdvertPageModel>(content, pageable, getTotal());
    }

    private int getTotal() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM adverts", Integer.class);
    }

    @Override
    public Long save(AdvertModel advertModel) {
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
                        "(nextval('advert_id_sequence'), " +
                        advertModel.getTitle() + ", " +
                        getStringArray(advertModel.getPhotos()) + ", " +
                        advertModel.getDescription() + ", " +
                        advertModel.getPrice() + ", " +
                        advertModel.getPostDate().toString() + ")",
                keyHolder, new String[] {"id"}
                );
        return keyHolder.getKey().longValue();
    }

    private java.sql.Array getStringArray(List<String> list) {
        java.sql.Array stringArray = null;
        try {
            stringArray = jdbcTemplate.getDataSource().getConnection().
                    createArrayOf("varchar", list.toArray());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stringArray;
    }
}