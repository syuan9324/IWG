package com.iisi.noti.Resource;

import com.iisi.noti.service.SqlCommandToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SqlCommandToolResource {
    private final Logger log = LoggerFactory.getLogger(SqlCommandToolResource.class);

    private final SqlCommandToolService sqlCommandToolService;

    public SqlCommandToolResource(SqlCommandToolService sqlCommandToolService) {
        this.sqlCommandToolService = sqlCommandToolService;
    }

    /**
     * requestMap 資料型態如下
     * 其中method有query,update
     * {
     * 	"sql":"{your sql command}",
     * 	"method":"query",
     * 	"params":{
     * 		"key":"value"
     *   }
     * }
     * @param requestMap
     * @return
     */
    @PostMapping("/sql-executor/exec-sql")
    public Map<String,Object> testSql(@RequestBody Map<String,Object> requestMap){
        Map<String,Object>result = new HashMap<>();
        String sql = requestMap.get("sql").toString();
        Map<String,Object> params = (Map<String, Object>) requestMap.get("params");
        String method = requestMap.get("method").toString();
        switch (method) {
            case "query":
                List<Map<String, Object>> res = sqlCommandToolService.execQuerySql(sql, params);
                result.put("data",res);
                break;
            case "update":
                int count = sqlCommandToolService.execUpdateSql(sql, params);
                result.put("data",count);
                break;
            default:
                break;
        }
        result.put("status","ok");
        return result;

    }
    /**
     * requestMap
     * {
     * 	"tableName":"",
     * 	"pkColName":""
     * 	"originColName":"",
     * 	"encryptColName":"",
     * }
     * @param requestMap
     * @return
     */
    @PostMapping("/sql-executor/encrypt-column-batch")
    public String encryptColumnSqlBatch(@RequestBody Map<String,Object> requestMap){

        int[] res = sqlCommandToolService.batchUpdate(requestMap);
        log.debug("check :{}",res.length);
        return "ok";

    }

    /**
     *
     * requestMap
     * {
     * 	"tableName":"",
     * 	"originColName":"",
     * 	"encryptColName":"",
     * }
     * @param requestMapList requestMap的list
     * @return
     */
    @PostMapping("/sql-executor/encrypt-column-batch/list")
    public String encryptColumnSqlBatchWithRequestList(@RequestBody List<Map<String,Object>> requestMapList){

        //用batch update
            requestMapList.forEach(requestMap->{
                int[] res = sqlCommandToolService.batchUpdate(requestMap);
                log.debug("check :{}",res.length);
            });
            //補一下batch update 沒有正常update到的
            // 兩種可能
            // 1. batch update 時候 pk 欄位有中文
            // 2. idno那個欄位值有中文或是全形字
            requestMapList.forEach(requestMap->{
                int res = sqlCommandToolService.forEachUpdateForCheckFullyUpdate(requestMap);
                log.debug("check :{}",res);
            });
            return "ok";

    }

    @PostMapping("/sql-executor/encrypt-column-batch/list/check")
    public String checkEncryptColumnSqlBatchWithRequestList(@RequestBody List<Map<String,Object>> requestMapList){

        requestMapList.forEach(requestMap->{
            int res = sqlCommandToolService.forEachUpdateForCheckFullyUpdate(requestMap);
            log.debug("check :{}",res);
        });

        return "ok";

    }

    /**
     *
     * requestMap
     * {
     * 	"tableName":"",
     * 	"originColName":"",
     * 	"encryptColName":"",
     * }
     * @param requestMap requestMap的list
     * @return
     */
    @PostMapping("/sql-executor/encrypt-column-batch/debug")
    public String encryptColumnSqlBatchWithRequestList(@RequestBody Map<String,Object> requestMap){

        String tableName = requestMap.get("tableName").toString();
        String pkColName = requestMap.get("pkColName").toString();
        String originColName = requestMap.get("originColName").toString();
        String encryptColName = requestMap.get("encryptColName").toString();

        StringBuilder sqlBuilder = new StringBuilder();
        //select distinct {idno} from {table_name}
        sqlBuilder.append("SELECT ").append(pkColName).append(",").append(originColName).append(" FROM ").append(tableName);
        //select 出來
        List<Map<String, Object>> res = sqlCommandToolService.execQuerySql(sqlBuilder.toString(), null);
        StringBuilder updateSqlBuilder = new StringBuilder();
        res.forEach(row->{
            //清掉builder
            updateSqlBuilder.setLength(0);
            //串接新查詢字串
            //update GEO_MEMBER set idno = :idno where GEO_MEMBER_ID = 1073
            updateSqlBuilder.append("update ").append(tableName).append(" set ").append(encryptColName).append(" =:")
                .append(originColName).append(" where ").append(pkColName)
                .append(" =:").append(pkColName);

            Map<String,Object>params = new HashMap<>();
            params.put(pkColName,row.get(pkColName));
            params.put(originColName,row.get(originColName));
//                if(row.get(originColName)!=null){
//                    sqlCommandToolService.execUpdateSql(updateSqlBuilder.toString(),params);
//                }
            sqlCommandToolService.execUpdateSql(updateSqlBuilder.toString(),params);
        });
        return "ok";

    }



    /**
     * requestMap
     * {
     * 	"tableName":"",
     * 	"idColName":"",
     * 	"originColName":"",
     * 	"encryptColName":"",
     * }
     * @param requestMap
     * @return
     */
//    @PostMapping("/sql-executor/encrypt-column")
//    public String encryptColumnSql(@RequestBody Map<String,Object> requestMap,Authentication auth){
//        log.debug("**********************");
//        log.debug("authName:{}",auth.getName());
//        if(this.privilegeUserName.equals(auth.getName())){
//            String tableName = requestMap.get("tableName").toString();
//            String idColName = requestMap.get("idColName").toString();
//            String originColName = requestMap.get("originColName").toString();
//            String encryptColName = requestMap.get("encryptColName").toString();
//
//            //SELECT
//            StringBuilder sqlBuilder = new StringBuilder();
//            sqlBuilder.append("SELECT ").append(idColName).append(",").append(originColName).append(" FROM ").append(tableName);
//            //select 出來
//            List<Map<String, Object>> res = sqlCommandToolService.execQuerySql(sqlBuilder.toString(), null);
//            StringBuilder updateSqlBuilder = new StringBuilder();
//            res.forEach(row->{
//                //清掉builder
//                updateSqlBuilder.setLength(0);
//                //串接新查詢字串
//                //update GEO_MEMBER set idno = :idno where GEO_MEMBER_ID = 1073
//                updateSqlBuilder.append("update ").append(tableName).append(" set ").append(encryptColName).append(" =:")
//                    .append(originColName).append(" where ").append(idColName)
//                    .append(" =:").append(idColName);
//
//                Map<String,Object>params = new HashMap<>();
//                params.put(idColName,row.get(idColName));
//                params.put(originColName,row.get(originColName));
//                if(row.get(originColName)!=null){
//                    sqlCommandToolService.execUpdateSql(updateSqlBuilder.toString(),params);
//                }
//            });
//
//            return "ok";
//        }
//        return "unprivileged-account";
//    }



}
