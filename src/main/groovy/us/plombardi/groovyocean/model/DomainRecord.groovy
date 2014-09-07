package us.plombardi.groovyocean.model



class DomainRecord {

  enum Type {
    A, AAAA, CNAME, MX, TXT, SRV
  }

  private final Domain domain
  private final Integer priority
  private final Integer port
  private final Integer weight

  String id
  String type
  String name
  String data

  void delete() {
    domain.deleteRecord(id)
  }

  Optional<Integer> getPriority() {
    return Optional.ofNullable(priority)
  }

  Optional<Integer> getPort() {
    return Optional.ofNullable(port)
  }

  Optional<Integer> getWeight() {
    return Optional.ofNullable(weight)
  }

  static class ActiveDomainRecord implements Serializable {

    final static serialVersionUID = 20140830L

    final Domain domain

    @Delegate
    final DomainRecord record

    ActiveDomainRecord(Domain domain, DomainRecord record) {
      if (!domain) { throw new IllegalArgumentException("domain:$Domain cannot be null") }
      if (!record) { throw new IllegalArgumentException("record:$DomainRecord cannot be null") }

      this.domain = domain
      this.record = record
    }

    ActiveDomainRecord rename(String newName) {
      throw new UnsupportedOperationException()
    }
  }
}
