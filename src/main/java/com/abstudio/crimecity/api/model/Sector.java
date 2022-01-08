package com.abstudio.crimecity.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Sector {
    String fr_label;
    String en_label;
    int x;
    int y;
    int h;
    int w;
}
