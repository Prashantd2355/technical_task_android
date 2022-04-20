package com.sliide.usermanagement.network.exceptions;

/**
 * To be thrown when server request returns non-successful status code (not between 200-300).
 */
public class CustomHttpException extends RuntimeException
{
    private int code;
    private int responseCode;

    public CustomHttpException(String message)
    {
        super(message);
    }

    public CustomHttpException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public CustomHttpException(int responseCode, String message, int code)
    {
        super(message);
        this.responseCode = responseCode;
        this.code = code;

    }

    public int getCode()
    {
        return code;
    }

    public int getResponseCode()
    {
        return responseCode;
    }
}
