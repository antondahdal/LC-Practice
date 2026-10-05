# Week 08 — review sheet

Week 8 covers two families: graph (grid walks, dependencies) and DP classics (build the answer from smaller answers).

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 200 | Number of Islands | Graph (DFS flood fill on a grid) | O(rows × cols) / O(rows × cols) | coach-written, untimed |
| 70 | Climbing Stairs | DP (Fibonacci) | O(n) / O(1) with two variables | on-time |
| 207 | Course Schedule | Graph (topological sort, Kahn's BFS) | O(V + E) / O(V + E) | coach-written, untimed |

How to tell them apart:

| You see | Tool |
|---|---|
| A grid of cells, "connected", "islands", "regions" | Graph walk (DFS or BFS), mark visited |
| "A before B", "prerequisites", "can you finish / order" | Graph, topological sort (loop = impossible) |
| "How many ways", "best total", and step n depends on smaller steps | DP |

---

## LC 200 Number of Islands — coach-written, untimed

https://leetcode.com/problems/number-of-islands/

### The problem

You get a grid of `'1'` (land) and `'0'` (water).
Land connects up, down, left, or right.
A corner touch is not a connection.
Return the number of islands.

```
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1
-> 3
```

### The idea

Pattern: graph walk (DFS flood fill).
Each cell is a node, and each side shared with land is an edge.
Scan every cell.
When you find land, that is a new island, so count it and sink the whole island to `'0'`.
Sinking is how you keep "visited" without an extra array, so the scan never counts the same land twice.
Time O(rows × cols): each cell is sunk at most once, and every later check on it returns at once.
Extra space O(rows × cols) in the worst case, for the recursion stack when the whole grid is land.

### How to solve it

1. Loop over every row and every column.
2. If the cell is `'1'`, add one to the count and sink from it.
3. Sink: stop if off the grid or on `'0'`.
4. Otherwise flip the cell to `'0'`, then sink up, down, left, and right.
5. Return the count.

Trace on the top-left island:

| Call | What happens |
|---|---|
| `sink(0,0)` | land, flip, go four ways |
| `(-1,0)` | off the grid, stop |
| `(1,0)` | land, flip, spread |
| `(0,1)` | land, flip, spread to `(1,1)` |
| `(1,1)` | land, flip, all neighbours water or sunk |

The scan then walks past those four cells and sees only `'0'`.

### Holes to patch

**What to keep so land is not counted twice.**
He named "graph" but did not know what to keep.
Without marking, the scan reaches `(0,1)` after `(0,0)` and counts the same island again: the first grid gives 7, not 3.
Rule: the moment you visit land, mark it (flip to `'0'`).

**Flip before you spread.**
If the flip comes after the four calls, `(0,0)` calls `(0,1)`, which calls `(0,0)` again, forever: `StackOverflowError`.

**Bounds check first.**
Check `row < 0 || row >= grid.length || col < 0 || col >= grid[0].length` before you read `grid[row][col]`.

**Why it is O(N) even with recursion.**
He asked how it is O(N).
The scan is N.
Each cell is flipped once, and each neighbour check on a sunk cell returns at once, about 4 per cell.
N + 4N is still O(N).

### Memorize this

1. Loop over every cell.
2. On `'1'`: count++, sink from it.
3. Sink: off grid or `'0'` → return.
4. Flip to `'0'`, sink four ways.
5. Return count.

```java
public int numIslands(char[][] grid) {
    int count = 0;
    for (int row = 0; row < grid.length; row++) {
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[row][col] == '1') {
                count++;
                sink(grid, row, col);
            }
        }
    }
    return count;
}

private void sink(char[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return;
    if (grid[row][col] == '0') return;
    grid[row][col] = '0';
    sink(grid, row - 1, col);
    sink(grid, row + 1, col);
    sink(grid, row, col - 1);
    sink(grid, row, col + 1);
}
```

Say in the interview: I scan the grid, and every time I hit land I count one island and flood-fill it to water, so each cell is visited once, O(rows × cols).

### How it went

Named "graph" and the right time and space.
Did not know the why or how to walk the grid.
Did not want to swap; asked the coach to write it so he could study it.
Coach-written, 5 of 5, untimed.

---

## LC 70 Climbing Stairs — on-time

https://leetcode.com/problems/climbing-stairs/

### The problem

A staircase has `n` steps.
Each move is 1 step or 2 steps.
Return how many different ways reach the top.
`n = 3` gives 3: `1+1+1`, `1+2`, `2+1`.

### The idea

Pattern: DP, the Fibonacci shape.
Your last move onto step `n` came from `n - 1` (a 1-step) or from `n - 2` (a 2-step).
So `ways(n) = ways(n-1) + ways(n-2)`, with `ways(1) = 1` and `ways(2) = 2`.
You only ever need the last two answers.
Time O(n): one pass from 3 to n.
Extra space O(1) with two variables, or O(n) with a table.

### How to solve it

1. If `n` is 1 or 2, return `n`.
2. Keep `twoBelow = 1`, `oneBelow = 2`.
3. For step 3 to n: `current = oneBelow + twoBelow`, then shift.
4. Return `oneBelow`.

| step | twoBelow | oneBelow | current |
|---|---|---|---|
| start | 1 | 2 | — |
| 3 | 2 | 3 | 3 |
| 4 | 3 | 5 | 5 |
| 5 | 5 | 8 | 8 |

### Holes to patch

**The why is the last move, not the number pattern.**
He saw 1, 2, 3 and guessed Fibonacci from the numbers.
That works here, but on a cousin (moves of 1, 2, or 3) the numbers trick fails.
Rule: ask "where did the last move come from?" and add those answers.

**Plain recursion is not O(n).**
He said O(n) time because of the call stack.
`ways(n-1) + ways(n-2)` with no memory calls the same steps again and again: about O(2ⁿ), and `n = 45` takes seconds.
Rule: O(n) only with a memo or a loop.

**Table vs two variables.**
His `int[n+1]` is correct and O(n) space.
You only read the last two cells, so two variables give O(1); that is the usual follow-up.

### Memorize this

1. `n <= 2` → return `n`.
2. `twoBelow = 1`, `oneBelow = 2`.
3. Loop 3..n: `current = oneBelow + twoBelow`, shift both.
4. Return `oneBelow`.

```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    int twoBelow = 1, oneBelow = 2;
    for (int i = 3; i <= n; i++) {
        int current = oneBelow + twoBelow;
        twoBelow = oneBelow;
        oneBelow = current;
    }
    return oneBelow;
}
```

Say in the interview: the last move came from one or two steps below, so ways(n) is the sum of the two before it; I keep only those two, O(n) time and O(1) space.

### How it went

On-time, about 7 minutes, 5 of 5, he coded it.
His version used an `int[n+1]` table.
Time answer needed the memo / loop correction.

---

## LC 207 Course Schedule — coach-written, untimed

https://leetcode.com/problems/course-schedule/

### The problem

There are `numCourses` courses, `0` to `numCourses - 1`.
`[a, b]` means take `b` before `a`.
Return `true` if you can finish every course.

```
numCourses = 3, [[1,0], [2,1]]    0 → 1 → 2           -> true
numCourses = 2, [[1,0], [0,1]]    0 → 1 → 0 (loop)    -> false
```

### The idea

Pattern: graph, topological sort (Kahn's algorithm, BFS).
Courses are nodes; `[a, b]` is an edge `b → a`.
You can finish everything unless the edges form a loop.
Take every course that waits on nothing, then unlock the courses after it.
Courses in a loop never reach zero waiting, so they are never taken.
If the number taken equals `numCourses`, there is no loop.
Time O(V + E): each course enters the queue once and each pair is handled once.
Extra space O(V + E) for the lists, the counts, and the queue.

### How to solve it

1. Build `unlocks[b]` (courses that `b` opens) and `waitingOn[a]` (how many `a` still needs).
2. Queue every course with `waitingOn == 0`.
3. Poll a course, `taken++`.
4. For each course it unlocks: `waitingOn--`; at 0, add it to the queue.
5. Return `taken == numCourses`.

Trace on `numCourses = 3, [[1,0], [2,1]]`:

| Step | ready | taken | waitingOn [0,1,2] |
|---|---|---|---|
| start | [0] | 0 | [0,1,1] |
| take 0 | [1] | 1 | [0,0,1] |
| take 1 | [2] | 2 | [0,0,0] |
| take 2 | [] | 3 | — |

On `[[1,0], [0,1]]`, `waitingOn = [1,1]`, nothing starts, `taken = 0`, so false.

### Holes to patch

**`false` means a loop.**
He named "graph" but did not see what makes the answer false.
Rule: a dependency problem is impossible only when there is a cycle.

**Pairs are not a graph yet.**
He did not know how to start.
First turn `[a, b]` into an adjacency list `b → a` plus a waiting count for `a`.
Watch the direction: `[1, 0]` means 0 unlocks 1.

**Self-prerequisite.**
`[[0, 0]]` gives `waitingOn[0] = 1`, so 0 never starts: false, which is right.

### Memorize this

1. Adjacency list `unlocks` + array `waitingOn`.
2. Queue all courses with 0 waiting.
3. Poll, `taken++`.
4. Each unlocked course: `waitingOn--`; at 0, enqueue.
5. Return `taken == numCourses`.

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> unlocks = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) unlocks.add(new ArrayList<>());
    int[] waitingOn = new int[numCourses];
    for (int[] p : prerequisites) {
        unlocks.get(p[1]).add(p[0]);
        waitingOn[p[0]]++;
    }

    Queue<Integer> ready = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) if (waitingOn[i] == 0) ready.add(i);

    int taken = 0;
    while (!ready.isEmpty()) {
        int current = ready.poll();
        taken++;
        for (int next : unlocks.get(current)) {
            if (--waitingOn[next] == 0) ready.add(next);
        }
    }
    return taken == numCourses;
}
```

Say in the interview: courses are nodes and prerequisites are edges; I take every course with no remaining prerequisites, unlock the ones after it, and if I take them all there is no cycle, O(V + E).

### How it went

Named "graph" but did not know the why or how to code it.
Asked the coach to write it like Islands so he could study it.
Coach-written, 8 of 8, untimed.
