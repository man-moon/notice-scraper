package com.ajouin.noticescraper.error.exception

open class NoticeTypeNotFoundException (
    errorCode: ErrorCode = ErrorCode.NOTICE_TYPE_NOT_FOUND
) : EntityNotFoundException(errorCode)