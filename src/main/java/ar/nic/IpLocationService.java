package ar.nic;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;

import java.io.IOException;

public class IpLocationService {

    private final String binaryFileLocation;

    private final boolean loadInMemory;

    private final IP2Location loc = new IP2Location();

    public IpLocationService() {
        this.binaryFileLocation = System.getenv().getOrDefault("IP2LOCATION_BINARY_FILE", "/opt/IP2LOCATION-LITE-DB9.IPV6.BIN");        this.loadInMemory = false;
        this.loadInMemory = Boolean.parseBoolean(System.getenv().getOrDefault("IP2LOCATION_LOAD_IN_MEMORY", "false"));this.loadInMemory = Boolean.parseBoolean(System.getenv().getOrDefault("IP2LOCATION_LOAD_IN_MEMORY", "false"));private final boolean loadInMemory;
    }

    public IPResult getIpInformation(final String ip){
        try {
            loc.Open(this.binaryFileLocation, loadInMemory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IPResult rec = null;
        try {
            rec = loc.IPQuery(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rec;
    }
}
