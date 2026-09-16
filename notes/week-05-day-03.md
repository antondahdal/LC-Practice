# Week 5 Day 3 — 2026-09-16 — extra vs notes repo

Wednesday. Part 1 coding + Ch 10 done here.

Cover: always paste the LeetCode URL. Rule added in `lc-session.mdc` / `AI-TEACHING.md`.

Bank leftover after Mon/Tue was **#21**. Mediums on the Week 5 list were already done, so first LC was Top Interview 150 **#2** (not on the printed Week 5 line). Then Easy **#21**.

## LC 2 Add Two Numbers — passed, coach wrote it

He asked for the code.

**Gate:** “regular loop”; then strings + `Integer` (overflow). Stack is **#445** (ones at the tail), not this problem. Name: two walkers. Keep **carry**. O(max(m,n)) / O(1) extra besides the new list.

**Memorize:** dummy + tail. Loop while either list **or carry**. Digit = (l1 or 0) + (l2 or 0) + carry. Write `% 10`, carry = `/ 10`. Missing node is 0, not stop.

**Weak:** splice leftover list when one walker falls off (carry can still change those digits). `9→9` plus `1` is `0→0→1`, not `1→9`.

**Cousin:** dummy; #445 stack because MSD is at the head.

## LC 21 Merge Two Sorted Lists — passed

**Gate:** two walkers, O(n+m). First said extra space = new list. Extra is **O(1)** if you rewire existing nodes.

**Weak:** took the **larger** head (`>=` / `>` then copy list1). Equal 1s are two steps, not one if that copies both. Leftover ifs inside `while (both)` never run; leftover belongs after the while, or `while (either)` with one-null meaning  hook that side. Copied `new ListNode` instead of hooking the live node. Returned dummy, then fixed to `dummy.next`.

**Cousin:** `tail.next = leftover` in one shot; same dummy as #2.

## Part 1 Design — Chapter 10 Hot key / partition lite — done

Anton: hot key is **7**. Client hits GET and Book. Nothing “breaks.” **409** = lots of Book on 7, seats run out. **502** = traffic, lock wait on that row. He had both. Coach mashed them, then restated 502 as if it was new.

**Sharpen:** key is `event:7` (that row / cache key / lock), not “the catalog.” Other events stay fine. 10× **that id** is the point.

**409** is Event sold out. He meant traffic **empties** 7, then later callers get 409. That is the row working. Not “409 means overload.”

**502** is Booking hung up waiting on Event. Possible if take on 7 is slammed.

GET 7 can be cache hits (already talked Mon/Tue). Book still hits Event’s **one row**. Extra pods do not split id 7. All Books for 7 still serialize on that lock.

**Trap:** 10× servers fixes a hot event. Treating 409 as “the box died.” Stale cache “1 left” → **201** (same as Ch 4; do not re-teach).

**Interview sentence:** Hot key is event 7. Extra browse can hit cache. Extra Book still fights one Event row. More pods do not split that id.
