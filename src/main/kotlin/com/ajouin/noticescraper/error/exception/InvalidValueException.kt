package com.ajouin.noticescraper.error.exception

open class InvalidValueException(
    errorCode: ErrorCode = ErrorCode.INVALID_INPUT_VALUE
): BusinessException(errorCode)