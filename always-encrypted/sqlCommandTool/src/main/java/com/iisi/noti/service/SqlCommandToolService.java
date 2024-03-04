package com.iisi.noti.service;

import com.iisi.noti.db.SqlExecutor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SqlCommandToolService {

    private final SqlExecutor sqlExecutor;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlCommandToolService(SqlExecutor sqlExecutor, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.sqlExecutor = sqlExecutor;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public List<Map<String, Object>> execQuerySql(String sqlString, Map<String, Object> paramMap) {
        return sqlExecutor.queryListBySqlString(sqlString, paramMap);
    }

    @Transactional
    public int execUpdateSql(String sqlString, Map<String, Object> paramMap) {
        return sqlExecutor.update(sqlString, paramMap);
    }

    @Transactional
    public int[] batchUpdate(Map<String, Object> requestMap) {

        String tableName = requestMap.get("tableName").toString();
        String pkColName = requestMap.get("pkColName").toString();


        String originColName = requestMap.get("originColName").toString();
        String encryptColName = requestMap.get("encryptColName").toString();

        StringBuilder sqlBuilder = new StringBuilder();
        //select {idno} from {table_name}
        sqlBuilder.append("SELECT ").append(pkColName).append(",").append(originColName).append(" FROM ").append(tableName);
        List<Map<String, Object>> res = this.execQuerySql(sqlBuilder.toString(), null);

        List<Map<String, Object>> batchValues = new ArrayList<>(res.size());
        String updateValueRenameKey = "";

        //有多個pk
        //要特別處理如果pk包含encrypt欄位的狀況
        if (pkColName.contains(",")) {
            String[] pks = pkColName.split(",");
            res.forEach(row -> {
                if (row.get(originColName) != null) {
                    MapSqlParameterSource temp = new MapSqlParameterSource(originColName, row.get(originColName));
                    Arrays.stream(pks).forEach(pk -> {
                        temp.addValue(pk, row.get(pk));
                    });
                    batchValues.add(temp.getValues());
                }


            });

        } else {
            res.forEach(row -> {
                if (row.get(originColName) != null) {
                    batchValues.add(
                            new MapSqlParameterSource(originColName, row.get(originColName))
                                    .addValue(pkColName, row.get(pkColName))
                                    .getValues());
                }

            });
        }
        //update GEO_MEMBER set {idno_encrpy} = :idno where {idno} = 1073

        StringBuilder updateSqlBuilder = new StringBuilder();
        if (pkColName.contains(",")) {
            updateSqlBuilder.append("update ").append(tableName)
                    .append(" set ").append(encryptColName).append(" =:").append(originColName)
//                    .append(" where ").append(originColName).append(" =:").append(originColName);
                    .append(" where ");
            String[] pks = pkColName.split(",");

            for (int i = 0; i < pks.length; i++) {
                updateSqlBuilder.append(pks[i]).append(" =:").append(pks[i]);
                if (i < pks.length - 1) {
                    updateSqlBuilder.append(" and ");
                }
            }
        } else {
            updateSqlBuilder.append("update ").append(tableName)
                    .append(" set ").append(encryptColName).append(" =:").append(originColName)
                    .append(" where ").append(pkColName).append(" =:").append(pkColName);
        }
        String sql = updateSqlBuilder.toString();
        Map[] checkMap = batchValues.toArray(new Map[batchValues.size()]);
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[batchValues.size()]));
        return updateCounts;
    }

    @Transactional
    public int forEachUpdateForCheckFullyUpdate(Map<String, Object> requestMap) {
        String tableName = requestMap.get("tableName").toString();
        String originColName = requestMap.get("originColName").toString();
        String encryptColName = requestMap.get("encryptColName").toString();

        StringBuilder sqlBuilder = new StringBuilder();
        //select distinct {idno},{idno_encrypt} from {table_name} where {idno_encrypt} is null and {idno} is not null
        sqlBuilder.append("SELECT DISTINCT ")
                .append(originColName).append(",")
                .append(encryptColName)
                .append(" FROM ").append(tableName)
                .append(" where ").append(encryptColName).append(" is null and ")
                .append(originColName).append(" is not null");

        List<Map<String, Object>> res = this.execQuerySql(sqlBuilder.toString(), null);
        StringBuilder updateSqlBuilder = new StringBuilder();
        AtomicInteger returnCount = new AtomicInteger();
        res.forEach(row->{
            updateSqlBuilder.setLength(0);
            //串接新查詢字串
            //update {table_name} set {idno_encrypt} = :{idno} where idno = {idno}
            updateSqlBuilder.append("update ").append(tableName).append(" set ").append(encryptColName).append(" =:")
                    .append(originColName).append(" where ").append(originColName)
                    .append(" =:").append(originColName);

            Map<String,Object>params = new HashMap<>();
            params.put(originColName,row.get(originColName));
            returnCount.addAndGet(this.execUpdateSql(updateSqlBuilder.toString(), params));
        });
        return returnCount.get();
    }
}
