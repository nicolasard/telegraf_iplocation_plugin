package ar.nic;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse the Line protocol.
 */
public class LineProtocol {

    private String measurement;

    private final Map<String, String> tags = new LinkedHashMap<>();

    private final Map<String, String> fields = new LinkedHashMap<>();

    private Long timestamp;

    private static final Pattern LINE_PATTERN = Pattern.compile("(.+?),(.+?) (.+?) (\\d+)?$");

    private LineProtocol() {}

    public static LineProtocol parse(final String line) {
        LineProtocol parser = new LineProtocol();
        Matcher matcher = LINE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Telegraf Line Protocol format");
        }
        parser.measurement = matcher.group(1);
        String tagsPart = matcher.group(2);
        String fieldsPart = matcher.group(3);
        String timestampPart = matcher.group(4);
        if (tagsPart != null) {
            for (String tag : tagsPart.split(",")) {
                String[] kv = tag.split("=");
                if (kv.length == 2) {
                    parser.tags.put(kv[0], kv[1]);
                }
            }
        }
        for (String field : fieldsPart.split(",")) {
            String[] kv = field.split("=");
            if (kv.length == 2) {
                parser.fields.put(kv[0], kv[1]);
            }
        }

        if (timestampPart != null) {
            parser.timestamp = Long.parseLong(timestampPart);
        }

        return parser;
    }

    public String build(){
        StringBuilder sb = new StringBuilder(measurement);
        if (!tags.isEmpty()) {
            sb.append(",");
            sb.append(tags.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((t1, t2) -> t1 + "," + t2)
                    .orElse(""));
        }
        sb.append(" ");
        sb.append(fields.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((f1, f2) -> f1 + "," + f2)
                .orElse(""));
        if (timestamp != null) {
            sb.append(" ").append(timestamp);
        }
        return sb.toString();
    }

    public String getMeasurement() {
        return measurement;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Measurement: " + measurement + ", Tags: " + tags + ", Fields: " + fields + ", Timestamp: " + timestamp;
    }

    public void addTag(final String name, final String value) {
        this.tags.put(name,value);
    }

    public void addField(final String name, final String value) {
        this.fields.put(name,value);
    }
}
