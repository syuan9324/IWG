# 介紹
透過API，對被javaCEK加密後的DB做事

##使用說明
打包後,放到可以連接到DB的主機上,

呼叫這個API做資料複製,以下的範例就是把BID_PRJ_PE這個table,ID的值複製到ID_ENCRYPT
注意! 如果originColName有中文會卡住...

    @PostMapping("/sql-executor/encrypt-column-batch/list")
    參數範例,放一個陣列包物件,可以透過另一個程式ae-sql-generator.js來產生這個參數
    [{
     "tableName":"BID_PRJ_PE",
     "originColName":"ID",
     "encryptColName":"ID_ENCRYPT",
     "pkColName":"WKUT,PRJNO,ID,PNAME,START_DATE,JOB_TYPE"
     }]



呼叫這個API下sql

    postMapping /sql-executor/exec-sql
    {
     "sql":"{your sql command}",
     "method":"query",
     "params":{
 		"key":"value"
      }
     }
sql是類似prepare statment寫法,參數用冒號放進去

    sql範例 select * from TABLE where Id = :id
    param範例 {"id":"123"}
    method可接受的 query or update
    

###打包
一般的maven打包指令配上參數
dev:公司環境測試用
uat:工程會雲端練習區用

    1.mvn clean
    2.mvn package -P uat
打包完會在target資料夾底下產生jar檔 noti-0.0.1-SNAPSHOT.jar

###正式使用
把剛剛打包完的jar檔,放到連的到對應資料庫的server;
用 java -jar 啟動 jar檔;
在透過linux指令curl call api



