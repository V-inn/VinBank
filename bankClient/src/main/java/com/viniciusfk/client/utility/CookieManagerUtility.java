package com.viniciusfk.client.utility;

import java.net.*;
import java.util.List;

public class CookieManagerUtility {
    private static final CookieManager cookieManager = new CookieManager();

    static {
        // Set as default CookieHandler
        CookieHandler.setDefault(cookieManager);
    }

    public static void addCookies(List<String> cookieHeaders) {
        for (String cookieHeader : cookieHeaders) {
            List<HttpCookie> cookies = HttpCookie.parse(cookieHeader);
            for (HttpCookie cookie : cookies) {
                cookieManager.getCookieStore().add(null, cookie);
            }
        }
    }

    public static List<HttpCookie> getCookies() {
        return cookieManager.getCookieStore().getCookies();
    }
}