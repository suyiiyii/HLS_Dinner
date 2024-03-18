package top.suyiiyii.security;

import top.suyiiyii.schemas.TokenData;

public class Login {
    public static TokenData login(String username, String password) {
        TokenData tokenData = new TokenData();
        tokenData.uid = 200;
        tokenData.role = "admin";
        return tokenData;
    }

}
