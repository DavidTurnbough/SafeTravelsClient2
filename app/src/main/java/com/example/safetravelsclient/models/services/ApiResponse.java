package com.example.safetravelsclient.models.services;

public class ApiResponse<T> {

    //**********
    // Variable Declarations.
    //**********
    private boolean isValidResponse;
    private String errorMessage;
    private String rawResponse;
    private T data;

    //**********
    // Constructors.
    //**********
    public ApiResponse()
    {
        this.isValidResponse = true;
        this.errorMessage = "";
        this.rawResponse = "";
        this.data = null;
    }

    //**********
    // Getter Methods.
    //**********
    public String getErrorMessage()
    {
        return this.errorMessage;
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
    public ApiResponse<T> setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
        return this;
    }

    public ApiResponse<T> setRawResponse(String rawResponse)
    {
        this.rawResponse = rawResponse;
        return this;
    }

    public ApiResponse<T> setValidResponse(boolean isValidResponse)
    {
        this.isValidResponse = isValidResponse;
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
