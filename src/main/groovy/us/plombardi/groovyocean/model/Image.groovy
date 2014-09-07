package us.plombardi.groovyocean.model

import groovy.transform.TupleConstructor
import us.plombardi.groovyocean.core.ImagesClient

import java.time.Instant


@TupleConstructor
class Image implements Serializable {

  final ImagesClient client
  final String id
  final String name
  final String distribution
  final String slug
  final List<String> regions
  final List<String> actions
  final Instant creationTime
  private _public

  Image rename(String name) {
    client.update()
  }

  boolean isPublic() {
    _public
  }

  void delete() {
    images.delete(id)
  }

  Image update(Image image) {
    if (this.id != image.id) {
      throw new IllegalArgumentException("Cannot update image based off another image. An attempt was made to update image.id='${this.id}' using image.id='${image.id}'")
    }

    if (this != image) {
      images.update(image)
    } else {
      throw new APIClientException()
    }
  }

  Action transfer(String regionId) {
    images.transfer(id, regionId)
  }
}
