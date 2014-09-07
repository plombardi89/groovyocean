package us.plombardi.groovyocean.model

import us.plombardi.groovyocean.core.KeysClient


class KeysCollection extends ResourceCollection {

  private final KeysClient client

  @Delegate final Map<String, Key> keys

  KeysCollection(KeysClient client, Collection<Key.ActiveKey> keys, meta) {
    super(meta)
    this.client = client
    this.keys = keys.inject([:]) { map, key -> map << [(key.id): key]}
  }
}
