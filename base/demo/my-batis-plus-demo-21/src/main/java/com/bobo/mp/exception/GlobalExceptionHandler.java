package com.bobo.mp.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 09:33
 * To change this template use File | Settings | File and Code Templates.
 */
//@RestControllerAdvice
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ExceptionHandler(value ={IllegalArgumentException.class,NullPointerException.class} )
    //public JSONObject handelException(HttpServletRequest request,
    //                                  IllegalArgumentException exception){
    //    JSONObject result=new JSONObject();
    //    result.put("code","fail");
    //    JSONObject errorMsg=new JSONObject();
    //    result.put("msg",errorMsg);
    //    return result;
    //}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map <String, String> handleIllegalArgumentExceptionExceptions(
            BaseException ex) {
        Map <String, String> errors = new HashMap <>();
        errors.put("111", "IllegalArgumentException");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity <JSONObject> handleValidationExceptions(
            BaseException ex) {
        //Map <String, String> errors = new HashMap <>();
        //errors.put("111", "222");
        //return errors;
        JSONObject result = new JSONObject();
        result.put("code", "fail");
        result.put("msg", "6666666666666");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(result);
    }

    //@ExceptionHandler(value = {IllegalArgumentException.class, NullPointerException.class})
    //public ResponseEntity <JSONObject> handleException(ServerWebExchange exchange, IllegalArgumentException exception) {
    //    JSONObject result = new JSONObject();
    //    result.put("code", "fail");
    //    result.put("msg", exception.getMessage() + "6666666666666");
    //    System.out.println("IllegalArgumentException");
    //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    //}

    //@ExceptionHandler(IllegalArgumentException.class)
    //public void handleException(){
    //    System.out.println("Exception handled!");
    //}
}
