package com.webshop.tokyolife.exception;

import com.webshop.tokyolife.controller.BaseController;
import com.webshop.tokyolife.dto.ResponseDTO;
import com.webshop.tokyolife.exception.custom.*;
import com.webshop.tokyolife.model.CustomError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler extends BaseController {
    @Value("${error.status.not-found}")
    private String notFoundStatus;
    @Value("${error.message.not-found}")
    private String notFoundMessage;

    @Value("${error.status.bad-request}")
    private String badRequestStatus;
    @Value("${error.message.bad-request}")
    private String badRequestMessage;

    @Value("${error.status.access-denied}")
    private String accessDeniedStatus;
    @Value("${error.message.access-denied}")
    private String accessDeniedMessage;

    @Value("${error.status.internal-error}")
    private String internalErrorStatus;
    @Value("${error.message.internal-error}")
    private String internalErrorMessage;

    @Value("${error.status.unathorized}")
    private String unathorizedStatus;
    @Value("${error.message.unathorized}")
    private String unathorizedMessage;

    @Value("${error.status.unavailable}")
    private String unavailableStatus;
    @Value("${error.message.unavailable}")
    private String unavailableMessage;

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> notFoundException(CustomNotFoundException customNotFoundException){
        CustomError customError = customNotFoundException.getCustomError();
        String status = customError.getStatus() == null ? notFoundStatus : customError.getStatus();
        String message = customError.getMessage() == null ? notFoundMessage : customError.getMessage();
        return response(new ResponseDTO(404, status, message, null));    }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> badRequestException(CustomBadRequestException customBadRequestException){
        CustomError customError = customBadRequestException.getCustomError();
        String status = customError.getStatus() == null ? badRequestStatus : customError.getStatus();
        String message = customError.getMessage() == null ? badRequestMessage : customError.getMessage();
        return response(new ResponseDTO(400, status, message, null));

    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<?> accessDeniedException(CustomAccessDeniedException customAccessDeniedException){
        CustomError customError = customAccessDeniedException.getCustomError();
        String status = customError.getStatus() == null ? accessDeniedStatus : customError.getStatus();
        String message = customError.getMessage() == null ? accessDeniedMessage : customError.getMessage();
        return response(new ResponseDTO(403, status, message, null));
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> unauthorizedException(CustomUnauthorizedException customUnathorizedException){
        CustomError customError = customUnathorizedException.getCustomError();
        String status = customError.getStatus() == null ? unathorizedStatus : customError.getStatus();
        String message = customError.getMessage() == null ? unathorizedMessage : customError.getMessage();
        return response(new ResponseDTO(401, status, message, null));
    }

    @ExceptionHandler(CustomInternalServerError.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> internalServerError(CustomInternalServerError customInternalServerError){
        CustomError customError = customInternalServerError.getCustomError();
        String status = customError.getStatus() == null ? internalErrorStatus : customError.getStatus();
        String message = customError.getMessage() == null ? internalErrorMessage : customError.getMessage();
        return response(new ResponseDTO(500, status, message, null));
    }

    @ExceptionHandler(CustomUnavailableException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<?> unavailableException(CustomUnavailableException customUnavailableException){
        CustomError customError = customUnavailableException.getCustomError();
        String status = customError.getStatus() == null ? unavailableStatus : customError.getStatus();
        String message = customError.getMessage() == null ? unavailableMessage : customError.getMessage();
        return response(new ResponseDTO(421, status, message, null));
    }


}
