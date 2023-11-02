package com.subaxpress.lib.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.io.InputStream;


public class CustomErrorDecoder implements ErrorDecoder {
    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is5xxServerError()) {
            InputStream body = responseBody.asInputStream();
            byte[] bytes = body.readAllBytes();
            String message = new String(bytes);
            return new RestApiServerException(requestUrl,  message);
        } else if (responseStatus.is4xxClientError()) {
            InputStream body = responseBody.asInputStream();
            byte[] bytes = body.readAllBytes();
            String message = new String(bytes);
            throw new RestApiClientException(requestUrl, message);
            //return new RestApiClientException(requestUrl, message);
        } else {
            return new Exception("Generic exception");
        }
    }
}
