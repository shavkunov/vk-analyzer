package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.users.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static ru.spbau.shavkunov.primitives.PostIdentity.BEST;
import static ru.spbau.shavkunov.primitives.PostIdentity.WORST;
import static ru.spbau.shavkunov.primitives.PostQuantity.*;

/**
 * Statistics contains analyzed information about given amount last posts of given user.
 */
@Entity
public class Statistics implements Serializable {
    @Id
    private @Nullable String id;

    // TODO : set precision to 2
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

    @OneToOne
    private @NotNull User owner;
    private int amount;

    public Statistics(@NotNull User owner, @NotNull List<Map> jsonObjects, int amount) throws IOException {
        countAverageLikes(jsonObjects);
        countAverageReposts(jsonObjects);
        countAverageViews(jsonObjects);

        bestRepostsPost = getBestRepostsPost(owner, jsonObjects);
        bestLikesPost = getBestLikesPost(owner, jsonObjects);
        bestViewsPost = getBestViewsPost(owner, jsonObjects);

        worseLikesPost = getWorseLikesPost(owner, jsonObjects);
        worseRepostsPost = getWorseRepostsPost(owner, jsonObjects);
        worseViewsPost = getWorseViewsPost(owner, jsonObjects);
        this.owner = owner;
        this.amount = amount;
    }

    private @NotNull Post findQuantityPost(@NotNull PostIdentity identifier, @NotNull User owner,
                                  @NotNull PostQuantity quantity, @NotNull List<Map> jsonObjects) throws IOException {

        int compareUnit = 1;
        if (identifier == WORST) {
            compareUnit = -1;
        }
        int finalCompareUnit = compareUnit;
        Map jsonPost = jsonObjects.stream()
                                  .max((json1, json2) -> (finalCompareUnit * Integer.compare(getQuantityCount(quantity, json1),
                                                                             getQuantityCount(quantity, json2))))
                                  .get();

        PostCategory category = PostCategory.getCategory(identifier, quantity);
        return new Post(owner, jsonPost, category);
    }

    private @NotNull Post getBestViewsPost(@NotNull User owner, @NotNull List<Map> json) throws IOException {
        return findQuantityPost(BEST, owner, VIEWS, json);
    }

    private @NotNull Post getBestLikesPost(@NotNull User owner, @NotNull List<Map> json) throws IOException {
        return findQuantityPost(BEST, owner, LIKES, json);
    }

    private @NotNull Post getBestRepostsPost(@NotNull User owner, @NotNull List<Map> json) throws IOException {
        return findQuantityPost(BEST, owner, REPOSTS, json);
    }

    private @NotNull Post getWorseLikesPost(@NotNull User owner, @NotNull List<Map> json) throws IOException {
        return findQuantityPost(WORST, owner, LIKES, json);
    }

    private @NotNull Post getWorseViewsPost(@NotNull User owner, @NotNull List<Map> json) throws IOException {
        return findQuantityPost(WORST, owner, VIEWS, json);
    }

    private @NotNull Post getWorseRepostsPost(@NotNull User owner, @NotNull List<Map> json) throws IOException {
        return findQuantityPost(WORST, owner, REPOSTS, json);
    }

    private @NotNull Integer getQuantityCount(@NotNull PostQuantity quantity, @NotNull Map json) {
        return (Integer) ((Map) json.get(quantity.toString())).get("count");
    }

    private void countAverageViews(@NotNull List<Map> jsonObjects) {
        averageViews = countAverageQuantity(VIEWS, jsonObjects);
    }

    private void countAverageLikes(@NotNull List<Map> jsonObjects) {
        averageLikes = countAverageQuantity(LIKES, jsonObjects);
    }

    private void countAverageReposts(@NotNull List<Map> jsonObjects) {
        averageReposts = countAverageQuantity(REPOSTS, jsonObjects);
    }

    private double countAverageQuantity(@NotNull PostQuantity quantity, @NotNull List<Map> jsonObjects) {
        return jsonObjects.stream()
                          .map(map -> (Map) map.get(quantity.toString()))
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

    public User getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }
}
