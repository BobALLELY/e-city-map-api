package com.abstudio.crimecity.api.controller;

import com.abstudio.crimecity.api.controller.dto.*;
import com.abstudio.crimecity.api.helper.Constants;
import com.abstudio.crimecity.api.model.Map;
import com.abstudio.crimecity.api.model.Sector;
import com.abstudio.crimecity.api.service.CrimeCityService;
import com.abstudio.crimecity.api.service.metric.RestMetricService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(path = "/${application.apiName}/${application.apiVersion}/maps", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MapController {

    @NonNull
    private final CrimeCityService crimeCityService;
    @NonNull
    private final IControllerMapper iControllerMapper;
    @NonNull
    private final RestMetricService metricService;

    /**
     * Returns all extensions
     *
     * @return
     */
    @Cacheable("maps")
    @Timed("ExtensionController.getAllExtensions")
    @GetMapping()
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MapDTO.class)
                )
            }
        ),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content),
        @ApiResponse(responseCode = "400", description = "Un ou plusieurs paramètres sont nuls ou mal formatés", content = @Content),
        @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas autorisé", content = @Content),
        @ApiResponse(responseCode = "403", description = "Space Crime n'est pas actif", content = @Content)
    })
    @Operation(summary = "Retourne toutes les informations de la carte pour une extension")
    public MapDTO getMapByExtension(
            @RequestParam(name = "extension_uuid", defaultValue = "26ac70df-9900-4b4c-bd6f-a9095d10b616") String extensionUUID,
            @RequestParam(name = "language", required = false, defaultValue = "fr") String language
    ) {
        String endpoint = "/maps";
        this.metricService.increaseIncomingRequest(endpoint);

        Map map = this.crimeCityService.getMapByExtensionUUID(extensionUUID);
        MapDTO dto = this.iControllerMapper.toMapDTO(map);

        // "http 200" metric
        this.metricService.increaseStatusRequest(HttpStatus.OK.value(), endpoint);

        return this.mapLanguage(map, dto, language);
    }

    /**
     * Permet de traduire les extensions
     * @param map
     * @param mapDTO
     * @param language => par défaut FR
     * @return
     */
    private MapDTO mapLanguage(Map map, MapDTO mapDTO, String language) {
        if(map == null || mapDTO == null) {
            return null;
        }
        mapDTO.setSectors(
            mapDTO.getSectors().stream().map(sectorDTO -> {
                Optional<Sector> found = map.getSectors().stream().filter(s -> s.getX() == sectorDTO.getX() && s.getY() == sectorDTO.getY()).findFirst();
                if(found.isPresent()) {
                    if(language != null && Constants.LANGUAGE.EN.getValue().equals(language.toLowerCase())) {
                        sectorDTO.setLabel(found.get().getEn_label());
                    }
                    else {
                        sectorDTO.setLabel(found.get().getFr_label());
                    }
                }
                return sectorDTO;
            }).collect(Collectors.toList())
        );
        return mapDTO;
    }
}
