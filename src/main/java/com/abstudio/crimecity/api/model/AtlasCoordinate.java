package com.abstudio.crimecity.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtlasCoordinate {
    String key;
    int x;
    int y;
    int h;
    int w;
}
