# Week 09 — review sheet

Week 9 coding is backtracking and a trie, two LCs a day.
Monday's Easy is the Week 6 leftover, a level-order walk.

| # | Problem | Pattern | Time / space | Result |
|---|---|---|---|---|
| 17 | Letter Combinations of a Phone Number | Backtracking | O(4^n · n) / O(n) besides the output | overtime, coach patched |
| 637 | Average of Levels in Binary Tree | BFS | O(n) / O(n) | on-time |
| 22 | Generate Parentheses | Backtracking | Catalan count, copy each string / O(n) besides the output | coach-written, untimed |
| 108 | Convert Sorted Array to BST | Middle of a sorted slice becomes the root | O(n) / O(log n) | coach-written, untimed |
| 46 | Permutations | Backtracking | O(n · n!) / O(n) besides the output | coach-written, untimed |
| 392 | Is Subsequence | Two pointers | O(n) / O(1) | on-time |

How to tell them apart:

| You see | Tool |
|---|---|
| "Every string these digits can spell", length is not fixed | Backtracking, one letter per digit, then undo |
| "Average / list of each row of a tree" | BFS, the queue size is the row |
| "Every valid way to place n pairs" | Backtracking, add `(` while opens are under n, add `)` only while closes are under opens |
| "Sorted array into a short BST" | Middle value of the current slice is the root, then the same split on each side |
| "Every ordering of distinct numbers" | Backtracking, one unused number per slot, then undo |
| "Is s a subsequence of t" | Two pointers, match moves s, every step moves t |

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

Pattern: backtracking.
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

Pattern: BFS, the queue size is one level.
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

---

## LC 22 Generate Parentheses — coach-written, untimed

https://leetcode.com/problems/generate-parentheses/

### The problem

`n` is how many pairs you must use.
Return every string that uses exactly `n` pairs and is still valid.
Order does not matter.

```
n = 2

(())
()()
```

`n = 3` has five strings: `((()))`, `(()())`, `(())()`, `()(())`, `()()()`.

### The idea

Pattern: backtracking.
You keep one partial string, plus how many opens and how many closes you have used.
You may add `(` while opens are still under `n`.
You may add `)` only while closes are still under opens.
When the string length is `2n`, it is one answer.
Then you drop the last character and try the other choice.
The number of answers is the Catalan count for `n`, and copying each string costs its length.
Extra space is O(n) for the partial string and the call stack.
The list of answers is the output.

### How to solve it

1. Start with an empty string, 0 opens, and 0 closes.
2. If the string length is `2n`, save it and return.
3. If opens are still under `n`, append `(`, recurse, then delete that character.
4. If closes are still under opens, append `)`, recurse, then delete that character.

Trace on `n = 2`:

| so far | opens | closes | next |
|---|---|---|---|
| (empty) | 0 | 0 | `(` |
| `(` | 1 | 0 | `(` |
| `((` | 2 | 0 | `)` only |
| `(()` | 2 | 1 | `)` |
| `(())` | 2 | 2 | save |
| `()` | 1 | 1 | `(` only |
| `()(` | 2 | 1 | `)` |
| `()()` | 2 | 2 | save |

### Holes to patch

**He did not know the name.**

The gate stopped there.
The rule he had to learn is the close count: `)` is legal only while closes are still under opens.
Adding `)` whenever closes are under `n` writes `)(` for `n = 1`.
That string uses one pair and is not valid.

**Same undo as Monday.**

Drop the last character after the recursive call.
Leave it on and the next branch starts from a string that is already too long.

### How it went

He did not know the pattern and chose to stay and learn it.
The clock never started.
The coach wrote the method.
3 of 3 tests passed.

### Memorize this

1. Keep one partial string, plus how many opens and closes you have used.
2. If the string length is `2n`, save it and return.
3. Add `(` while opens are still under `n`.
4. Add `)` only while closes are still under opens.
5. After each recursive call, delete the character you just added.

```java
public List<String> generateParenthesis(int n) {
    List<String> answers = new ArrayList<>();
    build(answers, new StringBuilder(), 0, 0, n);
    return answers;
}

private void build(List<String> answers, StringBuilder partial, int opens, int closes, int n) {
    if (partial.length() == 2 * n) {
        answers.add(partial.toString());
        return;
    }
    if (opens < n) {
        partial.append('(');
        build(answers, partial, opens + 1, closes, n);
        partial.deleteCharAt(partial.length() - 1);
    }
    if (closes < opens) {
        partial.append(')');
        build(answers, partial, opens, closes + 1, n);
        partial.deleteCharAt(partial.length() - 1);
    }
}
```

Say in the interview: I add an open while I still have pairs left, and a close only when it would match an open already in the string, then I undo that character.

---

## LC 108 Convert Sorted Array to Binary Search Tree — coach-written, untimed

https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/

### The problem

`nums` is sorted ascending, and every value is unique.
Build a height-balanced binary search tree from it.
More than one shape is allowed.

```
-10  -3  0  5  9

        0
       / \
     -3   9
     /   /
   -10  5
```

### The idea

Pattern: the middle value of the current slice becomes the root.
This is the Week 7 middle index on a sorted array.
The new piece is that the middle value is a tree node, and the same split then runs on the values before it and the values after it.
Taking the middle keeps both sides about the same height.
There was no earlier lesson under the name divide and conquer.
Time is O(n), because every value becomes a node.
Extra space is O(log n) for the calls, because the tree is balanced.

### How to solve it

1. Keep the left end and the right end of the slice you are building.
2. If `left > right`, the slice is empty, so that child is null.
3. The middle index of this slice becomes the root.
4. The left child is the same build on everything before the middle.
5. The right child is the same build on everything after the middle.

Trace on `[-10, -3, 0, 5, 9]`:

| slice | middle | left slice | right slice |
|---|---|---|---|
| -10 -3 0 5 9 | 0 | -10 -3 | 5 9 |
| -10 -3 | -3 | -10 | (empty) |
| -10 | -10 | (empty) | (empty) |
| 5 9 | 9 | 5 | (empty) |
| 5 | 5 | (empty) | (empty) |

When the slice has two values, `left + (right - left + 1) / 2` picks the right one.
The usual `(right - left) / 2` picks the left one.
Both trees are balanced.

### Holes to patch

**Heap, BFS, and intervals do not build this tree.**

A heap keeps one extreme value on top while you insert.
BFS walks a tree that is already there, one row at a time.
An interval is a start and an end that you merge when they overlap.
This array is single sorted values.
The middle value of the current slice becomes the root.

**The name was asked before the new piece was said.**

He had never had a lesson called divide and conquer.
The known move is the middle index from Week 7.
The new piece is that this middle value is the root, then the same split runs on each side.

### How it went

He named heap, then BFS, then said he did not know, then named intervals.
He chose to stay.
The clock never started.
The coach wrote the method.
4 of 4 tests passed.

### Memorize this

1. Keep the left end and the right end of the slice.
2. An empty slice returns null.
3. The middle value of this slice is the root.
4. Build the left child from everything before the middle.
5. Build the right child from everything after the middle.
6. A slice of two values may use either one as the root.

```java
public TreeNode sortedArrayToBST(int[] nums) {
    return build(nums, 0, nums.length - 1);
}

private TreeNode build(int[] nums, int left, int right) {
    if (left > right) {
        return null;
    }
    int mid = left + (right - left + 1) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    root.left = build(nums, left, mid - 1);
    root.right = build(nums, mid + 1, right);
    return root;
}
```

Say in the interview: the array is already sorted, so the middle value is the root and the same split on each side keeps the tree short.

---

## LC 46 Permutations — coach-written, untimed

https://leetcode.com/problems/permutations/

### The problem

`nums` holds distinct integers.
Return every ordering of those numbers.
Order of the lists does not matter.

```
[1, 2, 3]

1 2 3
1 3 2
2 1 3
2 3 1
3 1 2
3 2 1
```

### The idea

Pattern: backtracking.
You keep one partial list, plus which indexes are already used.
Each unused number is a legal pick for the next slot.
When the partial list is as long as `nums`, save a copy.
Then drop the last number and unmark it so the next number can take that slot.
Time is O(n · n!): there are n! orderings, and copying each one costs n.
Extra space is O(n) for the partial list, the used marks, and the call stack.
The list of answers is the output.

### How to solve it

1. Start with an empty partial list and a `used` mark per index.
2. If the partial list is as long as `nums`, save a copy and return.
3. For each index that is not used, mark it, add `nums[i]`, recurse.
4. Then remove the last number and unmark that index.
5. Every unused index is tried at this slot.

Trace on `[1, 2]`:

| partial | used | next pick | what happens |
|---|---|---|---|
| (empty) | F F | 1 | add 1 |
| 1 | T F | 2 | add 2, save `[1, 2]`, drop 2 |
| 1 | T F | (none left) | drop 1 |
| (empty) | F F | 2 | add 2 |
| 2 | F T | 1 | add 1, save `[2, 1]` |

### Holes to patch

**Time is not O(n²).**

n! lists come back, and each copy walks n numbers.
Say O(n · n!) time, O(n) extra space besides the output.

**Save a copy, not the same list.**

If you `add(partial)` without copying, later undo empties every answer already stored.
`answers.add(new ArrayList<>(partial))`.

### How it went

Untimed.
The method was written to restudy.
The undo is the same drop as Letter Combinations and Generate Parentheses.

### Memorize this

1. Keep one partial list and a used mark per index.
2. When the partial list is as long as `nums`, save a copy.
3. For each unused index, add that number, recurse, then undo.
4. Undo means drop the last number and unmark the index.
5. Values are distinct, so you do not skip duplicates.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> answers = new ArrayList<>();
    build(answers, new ArrayList<>(), new boolean[nums.length], nums);
    return answers;
}

private void build(List<List<Integer>> answers, List<Integer> partial, boolean[] used, int[] nums) {
    if (partial.size() == nums.length) {
        answers.add(new ArrayList<>(partial));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) {
            continue;
        }
        used[i] = true;
        partial.add(nums[i]);
        build(answers, partial, used, nums);
        partial.remove(partial.size() - 1);
        used[i] = false;
    }
}
```

Say in the interview: I fill one slot at a time from the numbers that are not used yet, then I undo so the next number can take that same slot.

---

## LC 392 Is Subsequence — on-time

https://leetcode.com/problems/is-subsequence/

### The problem

`s` and `t` are strings.
Return true if you can read `s` by walking `t` left to right and picking letters in order.
They do not have to sit next to each other.

```
s:  a   b     c
t:  a h b g d c     → true

s:  a   x     c
t:  a h b g d c     → false
```

### The idea

Pattern: two pointers.
One index on `s`, one index on `t`.
Walk `t` left to right.
A match moves the `s` index forward.
Every step moves the `t` index forward.
If the `s` index reaches `s.length()`, every letter was found in order.
Time is O(n) on the length of `t`.
Extra space is O(1).

### How to solve it

1. Empty `s` is true.
2. Start both indexes at 0.
3. While `t` still has letters, if they match, move `s` forward.
4. Always move `t` forward.
5. After a match, if `s` is fully consumed, return true.
6. If `t` runs out first, return false.

Trace on `s = "abc"`, `t = "ahbgdc"`:

| t letter | s index | match? | s index after |
|---|---|---|---|
| a | 0 (a) | yes | 1 |
| h | 1 (b) | no | 1 |
| b | 1 (b) | yes | 2 |
| g | 2 (c) | no | 2 |
| d | 2 (c) | no | 2 |
| c | 2 (c) | yes | 3, true |

### Holes to patch

**True on the last slot before that letter matches.**

`sInd == s.length() - 1` returns true while the last letter of `s` is still unmatched.
`s = "a"`, `t = "b"` comes back true.
`s = "a"`, `t = "bc"` does the same if that check sits at the top of the loop.
Return true only after a match makes `sInd == s.length()`.

```java
if (sInd == s.length()) return true;
```

**A length-1 special case is not the rule.**

`s.length() == 1 && t.length() == 1` only patches that one pair.
The same early return still lies on a longer `t`.
One check after the match.
Not a pile of length filters.

**Green tests can miss this.**

`abc` in `ahbgdc` goes green even with the hole.
Keep `s = "a"`, `t = "b"` in the suite.

### How it went

On-time.
The early return was the hole.
After the check moved to `sInd == s.length()` after a match, it was right.

### Memorize this

1. Empty `s` is true.
2. One index on `s`, one on `t`.
3. Match moves `s`. Every step moves `t`.
4. After a match, `sInd == s.length()` means true.
5. `t` running out first means false.

```java
public boolean isSubsequence(String s, String t) {
    int sInd = 0;
    int tInd = 0;
    if (s.length() == 0) {
        return true;
    }
    while (tInd < t.length()) {
        if (s.charAt(sInd) == t.charAt(tInd)) {
            sInd++;
        }
        tInd++;
        if (sInd == s.length()) {
            return true;
        }
    }
    return false;
}
```

Say in the interview: I walk `t` once and advance in `s` only on a match, so I am done when `s` is fully consumed.

---

## S3 — Proxies and AOP

`writeBook()` has `@Transactional`.

The controller call `bookingService.writeBook()` hits Spring's wrapper first.
The wrapper opens the transaction, then calls the real method.

`book()` calling `writeBook()` on the same object is `this`.
Spring never sees it.
No transaction.

`@Transactional` on a `private` method is the same miss.

Say in the interview: `@Transactional` runs only when the call goes through Spring's proxy, not through `this`.

---

## S4 — Bean scopes, lifecycle, circular dependencies

`@Service` is a singleton.
One `BookingService` for the whole app.
Not a new bean each time something injects it.

Order: `new`, inject constructor args, `@PostConstruct`, then it can take calls.

If `A` constructor-injects `B` and `B` constructor-injects `A`, neither can finish.
Startup fails.

Field injection can hide that loop until a request hits it.
Constructor injection blows up at boot.

Prototype is a new instance per lookup.
Do not use that for the service that writes tickets.

Say in the interview: `BookingService` is a singleton.
A constructor loop between two beans fails startup.
