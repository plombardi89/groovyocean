package us.plombardi.groovyocean.model

import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger


class RateLimit implements Serializable {

  final static serialVersionUID = 20140830L

  private final static int DEF_LIMIT = 1200

  private final AtomicInteger limit
  private final AtomicInteger remaining
  final Instant resetTime

  RateLimit() {
    this.limit = new AtomicInteger(DEF_LIMIT)
    this.remaining = new AtomicInteger(DEF_LIMIT)
    this.resetTime = null
  }

  int getLimit() {
    limit.get()
  }

  int getRemaining() {
    remaining.get()
  }

  boolean isExceeded() {
    getRemaining() == 0
  }

  String toString() {
    "${RateLimit}(limit=${limit.get()}, remaining=${remaining.get()}, resetTime=${resetTime})"
  }
}
