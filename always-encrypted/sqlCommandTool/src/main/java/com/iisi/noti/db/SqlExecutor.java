package com.iisi.noti.db;



import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * SQL 執行程式，提供一般 CRUD 功能。
 * <p>
 * 底層是 Spring Framework 的 {@link NamedParameterJdbcTemplate}，所以本程式所指的 SQL 均可以包含定名參數，例如
 * {@code SELECT * FROM T WHERE T.A = :a"}，當中的 {@code a} 就是參數 Map 中的鍵值。<strong>強烈建議使用
 * {@link Query#builder(String, Object...)} 協助產生 Query 物件。</strong>
 */
@Component
public class SqlExecutor {

    private final Logger log = LoggerFactory.getLogger(SqlExecutor.class);

    @Getter
    @Setter
    private Class<? extends RowMapper> rowMapper = BeanPropertyRowMapper.class;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SqlExecutor(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * 執行無參數 DELETE SQL 語句。
     *
     * @param sql DELETE SQL 語句。
     * @return 資料刪除筆數。
     */
    public int delete(String sql) {
        return delete(new Query(sql));
    }

    /**
     * 執行 DELETE SQL 語句。
     *
     * @param sql        DELETE SQL 語句。
     * @param parameters 參數集。
     * @return 資料刪除筆數。
     */
    public int delete(String sql, Map<String, ?> parameters) {
        return delete(new Query(sql, parameters));
    }

    /**
     * 執行 DELETE Query。
     *
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @return 資料刪除筆數。
     */
    public int delete(Query query) {
        return execute(query);
    }

    public int execute(Query query) {
        requireNonNull(query, "Parameter \"query\" must not be null.");

        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        return jdbcTemplate.update(queryString, parameters);
    }

    public int execute(Query query, KeyHolder generatedKeyHolder) {
        requireNonNull(query, "Parameter \"query\" must not be null.");
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        return jdbcTemplate.update(queryString, new MapSqlParameterSource(parameters), generatedKeyHolder);
    }


    /**
     * 執行無參數 INSERT SQL 語句。
     *
     * @param sql INSERT SQL 語句。
     * @return 資料新增筆數。
     */
    public int insert(String sql) {
        return insert(new Query(sql));
    }



    /**
     * 執行 INSERT SQL 語句。
     *
     * @param sql        INSERT SQL 語句。
     * @param parameters 參數集。
     * @return 資料新增筆數。
     */
    public int insert(String sql, Map<String, ?> parameters) {
        return insert(new Query(sql, parameters));
    }

    /**
     * 執行 INSERT Query。
     *
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @return 資料新增筆數。
     */
    public int insert(Query query) {
        return execute(query);
    }

    private boolean isNotNullAndNotEmpty(String sql) {
        return sql != null && !sql.trim().isEmpty();
    }

    /**
     * 執行 SELECT SQL 語句，取得所有符合的資料。
     *
     * @param <T>   資料型別。
     * @param sql   SELECT SQL 語句。
     * @param clazz 查詢資料型別。
     * @return 符合查詢條件的資料清單; 如果完全沒有符合的資料，則回傳空的清單。
     */
    public <T> List<T> queryForList(String sql, Class<T> clazz) {
        return queryForList(new Query(sql), clazz);
    }

    /**
     * 執行 SELECT SQL 語句，取得所有符合的資料。
     *
     * @param <T>        資料型別。
     * @param sql        SELECT SQL 語句。
     * @param parameters 參數集。
     * @param clazz      查詢資料型別。
     * @return 符合查詢條件的資料清單; 如果完全沒有符合的資料，則回傳空的清單。
     */
    public <T> List<T> queryForList(String sql, Map<String, ?> parameters, Class<T> clazz) {
        return queryForList(new Query(sql, parameters), clazz);
    }




    public List<Map<String, Object>> queryListBySqlString(String sql, Map<String, ?> parameters) {
        return queryForList(new Query(sql, parameters));
    }





    /**
     * 執行 SELECT SQL 語句，取得所有符合的資料。
     *
     * @param <T>   資料型別。
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @param clazz 查詢資料型別。
     * @return 符合查詢條件的資料清單; 如果完全沒有符合的資料，則回傳空的清單。
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryForList(Query query, Class<T> clazz) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();

        if (isSimpleType(clazz)) {
            return this.jdbcTemplate.queryForList(queryString, parameters, clazz);
        } else {
            final RowMapper mapper = BeanPropertyRowMapper.newInstance(clazz);
            return this.jdbcTemplate.query(queryString, parameters, mapper);
        }
    }


    public List<Map<String, Object>> queryForList(Query query) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        return this.jdbcTemplate.queryForList(queryString, parameters);
    }

    public Map<String, Object> queryForMap(Query query) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        return this.jdbcTemplate.queryForMap(queryString, parameters);
    }

    private <T> boolean isSimpleType(Class<T> clazz) {
        return (
                ClassUtils.isPrimitiveOrWrapper(clazz) ||
                        clazz.equals(String.class) ||
                        clazz.equals(BigDecimal.class) ||
                        clazz.equals(BigInteger.class)
        );
    }

    /**
     * 執行無參數 UPDATE SQL 語句。
     *
     * @param sql UPDATE SQL 語句。
     * @return 資料異動筆數。
     */
    public int update(String sql) {
        return update(new Query(sql));
    }


    /**
     * 執行 UPDATE SQL 語句。
     *
     * @param sql        UPDATE SQL 語句。
     * @param parameters 參數集。
     * @return 資料異動筆數。
     */
    public int update(String sql, Map<String, ?> parameters) {
        return update(new Query(sql, parameters));
    }

    /**
     * 執行 UPDATE Query。
     *
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @return 資料異動筆數。
     */
    public int update(Query query) {
        return execute(query);
    }


}
