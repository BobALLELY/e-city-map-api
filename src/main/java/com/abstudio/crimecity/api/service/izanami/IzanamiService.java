package com.abstudio.crimecity.api.service.izanami;

import com.abstudio.crimecity.api.exception.TechnicalException;
import com.abstudio.crimecity.api.model.feature.Feature;
import com.abstudio.crimecity.api.security.IUserAuthentication;
import com.abstudio.crimecity.api.service.izanami.config.FeatureFlippingConfig;
//import io.vavr.concurrent.Future;
//import izanami.javadsl.FeatureClient;
import lombok.extern.slf4j.Slf4j;
//import org.reactivecouchbase.json.JsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IzanamiService implements IIzanamiService {
//    private final FeatureClient featureClientWithCache;
    private final FeatureFlippingConfig featureFlippingConfig;
    private static final String LOG_PREFIX = "[IZANAMI]:";

//    @Autowired
//    public IzanamiService(FeatureClient featureClient, FeatureFlippingConfig featureFlippingConfig) {
//        this.featureFlippingConfig = featureFlippingConfig;
//        this.featureClientWithCache = featureClient;
//    }

    @Autowired
    public IzanamiService(FeatureFlippingConfig featureFlippingConfig) {
        this.featureFlippingConfig = featureFlippingConfig;
    }

    public Feature getCrimeCityAvailability(IUserAuthentication authenticatedUser) {
        try {
            String clientFeatureName = this.featureFlippingConfig.getClientFeature();
            IzanamiService.log.debug("{} get parameters: {}, {}", IzanamiService.LOG_PREFIX, authenticatedUser, clientFeatureName);
//
//            // Create context with token information
//            JsObject context = new JsObject();
//            context = context.with("user_id", authenticatedUser.getUserId());

            IzanamiService.log.info("{} Calling", IzanamiService.LOG_PREFIX);
//            IzanamiService.log.debug("{} Calling for feature {} with context: {}", IzanamiService.LOG_PREFIX, clientFeatureName, context.stringify());
//            Future<Boolean> futureCheck = this.featureClientWithCache.checkFeature(clientFeatureName, context);
//            Feature feature = Feature.builder().active(futureCheck.get()).build();
            Feature feature = Feature.builder().active(true).build();
            IzanamiService.log.info("{} Calling success ({})", IzanamiService.LOG_PREFIX, feature);
            return feature;
        }
        catch (Exception e) {
            // If error then log and set flag to false - we don't want to throw exception but bu default the feature is available
            IzanamiService.log.warn("{} An error occurred while getting data from Izanami API: {}", IzanamiService.LOG_PREFIX,
                    e.getLocalizedMessage());
            throw new TechnicalException(e.getLocalizedMessage(), e);
        }
    }
}
