package com.ajouin.noticescraper.error

import com.ajouin.noticescraper.error.exception.BusinessException
import com.ajouin.noticescraper.error.exception.ErrorCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.net.BindException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorCode> {
        return ResponseEntity.badRequest().body(e.errorCode)
    }
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.HANDLE_ACCESS_DENIED
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INVALID_TYPE_VALUE
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INVALID_TYPE_VALUE
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorCode> {
        val errorResponse = ErrorCode.INVALID_INPUT_VALUE
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

}