## 為何要有這個IWG
要監控多台server主機，確認主機裡面特定資料夾內的檔案是否有異動


## 機制介紹

![image](https://i.imgur.com/BjQwT6v.png)


## IWG機制的Table設計簡述

![image](https://i.imgur.com/WQ3QkD9.png)


#
HOSTNAME: 	iwg server會連到哪一台server做資料夾比對
ORIGIN_FOLDER:	iwg主機裡面,本地的ref資料夾
TARGET_FOLDER:	server主機的要被監控資料夾,

在這個table的內容,可以知道
1. IWG主機的ref資料夾是 /home/pwc/iwg/welcome-content
2. IWG會連到192.168.57.202, 看看202裡面的/opt/wildfly-4/welcome-content的裡面,
   是否跟IWG主機的/home/pwc/iwg/welcome-content裡面的一樣
3. IWG會連到192.168.57.201, 看看201裡面的/opt/wildfly-1/welcome-content的裡面,
   是否跟IWG主機的/home/pwc/iwg/welcome-content裡面的一樣


## IWG_HOSTS_TARGET欄位說明

ps. IWG可以監控單一個檔案 或是監控整個資料夾

如果要監控檔案,要填FILE_NAME,ORIGIN_FILE_LOCATION,TARGET_FILE_LOCATION

如果要監控資料夾,要填ORIGIN_FOLDER,TARGET_FOLDER

|column Name |說明|
|-----|--------|
|ID|PK|
|HOSTNAME|要監控的主機IP|
|PORT|要監控的主機PORT|
|FILE_NAME|要被監控的檔案|
|ORIGIN_FILE_LOCATION|IWG主機的REF檔案資料夾位置(不含檔名)|
|TARGET_FILE_LOCATION|被監控的主機的檔案資料夾位置(不含檔名)|
|ACTIVE|Y/N: 該筆監控是否要啟動|
|ORIGIN_FOLDER|IWG主機的REF檔案資料夾|
|TARGET_FOLDER|被監控的主機的檔案資料夾|
|CREATE_USER||
|CREATE_TIME||
|UPDATE_USER||
|UPDATE_TIME||
|PROFILE |當初開發時測試用..現在沒在用|
|TARGET_IN_LOCAL_LOCATION|當初開發時測試用..現在沒在用|


## 比對不一樣的處理方式
   1. 如果IWG ref有 被監控的server沒有
         
      會把ref裡面的檔案copy過去
   2. 如果IWG ref沒有,被監控的server有
      
      會把監控server的檔案轉移到${TARGET_FOLDER_BK}底下

   3. 如果IWG的檔案和被監控的server的檔案內容不一樣
      
      會把監控server的檔案轉移到${TARGET_FOLDER_BK}底下
      在把ref裡面的檔案copy過去