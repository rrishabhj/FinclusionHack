package com.rishabh.github.finclusionhack;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.bom.android.container.SDKContainer;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class App extends Application {
    private static App sInstance;
    private static SDKContainer sdkContainer;
    private static Gson sharedGson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class,  new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(final Double src, final Type typeOfSrc, final JsonSerializationContext context) {
                //avoid numbers showing exponential when displayed as string
                BigDecimal value = BigDecimal.valueOf(src);
                return new JsonPrimitive(value);
            }
        });

        sharedGson = gsonBuilder.create();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }

    public static App getInstance() {
        return sInstance;
    }

    public static SDKContainer getClientContainer() {
        return sdkContainer;
    }

    public static Gson getSharedGson() {
        return sharedGson;
    }

    public void initializeClientContainer() {
        sdkContainer = new SDKContainer.Builder(this.getApplicationContext()).build();
        sdkContainer.initializeContainer();
    }
}
