package com.itheima.xchat.utils;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.utils
 *  @文件名:   StringUtil
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/28 21:20
 *  @描述：    TODO
 */
public class StringUtil {
    private static final String TAG = "StringUtil";

    private static final String REGEX_USER_NAME = "^[a-zA-Z]\\w{2,19}$";
    private static final String REGEX_PRASSWORD = "[0-9]{3,20}";

    /*验证用户名是否符合规范
    * */
    public static boolean isValidUserName(String userName){
        return userName.matches(REGEX_USER_NAME);
    }

    /*验证密码是否符合规范
    * */
    public static boolean isValidPassword(String password){
        return password.matches(REGEX_PRASSWORD);
    }

}
