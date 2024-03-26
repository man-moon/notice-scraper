package com.ajouin.noticescraper.error.exception

open class BusinessException(val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR): RuntimeException()
