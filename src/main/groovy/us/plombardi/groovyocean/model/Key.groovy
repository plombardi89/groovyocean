package us.plombardi.groovyocean.model

import groovy.transform.Immutable
import groovy.transform.ToString
import us.plombardi.groovyocean.core.KeysClient


@ToString(excludes = ['publicKey'])
@Immutable
class Key implements Serializable {

  final static serialVersionUID = 20140830L

  String id
  String name
  String fingerprint
  String publicKey

  @ToString(excludes = ['keys'])
  static class ActiveKey implements Serializable {

    final static serialVersionUID = 20140830L

    private final transient KeysClient keys

    @Delegate
    final Key key

    ActiveKey(KeysClient client, Key key) {
      if (!client) { throw new IllegalArgumentException("client:$KeysClient cannot be null") }
      if (!key) { throw new IllegalArgumentException("key:$Key cannot be null") }

      this.keys = client
      this.key = key
    }

    ActiveKey rename(String newName) {
      keys.updateKey(name: newName, key.id)
    }

    void delete() {
      keys.delete(key.id)
    }
  }
}
