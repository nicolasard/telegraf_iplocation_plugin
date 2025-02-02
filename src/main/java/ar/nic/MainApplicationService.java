package ar.nic;

import com.ip2location.IPResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainApplicationService {

    final IpLocationService ipLocationService = new IpLocationService();

    public void process(){
        while(true){
            final String input = readLine();
            try{
                writeLine(this.process(input));
            }catch (RuntimeException e){
                System.out.println(e);
                //TODO: Log exception somewhere
                writeLine(input);
            }
        }
    }

    public String process(final String input){
        final LineProtocol lineProtocol = LineProtocol.parse(input);
        final String ipAdress = lineProtocol.getTags().get("remote_addr");
        final IPResult ipResult = ipLocationService.getIpInformation(ipAdress);
        lineProtocol.addTag("country",ipResult.getCountryShort());
        lineProtocol.addTag("city",ipResult.getCity().replace(" ", "-"));
        lineProtocol.addTag("latitude",getFormatedValue(ipResult.getLatitude()));
        lineProtocol.addTag("longitude",getFormatedValue(ipResult.getLongitude()));
        return lineProtocol.build();
    }

    private void writeLine(final String out){
        System.out.println(out);
    }

    private String readLine(){
        final InputStreamReader in = new InputStreamReader(System.in);
        final BufferedReader br = new BufferedReader(in);
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Convert a float to string.
     */
    public String getFormatedValue(float value){
        return String.valueOf(value);
    }

}
