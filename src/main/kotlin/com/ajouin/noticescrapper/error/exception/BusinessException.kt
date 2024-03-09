package com.ajouin.noticescrapper.error.exception

open class BusinessException(val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR): RuntimeException()
