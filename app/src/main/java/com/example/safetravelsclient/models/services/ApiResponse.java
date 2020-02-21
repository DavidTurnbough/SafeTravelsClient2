package com.example.safetravelsclient.models.services;

public class ApiResponse<T> {

    //**********
    // Variable Declarations.
    //**********
    private boolean isValidResponse;
    private String message;
    private String rawResponse;
    private T data;

    //**********
    // Constructors.
    //**********
    public ApiResponse()
    {
        this.isValidResponse = true;
        this.message = "";
        this.rawResponse = "";
        this.data = null;
    }

    //**********
    // Getter Methods.
    //**********
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

    //**********
    // Setter Methods.
    //**********
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

    //**********
    // Other Methods.
    //**********
    public boolean isValidResponse()
    {
        return this.isValidResponse;
    }

}
