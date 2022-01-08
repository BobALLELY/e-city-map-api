package com.abstudio.crimecity.api.service;

import com.abstudio.crimecity.api.exception.FeatureNotAvailableException;
import com.abstudio.crimecity.api.exception.TechnicalException;
import com.abstudio.crimecity.api.helper.TechnicalValidatorHelper;
import com.abstudio.crimecity.api.model.feature.Feature;
import com.abstudio.crimecity.api.security.IUserAuthentication;
import com.abstudio.crimecity.api.service.izanami.IIzanamiService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public abstract class ACrimeCityService {
    private static final String LOG_PREFIX = "[Crime City Service]:";

    abstract IIzanamiService getIIzanamiService();

    /**
     * Call Izanami to check feature Crime City availability
     *
     * @param authenticatedUser authenticated user
     * @return
     */
    protected void checkGameAvailability(IUserAuthentication authenticatedUser) {
        try {
            Feature featureVL = this.getIIzanamiService().getCrimeCityAvailability(authenticatedUser);
            if (null == featureVL || !featureVL.isActive()) {
                throw new FeatureNotAvailableException("Crime City application not active");
            }
        } catch (TechnicalException e) {
            // If error then log and set flag to false - we don't want to throw exception but bu default the feature is available
            ACrimeCityService.log.warn("{} An error occurred while getting data from Izanami API: {}", ACrimeCityService.LOG_PREFIX,
                    e.getLocalizedMessage());
            throw e;
        }
    }

    protected void checkUser(IUserAuthentication authenticatedUser) {
        TechnicalValidatorHelper.validateUser(authenticatedUser);
    }
}
