package com.example.safetravelsclient.models.services;

public class ApiResponse<T> {

    private boolean isValidResponse;
    private String message;
    private String rawResponse;
    private T data;

    public ApiResponse()
    {
        this.isValidResponse = true;
        this.message = "";
        this.rawResponse = "";
        this.data = null;
    }

    public boolean isValidResponse()
    {
        return this.isValidResponse;
    }

    public String getMessage()
    {
        return this.message;
    }

    public String getRawResponse()
    {
        return this.rawResponse;
    }

    public T getData()
    {
        return this.data;
    }

    public ApiResponse<T> setValidResponse(boolean isValidResponse)
    {
        this.isValidResponse = isValidResponse;
        return this;
    }

    public ApiResponse<T> setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ApiResponse<T> setRawResponse(String rawResponse)
    {
        this.rawResponse = rawResponse;
        return this;
    }

    public ApiResponse<T> setData(T data)
    {
        this.data = data;
        return this;
    }

}
