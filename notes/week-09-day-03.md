# Week 9 Day 3 — 2026-10-14 — extra vs notes repo

Wednesday.
Two LCs, then S3 and S4.

Today: #46 Permutations, #392 Is Subsequence.
#39 Combination Sum is out of the leftover bank.
Left in the LC bank: #14, #208, #530.
Spring left: S5–S8, then Friday R1–R8.

Never offer to swap a problem unless he asks.
From Day 4: one old LC, then one new LC, same pattern.
Fresh stub in `week09` for the old one.
No fourth day of the same family.
No new backtracking.

## LC 46 Permutations

Full write-up: `notes/review/week-09.md`.

You keep one partial list and a used mark per index.
Add an unused number, recurse, then drop it and unmark it.
Save a copy when the list is as long as `nums`.
Time is O(n · n!), not O(n²).

## LC 392 Is Subsequence

Full write-up: `notes/review/week-09.md`.

One index on `s`, one on `t`.
A match moves `s`.
Every step moves `t`.
True only after `s` is fully consumed.
`sInd == s.length() - 1` is too early.
`s = "a"`, `t = "b"` must be false.

## S3 — Proxies and AOP

`writeBook()` has `@Transactional`.

Call 1: the controller does `bookingService.writeBook()`.
That hits Spring's wrapper first.
The wrapper opens the transaction, then calls the real method.

Call 2: `book()` does `writeBook()` on the same object.
That is `this`.
Spring never sees it.
No transaction.

Same miss: `@Transactional` on a `private` method.

Say in the interview: `@Transactional` runs only when the call goes through Spring's proxy, not through `this`.

## S4 — Bean scopes, lifecycle, circular dependencies

`@Service` is a singleton.
One `BookingService` for the whole app.
Not a new bean each time something injects it.

Spring builds it in this order: `new`, inject constructor args, `@PostConstruct`, then it can take calls.

This cycle fails at startup:

```java
@Service
class A { A(B b) {} }

@Service
class B { B(A a) {} }
```

`A` cannot finish until `B` exists.
`B` cannot finish until `A` exists.

Field injection can hide that loop until a request hits it.
Constructor injection blows up at boot, which is what you want.

Prototype is a new instance per lookup.
Booking does not want that for the service that writes tickets.

Say in the interview: `BookingService` is a singleton.
A constructor loop between two beans fails startup.

## Calendar

Day 3 Part 1 closed (#46 untimed, coach-written; #392 on-time; S3; S4).
Pushed 2026-10-14 after Part 2 (Anton asked). Part 3 event search was not reached.
From Day 4: old LC then new LC, same pattern.
