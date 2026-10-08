# Week 08 — review sheet

Week 8 covers two families: graph (grid walks, dependencies) and DP classics (build the answer from smaller answers).
It also has one design problem, LRU Cache.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 200 | Number of Islands | Graph (DFS flood fill on a grid) | O(rows × cols) / O(rows × cols) | coach-written, untimed |
| 70 | Climbing Stairs | DP (Fibonacci) | O(n) / O(n) table (O(1) with two variables) | on-time |
| 207 | Course Schedule | Graph (topological sort, Kahn's BFS) | O(V + E) / O(V + E) | coach-written, untimed |
| 198 | House Robber | DP (skip or take) | O(n) / O(n) table (O(1) with two variables) | coach-written, untimed |
| 146 | LRU Cache | Design: HashMap + doubly linked list | O(1) per call / O(capacity) | coach-written, untimed |
| 322 | Coin Change | DP (fewest coins for each amount) | O(amount × coins) / O(amount) | coach-written |

How to tell them apart:

| You see | Tool |
|---|---|
| A grid of cells, "connected", "islands", "regions" | Graph walk (DFS or BFS), mark visited |
| "A before B", "prerequisites", "can you finish / order" | Graph, topological sort (loop = impossible) |
| "How many ways", "best total", and step n depends on smaller steps | DP |
| "Fewest coins", unlimited use of each coin, make an amount | DP, one cell per amount |
| "Cache", "evict the oldest / least recently used", O(1) per call | HashMap for lookup + doubly linked list for order |

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
Keep every answer in a table `stairs[i]`.
Time O(n): one pass from 3 to n.
Extra space O(n) for the table.

### How to solve it

1. If `n` is 1 or 2, return `n`.
2. Make `stairs = new int[n+1]`, with `stairs[1] = 1` and `stairs[2] = 2`.
3. For `i` from 3 to n: `stairs[i] = stairs[i-1] + stairs[i-2]`.
4. Return `stairs[n]`.

| i | stairs[i-2] | stairs[i-1] | stairs[i] |
|---|---|---|---|
| 3 | 1 | 2 | 3 |
| 4 | 2 | 3 | 5 |
| 5 | 3 | 5 | 8 |

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

1. `n` is 1 or 2 → return `n`.
2. `stairs = new int[n+1]`, `stairs[1] = 1`, `stairs[2] = 2`.
3. Loop 3..n: `stairs[i] = stairs[i-1] + stairs[i-2]`.
4. Return `stairs[n]`.

```java
public int climbStairs(int n) {
    if(n==1||n==2) return n;
    int[] stairs=new int[n+1];
    stairs[0]=0;
    stairs[1]=1;
    stairs[2]=2;
    for(int i=3;i<=n;i++){
        stairs[i]=stairs[i-1]+stairs[i-2];
    }
    return stairs[n];
}
```

Say in the interview: the last move came from one or two steps below, so ways(n) is the sum of the two before it; I fill a table from 3 to n, O(n) time and O(n) space, and I can drop it to two variables for O(1).

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
    for (int i = 0; i < numCourses; i++) {
        unlocks.add(new ArrayList<>());
    }
    int[] waitingOn = new int[numCourses];

    for (int[] pair : prerequisites) {
        int course = pair[0];
        int before = pair[1];
        unlocks.get(before).add(course);
        waitingOn[course]++;
    }

    Queue<Integer> ready = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) {
        if (waitingOn[i] == 0) ready.add(i);
    }

    int taken = 0;
    while (!ready.isEmpty()) {
        int current = ready.poll();
        taken++;
        for (int next : unlocks.get(current)) {
            waitingOn[next]--;
            if (waitingOn[next] == 0) ready.add(next);
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

---

## LC 198 House Robber — coach-written, untimed

https://leetcode.com/problems/house-robber/

### The problem

Houses stand in a row, and `nums[i]` is the money in house `i`.
You cannot take from two houses next to each other.
Return the most money you can take.

```
index:   0   1   2   3   4
money:   2   7   9   3   1
take:    x       x       x    -> 2 + 9 + 1 = 12
```

### The idea

Pattern: DP, the same skeleton as Climbing Stairs.
At house `i` there are only two choices.
Skip it: the best stays `best[i-1]`.
Take it: you cannot have taken `i-1`, so it is `nums[i] + best[i-2]`.
So `best[i] = max(best[i-1], nums[i] + best[i-2])`.
`best[i]` is the most money from houses `0..i`.
Time O(n): one pass.
Extra space O(n) for the table; O(1) with two variables, since only the last two cells are read.

### How to solve it

1. One house: return `nums[0]`.
2. `best = new int[nums.length]`.
3. `best[0] = nums[0]`, `best[1] = max(nums[0], nums[1])`.
4. For `i` from 2: `best[i] = max(best[i-1], nums[i] + best[i-2])`.
5. Return the last cell.

| i | nums[i] | skip = best[i-1] | take = nums[i] + best[i-2] | best[i] |
|---|---|---|---|---|
| 0 | 2 | — | 2 | 2 |
| 1 | 7 | 2 | 7 | 7 |
| 2 | 9 | 7 | 9 + 2 = 11 | 11 |
| 3 | 3 | 11 | 3 + 7 = 10 | 11 |
| 4 | 1 | 11 | 1 + 11 = 12 | 12 |

### Holes to patch

**Did not see it was the Climbing Stairs skeleton.**
He did not know the pattern at the gate, and after seeing the code said "it is the same as Stairs".
The numbers do not show it; the question does.
Rule: ask "what are my choices at `i`, and which smaller answers do they use?"
Here: skip uses `i-1`, take uses `i-2`.

**Why the formula never takes two neighbours.**
He asked how `nums[i] + best[i-2]` keeps houses apart.
`best[i-2]` only covers houses `0..i-2`, so house `i-1` is never in the "take" total.
The "skip" total `best[i-1]` never has house `i` in it.
On `[2, 7, 9]`: take = 9 + 2 (houses 0 and 2), skip = 7 (house 1); 7 and 9 are never added.

**`best[1]` is the bigger of the first two, not `nums[1]`.**
On `[2, 1, 1, 2]`, `best[1] = nums[1] = 1` would end with 3; the right answer is 4 (houses 0 and 3).

**Greedy fails.**
At `i = 3` above, taking the 3 gives 10 and loses to skipping (11).
Picking the bigger neighbour each time does not work; you must compare both totals.

### Memorize this

1. One house → `nums[0]`.
2. Table `best` of length n.
3. `best[0] = nums[0]`, `best[1] = max(nums[0], nums[1])`.
4. Loop from 2: `best[i] = max(best[i-1], nums[i] + best[i-2])`.
5. Return `best[n-1]`.

```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    int[] best = new int[nums.length];
    best[0] = nums[0];
    best[1] = Math.max(nums[0], nums[1]);
    for (int i = 2; i < nums.length; i++) {
        best[i] = Math.max(best[i - 1], nums[i] + best[i - 2]);
    }
    return best[nums.length - 1];
}
```

Say in the interview: at each house I either skip it and keep the best so far, or take it and add the best from two houses back, so best[i] is the max of those two; one pass, O(n) time, and O(1) space if I keep only the last two values.

Cousin: #213 House Robber II (houses in a circle): run this twice, once without the last house and once without the first, and take the bigger.

### How it went

Did not know the pattern at the gate.
Chose to learn instead of swapping; coach explained with the trace and wrote it in his Climbing Stairs shape.
Coach-written, 6 of 6, untimed.

---

## LC 146 LRU Cache — coach-written, untimed

https://leetcode.com/problems/lru-cache/

### The problem

Build a cache with a fixed capacity.
`get(key)` returns the value, or `-1` if the key is missing.
`put(key, value)` adds or updates; if the cache goes over capacity, remove the least recently used key.
Both must be O(1) on average.
`get` counts as a use.

| Call | Result | Order after (old → new) |
|---|---|---|
| `put(1,1)` | | 1 |
| `put(2,2)` | | 1, 2 |
| `get(1)` | 1 | 2, 1 |
| `put(3,3)` | removes 2 | 1, 3 |
| `get(2)` | -1 | 1, 3 |

### The idea

Pattern: HashMap + doubly linked list.
The map holds `key → node`, so any key is found in O(1).
The list holds the order of use: oldest at the head, newest at the tail.
Each node has `prev` and `next`, so a node can be cut out of the middle in O(1) with no scan.
Two dummy nodes, `head` and `tail`, remove every null check at the ends.
Time O(1) per call: a map lookup plus a few pointer changes.
Extra space O(capacity) for the map and the nodes.

### How to solve it

1. `Node` holds `key`, `value`, `prev`, `next`.
2. Fields: `capacity`, `HashMap<Integer, Node>`, dummy `head` and `tail` linked to each other.
3. `remove(node)`: its prev and next point past it.
4. `addToTail(node)`: insert just before `tail`.
5. `get`: missing → `-1`; else remove, add to tail, return value.
6. `put` on an existing key: update value, remove, add to tail.
7. `put` on a new key: new node, into the map, add to tail; if the map is over capacity, `lru = head.next`, remove it from the list and the map.

```
put(1,1)  head ⇄ [1] ⇄ tail
put(2,2)  head ⇄ [1] ⇄ [2] ⇄ tail
get(1)    head ⇄ [2] ⇄ [1] ⇄ tail      cut 1, add at tail
put(3,3)  head ⇄ [1] ⇄ [3] ⇄ tail      over capacity: evict head.next (2)
```

### Holes to patch

**`get` does not remove.**
He asked if `get` removes from the cache.
It returns the value and moves the key to the newest end; the key stays.

**HashMap alone was right for lookup, not for order.**
He had the HashMap for `get`.
A map has no "oldest" in O(1), so a second structure must hold the order.

**Stack for the order.**
The key to evict is the oldest, at the bottom, and a Stack only gives the top.
`get` on a key in the middle means digging it out: O(n).
Rule: the order structure must remove from the middle and add at the end in O(1).

**PriorityQueue for the order.**
Sorted by last-used time, `add` and `poll` are O(log n), and `remove(key)` from the middle is O(n).
Not O(1).

**The node must keep its key.**
On eviction you have the node `head.next`, but you must also remove it from the map: `map.remove(lru.key)`.
Without the key in the node, the map keeps a stale entry and `get` returns an evicted value.

**Pointer order in `addToTail`.**
Set `node.prev = tail.prev` and `node.next = tail` first, then `tail.prev.next = node`, then `tail.prev = node`.
Changing `tail.prev` first loses the old last node.

**Interview shortcut.**
`LinkedHashMap(capacity, 0.75f, true)` with `removeEldestEntry` does this in a few lines.
Say it, then expect to build it by hand.

### Memorize this

1. `Node(key, value, prev, next)`.
2. Map `key → node`, dummy `head` ⇄ `tail`.
3. `remove(node)`: join its neighbours.
4. `addToTail(node)`: insert before `tail`.
5. `get`: missing → -1; else move to tail, return value.
6. `put`: exists → update and move to tail; new → add to map and tail, over capacity → evict `head.next` from list and map.

```java
public class LRUCache {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0);
    private final Node tail = new Node(0, 0);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        remove(node);
        addToTail(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            remove(node);
            addToTail(node);
            return;
        }
        node = new Node(key, value);
        map.put(key, node);
        addToTail(node);
        if (map.size() > capacity) {
            Node lru = head.next;
            remove(lru);
            map.remove(lru.key);
        }
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToTail(Node node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }
}
```

Say in the interview: a HashMap finds the node in O(1) and a doubly linked list keeps the use order, newest at the tail; every get or put moves the node to the tail, and when I go over capacity I evict head.next from both, so each call is O(1).

Cousin: #460 LFU Cache (evict the least frequently used; Hard).

### How it went

Had the HashMap for lookup; tried a Stack, then a PriorityQueue, for the order.
Chose to learn instead of swapping; coach explained with the picture and wrote it.
Coach-written, 5 of 5, untimed.

---

## LC 322 Coin Change — coach-written

https://leetcode.com/problems/coin-change/

### The problem

You get coin values and a target amount.
You may use each coin any number of times.
Return the fewest coins that add up to the amount.
If you cannot make it, return `-1`.

Coins `1`, `2`, and `5`, amount `11`, is `3` (`5 + 5 + 1`).
Coins `[2]`, amount `3`, is `-1`.
Amount `0` is `0`.

### The idea

Pattern: DP, bottom-up.
`retarr[a]` is the fewest coins that make amount `a`.
You fill from `0` up to the target.
To make `a`, take one coin and add it to a smaller amount you already solved: `retarr[a - coin] + 1`, and keep the smaller candidate.
Time is O(amount × coins.length): every amount tries every coin.
Extra space is O(amount) for the table.
There is no closed formula.

### How to solve it

1. Array of length `amount + 1`.
2. Index `0` is `0`.
3. Every other index starts at `amount + 1`, meaning impossible.
4. For each amount `a` from `1` to the target, for each coin that fits, if `a - coin` is possible, keep the min of the current cell and `retarr[a - coin] + 1`.
5. If the target cell is still above `amount`, return `-1`.
6. Otherwise return that cell.

| a | fewest | one way |
|---|---|---|
| 0 | 0 | nothing |
| 1 | 1 | 1 |
| 2 | 1 | 2 |
| 3 | 2 | 2+1 |
| 5 | 1 | 5 |
| 6 | 2 | 5+1 |
| 11 | 3 | 5+5+1 |

### Holes to patch

**No formula.**
He asked if a math puzzle had to be solved first.
The only math is the smaller cell plus one.

**Not the stairs step.**
Stairs only reads one back and two back.
Here the index is the amount, and the step back is a coin value.
Coin `5` at amount `11` reads `retarr[6]`.

**The array is sized by the amount.**
He first made it `coins.length + 1`.
On `[1, 2, 5]` that is four slots, and there is no cell for `11`.
Rule: length `amount + 1`, because the index is the amount.

**One coin can be used more than once.**
His early return said: if the only coin is not equal to the amount, return `-1`.
Coins `[1]`, amount `2`, is `1 + 1`, so `2`, not `-1`.

**Time is not O(n).**
Each amount tries every coin.
O(amount × coins.length) time, O(amount) space.

### Memorize this

1. Array length `amount + 1`. Cell `a` is the fewest coins that make `a`.
2. Cell `0` is `0`. Other cells start at `amount + 1`.
3. For each `a`, for each coin that fits, candidate is `cell[a - coin] + 1`. Keep the smaller.
4. Target still impossible → `-1`, else return the target cell.

```java
public int coinChange(int[] coins, int amount) {
    int[] retarr = new int[amount + 1];
    for (int a = 1; a <= amount; a++) {
        retarr[a] = amount + 1;
        for (int i = 0; i < coins.length; i++) {
            int coin = coins[i];
            if (coin <= a && retarr[a - coin] <= amount) {
                retarr[a] = Math.min(retarr[a], retarr[a - coin] + 1);
            }
        }
    }
    if (retarr[amount] > amount) return -1;
    return retarr[amount];
}
```

Say in the interview: I keep the fewest coins for every amount from 0 up to the target, and each amount is one coin plus a smaller amount I already solved, so it is one pass of amounts times coins.

Cousin: #518 Coin Change II, which counts the ways, not the fewest coins.

### How it went

Named DP bottom-up.
The why stayed vague, and he compared it to Climbing Stairs.
Stayed on the problem and asked for the code.
Coach wrote it in his `retarr` shape, 5 of 5.
