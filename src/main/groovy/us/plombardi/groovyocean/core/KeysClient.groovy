package us.plombardi.groovyocean.core

import groovy.json.JsonOutput
import us.plombardi.groovyocean.model.APIInternalServiceException
import us.plombardi.groovyocean.model.Key
import us.plombardi.groovyocean.model.KeysCollection


class KeysClient extends ResourceClient {

  private final static String RESOURCE_PATH = '/account/keys'

  KeysClient(DigitalOceanClient doClient) {
    super(doClient)
  }

  private toKey(args) {
    def key = new Key(id: args.id, name: args.name, fingerprint: args.fingerprint, publicKey: args.public_key)
    new Key.ActiveKey(this, key)
  }

  KeysCollection getKeys(int page = 1) {
    if (page < 1) {
      throw new IllegalArgumentException("Cannot retrieve keys from an illegal page ($page)")
    }

    def resp = doClient.get(RESOURCE_PATH)
    if (resp.successful) {
      def content = mapify(resp.body())
      new KeysCollection(this, content.ssh_keys.collect { toKey(it) }, content.meta)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  Key.ActiveKey createKey(Key newKey) {
    if (!newKey?.name?.trim()) {
      throw new IllegalArgumentException('Cannot create new SSH key without a name')
    }

    if (!newKey?.publicKey?.trim()) {
      throw new IllegalArgumentException('Cannot create new SSH key without public key data')
    }

    def resp = doClient.post(RESOURCE_PATH, JsonOutput.toJson(name: newKey.name, public_key: newKey.publicKey))
    if (resp.successful) {
      def content = mapify(resp.body())
      toKey(content.ssh_key)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  Optional<Key.ActiveKey> getKey(String id) {
    if (!id?.trim()) {
      throw new IllegalArgumentException('Cannot retrieve an existing SSH key because the provided key ID or fingerprint was empty or null')
    }

    def resp = doClient.get(RESOURCE_PATH + "/${id}")
    if (resp.successful) {
      def content = mapify(resp.body())
      Optional.of(toKey(content.ssh_key))
    } else {
      Optional.empty()
    }
  }

  Key.ActiveKey updateKey(Map fieldsToModify, String keyId) {
    def resp = doClient.put(RESOURCE_PATH + "/${keyId}", JsonOutput.toJson(fieldsToModify))
    if (resp.successful) {
      def content = mapify(resp.body())
      toKey(content.ssh_key)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  void delete(String id) {
    doClient.delete(RESOURCE_PATH + "/${id}")
  }
}
