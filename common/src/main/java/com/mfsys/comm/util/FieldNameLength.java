package com.mfsys.comm.util;

public interface FieldNameLength {

    String CODE_1 = "VARCHAR(1)";
    String CODE_2 = "VARCHAR(2)";
    String CODE_3 = "VARCHAR(3)";
    String CODE_4 = "VARCHAR(4)";
    String CODE_10000 = "VARCHAR(10000)";
    String CODE_5 = "VARCHAR(5)";
    String CODE_6 = "VARCHAR(60)";
    String CODE_7 = "VARCHAR(7)";
    String CODE_10 = "VARCHAR(10)";
    String CODE_12 = "VARCHAR(12)";
    String CODE_20 = "VARCHAR(100)";
    String CODE_25 = "VARCHAR(25)";
    String CODE_255 = "VARCHAR(255)";
    String CODE_IBAN = "VARCHAR(100)";
    String CODE_30 = "VARCHAR(30)";
    String CODE_50 = "VARCHAR(50)";
    String CODE_100 = "VARCHAR(100)";
    String CODE_150 = "VARCHAR(150)";
    String CODE_200 = "VARCHAR(200)";
    String CODE_500 = "VARCHAR(500)";

    String DESCRIPTION_LONG = "VARCHAR(40)";
    String DESCRIPTION_SHORT = "VARCHAR(20)";
    String BOOLEAN_BIT = "TINYINT(1)";
    String AMOUNT_REAL = "DECIMAL(38,6)";
    String AMOUNT_REAL_PERCENTAGES = "DECIMAL(16,15)";
    String AMOUNT_INT = "DECIMAL(20,0)";
    String DECIMAL = "DECIMAL(6,0)";

    String DATE = "DATE";
    String DATETIME = "DATETIME";
    String TIMESTAMP = "TIMESTAMP";
    String TIMESTAMP_NULLABLE = "TIMESTAMP NULL";
    String PASSWORD = "VARCHAR(50)";

    String BIGINT = "BIGINT";
    String INT = "INT";
    String FLOAT = "FLOAT";
    String RATE_REAL = "DECIMAL(20,10)";
    String XML = "TEXT";
    String TEXT = "TEXT";
    String DAYS = INT;
    String TINYINT = "TINYINT";

    String FISCALYEAR = "INT(4)";

    String POR_ORGACODE = CODE_10;
    String PLC_LOCACODE = CODE_10;
    String PLC_SUBLOCACODE = CODE_10;
    String DMP_PRODCODE = CODE_5;
    String PCA_GLACCODE = CODE_20;
    String PSA_SACTACCOUNT = PCA_GLACCODE;
    String PCC_ACTGCODE = CODE_4;
    String PPC_PRCGCODE = CODE_10;
    String PLT_LIMTCODE = CODE_3;
    String PLE_ELMTCODE = CODE_5;
    String PLE_EXPTPCODE = CODE_10;
    String NEW_STATUS = CODE_10;
    String OVER_DUEDAYS = CODE_10;
    String T_DAY = CODE_10;
    String UN_DORMANTBY = CODE_10;
    String UN_DORMANTDATE = CODE_10;
    String FILE_NAME_LENGTH = CODE_100;
    String PAS_ACSTCODE = CODE_4;
    String PCR_CURRCODE = CODE_3;
    String PTR_TRANCODE = CODE_3;
    String PCH_CHRGCODE = CODE_3;
    String SGT_GNTRNUMBER = BIGINT;
    String NODE_ID = CODE_5;
    String PSP_STYPCODE = CODE_6;
    String PST_SCHTCODE = CODE_6;

    String DEBIT_CREDIT = CODE_2;

    String FIELD_VAL_COMB = CODE_150;
    String SUS_USERCODE = CODE_25;
    String ACCOUNT_NUMBER = CODE_20;
    String ACCOUNT_TITLE = CODE_200;
    String USER_NAME = CODE_200;
    String CUSTOMER_CODE = CODE_20;

    String FILE_PATH = CODE_200;

    String SVH_VCHDCOMBID = CODE_3;
    String PIT_INSTCODE = CODE_3;
    String PET_EVENTCODE = INT;

    String PFM_FMODCODE = CODE_2;
    String PRO_RLOVCODE = CODE_7;

    String PCB_CBLTCODE = CODE_4;
    String PTM_TMETCODE = CODE_10;
    String PRT_REQTCODE = CODE_10;
    String PCT_COLTCODE = CODE_10;
    String PDT_DOTYCODE = CODE_10;
    String PDT_DTYPCODE = CODE_10;
    String PPM_PYMDCODE = CODE_3;

    String PPO_PURPCODE = CODE_30;
    String ERROR_CODE = CODE_10;
    String ERROR_DESC = CODE_500;

    String MLSLNSHTYPE = CODE_1;

    String PET_EXRTCODE = CODE_4;
    String REMARKS = CODE_200;

    String ATTRIBUTE_PATH = CODE_200;

    String STORED_PROCEDURE = CODE_200;
    String PPF_PRFNCODE = CODE_3;
    String PPM_PRMSCODE = CODE_3;

    String PCA_GLACDESC = CODE_200;
    String COMMENTS = CODE_200;
    String MODULE_ID = CODE_50;
    String INTERSERVICE_OPERATION = CODE_20; //

    String PHONE_NUM = CODE_20;
    String FAX_NUM = CODE_20;
    String EMAIL_ADDRESS = CODE_100;

    String PFS_ACNTYEAR = "YEAR";

    String CHANNEL_CODE = CODE_3;
    String PIN_LENGTH = TINYINT;
    String PIN_VALUE = CODE_6;

    String PNT_NOTFCODE = CODE_10;
    String PCL_CLASSIFICATION = CODE_10;
    String PCC_CLSSUBJECT = CODE_50;
    String SCL_SUBCLSCODE = CODE_10;
    String PCC_CLSMESSAGE = CODE_500;
    String MESSAGE_PARAMS = CODE_100;

    String GCD_DENUMCODE = CODE_10;
    String PCT_CSTYCODE = CODE_1;

    String PCD_CORDCODE = CODE_50;
    String MIT_INSTCODE = CODE_3;
    String MIS_INSEBOOKNO = INT;
    String PIS_INSTCODE = CODE_1;
    String PIN_INVSCODE = CODE_2;
    String SGT_GNTRINSTRUMENTNO = CODE_10;
    String PIS_INSTLEAFSTATUS = CODE_3;
    String PRT_RESNCODE = CODE_5;
    String GCC_COSTCENTERID = CODE_4;
    String GCC_DESCRIPTION = DESCRIPTION_SHORT;

}
