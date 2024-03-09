package com.ajouin.noticescrapper.error.exception

open class HandleAccessException(
        errorCode: ErrorCode = ErrorCode.HANDLE_ACCESS_DENIED
) : BusinessException(errorCode)