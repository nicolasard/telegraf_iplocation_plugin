package ar.nic;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;

import java.io.IOException;

/**
 * Service to get the location of an IP address. This Service
 * make uses of the IP2Location library to get the location of an IP address.
 */
public class IpLocationService {

    private final String binaryFileLocation;

    private final boolean loadInMemory;

    private final IP2Location loc = new IP2Location();

    public IpLocationService() {
        this.binaryFileLocation = System.getenv().getOrDefault("IP2LOCATION_BINARY_FILE", "/opt/IP2LOCATION-LITE-DB9.IPV6.BIN");
        this.loadInMemory = Boolean.parseBoolean(System.getenv().getOrDefault("IP2LOCATION_LOAD_IN_MEMORY", "false"));
    }

    public IPResult getIpInformation(final String ip){
        try {
            loc.Open(this.binaryFileLocation, loadInMemory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return loc.IPQuery(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
