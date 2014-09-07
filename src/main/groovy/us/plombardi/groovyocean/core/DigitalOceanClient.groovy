package us.plombardi.groovyocean.core

import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response
import us.plombardi.groovyocean.GroovyOceanConfig
import us.plombardi.groovyocean.model.APIInternalServiceException
import us.plombardi.groovyocean.model.RateLimited
import us.plombardi.groovyocean.model.RateLimit

import java.time.Instant


class DigitalOceanClient {


  private final static String API_VERSION = 'v2'

  private final static OkHttpClient HTTP = new OkHttpClient() // thread-safe
  private final static MediaType JSON = MediaType.parse('application/json; charset=utf-8')

  private final URI digitalOceanURI
  private final GroovyOceanConfig config

  final RateLimit rateLimit = new RateLimit()

  DigitalOceanClient(URI digitalOceanURI, GroovyOceanConfig config) {
    if (!digitalOceanURI) {
      throw new IllegalArgumentException("digitalOceanURI:${URI.getClass().name} cannot be null")
    }

    if (!config) {
      throw new IllegalArgumentException("config:${GroovyOceanConfig.getClass().name} cannot be null")
    }

    this.digitalOceanURI = digitalOceanURI
    this.config = config
  }

  private Request.Builder newRequest() {
    new Request.Builder().addHeader('Authorization', "Bearer ${config.apiToken}")
  }

  private Response executeRequest(Request.Builder request) throws APIInternalServiceException {
    def response
    try {
      response = HTTP.newCall(request.build()).execute()
    } catch (IOException ioe) {
      throw new APIInternalServiceException(ioe)
    }

    handleResponse(response)
  }

  Response get(String path) {
    def request = newRequest().url(createAPIURI(path).toURL()).get()
    def response = executeRequest(request)
    response
  }

  Response put(String path, String data) {
    def body = RequestBody.create(JSON, data)
    def request = newRequest().url(createAPIURI(path).toURL()).post(body)
    def response = executeRequest(request)
    response
  }

  Response post(String path, String data) {
    def body = RequestBody.create(JSON, data)
    def request = newRequest().url(createAPIURI(path).toURL()).post(body)
    def response = executeRequest(request)
    response
  }

  Response delete(String path) {
    def request = newRequest().url(createAPIURI(path).toURL()).delete()
    def response = executeRequest(request)
    response
  }

  private URI createAPIURI(String path) {
    if (!path.startsWith("/$API_VERSION")) {
      path = joinPaths(API_VERSION, path)
    }

    digitalOceanURI.resolve('/').resolve(path)
  }

  private static joinPaths(String prefix, String suffix) {
    if (!prefix.endsWith('/')) {
      prefix = prefix + '/'
    }

    if (suffix.startsWith('/')) {
      suffix = suffix[1..-1]
    }

    prefix + suffix
  }

  private static Response handleResponse(Response response) {
    if (response.code() / 100 == 5) {
      throw new APIInternalServiceException(response.code(), response.message())
    } else {
      switch(response.code()) {
        case 404        : break
        case (400..499) : throw new RuntimeException('')
        case 429        : handleRateLimted(response)
      }
    }

    return response
  }

  private static void handleRateLimted(Response response) {
    def resetTimeHeader = response.header('RateLimit-Reset')
    throw new RateLimited(Instant.ofEpochSecond(resetTimeHeader.toLong()))
  }
}
