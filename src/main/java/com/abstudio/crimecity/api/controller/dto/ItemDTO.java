package com.abstudio.crimecity.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
    @JsonProperty("key")
    String key;
    @JsonProperty("x")
    int x;
    @JsonProperty("y")
    int y;
}
