const events = require("events");
const fs = require("fs");
const readline = require("readline");

let aeTableList = [        
        {tableName:'ADM_CONTACT',columnName:'IDNO',isPk:false,pkColumns:'USER_ID'},
        {tableName:'BID_CPAMI_ARCHITECT_RESUME',columnName:'IDN',isPk:true,pkColumns:'IDN,BUSINESS_NO',pkUpdateSkipColumn:'BUSINESS_NO',pkName:'PK_BID_CPAMI_ARCHITECT_RESUME'},//ok
        {tableName:'BID_CPAMI_FTENG',columnName:'FTENG_IDN',isPk:true,pkColumns:'FTENG_IDN,CPAMI_KEY',pkName:'PK_BID_CPAMI_FTENG'},//ok
        {tableName:'BID_CPAMI_FTENG_RESUME',columnName:'IDN',isPk:true,pkColumns:'IDN,JOB_TITLE,START_DATE',pkName:'PK_BID_CPAMI_FTENG_RESUME'},//ok
        {tableName:'BID_GOV_CHECKERR',columnName:'ROVE_TO',isPk:false,pkColumns:'WKUT,PRJNO,CHECK_DATE,ERR_NUM'},
        {tableName:'BID_EGR_DISCIPLINE',columnName:'ENGR_IDNO',isPk:false,pkColumns:'ENG_ENGR_DISCIPLINE_ID',pkName:'PK_BID_EGR_DISCIPLINE'},
        {tableName:'BID_PE',columnName:'ID',isPk:true,pkColumns:'ID',pkName:'PK_BID_PE'},// ########varchar have chinese
        {tableName:'BID_PRJ_PE',columnName:'ID',isPk:true,pkColumns:'WKUT,PRJNO,ID,PNAME,START_DATE,JOB_TYPE',pkName:'PK_BID_PRJ_PE'},
        {tableName:'BID_PERSONNEL_RESUME',columnName:'IDN',isPk:true,pkColumns:'IDN,NAME,JOB_TITLE',pkUpdateSkipColumn:'NAME',pkName:'PK_BID_PERSONNEL_RESUME'},//####varchar chinese
        {tableName:'BID_PERSONNEL_RESUME_ITEM',columnName:'IDN',isPk:true,pkColumns:'IDN,NAME,JOB_TITLE,INTERVAL_NO,WKUT,PRJNO',
                    pkUpdateSkipColumn:'NAME',pkName:'PK_BID_PERSONNEL_RESUME_ITEM'}, //#####varchar chinese
        {tableName:'BID_PISPBAS',columnName:'P_ID',isPk:true,pkColumns:'WKUT,P_ID,PK1',pkName:'PK_BID_PISPBAS'},//ok
        {tableName:'BID_PISP_BASEDATA',columnName:'P_ID',isPk:true,pkColumns:'P_ID',pkName:'PK_BID_PISP_BASEDATA'},//ok
        {tableName:'BID_PISP_DATE_EXTEND',columnName:'P_ID',isPk:true,pkColumns:'CHECK_UNIT,SDATE,P_ID',pkName:'PK_BID_PISP_DATE_EXTEND'},//ok
        {tableName:'BID_PRJ_QC',columnName:'ID',isPk:true,pkColumns:'WKUT,PRJNO,ID,START_DATE,JOB_TYPE',pkName:'PK_BID_PRJ_QC'}, // ########存了一堆中文
        {tableName:'BID_PRJ_WKMENG',columnName:'ID',isPk:true,pkColumns:'WKUT,PRJNO,ID,JOB,START_DATE',pkName:'PK_BID_PRJ_WKMENG'},// ########存了一堆中文
        {tableName:'BID_QC_RETRAIN',columnName:'ID',isPk:true,pkColumns:'ID,CLASS2',pkName:'PK_BID_QC_RETRAIN'}, //ok
        {tableName:'BID_QC_TRAIN',columnName:'ID',isPk:true,pkColumns:'ID,QC_TYPE',pkName:'PK_BID_QC_TRAIN'},//ok
        {tableName:'BID_VENDOR101',columnName:'RESP_IDN',isPk:false,pkColumns:'CCUT,PRJNO,O_WKUT,R101,START_DATE,END_DATE',pkName:'PK_BID_VENDOR101'},
        {tableName:'ENG_CHECK',columnName:'ENGR_IDNO',isPk:false,pkColumns:'ENG_CHECK_ID',nvarchar:true},
        {tableName:'ENG_COMP_MEMBER',columnName:'MEMBER_IDNO',isPk:false,pkColumns:'ENG_COMP_ID,ENG_COMP_MEMBER_ID,ENG_COMP_MEMBER_TYPE'},
        {tableName:'ENG_CST_LICENSE',columnName:'MASTER_IDNO',isPk:false,pkColumns:'ENG_CST_LICENSE_ID,ENG_CST_LICENSE_VERSION'},
        {tableName:'ENG_CST_LICENSE_MEMBER',columnName:'MEMBER_IDNO',isPk:false,pkColumns:'ENG_CST_LICENSE_ID,ENG_CST_LICENSE_MEMBER_ID,ENG_CST_LICENSE_VERSION'},//PK__ENG_CST___0DF247E68B859616
        {tableName:'ENG_ENGRCLASS_RECORD',columnName:'IDNO',isPk:false,pkColumns:'ENG_ENGRCLASS_RECORD_ID'},
        {tableName:'ENG_ENGRCLASS_RECORD_DETAIL',columnName:'IDNO',isPk:false,pkColumns:'ENG_COURSE_DETAIL_ID,ENG_ENGRCLASS_RECORD_ID'},
        {tableName:'ENG_ENGR_CERTIFICATE',columnName:'IDNO',isPk:false,pkColumns:'ENG_ENGR_CERTIFICATE_ID,ENG_ENGR_CERTIFICATE_VERSION'},
        {tableName:'ENG_ENGR_DISCIPLINE',columnName:'ENGR_IDNO',isPk:false,pkColumns:'ENG_ENGR_DISCIPLINE_ID'},//有中文
        {tableName:'ENG_ENGR_EXAM',columnName:'IDNO',isPk:false,pkColumns:'ENG_ENGR_EXAM_ID'},
        {tableName:'ENG_ENGR_INSURANCE',columnName:'IDNO',isPk:false,pkColumns:'ENG_ENGR_INSURANCE_ID'},
        {tableName:'ENG_ENGR_LICENSE',columnName:'IDNO',isPk:false,pkColumns:'ENG_ENGR_LICENSE_ID,ENG_ENGR_LICENSE_VERSION'},
        {tableName:'ENG_ENGR_PARTTIME_BLI',columnName:'ENG_ENGR_IDNO',isPk:false,pkColumns:'ENG_ENGR_PARTTIME_BLI_ID'},
        {tableName:'ENG_ENGR_PARTTIME_GCIS',columnName:'ENG_ENGR_IDNO',isPk:false,pkColumns:'ENG_ENGR_PARTTIME_GCIS_ID'},
        {tableName:'ENG_MEMBER',columnName:'IDNO',isPk:false,pkColumns:'ENG_MEMBER_ID'},
        {tableName:'ENG_YEARREPORT',columnName:'ENGR_IDNO',isPk:false,pkColumns:'ENG_YEARREPORT_ID,ENG_YEARREPORT_YYYY'},
        {tableName:'ENG_YEARREPORT_CASE_ENGR',columnName:'IDNO',isPk:false,pkColumns:'ENG_YEARREPORT_CASE_ENGR_ID,ENG_YEARREPORT_CASE_GUID,ENG_YEARREPORT_CASE_ID,ENG_YEARREPORT_ID,ENG_YEARREPORT_YYYY'},
        {tableName:'ENG_YEARREPORT_ENGR',columnName:'ENGR_IDNO',isPk:false,pkColumns:'ENG_YEARREPORT_ENGR_ID,ENG_YEARREPORT_ID,ENG_YEARREPORT_YYYY'},
        {tableName:'ENG_YEARREPORT_IMPORT_LOG',columnName:'IDNO',isPk:false,notNull:true,pkColumns:'ENG_YEARREPORT_IMPORT_LOG_ID'},
        {tableName:'ECT_PUBLIC',columnName:'IDNO',isPk:false,notNull:true,pkColumns:'ECT_PUBLISH_ID'},
        {tableName:'ECT_CHECK',columnName:'IDNO',isPk:false,notNull:true,pkColumns:'ECT_CHECK_ID'},
        {tableName:'GEO_EXAM_PASS_IMPORT',columnName:'IDNO',isPk:false,pkColumns:'GEO_EXAM_PASS_IMPORT_ID',nvarchar:true},
        {tableName:'GEO_MEMBER',columnName:'IDNO',isPk:false,pkColumns:'GEO_MEMBER_ID',nvarchar:true},
        {tableName:'QUA_QTRAIN_PERSON',columnName:'PEO_ID',isPk:false,pkColumns:'SID'},//QUA_QTRAIN_PERSON_PK
        {tableName:'QUA_QTRAIN_QUESTIONNAIRE',columnName:'PEO_ID',isPk:false,pkColumns:'QUA_QTRAIN_QUESTIONNAIRE_ID'}, //QUA_QTRAIN_QUESTIONNAIRE_PK
        {tableName:'QUA_QTRAIN_STUDENT',columnName:'PEO_ID',isPk:false,pkColumns:'QUA_QTRAIN_STUDENT_ID'}
    ]

//step0 判斷是否有中文
function judgeChinese(){
    //select * from {table} where {columnName} not LIKE '[A-Z]%'  and {columnName}  not LIKE '[0-9]%' and{columnName} not like '%[%$#@ *!?./]%'
    let sqlList = [];
    aeTableList.forEach(tableMetaData =>{
        sqlList.push(`select  ${tableMetaData.columnName} from ${tableMetaData.tableName} where ${tableMetaData.columnName} not LIKE '[A-Z]%'  and ${tableMetaData.columnName}  not LIKE '[0-9]%' and ${tableMetaData.columnName} not like '%[%$#@ *!?./]%'`)
    })

    sqlList.forEach(ele=>console.log(ele));
    sqlList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
   
}


//step 1  產生備份table sql
function backupTable(){
    //select * into BID_PERSONNEL_RESUME_BK from BID_PERSONNEL_RESUME
    let sqlList = [];
    aeTableList.forEach(tableMetaData =>{
        sqlList.push(`select * into ${tableMetaData.tableName}_BK from ${tableMetaData.tableName}`)
    })

    sqlList.forEach(ele=>console.log(ele));
    sqlList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
   
}
//step 1.3 如果idno欄位資料型態為varchar 但有存中文,要轉成nvarchar==> 會有問題 不能這樣做
// function handleVarcharHasChinese(){
//     let sqlList = [];
//         aeTableList.forEach(tableMetaData =>{
//             if(tableMetaData.vhc){
//                 //如果是pk的話要先drop pk,alter,在add pk回去
//                 if(tableMetaData.isPk){
//                     sqlList.push((`ALTER TABLE ${tableMetaData.tableName} DROP CONSTRAINT ${tableMetaData.pkName};
//                                  \nALTER TABLE ${tableMetaData.tableName} ALTER COLUMN ${tableMetaData.columnName} nvarchar(100) COLLATE Chinese_Taiwan_Stroke_CS_AS not null
//                                  \nALTER TABLE ${tableMetaData.tableName} ADD CONSTRAINT ${tableMetaData.pkName} PRIMARY KEY (${tableMetaData.pkColumns});

//                     `))

//                 //不是的話單純alter就好
//                 }else{
//                     sqlList.push((`ALTER TABLE ${tableMetaData.tableName} ALTER COLUMN ${tableMetaData.columnName} nvarchar(100) COLLATE Chinese_Taiwan_Stroke_CS_AS;`))
//                 }
//             }
//         })
//     sqlList.forEach(ele=>{
//         fs.appendFileSync('output.sql', ele + "\n");
//     });    
// }

//step 1.5 drop column

function dropColumn(){
    /**
     * ALTER TABLE dbo.BID_PERSONNEL_RESUME 
        drop column idn_ENCRYPT varchar(200)         
     */
        let sqlList = [];
        aeTableList.forEach(tableMetaData =>{
            sqlList.push(`ALTER TABLE dbo.${tableMetaData.tableName} \nDROP COLUMN ${tableMetaData.columnName}_ENCRYPT`)
        })
        sqlList.forEach(ele=>console.log(ele));
        sqlList.forEach(ele=>{
            fs.appendFileSync('output.sql', ele + "\n");
        });
}

//step2 add column
function addColumn(){
    /**
     * ALTER TABLE dbo.BID_PERSONNEL_RESUME 
        ADD idn_ENCRYPT varchar(200) 
        COLLATE Chinese_Taiwan_Stroke_BIN2  ENCRYPTED WITH (COLUMN_ENCRYPTION_KEY = JavaCEK, ENCRYPTION_TYPE = DETERMINISTIC, ALGORITHM = 'AEAD_AES_256_CBC_HMAC_SHA_256')
     */
    let sqlList = [];
    aeTableList.forEach(tableMetaData =>{
        sqlList.push(`ALTER TABLE dbo.${tableMetaData.tableName} \nADD ${tableMetaData.columnName}_ENCRYPT \nvarchar(200) COLLATE Chinese_Taiwan_Stroke_BIN2  ENCRYPTED WITH (COLUMN_ENCRYPTION_KEY = JavaCEK, ENCRYPTION_TYPE = DETERMINISTIC, ALGORITHM = 'AEAD_AES_256_CBC_HMAC_SHA_256')`)
    })
    sqlList.forEach(ele=>console.log(ele));
    sqlList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}

//step3 透過api insert data 進新加的欄位
function copyDataByApi(){
    /**
     * {
        "tableName":"BID_PERSONNEL_RESUME",
        "originColName":"IDN",
        "encryptColName":"IDN_ENCRYP"
        "pkColName":"WKUT,PRJNO,ID,JOB,START_DATE"
}       }
     */    
    requestBodyList = [];
    tableMetaData = aeTableList[0];
    aeTableList.forEach(tableMetaData =>{
        if(tableMetaData.pkUpdateSkipColumn){
            let originArr = tableMetaData.pkColumns.split(',');
            let ignoreArr = tableMetaData.pkUpdateSkipColumn.split(',');
            ignoreArr.forEach(ele=>{
                originArr = originArr.filter(el=>{
                    return ele !== el;
                })
            })
            tableMetaData.newPkColumns = originArr.join(',');
            requestBodyList.push((`{\n "tableName":"${tableMetaData.tableName}",\n "originColName":"${tableMetaData.columnName}",\n "encryptColName":"${tableMetaData.columnName}_ENCRYPT",\n "pkColName":"${tableMetaData.newPkColumns}" \n},`))
        }else{
            requestBodyList.push((`{\n "tableName":"${tableMetaData.tableName}",\n "originColName":"${tableMetaData.columnName}",\n "encryptColName":"${tableMetaData.columnName}_ENCRYPT",\n "pkColName":"${tableMetaData.pkColumns}" \n},`))
        }
        
    })
    requestBodyList.forEach(ele=>console.log(ele));
    requestBodyList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });

}

//step4 rename column 
function renameColumn(){
    /**
     * EXEC sp_rename 'dbo.BID_PERSONNEL_RESUME.IDN', 'IDN_DECRYPT', 'COLUMN';
     * EXEC sp_rename 'dbo.BID_PERSONNEL_RESUME.IDN_ENCRYPT', 'IDN', 'COLUMN';
     */
    let renameOriginColumnList = [];
    let renameEncryptColumnList = [];
    aeTableList.forEach(tableMetaData =>{
        renameOriginColumnList.push(`EXEC sp_rename 'dbo.${tableMetaData.tableName}.${tableMetaData.columnName}', '${tableMetaData.columnName}_DECRYPT', 'COLUMN';`)
        renameEncryptColumnList.push(`EXEC sp_rename 'dbo.${tableMetaData.tableName}.${tableMetaData.columnName}_ENCRYPT', '${tableMetaData.columnName}', 'COLUMN';`)
    })
    renameOriginColumnList.forEach(ele=>console.log(ele));
    renameEncryptColumnList.forEach(ele=>console.log(ele));
    renameOriginColumnList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
    fs.appendFileSync('output.sql', '\n');
    renameEncryptColumnList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}

// step5 特殊處理pk
function handlePkColumn(){
    /**
        ALTER TABLE BID_PERSONNEL_RESUME
        DROP CONSTRAINT PK_BID_PERSONNEL_RESUME;

        -- 5.3 補上PK 前先改成not null

        ALTER TABLE BID_PERSONNEL_RESUME ALTER COLUMN IDN 
        varchar(200) 
        COLLATE Chinese_Taiwan_Stroke_BIN2  ENCRYPTED WITH (COLUMN_ENCRYPTION_KEY = JavaCEK, ENCRYPTION_TYPE = DETERMINISTIC, ALGORITHM = 'AEAD_AES_256_CBC_HMAC_SHA_256')
        NOT NULL;

        --5.4 加上PK

        ALTER TABLE BID_PERSONNEL_RESUME
        ADD CONSTRAINT PK_BID_PERSONNEL_RESUME PRIMARY KEY (IDN,NAME,JOB_TITLE);
     */
    let handlePkList = [];
    aeTableList.forEach(tableMetaData =>{
        if(tableMetaData.isPk){
            handlePkList.push((`ALTER TABLE ${tableMetaData.tableName} DROP CONSTRAINT ${tableMetaData.pkName};
                \nALTER TABLE ${tableMetaData.tableName} ALTER COLUMN ${tableMetaData.columnName} varchar(200) COLLATE Chinese_Taiwan_Stroke_BIN2  ENCRYPTED WITH (COLUMN_ENCRYPTION_KEY = JavaCEK, ENCRYPTION_TYPE = DETERMINISTIC, ALGORITHM = 'AEAD_AES_256_CBC_HMAC_SHA_256') NOT NULL;
                \nALTER TABLE ${tableMetaData.tableName} ALTER COLUMN ${tableMetaData.columnName}_DECRYPT varchar(200) NULL;
                \nALTER TABLE ${tableMetaData.tableName} ADD CONSTRAINT ${tableMetaData.pkName} PRIMARY KEY (${tableMetaData.pkColumns});
            `))
        }
    })
    handlePkList.forEach(ele=>console.log(ele));
    handlePkList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}

// step6 check
function checkAeResult(){
    /**
     * select top 100(*) from tb where idno_decrpted is not null
     */
    let checkSqlList = [];
    aeTableList.forEach(tableMetaData =>{
        checkSqlList.push(`select top 100 * from ${tableMetaData.tableName} where ${tableMetaData.columnName}_DECRYPT is not null and ${tableMetaData.columnName} is null`)

    })
    checkSqlList.forEach(ele=>console.log(ele));

    checkSqlList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });

}

//================= reverse用(舊的欄位 idn_decrypt還存在的情況)
// step r1 取消pk

function reversePkColumn(){
    /**
        ALTER TABLE BID_PERSONNEL_RESUME
        DROP CONSTRAINT PK_BID_PERSONNEL_RESUME;

        -- 5.3 補上PK 前先改成not null

        ALTER TABLE BID_PERSONNEL_RESUME ALTER COLUMN IDN 
        varchar(200) 
        COLLATE Chinese_Taiwan_Stroke_BIN2  ENCRYPTED WITH (COLUMN_ENCRYPTION_KEY = JavaCEK, ENCRYPTION_TYPE = DETERMINISTIC, ALGORITHM = 'AEAD_AES_256_CBC_HMAC_SHA_256')
        NOT NULL;

        --5.4 加上PK

        ALTER TABLE BID_PERSONNEL_RESUME
        ADD CONSTRAINT PK_BID_PERSONNEL_RESUME PRIMARY KEY (IDN,NAME,JOB_TITLE);
     */
    let handlePkList = [];
    aeTableList.forEach(tableMetaData =>{
        if(tableMetaData.isPk){
            handlePkList.push((`ALTER TABLE ${tableMetaData.tableName} DROP CONSTRAINT ${tableMetaData.pkName};`))
        }
    })
    handlePkList.forEach(ele=>console.log(ele));
    handlePkList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}
// step r2 把idn改成 idn_encrypt,在把idn idn_decrypt改成 idn
function renameColumnReverse(){
    /**
     * EXEC sp_rename 'dbo.BID_PERSONNEL_RESUME.IDN', 'IDN_DECRYPT', 'COLUMN';
     * EXEC sp_rename 'dbo.BID_PERSONNEL_RESUME.IDN_ENCRYPT', 'IDN', 'COLUMN';
     */
    let renameOriginColumnList = [];
    let renameEncryptColumnList = [];
    aeTableList.forEach(tableMetaData =>{
        renameOriginColumnList.push(`EXEC sp_rename 'dbo.${tableMetaData.tableName}.${tableMetaData.columnName}', '${tableMetaData.columnName}_ENCRYPT', 'COLUMN';`)
        renameEncryptColumnList.push(`EXEC sp_rename 'dbo.${tableMetaData.tableName}.${tableMetaData.columnName}_DECRYPT', '${tableMetaData.columnName}', 'COLUMN';`)
    })
    renameOriginColumnList.forEach(ele=>console.log(ele));
    renameEncryptColumnList.forEach(ele=>console.log(ele));
    renameOriginColumnList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
    fs.appendFileSync('output.sql', '\n');
    renameEncryptColumnList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}
// step r3 drop column
function dropEncryptColumn(){
    /**
     * ALTER TABLE dbo.BID_PERSONNEL_RESUME 
        drop column idn_ENCRYPT varchar(200)         
     */
        let sqlList = [];
        aeTableList.forEach(tableMetaData =>{
            sqlList.push(`ALTER TABLE dbo.${tableMetaData.tableName} \nDROP COLUMN ${tableMetaData.columnName}_ENCRYPT`)
        })
        sqlList.forEach(ele=>console.log(ele));
        sqlList.forEach(ele=>{
            fs.appendFileSync('output.sql', ele + "\n");
        });
}
// step r4 把原本的允許null改成not null
/**
 * ALTER TABLE BID_CPAMI_ARCHITECT_RESUME ALTER COLUMN IDN nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CS_AS NOT NULL;
 */

function pkNotNull(){
    let handlePkList = [];
    aeTableList.forEach(tableMetaData =>{
        if(tableMetaData.isPk){
            handlePkList.push((`ALTER TABLE ${tableMetaData.tableName} ALTER COLUMN ${tableMetaData.pkName} nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CS_AS NOT NULL;`))
        }
    })
    handlePkList.forEach(ele=>console.log(ele));
    handlePkList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}

// step r5 加回主鍵
function handlePkColumnBack(){
    /**
        加上PK
        ALTER TABLE BID_PERSONNEL_RESUME
        ADD CONSTRAINT PK_BID_PERSONNEL_RESUME PRIMARY KEY (IDN,NAME,JOB_TITLE);
     */
    let handlePkList = [];
    aeTableList.forEach(tableMetaData =>{
        if(tableMetaData.isPk){
            handlePkList.push((`ALTER TABLE ${tableMetaData.tableName} ADD CONSTRAINT ${tableMetaData.pkName} PRIMARY KEY (${tableMetaData.pkColumns});`))
        }
    })
    handlePkList.forEach(ele=>console.log(ele));
    handlePkList.forEach(ele=>{
        fs.appendFileSync('output.sql', ele + "\n");
    });
}

fs.appendFileSync('output.sql', '----- step0 judgeChinese' + "\n");
judgeChinese()
fs.appendFileSync('output.sql', '----- step1 back up tables' + "\n");
backupTable();
// fs.appendFileSync('output.sql', '----- step1.5 drop column' + "\n");
// dropColumn();
fs.appendFileSync('output.sql', '----- step2 add column' + "\n");
addColumn();
fs.appendFileSync('output.sql', '----- step3 copy data by api with following request' + "\n");
copyDataByApi();
fs.appendFileSync('output.sql', '----- step4 rename columns' + "\n");
renameColumn();
fs.appendFileSync('output.sql', '----- step5 handle pk' + "\n");
handlePkColumn();
fs.appendFileSync('output.sql', '----- reverse step' + "\n");
checkAeResult();

// reversePkColumn();
// fs.appendFileSync('output.sql',"\n");
// renameColumnReverse();
// fs.appendFileSync('output.sql',"\n");
// dropEncryptColumn();
// fs.appendFileSync('output.sql',"\n");
// pkNotNull();
// fs.appendFileSync('output.sql',"\n");
// handlePkColumnBack();