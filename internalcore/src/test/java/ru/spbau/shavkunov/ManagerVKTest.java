package ru.spbau.shavkunov;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.junit.Test;
import ru.spbau.shavkunov.primitives.Post;
import ru.spbau.shavkunov.primitives.Statistics;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerVKTest {
    //@Test
    @SuppressWarnings("unchecked")
    public void rightResponseTest() throws Exception {
        ManagerVK manager = new ManagerVK("alfabang", "10");
        //System.out.println(new MapPrinter<>(manager.identify()));
    }

    //@Test
    public void isGroupRequestTest() throws Exception {
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

    //@Test
    public void getStatsTest() throws Exception {
        String url = "https://api.vk.com/method/wall.get?owner_id=-23242408&count=20&access_token=d2872404d2872404d28724049dd2da8487dd287d28724048b042815868fa71cc8fc8992&v=5.67";

        ManagerVK vk = new ManagerVK("alfabank", "20");
        Statistics stats = vk.getStatistics();
    }

    @Test
    public void testPostDescription() throws Exception {
        ManagerVK vk = new ManagerVK("alfabank", "20");
        Statistics stats = vk.getStatistics();

        List<Post> posts = new ArrayList();
        posts.add(stats.getBestLikesPost());
        posts.add(stats.getBestRepostsPost());
        posts.add(stats.getBestViewsPost());

        posts.add(stats.getWorseLikesPost());
        posts.add(stats.getWorseRepostsPost());
        posts.add(stats.getWorseViewsPost());

        stats.getBestLikesPost().getDescription();
        //posts.stream().forEach(post -> System.out.println(post.getDescription()));
    }

    @Test
    public void testPersonRequestTest() throws Exception {
        ManagerVK vk = new ManagerVK("hamelinny", "20");
        Statistics stats = vk.getStatistics();
    }

    //@Test
    // TODO: more exceptions: user hid wall, user banned
    public void testAnotherPerson() throws Exception {
        ManagerVK vk = new ManagerVK("mv.shavkunov", "20");
        Statistics stats = vk.getStatistics();
    }

    @Test
    public void testRound() {
        double value = 1881234.66666666666666;

        DecimalFormat df = new DecimalFormat("#.###");
        String answer = df.format(value).replace(',', '.');
        System.out.println(Double.parseDouble(answer));
    }
}