package io.github.oliverschen.gateway.router;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ck
 */
public class RibbonRouter implements HttpEndpointRouter{

    @Override
    public String route(List<String> endpoints) {
        return endpoints.get(ThreadLocalRandom.current().nextInt(endpoints.size()));
    }
}
