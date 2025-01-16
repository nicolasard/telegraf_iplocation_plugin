import ar.nic.MainApplicationService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainApplicationServiceTest {

    @Test
    public void getTagsTest() {
        final MainApplicationService mainApplicationService = new MainApplicationService();
        final String log = "weblogs,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final HashMap<String, String> tags = mainApplicationService.getTags(log);
        assertEquals("18.231.0.100",tags.get("remote_addr"));
    }

    @Test
    public void addTagTest() {
        final MainApplicationService mainApplicationService = new MainApplicationService();
        final String log = "weblogs,isp=unknow,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final String logResult = "weblogs,name=value,isp=unknow,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final String result = mainApplicationService.addTag(log,"name","value");
        assertEquals(logResult,result);
    }

    @Test
    public void addValueTest() {
        final MainApplicationService mainApplicationService = new MainApplicationService();
        final String log = "weblogs,isp=unknow,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final String logResult = "weblogs,isp=unknow,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0,name=value 1735396661095817863";
        final String result = mainApplicationService.addValue(log,"name","value");
        assertEquals(logResult,result);
    }

    @Test
    public void getLineValue(){
        final MainApplicationService mainApplicationService = new MainApplicationService();
        final String log = "weblogs,isp=unknow,host=servus-apps,path=/opt/sanclementetulugar-web.log,remote_addr=18.231.0.100,server_name=sanclementetulugar.com,status=200 user_agent=\"synthetic-monitoring-agent/v0.30.0-0-g864dfd5bcefb (linux amd64; 864dfd5bcefb3f1e5b8c1f897e144af84813cd3a; 2024-12-17 09:18:16+00:00; +https://github.com/grafana/synthetic-monitoring-agent)\",response_time=0 1735396661095817863";
        final String result = mainApplicationService.getValue(log,"8.8.8.8");
        assertEquals("",result);
    }
}
