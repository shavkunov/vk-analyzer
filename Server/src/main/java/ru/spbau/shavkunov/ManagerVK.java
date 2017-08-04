package ru.spbau.shavkunov;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.Users.Group;
import ru.spbau.shavkunov.Users.Person;
import ru.spbau.shavkunov.Users.User;
import ru.spbau.shavkunov.exceptions.BadJsonResponseException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.network.Method;
import ru.spbau.shavkunov.network.Parameter;
import ru.spbau.shavkunov.primitives.Statistics;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static ru.spbau.shavkunov.Constants.SERVICE_TOKEN;
import static ru.spbau.shavkunov.network.Method.*;

public class ManagerVK {
    /**
     * ID of the user or community of vk.com
     */
    private @NotNull String ID;

    private @NotNull String amount;

    /**
     * @param ID -- ID of given user or community
     * @param amount -- amount of posts to analyze.
     * It should satisfy a condition: amount at least 10, but not no more then 80.
     * @throws InvalidAmountException throws if amount doesn't satisfy the condition.
     */
    public ManagerVK(@NotNull String ID, int amount) throws InvalidAmountException {
        if (amount < 10 || amount > 80) {
            throw new InvalidAmountException();
        }

        this.amount = String.valueOf(amount);
        this.ID = ID;
    }

    // These two methods: getStatistics and identify don't check whether there are some problems with connection.
    // I mean, if the request is right and connection is timed out then they will throw exception.
    // TODO : 3 times same thing!!!
    public @NotNull Statistics getStatistics() throws BadJsonResponseException, IOException {
        User user = identify();
        URL postsRequest = getRequestUrl(WALL_GET,
                new Parameter("owner_id", String.valueOf(user.getID())),
                new Parameter("count", amount),
                new Parameter("access_token", SERVICE_TOKEN));
        HttpURLConnection connection = (HttpURLConnection) postsRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map response = mapper.fromJson(connection.getInputStream(), Map.class);
        if (response.containsKey("response")) {
            List<Map> objects = (List<Map>) response.get("response");
            objects.remove(0); // first one always total count

            return new Statistics(user, objects);
        }

        throw new BadJsonResponseException();
    }

    public @NotNull User identify() throws BadJsonResponseException, IOException {
        // TODO : twice written the same thing, need to change
        URL isGroupRequest = getRequestUrl(GROUP_GET_BY_ID,
                                            new Parameter("group_id", ID));
        HttpURLConnection connection = (HttpURLConnection) isGroupRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map groupResponse = mapper.fromJson(connection.getInputStream(), Map.class);
        if (groupResponse.containsKey("response")) {
            Map information = (Map) groupResponse.get("response");
            String groupID = (String) information.get("gid");
            String groupName = (String) information.get("name");
            String photoURL = (String) information.get("photo");
            return new Group(groupName, groupID, new URL(photoURL));
        }

        URL isUserRequest = getRequestUrl(USERS_GET,
                                            new Parameter("user_ids", ID),
                                            new Parameter("fields", "photo_400_orig"));
        connection = (HttpURLConnection) isUserRequest.openConnection();
        Map userResponse = mapper.fromJson(connection.getInputStream(), Map.class);
        if (userResponse.containsKey("response")) {
            Map information = (Map) userResponse.get("response");
            String userID = (String) information.get("uid");
            String firstName = (String) information.get("first_name");
            String lastName = (String) information.get("last_name");
            String photoURL = (String) information.get("photo_400_orig");
            return new Person(firstName, lastName, userID, new URL(photoURL));
        }

        throw new BadJsonResponseException();
    }

    private URL getRequestUrl(@NotNull Method method, @NotNull Parameter... parameters) throws MalformedURLException {
        // TODO : replace with string builder
        String base = "https://api.vk.com/method/";
        String request = base + method.toString() + "?";

        for (Parameter parameter : parameters) {
            request += parameter.getName() + "=" + parameter.getValue() + "&";
        }

        //remove last '&'
        request = request.substring(0, request.length() - 1);

        return new URL(request);
    }
}