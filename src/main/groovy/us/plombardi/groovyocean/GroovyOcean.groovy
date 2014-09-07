package us.plombardi.groovyocean

import us.plombardi.groovyocean.core.ActionsClient
import us.plombardi.groovyocean.core.DigitalOceanClient
import us.plombardi.groovyocean.core.DomainsClient
import us.plombardi.groovyocean.core.DropletsClient
import us.plombardi.groovyocean.core.ImagesClient
import us.plombardi.groovyocean.core.RegionsClient
import us.plombardi.groovyocean.core.SizesClient
import us.plombardi.groovyocean.core.KeysClient
import us.plombardi.groovyocean.model.Action
import us.plombardi.groovyocean.model.Domain
import us.plombardi.groovyocean.model.Domain.UncreatedDomain
import us.plombardi.groovyocean.model.DomainCollection
import us.plombardi.groovyocean.model.Droplet
import us.plombardi.groovyocean.model.Image
import us.plombardi.groovyocean.model.Key
import us.plombardi.groovyocean.model.KeysCollection
import us.plombardi.groovyocean.model.RateLimit
import us.plombardi.groovyocean.model.RateLimited
import us.plombardi.groovyocean.model.RegionsCollection
import us.plombardi.groovyocean.model.SizesCollection


class GroovyOcean {

  private final static String API_HOST = 'api.digitalocean.com'

  private final DigitalOceanClient doClient
  private final ActionsClient actionsClient
  private final KeysClient keysClient
  private final ImagesClient imagesClient
  private final DropletsClient dropletsClient
  private final DomainsClient domainsClient
  private final SizesClient sizesClient
  private final RegionsClient regionsClient

  GroovyOcean(String apiToken) {
    this(new DigitalOceanClient(URI.create("https://${API_HOST}"), new GroovyOceanConfig(apiToken)))
  }

  GroovyOcean(DigitalOceanClient client) {
    if (!client) {
      throw new IllegalArgumentException("Cannot construct $GroovyOcean instance with a null $DigitalOceanClient")
    }

    this.doClient = client
    this.actionsClient  = new ActionsClient(client)
    this.dropletsClient = new DropletsClient(client, actionsClient)
    this.imagesClient   = new ImagesClient(client, actionsClient)
    this.domainsClient  = new DomainsClient(client)
    this.sizesClient    = new SizesClient(client)
    this.regionsClient  = new RegionsClient(client)
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Actions Resource Operations
  // -------------------------------------------------------------------------------------------------------------------

  Optional<Action> getAction(String id) {
    actionsClient.getAction(id)
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Regions Resource Operations
  // -------------------------------------------------------------------------------------------------------------------

  RegionsCollection getRegions() throws RateLimited {
    regionsClient.regions
  }

  // -------------------------------------------------------------------------------------------------------------------
  // SSH Keys Resource Operations
  // -------------------------------------------------------------------------------------------------------------------

  Key.ActiveKey createKey(Key newKey) throws RateLimited {
    keysClient.createKey(newKey)
  }

  Optional<Key.ActiveKey> getKey(String idOrFingerprint) throws RateLimited {
    keysClient.getKey(idOrFingerprint)
  }

  KeysCollection getKeys(int page = 1) throws RateLimited {
    keysClient.getKeys(page)
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Rate Limit Information
  // -------------------------------------------------------------------------------------------------------------------

  RateLimit getRateLimit() {
    doClient.rateLimit
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Droplets Resource
  // -------------------------------------------------------------------------------------------------------------------

  Optional<Droplet.ActiveDroplet> getDroplet(String id) throws RateLimited {
    dropletsClient.getDroplet(id)
  }

  void deleteDroplet(String id) throws RateLimited {
    dropletsClient.deleteDroplet(id)
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Domains Resource
  // -------------------------------------------------------------------------------------------------------------------

  Optional<Domain> getDomain(String name) throws RateLimited {
    domainsClient.getDomain(name)
  }

  DomainCollection getDomains(int page = 1) throws RateLimited {
    domainsClient.getDomains(page)
  }

  Domain createDomain(UncreatedDomain domain) throws RateLimited {
    domainsClient.createDomain(domain)
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Images Resource
  // -------------------------------------------------------------------------------------------------------------------

  Optional<Image> getImage(String id) {
    imagesClient.getImage(id)
  }

  Map<String, Image> getImages() {
    imagesClient.images
  }

  SizesCollection getSizes() throws RateLimited {
    sizesClient.sizes
  }
}
