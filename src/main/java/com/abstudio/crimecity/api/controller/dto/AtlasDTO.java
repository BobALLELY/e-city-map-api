package com.abstudio.crimecity.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AtlasDTO {
    @JsonProperty("key")
    String key;
    @JsonProperty("x")
    int x;
    @JsonProperty("y")
    int y;
    @JsonProperty("h")
    int h;
    @JsonProperty("w")
    int w;
}
