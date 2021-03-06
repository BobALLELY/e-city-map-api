package com.abstudio.crimecity.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AtlasDTO {
    @JsonProperty("image_url")
    String imageUrl;
    @JsonProperty("coordinates")
    List<AtlasCoordinateDTO> coordinates;
}
