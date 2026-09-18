package test.week03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week03.GroupAnagrams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupAnagramsTest {

    private GroupAnagrams sol;

    @BeforeEach
    void setUp() {
        sol = new GroupAnagrams();
    }

    @Test
    void classic() {
        List<List<String>> out = sol.groupAnagrams(new String[] {"eat", "tea", "tan", "ate", "nat", "bat"});
        assertEquals(3, out.size());
        Set<Set<String>> groups = new HashSet<>();
        for (List<String> g : out) {
            groups.add(new HashSet<>(g));
        }
        assertTrue(groups.contains(Set.of("eat", "tea", "ate")));
        assertTrue(groups.contains(Set.of("tan", "nat")));
        assertTrue(groups.contains(Set.of("bat")));
    }

    @Test
    void empty() {
        assertEquals(List.of(), sol.groupAnagrams(new String[] {}));
    }
}
