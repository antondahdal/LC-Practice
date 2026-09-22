# Week 6 Day 2 — 2026-09-22 — extra vs notes repo

Tuesday.
Trees.
Two LCs then LC-SD.
Third Easy skipped (he will restudy BFS/DFS himself).
Not three-LC cadence today.

Bank leftover was #98 Validate BST, #230 Kth Smallest.
Fill would have been another tree Easy (#100 / #101 / #112).
He said skip if it is BFS or DFS.

**Clock (Anton 2026-09-22):** two pings only.
5 min left, then time.
Do not keep saying the clock is running during the question.
At 0 still say stop.
He may keep going for learning.

## LC 98 Validate Binary Search Tree — overtime, he coded

Is this tree a BST.
Local left/right child check is not enough.
Original root only is not enough.
Original plus father is not enough.

**Gate:** recursion.
Keep a window: `low` and `high` as helper **arguments** (copies).
Left: same `low`, `high = node.val`.
Right: `low = node.val`, same `high`.
Parent frame is not updated.
Time O(n).
Extra space O(h) stack, worst O(n).

**Clock:** 25 min Medium.
He asked to drop mid-clock pings.
Coach stayed silent at 0 the first time.
Wrong read.
Then he asked remaining time (already overtime).
He coded after 0 for learning.
Notes: **overtime**.
Tests green after the `long` window.

**Memorize:** `check(node, low, high)`.
`null` → true.
`val <= low` or `val >= high` → false.
Left `(left, low, val)`.
Right `(right, val, high)`.
First call open window with `Long.MIN_VALUE` / `Long.MAX_VALUE`.

**Weak:** locals `minVal`/`maxVal` inside `isValidBST` reset every call.
Class fields would overwrite between left and right.
`Integer.MIN_VALUE` as `low` rejects a legal `MIN_VALUE` leaf (`val <= low`).
Boxed `Long` does not take `int` `root.val` (compile error).
Primitive `long`.
Equals are invalid (`<=` / `>=`).
He starts the recursive pair on the right first.
Order does not change the bounds.

**Cousin:** inorder, keep previous value, each new value must be strictly bigger.

## LC 230 Kth Smallest Element in a BST — overtime, he coded

`k`th value in size order (1-based).
Do not reorder the tree.

**Gate:** first depth / `k` times left from the top.
Wrong (example `3 / 1 4 / 2`, `k = 2` is `2`, not two lefts).
Then inorder (not preorder).
Keep a **count**.
He did not know preorder / inorder / postorder names.
Coach explained the three walks with the clock off.
Then he said start.

**Clock:** 25 min Medium.
5 min left ping, then time.
He kept going after 0.
Notes: **overtime**.
Tests green.

**Memorize:** inorder: left, this node, right.
Class field `counter` (one box, not an `int` argument).
After left, if `counter == k` return `left`.
Then `counter++`.
If `counter == k` return `node.val`.
After right, if `counter == k` return `right`.

**Weak:** `int counter = 0` inside the helper or as a copied argument.
`counter++` in the child does not update the parent.
Count is the opposite keep of `low`/`high` (those copies are the point; count must be shared).
Ignored recursive return, so a hit on the left died and the parent counted again.
`return node.left.val` instead of `node.val`.
`System.out.println` leftover.
List + `get(k-1)` is the cousin (extra O(n)).

**Skip third:** he will restudy BFS/DFS himself.
Do not assign #100 / #101 / #112 today.

## Part 1 Design — Chapter 8 timeout / retry / click id — recap done

W4 Tue already ran this chapter.
W5 Thu already ran Ch 7 (Booking waits on Event HTTP).
Today leftover is Ch 8 recap, not Ch 7 again.
Not Monday’s outbox / `BookingCreatedEvent`.
**Event** here is the seat service.

Anton: Event slow then dies, Booking is not sure the take is in Event’s DB.
Retry the take before telling the user, but no duplicate.
Then: recheck Event by **click id**; if missing, take again.

**Trap:** GET-then-POST can still double.
The first take can land between “not found” and the second take.
Unique click id on the **write**, not only on the GET.
Blind retry is a second write if Event wrote and then died.

**Interview sentence:** time out the wait.
Same click id on every take.
Recheck if you want; the unique id blocks a second seat.

## Calendar

Day 2 **coding (2 LCs) + LC-SD closed**.
Third LC skipped.
**Do not push** until he finishes Part 3 and asks.
**Next weekday:** Week 6 Day 3 — tree bank fill or whatever is left on the map, plus Ch 9 skip / batching-timeout from `lc-sd-map`.
