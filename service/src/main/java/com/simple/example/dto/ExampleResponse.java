package com.simple.example.dto;

import com.simple.common.api.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExampleResponse extends BaseResponse {
    private String id;
    private String name;
    private String email;
    private boolean confirmedAndActive;
    private Instant memberSince;
    private boolean support;
    private String phoneNumber;
    private String photoUrl;
}
