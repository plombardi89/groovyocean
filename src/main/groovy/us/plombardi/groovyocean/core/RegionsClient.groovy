package us.plombardi.groovyocean.core

import us.plombardi.groovyocean.model.RegionsCollection


class RegionsClient extends ResourceClient {

  private final static String RESOURCE_PATH = '/regions'

  RegionsClient(DigitalOceanClient doClient) {
    super(doClient)
  }

  RegionsCollection getRegions() {
    def resp = doClient.get(RESOURCE_PATH)
    def content = mapify(resp.body())
    new RegionsCollection(content.meta, content.sizes)
  }
}
