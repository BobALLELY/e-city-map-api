package com.abstudio.crimecity.api.service;

import com.abstudio.crimecity.api.exception.FeatureNotAvailableException;
import com.abstudio.crimecity.api.exception.NotFoundException;
import com.abstudio.crimecity.api.exception.TechnicalException;
import com.abstudio.crimecity.api.model.AtlasFile;
import com.abstudio.crimecity.api.model.Case;
import com.abstudio.crimecity.api.model.Map;
import com.abstudio.crimecity.api.service.izanami.IIzanamiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrimeCityService extends ACrimeCityService {
    private static final String LOG_PREFIX = "[Crime City Map Service]:";
    private static final String LOG_FORBIDDEN_PARAMS = "{} forbidden: {}";
    private static final String LOG_GLOBAL_WARN = "{} An error occurred while getting data: {}";

    @NonNull IIzanamiService izanamiService;

    @Override
    IIzanamiService getIIzanamiService() {
        return this.izanamiService;
    }

    public Map getMapByExtensionUUID(String extensionUUID) {
        try {
            if(extensionUUID == null || !isUUID(extensionUUID)) {
                throw new NotFoundException("Extension not found");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("/maps/" + extensionUUID + "/extension.json");
            // on ajoute les ressources de l'extension
            Map map = objectMapper.readValue(resource.getInputStream(), Map.class);
            map.setExtensionUUID(extensionUUID);

            // on ajoute toutes les ressources des enquêtes
            map.getCaseFiles().stream().forEach(caseFile -> {
                try {
                    ClassPathResource resourceCaseFile = new ClassPathResource("/maps/" + extensionUUID + "/cases/" + caseFile);
                    Case c = objectMapper.readValue(resourceCaseFile.getInputStream(), Case.class);
                    map.getItems().addAll(c.getItems());
                } catch (IOException e) {
                    CrimeCityService.log.error(LOG_FORBIDDEN_PARAMS, CrimeCityService.LOG_PREFIX, e.getLocalizedMessage());
                }
            });
            // on ajoute tous les atlas
            map.getAtlasFiles().stream().forEach(atlasFile -> {
                try {
                    ClassPathResource resourceCaseFile = new ClassPathResource("/maps/" + extensionUUID + "/" + atlasFile);
                    AtlasFile file = objectMapper.readValue(resourceCaseFile.getInputStream(), AtlasFile.class);
                    map.getAtlas().addAll(file.getSpriteSheets());
                } catch (IOException e) {
                    CrimeCityService.log.error(LOG_FORBIDDEN_PARAMS, CrimeCityService.LOG_PREFIX, e.getLocalizedMessage());
                }
            });

            CrimeCityService.log.info("Map found for extension uuid: '"+extensionUUID+"'");
            return map;
        } catch ( FeatureNotAvailableException e) {
            CrimeCityService.log.warn(LOG_FORBIDDEN_PARAMS, CrimeCityService.LOG_PREFIX, e.getLocalizedMessage());
            throw e;
        }  catch (NotFoundException e) {
            CrimeCityService.log.warn("{} {}", CrimeCityService.LOG_PREFIX, e.getLocalizedMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            CrimeCityService.log.warn("{} illegal parameter: {}", CrimeCityService.LOG_PREFIX, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            CrimeCityService.log.warn(LOG_GLOBAL_WARN, CrimeCityService.LOG_PREFIX, e.getLocalizedMessage());
            throw new TechnicalException(e.getLocalizedMessage(), e);
        }
    }

    private boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
