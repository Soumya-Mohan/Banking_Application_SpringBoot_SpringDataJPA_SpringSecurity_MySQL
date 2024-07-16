package com.UserService.Model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    private String message;
    private int code;
    private Object data;


}
