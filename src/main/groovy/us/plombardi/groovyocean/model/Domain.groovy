package us.plombardi.groovyocean.model

import groovy.transform.Immutable
import groovy.transform.ToString
import us.plombardi.groovyocean.core.DomainsClient


@ToString(excludes = ['client'])
@Immutable(knownImmutableClasses = [DomainsClient])
class Domain implements Serializable {

  final static serialVersionUID = 20140830L

  private final transient DomainsClient client

  String name
  long ttl
  private String zoneFileContent

  void delete() {
    client.delete(name)
  }

  void deleteRecord(String recordId) {
    client.deleteRecord(this.name, recordId)
  }

  Optional<String> getZoneFileContent() {
    Optional.ofNullable(zoneFileContent)
  }

  @ToString
  static class UncreatedDomain {

    final String name
    final String ipAddress

    UncreatedDomain(String name, String ipAddress) {
      if (!name?.trim()) {
        throw new IllegalArgumentException("Cannot create with a null or empty name")
      }

      if (!ipAddress?.trim()) {
        throw new IllegalArgumentException("Cannot create with a null or empty IP address")
      }

      this.name = name
      this.ipAddress = ipAddress
    }
  }
}
