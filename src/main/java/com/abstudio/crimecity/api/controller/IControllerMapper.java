package com.abstudio.crimecity.api.controller;

import com.abstudio.crimecity.api.controller.dto.*;
import com.abstudio.crimecity.api.model.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface IControllerMapper {

    MapDTO toMapDTO(Map model);
    AtlasDTO toAtlasDTO(Atlas model);
    SectorDTO toSectorDTO(Sector model);

}
