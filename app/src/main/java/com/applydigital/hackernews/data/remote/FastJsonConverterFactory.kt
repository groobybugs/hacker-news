package com.applydigital.hackernews.data.remote

import com.alibaba.fastjson2.JSON
import java.lang.reflect.Type
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit


class FastJsonConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return Converter { value ->
            val bodyString = value.string()
            if (isJson(bodyString)) {
                JSON.parseObject(bodyString, type) as Any
            } else {
                bodyString as Any
            }
        }
    }

    private fun isJson(bodyString: String): Boolean {
        return bodyString.trim().startsWith("{") || bodyString.trim().startsWith("[")
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody> {
        return Converter<Any?, RequestBody> { value -> JSON.toJSONString(value).toRequestBody("application/json; charset=UTF-8".toMediaType()) }
    }

    companion object {
        fun create(): FastJsonConverterFactory {
            return FastJsonConverterFactory()
        }
    }
}
