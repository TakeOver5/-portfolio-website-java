package com.tapcus.portfoliowebsitejava.handler;

import com.tapcus.portfoliowebsitejava.util.Result;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 400 - Bad Request 參數驗證失敗
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> handler(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();

        log.error("參數驗證失敗 ---------------- {}", objectError.getDefaultMessage());

        return Result.error("參數驗證失敗", objectError.getDefaultMessage());
    }

    // 400 - Bad Request 參數校驗異常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ResponseStatusException.class)
    public Result<String> handler(ResponseStatusException e) {

        log.error("參數校驗異常 ---------------- {}", e.getMessage());

        return Result.error("參數校驗異常",e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = JwtException.class)
    public Result<String> handler(JwtException e) {
        log.error("token 異常 ---------------- {}", e.getMessage());
        return Result.error(401, e.getMessage());
    }

    // 500 - Internal Server Error 通用例外
    @ExceptionHandler(Exception.class)
    public Result<String> handleException400(Exception e) {
        e.printStackTrace();;
        log.error("伺服器例外 ---------------- {}", e.getMessage());
        String errorMsg = e.getMessage() == null ? Arrays.toString(e.getStackTrace()) : e.getMessage();
        return Result.error("請聯絡管理員", errorMsg);
    }

    // 400 - Internal Server Error 運行時異常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeException(RuntimeException e) {
        e.printStackTrace();
        log.error("運行時例外 ---------------- {}", e.getMessage());
        String errorMsg = e.getMessage() == null ? Arrays.toString(e.getStackTrace()) : e.getMessage();
        return Result.error(errorMsg);
    }
}
