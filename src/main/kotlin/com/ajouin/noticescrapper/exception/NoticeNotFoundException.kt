package com.ajouin.noticescrapper.exception

import com.ajouin.noticescrapper.error.exception.EntityNotFoundException
import com.ajouin.noticescrapper.error.exception.ErrorCode

class NoticeNotFoundException: EntityNotFoundException(ErrorCode.SCHOOL_NOTICE_NOT_FOUND)