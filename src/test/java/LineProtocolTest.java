import ar.nic.LineProtocol;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineProtocolTest {

    /**
     * Test that we are able to parse a telegraf line protocol. And build the original line,
     * back from the parsed object.
     */
    @Test
    public void parseTest() {
        final String log = "weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final LineProtocol lineProtocol = LineProtocol.parse(log);
        // Test measurement
        assertEquals("weblogs",lineProtocol.getMeasurement());
        // Test tags
        final Map<String, String> tags = lineProtocol.getTags();
        assertEquals("18.231.0.100", tags.get("remote_addr"));
        assertEquals("/opt/sanclementetulugar-web.log", tags.get("path"));
        assertEquals("servus-apps", tags.get("host"));
        assertEquals("sanclementetulugar.com", tags.get("server_name"));
        assertEquals("200", tags.get("status"));
        // Test fields
        final Map<String, String> fields = lineProtocol.getFields();
        assertEquals("\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\"", fields.get("user_agent"));
        assertEquals("0", fields.get("response_time"));
        //Test we are able to build back the line
        assertEquals(log, lineProtocol.build());
    }

    @Test
    public void testAddTag(){
        final String log = "weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final LineProtocol lineProtocol = LineProtocol.parse(log);
        lineProtocol.addTag("name","value");
        assertEquals("weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200,name=value user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863",lineProtocol.build());
    }

    @Test
    public void testAddField(){
        final String log = "weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final LineProtocol lineProtocol = LineProtocol.parse(log);
        lineProtocol.addField("name","value");
        assertEquals("weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0,name=value 1735396661095817863",lineProtocol.build());
    }

}
