package us.plombardi.groovyocean.model

import us.plombardi.groovyocean.core.ImagesClient

import java.time.Instant


class ImageCollection extends ResourceCollection {

  private final ImagesClient client

  @Delegate private final Map<String, Image> images;

  ImageCollection(ImagesClient client, Collection<Image> images, meta) {
    super(meta)

    if (!client) {
      throw new IllegalArgumentException()
    }

    this.client = client
    this.images = images.inject([:]) { map, size -> toImage(client, size) }
  }

  Optional<Image> getImage(String id) {
    Optional.ofNullable(images[id])
  }

  boolean hasImage(String id) {
    getImage(id).present
  }
  
  private static toImage(ImagesClient client, args) {
    new Image(
        images: client,
        id: args.id,
        name: args.name,
        distribution: args.distribution,
        slug: args.slug,
        _public: args.public,
        regions: args.regions,
        creationTime: Instant.parse(args.created_at.toString()),
        actions: args.action_ids
    )  
  }
}
