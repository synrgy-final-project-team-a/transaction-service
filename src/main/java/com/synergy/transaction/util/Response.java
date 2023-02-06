package com.synergy.transaction.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {

    public ResponseEntity<Map<String, Object>> resSuccess(Object data, String message, Integer statusCode) {
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("message", message);
        res.put("status_code", statusCode);

        HttpStatus httpStatus = HttpStatus.OK;

        if (statusCode == 201) {
            httpStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(res, httpStatus);
    }

    public Map<String, Object> clientError(Object message) {
        Map<String, Object> res = new HashMap<>();
        res.put("message", message);
        res.put("status_code", 400);
        return res;
    }

    public ResponseEntity<Map<String, Object>> internalServerError(Object message) {
        Map<String, Object> res = new HashMap<>();
        res.put("message", message);
        res.put("status", 500);
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Map<String, Object> notFoundError(Object message) {
        Map<String, Object> res = new HashMap<>();
        res.put("message", message);
        res.put("status", 404);
        return res;
    }
}
