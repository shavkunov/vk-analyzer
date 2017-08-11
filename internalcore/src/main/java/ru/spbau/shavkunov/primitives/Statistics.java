package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.users.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
public class Statistics implements Serializable {
    // TODO : add global constants

    @Id
    private @Nullable String id;

    private double averageLikes;
    private double averageReposts;
    private double averageViews;

    @OneToOne
    private @NotNull Post bestLikesPost;

    @OneToOne
    private @NotNull Post bestRepostsPost;

    @OneToOne
    private @NotNull Post bestViewsPost;

    @OneToOne
    private @NotNull Post worseLikesPost;

    @OneToOne
    private @NotNull Post worseRepostsPost;

    @OneToOne
    private @NotNull Post worseViewsPost;

    public Statistics(@NotNull User owner, @NotNull List<Map> jsonObjects) {
        countAverageLikes(jsonObjects);
        countAverageReposts(jsonObjects);
        countAverageViews(jsonObjects);

        bestRepostsPost = getBestRepostsPost(owner, jsonObjects);
        bestLikesPost = getBestLikesPost(owner, jsonObjects);
        bestViewsPost = getBestViewsPost(owner, jsonObjects);

        worseLikesPost = getWorseLikesPost(owner, jsonObjects);
        worseRepostsPost = getWorseRepostsPost(owner, jsonObjects);
        worseViewsPost = getWorseViewsPost(owner, jsonObjects);
    }

    private @NotNull Post findQuantityPost(@NotNull String identifier, @NotNull User owner,
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

    private @NotNull Post getBestViewsPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("best", owner, "views", json);
    }

    private @NotNull Post getWorseLikesPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("worse", owner, "likes", json);
    }

    private @NotNull Post getWorseViewsPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("worse", owner, "views", json);
    }

    private @NotNull Post getBestLikesPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("best", owner, "likes", json);
    }

    private @NotNull Post getWorseRepostsPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("worse", owner, "reposts", json);
    }

    private @NotNull Post getBestRepostsPost(@NotNull User owner, @NotNull List<Map> json) {
        return findQuantityPost("best", owner, "reposts", json);
    }

    private @NotNull Integer getQuantityCount(@NotNull String quantity, @NotNull Map json) {
        return (Integer) ((Map) json.get(quantity)).get("count");
    }

    private void countAverageViews(@NotNull List<Map> jsonObjects) {
        averageViews = countAverageQuantity("views", jsonObjects);
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
                          .mapToInt(map -> (Integer) map.get("count"))
                          .mapToDouble(input -> (double) input)
                          .average()
                          .getAsDouble();
    }

    public double getAverageViews() {
        return averageViews;
    }

    public double getAverageLikes() {
        return averageLikes;
    }

    public double getAverageReposts() {
        return averageReposts;
    }

    public @NotNull Post getWorseLikesPost() {
        return worseLikesPost;
    }

    public @NotNull Post getBestLikesPost() {
        return bestLikesPost;
    }

    public @NotNull Post getWorseRepostsPost() {
        return worseRepostsPost;
    }

    public @NotNull Post getBestRepostsPost() {
        return bestRepostsPost;
    }

    public @NotNull Post getBestViewsPost() {
        return bestViewsPost;
    }

    public @NotNull Post getWorseViewsPost() {
        return worseViewsPost;
    }

    public String getId() {
        return id;
    }
}
