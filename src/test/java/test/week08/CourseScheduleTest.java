package test.week08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.week08.CourseSchedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseScheduleTest {

    private CourseSchedule sol;

    @BeforeEach
    void setUp() {
        sol = new CourseSchedule();
    }

    @Test
    void simpleChain() {
        assertTrue(sol.canFinish(2, new int[][]{{1, 0}}));
    }

    @Test
    void twoWaitOnEachOther() {
        assertFalse(sol.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
    }

    @Test
    void noPrerequisites() {
        assertTrue(sol.canFinish(3, new int[][]{}));
    }

    @Test
    void longerChain() {
        assertTrue(sol.canFinish(4, new int[][]{{1, 0}, {2, 1}, {3, 2}}));
    }

    @Test
    void loopOfThree() {
        assertFalse(sol.canFinish(3, new int[][]{{1, 0}, {2, 1}, {0, 2}}));
    }

    @Test
    void diamond() {
        assertTrue(sol.canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}));
    }

    @Test
    void loopInSecondPart() {
        assertFalse(sol.canFinish(5, new int[][]{{1, 0}, {3, 2}, {4, 3}, {2, 4}}));
    }

    @Test
    void selfPrerequisite() {
        assertFalse(sol.canFinish(1, new int[][]{{0, 0}}));
    }
}
