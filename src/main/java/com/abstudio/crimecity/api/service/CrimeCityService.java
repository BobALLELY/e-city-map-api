package com.abstudio.crimecity.api.service;

import com.abstudio.crimecity.api.exception.FeatureNotAvailableException;
import com.abstudio.crimecity.api.exception.NotFoundException;
import com.abstudio.crimecity.api.exception.TechnicalException;
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
            //read json file and convert to customer object
            Map map = objectMapper.readValue(resource.getFile(), Map.class);
            List<File> files = this.getAllCasesConfigurationFiles(extensionUUID);
            files.stream().forEach(f -> {
                try {
                    Case c = objectMapper.readValue(f, Case.class);
                    map.getAtlas().addAll(c.getAtlas());
                    map.getItems().addAll(c.getItems());
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

    /**
     * Retourne tous les fichiers de configuration des enquêtes d'une extension
     * @param extensionUUID
     * @return
     */
    private List<File> getAllCasesConfigurationFiles(String extensionUUID) throws IOException {
        ClassPathResource casesDirectory = new ClassPathResource("/maps/" + extensionUUID + "/cases");
        return Arrays.stream(casesDirectory.getFile().listFiles()).collect(Collectors.toList());
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
