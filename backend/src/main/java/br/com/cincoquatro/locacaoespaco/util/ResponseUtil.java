package br.com.cincoquatro.locacaoespaco.util;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static ResponseEntity<?> mountResult_200(String message) {
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        return ResponseEntity.ok().body(result);
    }

}
