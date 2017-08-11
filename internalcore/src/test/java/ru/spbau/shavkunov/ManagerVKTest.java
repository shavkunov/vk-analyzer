package ru.spbau.shavkunov;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.junit.Test;
import ru.spbau.shavkunov.exceptions.BadJsonResponseException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.primitives.Statistics;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ManagerVKTest {
    @Test
    @SuppressWarnings("unchecked")
    public void rightResponseTest() throws InvalidAmountException, IOException, URISyntaxException {
        ManagerVK manager = new ManagerVK("alfabang", 10);
        //System.out.println(new MapPrinter<>(manager.identify()));
    }

    @Test
    public void isGroupRequestTest() throws IOException {
        URL isGroupRequest = new URL("https://api.vk.com/method/groups.getById?group_id=alfabank&v=5.57");
        HttpURLConnection connection = (HttpURLConnection) isGroupRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map groupResponse = mapper.fromJson(connection.getInputStream(), Map.class);
        //System.out.println(groupResponse.get("response"));
        List information = (List) groupResponse.get("response");
        Map informationAsMap = (Map) information.get(0);
        System.out.print(new MapPrinter<>(informationAsMap));

        String url = "https://pp.userapi.com/c837125/v837125523/364c9/77xYhOE6t-4.jpg";
        URL urlExample = new URL(url);
    }

    @Test
    public void getStatsTest() throws IOException, InvalidAmountException, BadJsonResponseException {
        String url = "https://api.vk.com/method/wall.get?owner_id=-23242408&" +
                     "count=20&" +
                     "access_token=d2872404d2872404d28724049dd2da8487dd287d28724048b042815868fa71cc8fc8992&" +
                     "v=5.57";

        ManagerVK vk = new ManagerVK("alfabank", 20);
        Statistics stats = vk.getStatistics();
    }
}