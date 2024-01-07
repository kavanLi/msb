import com.msb.core.config.Config;
import com.msb.gateway.config.center.api.ConfigCenter;

import java.util.ServiceLoader;

public class JDKSpiTest {
    public static void main(String[] args) {

        ServiceLoader<ConfigCenter> configCenters = ServiceLoader.load(ConfigCenter.class);
        for (ConfigCenter configCenter : configCenters) {
            System.out.println(configCenter);
        }
    }
}
