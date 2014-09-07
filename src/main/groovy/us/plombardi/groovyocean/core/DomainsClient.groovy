package us.plombardi.groovyocean.core

import groovy.json.JsonOutput
import us.plombardi.groovyocean.model.APIInternalServiceException
import us.plombardi.groovyocean.model.Domain
import us.plombardi.groovyocean.model.Domain.UncreatedDomain
import us.plombardi.groovyocean.model.DomainCollection

class DomainsClient extends ResourceClient {

  private final static String RESOURCE_PATH = '/domains'

  DomainsClient(DigitalOceanClient doClient) {
    super(doClient)
  }

  private toDomain(args) {
    new Domain(client: this, name: args.name, ttl: args.ttl, zoneFileContent: args.zone_file)
  }

  DomainCollection getDomains(int page = 1) {
    if (page < 1) {
      throw new IllegalArgumentException("Cannot retrieve domains from an illegal page ($page)")
    }

    def resp = doClient.get(RESOURCE_PATH)
    if (resp.successful) {
      def content = mapify(resp.body())
      new DomainCollection(this, content.domains.collect { toDomain(it) }, content.meta)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  Domain createDomain(UncreatedDomain newDomain) {
    if (!newDomain) {
      throw new IllegalArgumentException("Cannot create a new Domain because the provided reference is null")
    }

    def resp = doClient.post(RESOURCE_PATH, JsonOutput.toJson(name: newDomain.name, ip_address: newDomain.ipAddress))
    if (resp.successful) {
      def content = mapify(resp.body())
      toDomain(content.domain)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  Optional<Domain> getDomain(String name) {
    if (!name?.trim()) {
      throw new IllegalArgumentException("Cannot retrieve an existing domain because the provided domain name was null or empty")
    }

    def resp = doClient.get("/${RESOURCE_PATH}/${name}")

    if (resp.successful) {
      def content = mapify(resp.body())
      Optional.of(toDomain(content.domain))
    } else {
      Optional.empty()
    }
  }

  void delete(String name) {
    doClient.delete("/${RESOURCE_PATH}/${name}")
  }

  void deleteRecord(String domainName, String recordId) {
    doClient.delete("/domains/${domainName}/records/${recordId}")
  }
}
