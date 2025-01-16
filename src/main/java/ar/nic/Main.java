package ar.nic;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*+
 * This small java app is a processor for telegraf to add the location of the ips.
 */
public class Main {

    public static void main(String[] args) {
        MainApplicationService mainApplicationService = new MainApplicationService();
        mainApplicationService.process();
    }

}