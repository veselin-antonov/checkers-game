package bg.reachup.edu.buisness.utils;

import java.util.Objects;

/**
 * Generic record that holds two objects of the specified types
 */
public record Pair<T1, T2>(T1 value1, T2 value2) {

    /**
     * Overrides java.lang.Object.toString method.
     * Serializes the record instance in the following format: (&lt;value1&gt;, &lt;value2&gt;)
     * @return
     */
    @Override
    public String toString() {
        return String.format("(%s, %s)", value1.toString(), value2.toString());
    }
}