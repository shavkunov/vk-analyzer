package ru.spbau.shavkunov;

import org.junit.Test;
import ru.spbau.shavkunov.primitives.Statistics;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mikhail Shavkunov
 */
public class StatisticsTest {
    @Test
    public void getDoubleWithPrecision() throws Exception {
        double value = 1.111111111111;
        double actual = Statistics.getDoubleWithPrecision(value, 5);
        assertEquals(1.11111, actual, 0);

        double value1 = 5/3.0;
        double actual1 = Statistics.getDoubleWithPrecision(value1, 2);
        assertEquals(1.67, actual1, 0);
    }
}