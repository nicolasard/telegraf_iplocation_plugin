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
                final String remoteAddress = getTags(input).get("remote_addr");
                writeLine(this.getValue(input,remoteAddress));
            }catch (RuntimeException e){
                System.out.println(e);
                //TODO: Log exception somewhere
                writeLine(input);
            }
        }
    }

    public String getValue(final String input, final String ipAdress){
        final IPResult ipResult = ipLocationService.getIpInformation(ipAdress);
        final String result1 = addTag(input,"country",ipResult.getCountryShort());
        final String result0 = addTag(result1, "city", ipResult.getCity().replace(" ", "-"));
        final String result2 = addValue(result0,"latitude",getFormatedValue(ipResult.getLatitude()));
        return addValue(result2,"longitude",getFormatedValue(ipResult.getLongitude()));
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

    /*
     * The following methods are using to parse the line protocol. Maybe would be nice to add some abstraction to this.
     */
    public String addTag(final String input,final String tagName, final String tagValue){
        String output = input.substring(0,input.indexOf(","));
        output = output+","+tagName+"="+tagValue+input.substring(input.indexOf(","),input.length());
        return output;
    }

    public HashMap<String, String> getTags(final String input){
        final HashMap<String,String> tags = new HashMap<>();
        final String[] tagsSeparatedList = input.split(",");
        for (String tagSeparated : tagsSeparatedList){
            final String[] nameValue = tagSeparated.split("=");
            if (nameValue.length == 2){
                tags.put(nameValue[0],nameValue[1]);
            }
        }
        return tags;
    }

    public String addValue(String log, String key, String value) {
        // Locate the last space in the log (before the timestamp)
        int lastSpaceIndex = log.lastIndexOf(' ');

        if (lastSpaceIndex == -1) {
            // If no space is found, return the log as-is (unlikely for valid logs)
            return log;
        }

        // Build the updated log with the new key-value pair added
        String beforeTimestamp = log.substring(0, lastSpaceIndex); // Everything before the timestamp
        String timestamp = log.substring(lastSpaceIndex);         // The timestamp itself

        // Append the new key-value pair before the timestamp
        return beforeTimestamp + "," + key + "=" + value + timestamp;
    }
}
