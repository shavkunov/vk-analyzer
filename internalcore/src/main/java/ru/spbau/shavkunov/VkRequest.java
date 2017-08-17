package ru.spbau.shavkunov;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.exceptions.BadJsonResponseException;
import ru.spbau.shavkunov.network.Method;
import ru.spbau.shavkunov.network.Parameter;
import ru.spbau.shavkunov.users.Group;
import ru.spbau.shavkunov.users.Person;
import ru.spbau.shavkunov.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.spbau.shavkunov.Constants.SERVICE_TOKEN;
import static ru.spbau.shavkunov.Constants.VK_PREFIX;
import static ru.spbau.shavkunov.network.Method.*;

/**
 * Class used to send request to vk and get responses from vk website.
 */
public class VkRequest {

    /**
     * Get wall posts of specified user.
     * @param owner user, whose posts client wants to get.
     * @param posts amount of posts
     * @return response from vk as json
     */
    public static @NotNull Map getWall(@NotNull User owner, int posts) throws IOException {
        URL postsRequest = getRequestUrl(WALL_GET,
                new Parameter("owner_id", String.valueOf(owner.getID())),
                new Parameter("count", String.valueOf(posts)),
                new Parameter("access_token", SERVICE_TOKEN),
                new Parameter("v", "5.67"));
        HttpURLConnection connection = (HttpURLConnection) postsRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map response = mapper.fromJson(getJsonAsString(connection.getInputStream()), Map.class);

        return response;
    }

    /**
     * Client provided link to user. This method recognize what is it: a vk group or a user.
     * @param user link to vk user.
     * @return User object with appropriate methods.
     */
    public @NotNull static User identify(@NotNull String user) throws BadJsonResponseException, IOException {
        URL isGroupRequest = getRequestUrl(GROUP_GET_BY_ID,
                new Parameter("group_id", user),
                new Parameter("v", "5.67"));
        HttpURLConnection connection = (HttpURLConnection) isGroupRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map groupResponse = mapper.fromJson(connection.getInputStream(), Map.class);
        if (groupResponse.containsKey("response")) {
            Map information = (Map) ((List) groupResponse.get("response")).get(0);
            Integer groupID = (Integer) information.get("id");
            String groupName = (String) information.get("name");
            String photoURL = (String) information.get("photo_100");
            String userLink = VK_PREFIX + user;
            return new Group(groupName, groupID.toString(), new URL(photoURL), userLink);
        }

        URL isUserRequest = getRequestUrl(USERS_GET,
                new Parameter("user_ids", user),
                new Parameter("fields", "photo_400_orig"),
                new Parameter("v", "5.67"));
        connection = (HttpURLConnection) isUserRequest.openConnection();
        Map userResponse = mapper.fromJson(connection.getInputStream(), Map.class);
        if (userResponse.containsKey("response")) {
            Map information = (Map) ((List) userResponse.get("response")).get(0);
            Integer userID = (Integer) information.get("id");
            String firstName = (String) information.get("first_name");
            String lastName = (String) information.get("last_name");
            String photoURL = (String) information.get("photo_400_orig");
            String userLink = VK_PREFIX + "id" + userID;
            return new Person(firstName, lastName, userID.toString(), new URL(photoURL), userLink);
        }

        throw new BadJsonResponseException();
    }

    /**
     * Gets http request url to vk.
     * @param method method, which is wanted to execute of vk api
     * @param parameters parameters of vk request. They separate with & symbol and format is: key=value.
     * @return wanted url
     */
    public static URL getRequestUrl(@NotNull Method method, @NotNull Parameter... parameters) throws MalformedURLException {
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.vk.com/method/");
        builder.append(method.toString());
        builder.append("?");

        String separator = "";
        for (Parameter parameter : parameters) {
            builder.append(separator);
            builder.append(parameter.getName());
            builder.append("=");
            builder.append(parameter.getValue());
            separator = "&";
        }

        return new URL(builder.toString());
    }

    /**
     * Converts input json to string. TODO : remove this method and replace with more approriate.
     * @param inputStream response from server
     * @return json as string
     */
    private static @NotNull String getJsonAsString(@NotNull InputStream inputStream) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        return responseStrBuilder.toString();
    }


    public static  @Nullable String getImageURL(@NotNull User owner, @NotNull Integer originalID) {
        logger.debug("Getting image url");
        try {
            String photoID = owner.getID() + "_" + originalID;
            logger.debug("Photo ID : {}", photoID);
            URL isUserRequest = getRequestUrl(PHOTO_GET_BY_ID,
                    new Parameter("photos", photoID));

            logger.debug("is user request: {}", isUserRequest);
            HttpURLConnection connection = (HttpURLConnection) isUserRequest.openConnection();
            ObjectMapper mapper = JsonFactory.create();
            Map photoResponse = mapper.fromJson(connection.getInputStream(), Map.class);
            Map response = (Map) ((List) photoResponse.get("response")).get(0);
            String imageUrl = (String) response.get("src_big");
            logger.debug("image url: {}", imageUrl);
            return imageUrl;
        } catch (Exception e) {
            logger.debug(Arrays.toString(e.getStackTrace()));
        }

        logger.debug("Failed to get image url");
        return null;
    }
}
