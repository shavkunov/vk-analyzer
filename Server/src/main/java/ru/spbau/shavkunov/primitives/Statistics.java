package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import ru.spbau.shavkunov.Users.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Mikhail Shavkunov
 */
public class Statistics {
    private double averageLikes;
    private double averageReposts;
    // private double averageViews;?
    private @NotNull Post worseLikesPost;
    private @NotNull Post bestLikesPost;
    private @NotNull Post worseRepostsPost;
    private @NotNull Post bestRepostsPost;

    public Statistics(@NotNull User owner, @NotNull List<Map> jsonObjects) {
        countAverageLikes(jsonObjects);
        countAverageReposts(jsonObjects);
        worseLikesPost = getWorseLikesPost(owner, jsonObjects);
        bestLikesPost = getBestLikesPost(owner, jsonObjects);
        worseRepostsPost = getWorseRepostsPost(owner, jsonObjects);
        bestRepostsPost = getBestRepostsPost(owner, jsonObjects);
    }

    private Post findQuantityPost(@NotNull String identifier, @NotNull User owner,
                                  @NotNull String quantity, @NotNull List<Map> jsonObjects) {

        int compareUnit = 1;
        if (identifier.equals("worse")) {
            compareUnit = -1;
        }
        int finalCompareUnit = compareUnit;
        Map jsonPost = jsonObjects.stream()
                                  .max((json1, json2) -> (finalCompareUnit * Integer.compare(getQuantityCount(quantity, json1),
                                                                             getQuantityCount(quantity, json2))))
                                  .get();

        return new Post(owner, jsonPost);
    }

    private Post getWorseLikesPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("worse", owner, "likes", json);
    }

    private Post getBestLikesPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("best", owner, "likes", json);
    }

    private Post getWorseRepostsPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("worse", owner, "reposts", json);
    }

    private Post getBestRepostsPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("best", owner, "reposts", json);
    }

    private Post getWorsePost(@NotNull User owner, @NotNull String quantity, @NotNull List<Map> json) {
        return findQuantityPost("worse", owner, quantity, json);
    }

    private Post getBestPost(@NotNull User owner, @NotNull String quantity, @NotNull List<Map> json) {
        return findQuantityPost("best", owner, quantity, json);
    }

    private Integer getQuantityCount(@NotNull String quantity, @NotNull Map json) {
        return (Integer) ((Map) json.get(quantity)).get("count");
    }

    private void countAverageLikes(@NotNull List<Map> jsonObjects) {
        averageLikes = countAverageQuantity("likes", jsonObjects);
    }

    private void countAverageReposts(@NotNull List<Map> jsonObjects) {
        averageReposts = countAverageQuantity("reposts", jsonObjects);
    }

    private double countAverageQuantity(@NotNull String quantity, @NotNull List<Map> jsonObjects) {
        return jsonObjects.stream()
                          .map(map -> (Map) map.get(quantity))
                          .mapToDouble(map -> (Double) map.get("count"))
                          .average()
                          .getAsDouble();
    }

    public double getAverageLikes() {
        return averageLikes;
    }

    public double getAverageReposts() {
        return averageReposts;
    }

    public Post getWorseLikesPost() {
        return worseLikesPost;
    }

    public Post getBestLikesPost() {
        return bestLikesPost;
    }

    public Post getWorseRepostsPost() {
        return worseRepostsPost;
    }

    public Post getBestRepostsPost() {
        return bestRepostsPost;
    }
}
