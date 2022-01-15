package com.abstudio.crimecity.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Map {
    String extensionUUID;
    String hash;
    String version;
    String mapImageUrl;
    String atlasImageUrl;
    List<String> caseFiles;
    List<String> atlasFiles;
    List<Atlas> atlas;
    List<Sector> sectors;
    List<Item> items;
}
