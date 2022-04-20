package com.sliide.usermanagement.network.exceptions;

public class UnauthorizedAccessExceptionCustom extends CustomHttpException
{
    public UnauthorizedAccessExceptionCustom(String message)
    {
        super(message);
    }
}
