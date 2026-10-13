# Week 09 — review sheet

Week 9 coding is backtracking and a trie, two LCs a day.
Monday's Easy is the Week 6 leftover, a level-order walk.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 17 | Letter Combinations of a Phone Number | Backtracking | O(4^n · n) / O(n) besides the output | overtime, coach patched |
| 637 | Average of Levels in Binary Tree | BFS | O(n) / O(n) | on-time |

How to tell them apart:

| You see | Tool |
|---|---|
| "Every string these digits can spell", length is not fixed | Backtracking, one letter per digit, then undo |
| "Average / list of each row of a tree" | BFS, the queue size is the row |

---

## LC 17 Letter Combinations of a Phone Number — overtime, coach patched

https://leetcode.com/problems/letter-combinations-of-a-phone-number/

### The problem

`digits` is a string of phone keys from 2 to 9.
Each key has letters.
Return every string those keys can spell.
Order does not matter.
An empty string returns an empty list.

```
2 → a b c
3 → d e f

ad ae af
bd be bf
cd ce cf
```

### The idea

Backtracking.
You keep one partial string, one letter chosen for each digit so far.
When the partial string is as long as `digits`, it is one answer.
Then you drop the last letter and try the next letter on that same key.
Time is O(4^n · n): each digit has at most 4 letters, and copying a string of length n costs n.
Extra space is O(n) for the partial string and the call stack.
The output itself holds every answer.

### How to solve it

1. If `digits` is empty, return an empty list.
2. Store the keypad in an array. Index 2 is `"abc"`, index 9 is `"wxyz"`.
3. Start at digit 0 with an empty partial string.
4. If the index equals `digits.length()`, save the partial string and return.
5. For each letter on the current key, append it, go to the next digit, then delete that letter.
6. Repeat until every branch has been saved.

Trace on `"23"`:

| index | partial | letter chosen | what happens |
|---|---|---|---|
| 0 | (empty) | a | go to digit 1 |
| 1 | a | d | length 2, save `ad`, drop `d` |
| 1 | a | e | save `ae`, drop `e` |
| 1 | a | f | save `af`, drop `f` |
| 0 | (empty) | b | same three letters from 3 |

### Holes to patch

**The second key was the next keypad slot.**

`KEYS[button + 1]` is slot 3 when the button is 2.
`"22"` must read `"abc"` again, not `"def"`.
Rule: the index is the next character in `digits`, not `button + 1`.

```java
int next = digits.charAt(i + 1) - '0';
```

**The inner loop stopped at 3.**

7 and 9 have four letters.
`j < 3` drops `s` and `z`.
Rule: the bound is that key's length.

**A pair of keys is not the whole answer.**

`"234"` must be length 3: one letter from 2, one from 3, and one from 4.
`ad` is not an answer.
His loop `addAll`s each adjacent pair into the same list, so three digits come back as two-letter strings.
Rule: the next digit appends onto the strings already stored, or the recursive call moves one digit forward and then undoes the letter.

**The set does not remember a pair.**

He checks `button + nextChar` and then stores only `button`.
Those are different numbers, so the check does not mean "already combined".
Rule: you do not need a set. Every digit is used, in order, including a repeated 2.

### How it went

Overtime.
The coach patched the next-digit index and the `j < 3` bound.
5 of 5 on tests of length 0, 1, and 2.
A third digit is still a list of pairs.

### Memorize this

1. Empty input returns an empty list.
2. Keypad array, digit is the index.
3. Walk the digits with one partial string.
4. When the partial string is long enough, save it.
5. Append one letter, recurse to the next digit, then delete that letter.
6. 7 and 9 have four letters. Do not assume 3.

```java
private static final String[] KEYS = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
};

public List<String> letterCombinations(String digits) {
    List<String> ret = new ArrayList<>();
    if (digits.isEmpty()) {
        return ret;
    }
    build(digits, 0, new StringBuilder(), ret);
    return ret;
}

private void build(String digits, int i, StringBuilder cur, List<String> ret) {
    if (i == digits.length()) {
        ret.add(cur.toString());
        return;
    }
    String letters = KEYS[digits.charAt(i) - '0'];
    for (int j = 0; j < letters.length(); j++) {
        cur.append(letters.charAt(j));
        build(digits, i + 1, cur, ret);
        cur.deleteCharAt(cur.length() - 1);
    }
}
```

Say in the interview: I try one letter for this digit, go on to the next digit, and undo the letter so the next letter can use the same prefix.

---

## LC 637 Average of Levels in Binary Tree — on-time

https://leetcode.com/problems/average-of-levels-in-binary-tree/

### The problem

You get the root of a binary tree.
Return the average of the node values on each level, top to bottom.

```
        3
       / \
      9   20
         /  \
       15    7
```

Level 0 is 3.
Level 1 is 14.5.
Level 2 is 11.

### The idea

BFS.
At the start of a level the queue holds exactly that level.
Take that many nodes, sum them, and divide by the count.
Children go to the back, so they are the next level.
Time is O(n) because each node is taken once.
Extra space is O(n) because the queue holds the widest level.

### How to solve it

1. If the root is null, return an empty list.
2. Put the root in a queue.
3. While the queue is not empty, `size` is this level.
4. Sum exactly `size` nodes. Enqueue a child only when it is not null.
5. Append `sum / size`.
6. Repeat. The children you just added are the next level.

| level | nodes taken | sum | count | average |
|---|---|---|---|---|
| 0 | 3 | 3 | 1 | 3 |
| 1 | 9, 20 | 29 | 2 | 14.5 |
| 2 | 15, 7 | 22 | 2 | 11 |

### Holes to patch

**A null root returns null.**

The caller then cannot ask for the size.
Rule: return an empty list.

**The sum must not be an `int`.**

Two nodes of `2147483647` overflow an `int` sum, and the average comes back negative.
His code adds into a `double`, which is why the big-value test passed.
Rule: add into a `double` or a `long`, then divide.

### How it went

On-time, about 8 minutes, 5 of 5, he coded it.
He named BFS because he needed the whole row, and O(n) time and O(n) extra space.

### Memorize this

1. Queue starts with the root.
2. The current queue size is one level.
3. Take that many. Sum them. Enqueue real children only.
4. Append sum divided by the count.
5. Add into a `double` or a `long`.

```java
public List<Double> averageOfLevels(TreeNode root) {
    List<Double> ret = new ArrayList<>();
    if (root == null) {
        return ret;
    }
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.remove();
            sum += node.val;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        ret.add(sum / size);
    }
    return ret;
}
```

Say in the interview: I take one level at a time by snapshotting the queue size, sum only those nodes, and let their children be the next level.
