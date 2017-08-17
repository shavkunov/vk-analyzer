package ru.spbau.shavkunov;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spbau.shavkunov.exceptions.BadJsonResponseException;
import ru.spbau.shavkunov.exceptions.EmptyLinkException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.exceptions.InvalidPageLinkException;
import ru.spbau.shavkunov.network.Method;
import ru.spbau.shavkunov.network.Parameter;
import ru.spbau.shavkunov.primitives.Statistics;
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

import static ru.spbau.shavkunov.Constants.*;
import static ru.spbau.shavkunov.network.Method.*;

/**
 * Manager collects appropriate data and give statistics about user wall.
 */
public class ManagerVK {
    private static final @NotNull Logger logger = LoggerFactory.getLogger(ManagerVK.class);

    /**
     * userID of the user or community of vk.com
     */
    private @NotNull String userID;
    private @NotNull int amount;

    /**
     * @param link -- possible link of given user or community
     * @param requestAmount -- amount of posts to analyze.
     * It should satisfy a condition: amount at least 10, but not no more then 80.
     * @throws InvalidAmountException throws if amount doesn't satisfy the condition.
     */
    public ManagerVK(@NotNull String link, @NotNull String requestAmount) throws InvalidAmountException, InvalidPageLinkException, EmptyLinkException {
        logger.debug("Manager init");
        if (link.equals("")) {
            logger.debug("The link is empty");
            throw new EmptyLinkException();
        }

        if (!validateVkLink(link)) {
            logger.debug("Link is invalid");
            throw new InvalidPageLinkException();
        }

        Integer amount;
        try {
            amount = Integer.valueOf(requestAmount);
        } catch (Exception e) {
            logger.debug("Amount is not a number: {}", requestAmount);
            throw new InvalidAmountException();
        }

        if (amount < 10 || amount > 80) {
            logger.debug("Amount don't fit the condition. Amount: {}", amount);
            throw new InvalidAmountException();
        }

        this.amount = amount;
        logger.debug("Validation succeeded");
    }

    // These two methods: getStatistics and identify don't check whether there are some problems with connection.
    // I mean, if the request is right and connection is timed out then they will throw exception.
    // TODO : 3 times same thing!!!
    public @NotNull Statistics getStatistics() throws BadJsonResponseException, IOException {
        User user = identify();
        URL postsRequest = getRequestUrl(WALL_GET,
                new Parameter("owner_id", String.valueOf(user.getID())),
                new Parameter("count", String.valueOf(amount)),
                new Parameter("access_token", SERVICE_TOKEN),
                new Parameter("v", "5.67"));
        HttpURLConnection connection = (HttpURLConnection) postsRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map response = mapper.fromJson(getJsonAsString(connection.getInputStream()), Map.class);
        if (response.containsKey("response")) {
            Map objects = (Map) response.get("response");
            // first one always total count
            return new Statistics(user, (List<Map>) objects.get("items"), amount);
        }

        throw new BadJsonResponseException();
    }

    public @NotNull User identify() throws BadJsonResponseException, IOException {
        // TODO : twice written the same thing, need to change
        URL isGroupRequest = getRequestUrl(GROUP_GET_BY_ID,
                                            new Parameter("group_id", userID),
                                            new Parameter("v", "5.67"));
        HttpURLConnection connection = (HttpURLConnection) isGroupRequest.openConnection();

        ObjectMapper mapper = JsonFactory.create();
        Map groupResponse = mapper.fromJson(connection.getInputStream(), Map.class);
        if (groupResponse.containsKey("response")) {
            Map information = (Map) ((List) groupResponse.get("response")).get(0);
            Integer groupID = (Integer) information.get("id");
            String groupName = (String) information.get("name");
            String photoURL = (String) information.get("photo_100");
            String userLink = VK_PREFIX + userID;
            return new Group(groupName, groupID.toString(), new URL(photoURL), userLink);
        }

        URL isUserRequest = getRequestUrl(USERS_GET,
                                            new Parameter("user_ids", userID),
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

    public static URL getRequestUrl(@NotNull Method method, @NotNull Parameter... parameters) throws MalformedURLException {
        // TODO : replace with string builder
        String base = "https://api.vk.com/method/";
        String request = base + method.toString() + "?";

        for (Parameter parameter : parameters) {
            request += parameter.getName() + "=" + parameter.getValue() + "&";
        }

        request = request.substring(0, request.length() - 1);

        return new URL(request);
    }

    // TODO : it's temporary. Something gone wrong with parsing directly from boon.
    // maybe it's useful to convert input stream to utt-8
    private @NotNull String getJsonAsString(@NotNull InputStream inputStream) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        return responseStrBuilder.toString();
    }

    private boolean isVkLink(@NotNull String link) {
        logger.debug("Is link {} a vk link?", link);
        if (link.startsWith(VK_PREFIX)) {
            logger.debug("Link started with {} and it's correct", VK_PREFIX);
            userID = link.replaceFirst("^" + VK_PREFIX, "");
            return true;
        }

        if (link.startsWith(VK_PREFIX_NO_PROTOCOL)) {
            logger.debug("Link started with {} and it's correct", VK_PREFIX_NO_PROTOCOL);
            userID = link.replaceFirst("^" + VK_PREFIX_NO_PROTOCOL, "");
            return true;
        }

        logger.debug("Link isn't a vk link");
        return false;
    }

    private boolean validateVkLink(@NotNull String link) {
        logger.debug("Validating link: {}", link);
        if (!isVkLink(link)) {
            userID = link;
            link = VK_PREFIX + link;
        }

        logger.debug("Trying to check existence of vk user: {}", link);
        try {
            URL request = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();

            if (connection.getResponseCode() == 404) {
                logger.debug("This user doesn't exist");
                return false;
            }
        } catch (Exception e) {
            logger.debug(Arrays.toString(e.getStackTrace()));
            return false;
        }

        logger.debug("Link confirmed");
        return true;
    }
}