package com.kindofhttp.cc.rerxutils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by cc on 17/5/23.
 */

public class ResponseAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        if (typeToken.getType() != BaseResponseEntity.class) {
            return null;
        }
        return (TypeAdapter<T>) newPasswordResponseAdapter(gson.getDelegateAdapter(this, TypeToken.get(BaseResponseEntity.class)));
    }

    private  TypeAdapter<BaseResponseEntity> newPasswordResponseAdapter (final TypeAdapter<BaseResponseEntity> delegateAdapter) {
        return new TypeAdapter<BaseResponseEntity>() {

            @Override
            public void write(JsonWriter out, BaseResponseEntity value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public BaseResponseEntity read(JsonReader in) throws IOException {
                in.beginArray();
                BaseResponseEntity response = delegateAdapter.read(in);
                while(in.hasNext()) {
                    // Skip remaining elements in the array
                    in.skipValue();
                }
                in.endArray();
                return response;
            }
        };
    }
}
