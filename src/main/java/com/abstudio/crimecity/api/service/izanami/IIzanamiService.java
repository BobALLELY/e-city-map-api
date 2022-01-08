package com.abstudio.crimecity.api.service.izanami;

import com.abstudio.crimecity.api.model.feature.Feature;
import com.abstudio.crimecity.api.security.IUserAuthentication;

public interface IIzanamiService {

    /**
     * Returns {@link Feature} from Izanami API
     *
     * @param userAuthentication authenticated user
     * @return
     */
    Feature getCrimeCityAvailability(IUserAuthentication userAuthentication);
}
