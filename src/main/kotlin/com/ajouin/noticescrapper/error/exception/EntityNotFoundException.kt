package com.ajouin.noticescrapper.error.exception

open class EntityNotFoundException(
    errorCode: ErrorCode = ErrorCode.ENTITY_NOT_FOUND
) : BusinessException(errorCode)