package com.abstudio.crimecity.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Atlas {
    String imageUrl;
    List<AtlasCoordinate> coordinates;
}
