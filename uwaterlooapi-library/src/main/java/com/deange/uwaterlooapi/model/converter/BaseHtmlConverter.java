package com.deange.uwaterlooapi.model.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* package */ abstract class BaseHtmlConverter<T>
        implements
        Converter<ResponseBody, T> {

    protected final HttpUrl mBaseUrl;

    public BaseHtmlConverter(final HttpUrl baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Override
    public final T convert(final ResponseBody value) throws IOException {
        return parse(Jsoup.parse(value.byteStream(), "UTF-8", mBaseUrl.toString()));
    }

    public abstract T parse(final Document document);

}
