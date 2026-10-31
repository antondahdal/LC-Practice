# Week 11 Day 4 — 2026-10-29

Thursday.
Monday is closed.
Tuesday and Wednesday have no notes.
Both LCs are done.
Best Time to Buy and Sell Stock is closed.
On time.
5 of 5.
He coded it.
The name was given before the clock.
Remove Duplicates from Sorted Array II is closed.
On time.
5 of 5.
Coach-fixed.
The count never wrote into the array.
`counter <= 2` still accepted the third copy.
`current` started at 0, so a real 0 was not a new value.
Part 1 is closed.
Part 2 book-path resume is closed.
He said continue.
Kafka on the outbox is open.
He has not answered it.
`OutOfMemoryError` is closed.
`EventController` confirm is closed.
`confirmHold` status is closed.
Seats on confirm are closed.
`expireHold` is closed.
Kafka stays paused.
He did not ask for it.
Split-bill stays off. Thursday has no design recap.

Reload this in `SpringBoot-bookingApp`.
Do not re-open the LCs.
Do not code them again.
Part 1 is closed.
Part 2 has no open code step.
The book-path resume closed in this chat.
Part 2 is closed.
Ask the Kafka question below.
He talks first.
Do not answer before he talks.

## Spring — first question closed

`updateEvent` loads an event inside a transaction, sets a new title, and returns.
There is no `save()` call.
Does Postgres get the new title?
Why?

He said yes.
The context from the select is compared.
A different title is written.
Right.
The loaded event is managed.
Commit does the dirty check and writes the `UPDATE`.

Trap: calling `save()` to make the title stick.
The watched event already updates on commit.

Interview: a title change on an event I just loaded does not need `save()`.
Hibernate writes the `UPDATE` when the transaction commits.

## Spring — second question closed

`Event.venue` is lazy.
`open-in-view` is off.
A method loads the event with `findById`, the transaction ends, then it reads the venue name.
What does the caller get, and why?

He said a lazy error.
He did not remember the name.
No connection, and the read still tries to load the venue.
Right.
The name is `LazyInitializationException`.
The session is already closed.

Trap: setting `venue` to `EAGER` on every load.
Fetch the venue in the query that needs the name.

Interview: a lazy venue read after the transaction ends throws `LazyInitializationException`.
Fetch the venue inside that transaction.

## OutOfMemoryError — closed

A service throws `OutOfMemoryError` in production.
What are the first steps?

He said check why Venue is so big, then check why the memory is not big enough for it.
Stayed.
The process is out of heap.
Keep the heap dump if the JVM wrote one, and let the pod restart.
The dump names the objects that filled the heap.

The same error comes back an hour after a bigger heap.
What does that tell you?

He said a leak that is not closed is taking space.
Right.
A bigger heap only delays it.
Something is still holding objects.

## EventController confirm — closed

`POST /api/events/holds/{holdId}/confirm` on `EventController`.
What does it call, and what status and body does it return?

He said it calls Event service to confirm the seat hold.
Body `Ok`, status 200.
Right.
It calls `confirmHold`.
The response is 200 with body `Ok`.

## confirmHold — status closed

`EventServiceImpl.confirmHold`.
The hold is still `HELD`.
What does this method change?

He said `CONFIRMED`.
Right.
The hold status becomes `CONFIRMED`.

## Seats on confirm — closed

`confirmHold` set the hold to `CONFIRMED`.
Does it add the seats back to `availableSeats`?

He said no.
Right.
`confirmHold` does not change `availableSeats`.

## expireHold — closed

`EventServiceImpl.expireHold`.
In which status does it add the seats back?

He said `HELD`.
Right.
`expireHold` adds the seats back only while the hold is `HELD`.

## Kafka on the outbox — open

He said continue.
This is the leftover after the book path.
Monday's note lists it after `OutOfMemoryError`.
`BookingWriter` saves the ticket and the `NOTIFY` row in one commit.
Kafka is the log a worker can publish that row onto.
What does publishing that row add?
He has not answered.

Split-bill drawing stays off.
Thursday has no design recap.
Do not re-ask S1, S2, S3, S4, S5, S6, S7, or S8.
Do not re-ask dirty checking, lazy loading, `OutOfMemoryError`, the confirm path, or `expireHold`.
