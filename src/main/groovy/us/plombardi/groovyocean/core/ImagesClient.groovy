package us.plombardi.groovyocean.core

import groovy.json.JsonOutput
import us.plombardi.groovyocean.model.APIInternalServiceException
import us.plombardi.groovyocean.model.Action
import us.plombardi.groovyocean.model.Image
import us.plombardi.groovyocean.model.ImageCollection

import java.time.Instant


class ImagesClient extends ResourceClient {

  private final static String RESOURCE_PATH = '/images'

  ImagesClient(DigitalOceanClient doClient, ActionsClient actionsClient) {
    super(doClient, actionsClient)
  }
  
  private toImage(image) {
    new Image(
        images: this,
        id: image.id,
        name: image.name,
        distribution: image.distribution,
        slug: image.slug,
        _public: image.public,
        regions: image.regions,
        creationTime: Instant.parse(image.created_at.toString()),
        actions: image.action_ids
    )  
  }
  
  ImageCollection getImages(int page = 1) {
    if (page < 1) {
      throw new IllegalArgumentException("Cannot retrieve images from an illegal page ($page)")
    }

    def resp = doClient.get(RESOURCE_PATH)
    if (resp.successful) {
      def content = mapify(resp.body())
      new ImageCollection(this, content.images.collect { toImage(it) }, content.meta)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  Optional<Image> getImage(String idOrSlug) {
    def resp = doClient.get("${RESOURCE_PATH}/${idOrSlug}")
    if (resp.successful) {
      def content = mapify(resp.body())
      Optional.of(toImage(content.image))
    } else {
      Optional.empty()
    }
  }

  Image updateImage(fieldsToModify, String imageId) {
    def resp = doClient.put("${RESOURCE_PATH}/${imageId}", JsonOutput.toJson(fieldsToModify))
    if (resp.successful) {
      def content = mapify(resp.body())
      toImage(content.image)
    } else {
      throw new APIInternalServiceException(resp.code())
    }
  }

  void delete(String id) {
    doClient.delete("${RESOURCE_PATH}/${id}")
  }

  Action getImageAction(String imageId, String actionId) {
    null
  }

  Action transfer(String id, String region) {
    def response = doClient.post("${RESOURCE_PATH}/${id}/actions", JsonOutput.toJson(type: 'transfer', region: 'region'))
    null
  }
}
