##介紹
打包後直接執行,會在有JavaCMK的DB,產生javaCEK

##使用
在AlwaysEncrypted.java這個檔案上,寫死參數,直接call
需要定義的參數如以下:
private static String keyAlias = "AlwaysEncryptedKey";

    DB裡面CMK的名字
    String columnMasterKeyName = "JavaCMK";
    
    要產生的CEK的名字
    String columnEncryptionKey = "JavaCEK";
    
    javaKeyStore的位置
    String keyStoreLocation = "/home/iisiadmin/jks/keystore.jks";
    
    DB連線字串
    String connectionUrl = "jdbc:sqlserver://10.100.216.11:1433;encrypt=true;databaseName=PWC_DB;user=sa;password=1qaz!QAZ;columnEncryptionSetting=Enabled;trustServerCertificate=true";
    
    放在javaKeyStore裡面的key的密碼
    char[] keyStoreSecret = "IISI@pwcdb20231231".toCharArray();

##打包
    
    mvn clean
    mvn package

會在target底下有

    always-encrypt-1.0.0.jar
    always-encrypt-1.0.0-jar-with-dependencies.jar

複製always-encrypt-1.0.0-jar-with-dependencies.jar到可以連到DB的server
直接

    java -jar always-encrypt-1.0.0-jar-with-dependencies.jar

跑完就可以到DB看看有沒有JavaCEK了

    

