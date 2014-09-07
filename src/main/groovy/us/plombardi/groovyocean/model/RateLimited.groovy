package us.plombardi.groovyocean.model

import java.time.Duration
import java.time.Instant

/**
 * Indicates that a request has been rate-limited by Digital Ocean. Implemented as a checked exception so that Java
 * consumers know that they must handle this problem.
 *
 * @author plombardi
 */


class RateLimited extends Exception {

  final Instant resetTime

  /**
   * Construct a new RateLimited exception with RateLimit-Reset value provided by the Digital Ocean API.
   *
   * @param resetTime The time at which the oldest API request will be forgotten and more API requests can be sent.
   */
  RateLimited(Instant resetTime) {
    super("API rate limit exceeded; next request can be sent at '${resetTime.toString()} or (${Duration.between(Instant.now(), resetTime)}) seconds from now'")

    if (!resetTime) {
      throw new IllegalArgumentException("resetTime:${Instant.getClass().name} cannot be null")
    }

    this.resetTime = resetTime
  }
}
