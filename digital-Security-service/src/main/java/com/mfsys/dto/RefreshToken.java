package com.mfsys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @JsonProperty("refreshToken")
    private String refreshToken;
    @JsonProperty("accessToken")
    private String accessToken;
}
