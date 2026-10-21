# Week 10 Day 3 — 2026-10-21 — handoff

Wednesday.
Part 1 is closed.
Both LCs are done.
S8 and S3 are closed.
The Wednesday design recap has no open piece. Ch 11 and Ch 10 are already closed.
Part 3 started 2026-10-21. He asked for LLD as the HTTP API, not classes, then HLD. Not this app. Product is split-bill, the bank item he skipped in Week 7. Do not rerun Monday's shows page or the deploy board. Do not draw classes.
API design is closed. Payback is `POST /groups/{groupId}/payments` with `fromUserId`, `toUserId`, `amount`, and `clickId`. Same click id returns 200 from a repository lookup inside that service. A different body for that click id is 409. A new click id is 201. He said the lookup is a repo call, not a second HTTP call. That is right.
HLD of split-bill is next. He has not started it.
Part 2 is a backend restudy. He does not want the frontend. Code first, then a question. 2026-10-21.
Open the file, then one question. He talks first.
Piece 1 is open. He has not answered it.

Reload this in `SpringBoot-bookingApp`.
Do not re-open the LCs.
Do not code them again.

## LC 20 Valid Parentheses — overtime, he fixed after 0

https://leetcode.com/problems/valid-parentheses/

Stack.
You keep each opener that is still unmatched.
Time is O(n).
Extra space is O(n).

The 15-minute clock hit 0 with `)(` and `())` still throwing `EmptyStackException`.
`pop` ran with nothing open.
He then checked an empty stack before `pop`.
10 of 10 after the clock.
No coach code.
Not retired.
Code is `questions.week10.ValidParentheses`.

## LC 45 Jump Game II — coach-written, untimed

https://leetcode.com/problems/jump-game-ii/

He asked for the code.
Greedy.
`farthest` is the furthest index this jump can reach.
When `i` reaches `end`, count one jump and set `end` to `farthest`.
5 of 5.
Code is `questions.week10.JumpGameII`.

Gas Station was set aside.
He did not understand the question.
Do not bring it back today.

## Spring — S8 and S3 closed

Do not re-ask S1, S2, S5, S8, or S3.
S1 and S2 were closed on Monday.
S5 was a bad token dying in the filter.

S8 testing slices.
He said the controller test is fake inserts with the service working, and the query-count test shows the insert SQL works.
The service in `BookingControllerTest` is the fake. A green run proves the HTTP status that fake was set to return.
The inserts in `EventListQueryCountTest` are setup. The green line is the statement count on the read that loads events with the venue.
H2: he said not sure.
H2 does not take the row lock Postgres takes with `FOR UPDATE`. The last-seat test has to run on Postgres.
Line: the controller test proves the status from the fake. The query-count test proves the list SQL. The last-seat lock needs Postgres.

S3 proxies.
He said `writeBook` is separate so the lock is released after Event finishes, and a self-call means `@Transactional` will not work.
The self-call half is right.
The separate bean is so the call goes through Spring's proxy.
`book()` has no transaction so Booking does not hold a connection during the Auth and Event calls. The seat lock ends when Event returns, before the ticket is written.
Line: `@Transactional` runs only when the call goes through Spring's proxy. `writeBook` is its own bean so that call starts a transaction. A call through `this` starts none.

## Design recap

Ch 11 token bucket is closed.
Ch 10 consistent hashing is closed.
Do not re-ask those.
No other weak LC-SD piece is still open. Week 8 already recapped Ch 9. Do not invent a chapter.

## Part 2 — whole app, interview restudy

The ordered read is `booking-app-interview-notes/interview-notes/book-path.md`.
Read that from the gateway to the hold expiry. This file is the quiz, not the order.

Backend only. He does not know the frontend. Do not open `event-booking-demo`.
Code first. He talks first. Say right or wrong before the next question. Always give the full file path. Then the trap and one interview sentence.
Do not re-ask S1, S2, S3, S5, S8, Ch 10, or Ch 11.

Piece 1 closed. He pointed at `bookingRoutes` and said it goes to the booking controller.
The bean forwards HTTP to `booking.service.uri` (`http://localhost:8080` in the gateway properties). `BookingController` runs in that other process.
Line: the gateway forwards that POST to the booking service URI. `BookingController` runs after the HTTP arrives.

Piece 2 closed. He said 201 and responseDTO.
201 is right (`HttpStatus.CREATED`). The DTO is the body. The method it calls is `bookingService.book`.
Line: `book()` returns 201 with the `BookingResponseDto` from `bookingService.book`.

Piece 3 closed. He said the first call checks whether the user from the token exists.
The goal is right. He did not name the method. `book` does not read the token. The line calls `authClient.checkIfExist()`, which sends the Authorization header to Auth.
Line: `book` starts with `authClient.checkIfExist()`. Auth is the one that reads the token and looks the user up.

Piece 4 closed. He said line 37 takes the click id and looks for a booking with that id, so a double click returns what is already there or an error.
The lookup is right. The method is `bookingWriter.findByClickId(dto.getClickId())`.
Same user, event, and seats: `replay` returns that ticket. A different user, event, or seat count throws `ClickIdMismatchException`, which is 409.
Line: same click id with the same book returns the ticket. Same click id with a different book is 409.

Piece 5 closed. He said line 41 is an HTTP call to Event that subtracts seats, checks there are enough, and returns an `EventResponseDto` used to write the ticket in Booking's database.
The call and the DTO are right. The method is `eventClient.reserveSeats(id, dto.getSeats(), dto.getClickId())`: event id, seat count, click id.
This line sends the request. Event does the check and the subtract. The ticket write is line 43, `bookingWriter.writeBook`.
Line: `reserveSeats` sends the event id, the seats, and the click id. The DTO that comes back is what the ticket write uses.

He asked to be graded against the exact method, not a high-level paraphrase.

Piece 6 closed. He said it locks the event repository row, and not enough seats returns `InsufficientSeatsException`.
The idea is right. The lock is line 122, `eventRepository.findByIdForUpdate(id)`, the event row. Not enough seats is line 133, `throw new InsufficientSeatsException`. That is a throw, and the handler makes it 409. Enough seats is line 135, `availableSeats` minus the requested seats.
Line: `reserveSeats` locks the event row with `findByIdForUpdate`. Not enough seats throws `InsufficientSeatsException`.

Piece 7 closed. He said a double click with the same seats returns the same ticket, and a different event id or different seats is `ClickIdMismatchException`.
The lookup and the mismatch are right. Line 124 is `seatHoldRepository.findByClickId(clickId)`.
Same event id and same seats: line 129 returns `toResponse(event, existing.getId())`. That is an `EventResponseDto` for the existing hold. Seats are not subtracted. It is not a ticket.
Different event id or different seats: one throw, `ClickIdMismatchException`. Either mismatch is enough.
Line: same click id, same event, same seats returns the existing hold and does not subtract. A different event or a different seat count throws `ClickIdMismatchException`.

Piece 8 closed. He said the seats are subtracted, the event is saved, then a seat hold is saved with status `HELD`.
That part is right. Lines 135–136 reduce `availableSeats` and save the event. Lines 137–144 save a `SeatHold` with `setStatus(HoldStatus.HELD)`.
He missed the field the question asked for. Line 140 is `seatHold.setClickId(clickId)`.
Line: after the seat count drops, a `SeatHold` is saved with that click id and status `HELD`. A retry finds that hold and does not subtract again.

Piece 9 closed. He said the commit saves the ticket, then two outbox messages: one to confirm, one to notify. The email is not sent here. He also pointed at line 58, `publishEvent`, and said he is not sure what it does. The DTO goes back to the service and the controller.
The three saves are right. Line 53 saves the `Booking`. Line 55 saves an `OutboxMessage` of type `NOTIFY`. Line 56 saves an `OutboxMessage` of type `CONFIRM_HOLD`. Both outbox rows are `PENDING`. `CONFIRM_HOLD` confirms the seat hold, not the event.
Line: `writeBook` commits the ticket and two outbox rows together. The mail is not sent in this method.

Piece 10 closed. He said it notifies the user like an email, but in the logs, then sets the outbox message to SENT.
Right. Line 26 is `bookingNotifier.send`, which prints `ticket` plus the booking id. It does not email, and it does not call Event. Lines 32–34 load the `NOTIFY` row and `setStatus("SENT")`. `CONFIRM_HOLD` stays `PENDING`.
Line: after commit, the listener prints the ticket id and marks the `NOTIFY` row `SENT`. The hold confirm is still waiting.

Piece 11 closed. He said this outbox message is for Event to confirm that the ticket is done.
The Event call is right. The object is wrong. Line 49 is `eventClient.confirmHold(message.getHoldId())`. It sends the hold id. The ticket was already saved in `writeBook`.
Line: the poller asks Event to confirm the seat hold. The ticket row is already saved.

Piece 12 closed. His answer to line 169 is that `confirmHold` confirms the hold. That is right. Line 174 sets the hold from `HELD` to `CONFIRMED`.
He also said a ticket problem returns the seats. That return is `expireHold`, not `confirmHold`.
Line: `confirmHold` sets the hold to `CONFIRMED`.

Piece 13 closed. He said it loads the expired ones and expires them.
The loop is right. Line 31 calls `expireHold`.
The load is not status `EXPIRED`. Line 27 is `findByStatusAndExpiresAtBefore(HoldStatus.HELD, LocalDateTime.now())`: still `HELD`, and `expiresAt` is already past.
Line: the job loads holds that are still `HELD` and past their time, then `expireHold` marks them `EXPIRED` and puts the seats back.

He stopped Part 2 here. The gate question was too far for today. Do not continue it in this session.

Next time, if he wants to resume: `gateway/src/main/java/com/eventbooking/gateway/BookRateLimitFilter.java`, method `doFilterInternal`. When `tryConsume` returns false, what status is set, and does `chain.doFilter` run? He has not answered it.
