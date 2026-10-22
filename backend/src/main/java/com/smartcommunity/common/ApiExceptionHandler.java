package com.smartcommunity.common;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import java.time.Instant;
import java.util.NoSuchElementException;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<ApiError> validation(MethodArgumentNotValidException e, HttpServletRequest r) {
  String m=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Invalid request");
  return response(HttpStatus.BAD_REQUEST,m,r);
 }
 @ExceptionHandler({IllegalArgumentException.class})
 ResponseEntity<ApiError> bad(IllegalArgumentException e,HttpServletRequest r){return response(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
 @ExceptionHandler(NoSuchElementException.class)
 ResponseEntity<ApiError> missing(NoSuchElementException e,HttpServletRequest r){return response(HttpStatus.NOT_FOUND,e.getMessage(),r);}
 @ExceptionHandler(AccessDeniedException.class)
 ResponseEntity<ApiError> denied(AccessDeniedException e,HttpServletRequest r){return response(HttpStatus.FORBIDDEN,"Access denied",r);}
 @ExceptionHandler(Exception.class)
 ResponseEntity<ApiError> general(Exception e,HttpServletRequest r){return response(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred",r);}
 private ResponseEntity<ApiError> response(HttpStatus s,String m,HttpServletRequest r){return ResponseEntity.status(s).body(new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI()));}
}
