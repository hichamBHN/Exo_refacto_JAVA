package com.nimbleways.springboilerplate.dto.product;

import com.nimbleways.springboilerplate.enums.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseEntity {
    private Object data;
    private int status;
    private Version version;
    private List<String> errors;


    public static class V1 {
        public static ResponseEntity buildCreatedResponse(Object data) {
            return new ResponseEntity(data, HttpStatus.CREATED.value(), Version.V1, null);
        }

        public static ResponseEntity buildOkResponse(Object data) {
            return new ResponseEntity(data, HttpStatus.OK.value(), Version.V1, null);
        }

        public static ResponseEntity buildNotFoundResponse(List<String> errors) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND.value(), Version.V1, errors);
        }

        public static ResponseEntity buildBadRequestResponse() {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST.value(), Version.V1, null);
        }

        public static ResponseEntity buildInternalServerResponse(List<String> errors) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), Version.V1, errors);
        }
    }
}

