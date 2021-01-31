package com.ming.sjll.base.tool;

import com.ming.sjll.base.tool.token.APIUtil;
import com.ming.sjll.base.tool.token.AndroidFileUtil;
import com.ming.sjll.base.tool.token.DataConfig;
import com.ming.sjll.base.tool.token.WinnerApplication;

public class Token {


    private static String userToken;

    public static String getUserToken() {
        userToken = AndroidFileUtil.readFileByLines(WinnerApplication.getContext().getCacheDir().getAbsolutePath() + "/" + DataConfig.TOKEN_FILE_NAME);
        return userToken;
    }

    public static String setUserToken(String token) {
        APIUtil.saveToken(token);
        return userToken;
    }
}
