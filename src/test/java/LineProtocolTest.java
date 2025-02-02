import ar.nic.LineProtocol;
import ar.nic.MainApplicationService;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineProtocolTest {

    @Test
    public void getTagsTest() {
        final String log = "weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final LineProtocol lineProtocol = LineProtocol.parse(log);
        // Test measurement
        assertEquals("weblogs",lineProtocol.getMeasurement());
        // Test tags
        assertEquals("18.231.0.100",lineProtocol.getTags().get("remote_addr"));
        assertEquals("/opt/sanclementetulugar-web.log",lineProtocol.getTags().get("path"));
        assertEquals("servus-apps",lineProtocol.getTags().get("host"));
        assertEquals("sanclementetulugar.com",lineProtocol.getTags().get("server_name"));
        // Test fields
        //assertEquals("200",lineProtocol.getFields().get("status"));
        //assertEquals("0",lineProtocol.getFields().get("response_time"));
    }
}
