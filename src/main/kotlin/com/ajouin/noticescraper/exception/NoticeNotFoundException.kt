package com.ajouin.noticescraper.exception

import com.ajouin.noticescraper.error.exception.EntityNotFoundException
import com.ajouin.noticescraper.error.exception.ErrorCode

class NoticeNotFoundException: EntityNotFoundException(ErrorCode.SCHOOL_NOTICE_NOT_FOUND)