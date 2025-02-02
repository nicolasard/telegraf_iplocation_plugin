package ar.nic;

import org.tinylog.Logger;

/*+
 * This small java app is a processor for telegraf to add the location of the ips.
 */
public class Main {

    public static void main(String[] args) {
        MainApplicationService mainApplicationService = new MainApplicationService();
        Logger.info("Starting the infinite loop to process the input");
        mainApplicationService.process();
    }

}