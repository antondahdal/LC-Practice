# Week 8 Day 4 — 2026-10-07 — extra vs notes repo

Wednesday, called Day 4 by Anton.
Day 2 was the CV drill (no LCs); no LCs were logged on Day 3.

Today: #198 House Robber, #146 LRU Cache.
Anton stopped LC there.
Left in the bank: #322 Coin Change.

## LC 198 House Robber — coach-written, untimed

Full write-up: `notes/review/week-08.md`.

Key idea: at each house, skip it (`best[i-1]`) or take it (`nums[i] + best[i-2]`), and keep the bigger.

**Holes:**
Did not know the pattern at the gate.
Did not see it was the Climbing Stairs skeleton until he saw the code.
Asked how `nums[i] + best[i-2]` keeps neighbours apart: `best[i-2]` never includes house `i-1`.

**How it went:**
Chose to learn instead of swapping.
Coach wrote it in his Climbing Stairs shape, 6 of 6.

## LC 146 LRU Cache — coach-written, untimed

Full write-up: `notes/review/week-08.md`.

Key idea: HashMap `key → node` for lookup, doubly linked list for the use order; every call moves the node to the tail, and eviction takes `head.next` out of both.

**Holes:**
Had the HashMap for `get`.
Thought `get` might remove the key; it only moves it to the newest end.
Tried a Stack, then a PriorityQueue, for the order; both need O(n) to pull a key from the middle.

**How it went:**
Chose to learn instead of swapping.
Coach wrote it, 5 of 5.

## Part 1 Design — Ch 10 consistent hashing recap — Done

Map slot W8 Mon–Thu: recap a weak piece.
Ch 9 batching was Monday.
Ch 10 was the weakest left (W7 Mon he opened with "I really don't know").
Token bucket (Ch 11, W7 Tue) is the other weak piece; still open.

New angle, not the same prompt: last time a node was added, this time one dies.

Prompt: Event GET cached on 4 Redis nodes, sale at 10×, node 3 dies.
What happens with `% N`, with a ring, to a browsing user, and to Book?

**His answer:**
`% N`: "keys of the dead pod are read from the DB again and spread to the rest" — that is the ring answer, not modulo.
Ring: unclear, said it depends on where the data was stored.
Browse: latency for users whose data was on the dead node (right).
Book: not answered.

**Right answer:**
`% N`: N goes 4 → 3, so the formula changes for every key, and about 3 of 4 keys point to a new node.
Those are misses even though the data still sits on live nodes: a wave on the DB at peak.
Ring: only node 3's keys (about 1 of 4) move to the next node clockwise; the rest stay hot: a bump.
Browse: latency for almost everyone with `%`, only node 3's events on a ring.
Book: no change; it never reads the cache, it goes to Event's row.

**Trap:** "only the dead node's keys move" is true only on a ring; with `%` one node dying moves almost everything.

**Interview sentence:** with modulo, losing one of 4 nodes remaps about 3 of 4 keys and floods the DB at peak.
On a hash ring only the dead node's keys move to its neighbour, so the DB sees a small bump, and Book is unaffected because it never reads the cache.

**Weak:**
Still mixes up which scheme moves only the dead node's keys.
Forgot Book unless asked.

## Coaching notes

Two sessions in a row he did not know the pattern and chose to learn.
The swap-or-learn offer still works; keep offering it once, without hints.
On DP, point him back to "what are my choices at `i`" when he reviews, not to the number pattern.

## Calendar

Day 4 Part 1 closed (#198 coach-written, #146 coach-written, Ch 10 consistent hashing recap).
#322 Coin Change carried.
Next design recap if a slot is left: Ch 11 token bucket.
Not pushed; push after Part 3 when Anton says.
