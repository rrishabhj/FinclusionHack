package com.rishabh.github.finclusionhack.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpMessage;

import java.util.Date;

public final class ApiUtils {
    private static Gson sGson;

    @SuppressWarnings("deprecation")
    public static void appendCommonRequestParams(HttpMessage httpMessage) {
        httpMessage.setHeader("Content-Type", "application/json");
        httpMessage.setHeader("Accept", "application/json");
        httpMessage.setHeader("Authorization", String.format("Bearer %s", Constants.QUICKSTART_AUTHORIZATION));
        httpMessage.setHeader("AppId", Constants.QUICKSTART_APP_ID);
        httpMessage.setHeader("DeviceId", Constants.QUICKSTART_DEVICE_ID);
    }

    public static Gson getSharedGson() {
        if (sGson == null) {
            sGson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();
        }

        return sGson;
    }

    private ApiUtils() {
    }
}
