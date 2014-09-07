package us.plombardi.groovyocean.model

/**
 *
 * @author plombardi
 */


class APIInternalServiceException extends IOException {

  final Optional<Integer> statusCode

  APIInternalServiceException(Throwable cause) {
    this(null, null, cause)
  }

  APIInternalServiceException(Integer statusCode, String message = null, Throwable cause = null) {
    super("API service experienced an internal service exception ${!statusCode ?: "(${statusCode}) "}${!message ?: "because of '${message}'"}", cause)
    this.statusCode = Optional.ofNullable(statusCode)
  }
}
