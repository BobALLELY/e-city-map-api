package com.abstudio.crimecity.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class MapDTO {
    @JsonProperty("extension_uuid")
    String extensionUUID;
//    @JsonProperty("hash")
//    String hash;
    @JsonProperty("version")
    String version;
    // Fond de la carte
    @JsonProperty("map_image_url")
    String mapImageUrl;
    @JsonProperty("atlas")
    List<AtlasDTO> atlas;
    @JsonProperty("sectors")
    List<SectorDTO> sectors;
    @JsonProperty("items")
    List<ItemDTO> items;
}
