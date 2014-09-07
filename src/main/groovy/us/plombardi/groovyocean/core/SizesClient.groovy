package us.plombardi.groovyocean.core

import us.plombardi.groovyocean.model.SizesCollection


class SizesClient extends ResourceClient {

  private final static String RESOURCE_PATH = '/sizes'

  SizesClient(DigitalOceanClient doClient) {
    super(doClient)
  }

  SizesCollection getSizes() {
    def resp = doClient.get(RESOURCE_PATH)
    def content = mapify(resp.body())
    new SizesCollection(content.meta, content.sizes)
  }
}
