package com.ruvalcaba.quizapplication.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object data){
        Map<String, Object> response = new HashMap<>();
        response.put("Message", message);
        response.put("HTTP Status", httpStatus);
        response.put("Data", data);

        return new ResponseEntity<>(response, httpStatus);
    }
}
