package us.plombardi.groovyocean


class GroovyOceanConfig {

  private final static int MIN_PAGE_SIZE = 1

  final String apiToken

  int pageSize = 25

  GroovyOceanConfig(String apiToken) {
    if (!apiToken?.trim()) {
      throw new IllegalArgumentException("apiToken:${String} cannot be null or empty")
    }

    this.apiToken = apiToken
  }

  GroovyOceanConfig setPageSize(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Query page size cannot be less than ($MIN_PAGE_SIZE)")
    }

    this.pageSize = value
    this
  }
}
