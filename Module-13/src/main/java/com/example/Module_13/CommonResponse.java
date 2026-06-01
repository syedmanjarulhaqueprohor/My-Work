package com.example.Module_13;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private String timeStamp;
    private String error;
    private String message;
    private String detail;
}
