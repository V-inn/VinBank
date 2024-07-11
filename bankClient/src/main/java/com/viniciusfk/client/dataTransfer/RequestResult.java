package com.viniciusfk.client.dataTransfer;


/**
 * @author com.viniciusfk
 *
 * @param result
 * Transmits a clear state of the result.
 *
 * @param message
 * Transmits the information about the result, that can be showcased to the user.
 *
 * @param errorType
 * Transmits a technical error type for code management and error treatment. Is optional.
 *
 * @param errorCode
 * Transmits the numeric error code and must be according to the error type also used for error treatment. Is optional.
 */
public record RequestResult(boolean result, String message, String errorType, Byte errorCode) { }