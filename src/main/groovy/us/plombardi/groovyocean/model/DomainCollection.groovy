package us.plombardi.groovyocean.model

import us.plombardi.groovyocean.core.DomainsClient


class DomainCollection extends ResourceCollection {

  private final DomainsClient client

  @Delegate
  final Map<String, Domain> domains

  DomainCollection(DomainsClient client, Collection<Domain> domains, meta) {
    super(meta)

    if (!client) {
      throw new IllegalArgumentException("client:${DomainsClient} cannot be null")
    }

    this.client = client
    this.domains = domains?.inject([:]) { map, domain -> map << [(domain.name): domain]}
  }
}
