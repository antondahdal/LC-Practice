# Week 11 Day 1 — 2026-10-26

Monday.
Wrapped 2026-10-26.

**Next:** Part 3 row, `OutOfMemoryError`. First steps when a service throws it in production. One short question. Bullet, then the question.
Then Part 2 resume, in order: `EventController` for `POST /api/events/holds/{holdId}/confirm` (what it calls, status and body). Then `EventServiceImpl.confirmHold`. Then the 10-minute hold.
After OOM, the screenshot list still open: Kafka on the outbox, and the split-bill drawing. Skip SOLID as a fresh row. Virtual threads was S7.

Both LCs are done.
Contains Duplicate is closed. On time. 5 of 5. He coded it. No coach fix.
Rotate Array is closed. On time. 5 of 5. He coded the three reverses.
`k = k - length` once. `[1, 2]` with `k = 5` reads past the end. The step count is `k % length`.
Part 1 Spring is closed.
S4 is closed. One BookingService. A prototype helper injected into its constructor is also one. Spring creates that helper once, with the service. book() does not ask for a new helper.
S6 is closed. seats 0 fails @Min(1) on @Valid. book() does not run. Boot's default handler writes 400. GlobalExceptionHandler does not.
The Monday design recap has nothing left open. Do not invent a chapter.
Part 2 is open. Book-path restudy, pieces 1–13 closed through the hold expiry job.
`tryConsume` false is closed. Status 429. He first said `chain.doFilter` still runs. He corrected it: line 44 returns.
The `catch` is closed. A `RuntimeException` sets 503 and returns. `chain.doFilter` does not run.
`callerKey` is closed. No token: `"ip:"` plus `getRemoteAddr()`. With a bearer token he asked if the token is the subject. It is not. The token is the whole Bearer value. `subjectOf` reads the `sub` claim. The key is `"user:"` plus that claim.
`tokenBucket` is closed. `REDIS_URL` builds `RedisTokenBucket`. No Redis host builds `InMemoryTokenBucket`. He said a second local process still lets the Book through, because each process has its own memory. Redis exists so every gateway process shares one count.
Jar size is closed. Capacity 5. `refill-ms` is 15000, so one token comes back every 15 seconds.
`book()` has no `@Transactional`. He said the two HTTP calls must not hold a pool connection. The connection is not borrowed until `writeBook`.
The same-tap `catch` is closed. Different user: `ClickIdMismatchException`. Same user, event, and seats: `replay` returns the first ticket. The controller still answers 201. He named `BookingResponseDto` and the first ticket.
`publishEvent` is closed. `writeBook` does not send mail. `EventIdPublisher` is `@Async` and `AFTER_COMMIT`. It prints the ticket id and sets the `NOTIFY` row to `SENT`. `CONFIRM_HOLD` stays `PENDING`.
`HoldDataExceedTimeException` is closed. The poller sets the ticket to `CANCELLED`.
`EventClient.confirmHold` is closed. Path `/api/events/holds/{holdId}/confirm`. Header `X-Internal-Token`.
Part 2 wrapped for today. Questions stay short from here.
Resume at `EventController` for `POST /api/events/holds/{holdId}/confirm`. What does it call, and what status and body does it return?
Then `EventServiceImpl.confirmHold` only after that. Then the 10-minute hold: confirm does not return seats, expire returns them only while `HELD`.
Part 3 is open. Iterators are explained: fail-fast throws, fail-safe walks a copy. Example is in `interview-notes/core-java.md`.
`ConcurrentHashMap` is closed. He said lock the row being changed. The idea is right: lock that key's bucket, not the whole map. Not a database row.
Thread pool queue is closed. All threads busy and the queue full: the new task is rejected. Default is `RejectedExecutionException`.
Mutable `HashMap` key is closed. He said the lookup will not find it. The entry stays in the old bucket.
`orElse` vs `orElseGet` is explained. He did not know it. `orElse` always runs the fallback. `orElseGet` runs it only when empty.
Next Part 3 row: `OutOfMemoryError` first steps.
Do not re-ask S1, S2, S3, S4, S5, S6, S8, Ch 10, or Ch 11.

Reload this in `SpringBoot-bookingApp`.
Do not re-open the LCs.
Do not code them again.

## Spring — S4 and S6 closed

S4. One `BookingService`. One prototype helper. Created once, in the constructor.
Trap: a new helper on every `book()` call.
Line: a prototype helper injected into a singleton is created once, with that singleton.

S6. `seats` 0. `@Valid` and `@Min(1)` stop the method body. Boot's default handler writes 400.
Trap: `GlobalExceptionHandler` writes that 400, or `book()` still runs.
Line: a bad body is 400 from Boot's default handler, before `book()` runs. Sold out is 409 from `GlobalExceptionHandler`.

The Java microservices list from the screenshots is Part 3.
Do not run it in Part 1.
One question at a time, he talks first, then the trap and one interview sentence.
Skip rows already closed: S1, S2, S3, S5, S8, Ch 10, Ch 11, the book-path pieces through the hold expiry job.
Part 3 wrapped through `orElse` / `orElseGet`. Examples are in notes `core-java.md`.
Still open on that list: `OutOfMemoryError`, Kafka on top of the outbox, and the split-bill high-level drawing.
