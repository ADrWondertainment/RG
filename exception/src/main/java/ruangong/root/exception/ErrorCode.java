package ruangong.root.exception;

public class ErrorCode {


    public static final Integer USER_NAME_DUPLICATED = 10000;
    public static final Integer USER_INSERT_FAILURE = 10010;
    public static final Integer USER_INSERT_SUCCESS = 10020;
    public static final Integer USER_NAME_UNFINDED = 10030;
    public static final Integer USER_PASSWORD_UNMATCH = 10040;
    public static final Integer USER_LOGIN_SUCCESS = 10050;
    public static final Integer USER_CHANGEPASS_FAILURE = 10060;
    public static final Integer USER_CHANGEPASS_SUCCESS = 10070;

    public static final Integer USER_ILLEGAL_ACCESS = 20070;


    public static final Integer SUCCESS = 1;
    public static final Integer FAIL = 0;
    /*----------------------------------------------------------------------------*/
    public static final Integer ALL_SET = 66666;
    public static final Integer TEMPLATE_INSERT_SUCCESS = 20011;
    public static final Integer TEMPLATE_NAME_DUPLICATED = 20000;
    public static final Integer TEMPLATE_INSERT_FAILURE = 20010;
    public static final Integer TEMPLATE_DATA_NULL = 20020;
    public static final Integer TEMPLATE_ID_UNREGISTERED = 20030;
    public static final Integer TEMPLATE_DELETE_FAILURE = 20040;
    public static final Integer TEMPLATE_SELECT_FAILURE = 20080;


    public static final Integer FRONT_DATA_IRREGULAR = 20050;

    public static final Integer UTIL_ERROR = 20060;


    public static final Integer FAST_CREAT_SHEET_FAILURE = 20090;

    public static final Integer SHEET_SELECT_FAILURE = 20100;

    public static final Integer ANSWER_INSERT_FAILURE = 20110;
    public static final Integer ANSWER_ID_UNREGISTERED = 20120;
    public static final Integer ANSWER_SELECT_FAILURE = 20130;
    public static final Integer ANSWER_PROCESS_FAILURE = 20140;

    public static final Integer ILLEGAL_ANSWER_TIME = 20150;

    public static final Integer PASS_FAILURE = 20160;
    public static final Integer SHEET_STRUCTURE_CONTAMINATED = 20170;
}
