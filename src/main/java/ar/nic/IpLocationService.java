package ar.nic;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;

import java.io.IOException;

public class IpLocationService {

    private final String binaryFileLocation;

    private final boolean loadInMemmory;

    private final IP2Location loc = new IP2Location();

    public IpLocationService() {
        this.binaryFileLocation = "/opt/IP2LOCATION-LITE-DB9.IPV6.BIN";
        this.loadInMemmory = false;
    }

    public IPResult getIpInformation(final String ip){
        try {
            loc.Open(this.binaryFileLocation, loadInMemmory);
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
