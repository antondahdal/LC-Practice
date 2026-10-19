# Week 10 — review sheet

Week 10 is redo plus one fresh problem a day.
The family is hidden until you name it.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 215 | Kth Largest Element in an Array | Heap (size-k min-heap) | O(n log k) / O(k) | on-time redo, retired. Clock version was a max-heap of every value, O(n log n) / O(n). Write-up is in week-07. |
| 55 | Jump Game | Running max | O(n) / O(1) | overtime, coach-patched |

---

## LC 55 Jump Game — overtime, coach-patched

https://leetcode.com/problems/jump-game/

### The problem

You start at index 0.
`nums[i]` is the farthest jump you may take from that index.
You may jump a shorter distance.
Return true if some series of jumps lands on the last index.
`[2, 3, 1, 1, 4]` is true.
`[3, 2, 1, 0, 4]` is false.

### The idea

Pattern: running max.
You keep the farthest index any jump so far can land on.
From `i`, that landing is `i + nums[i]`.
If your index moves past that farthest index, you cannot stand there.
If the farthest index reaches the last index, you are done.
Time is O(n).
Extra space is O(1).

### How to solve it

1. `farthest = 0`.
2. Walk `i` from the left while `i` is still inside `farthest`.
3. `farthest` becomes the bigger of itself and `i + nums[i]`.
4. If `farthest` reaches the last index, return true.
5. If `i` moves past `farthest`, return false.

`[2, 3, 1, 1, 4]`:

| i | nums[i] | i + nums[i] | farthest |
|---|---|---|---|
| 0 | 2 | 2 | 2 |
| 1 | 3 | 4 | 4 |

`4` is the last index, so true.

`[3, 2, 1, 0, 4]`:

| i | nums[i] | i + nums[i] | farthest |
|---|---|---|---|
| 0 | 3 | 3 | 3 |
| 1 | 2 | 3 | 3 |
| 2 | 1 | 3 | 3 |
| 3 | 0 | 3 | 3 |

The next index is `4`, which is past `3`, so false.

### Holes to patch

**Comparing two values.**
`nums[i] == nums[nums.length - 1]` asks whether this cell holds the same number as the last cell.
`[2, 3, 1, 1, 4]` never holds a `4` until the last cell, so the flag stays false while you are still on `3`.
Reaching the end means the index you keep is the last index.

**Using the jump length as an array slot.**
The helper did `nums[max - i]` while `max` was `nums[i]`.
A jump length is not an index.
From `i`, the farthest index is `i + nums[i]`.

**The loop bound and the loop variable.**
`i <= nums.length` reads past the last index.
Setting `i` from the helper, while the `for` also does `i++`, repeats.
On `[2, 3, 1, 1, 4]` the helper was called with `1` and returned `1`.
The `for` then moved `i` back to `2`.
The same call ran again.

**The biggest value in range is the wrong choice.**
`[5, 3, 0, 0, 0, 2, 0, 0]` can reach the end by landing on index `5`, because `5 + 2` is `7`.
The biggest value you can see from index `0` is `3` at index `1`.
From index `1` the farthest landing is `4`, and you never reach index `5`.
Keep `i + nums[i]`, not the biggest `nums` value.

### How it went

Overtime.
5 of 6 at the bell.
`[2, 3, 1, 1, 4]` came back false.
The helper and the checks were patched after the clock.
6 of 6.
The patched helper still rewalks the window, so that version is O(n²).

### Memorize this

1. `farthest = 0`.
2. Walk `i` while `i` is still a reachable index.
3. `farthest` becomes the bigger of itself and `i + nums[i]`.
4. If `farthest` reaches the last index, return true.
5. If `i` moves past `farthest`, return false.

```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;
        farthest = Math.max(farthest, i + nums[i]);
        if (farthest >= nums.length - 1) return true;
    }
    return true;
}
```

Say in the interview: I keep the farthest index I can reach, and I stop when my index passes it.
