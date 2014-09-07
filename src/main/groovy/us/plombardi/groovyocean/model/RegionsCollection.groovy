package us.plombardi.groovyocean.model


class RegionsCollection extends ResourceCollection {

  @Delegate private final Map<String, Region> regions

  RegionsCollection(meta, regions) {
    super(meta)
    this.regions = regions.inject([:]) { map, region -> toRegion(region) }
  }

  Optional<Region> getRegion(String slug) {
    Optional.ofNullable(regions[slug])
  }

  boolean hasRegion(String slug) {
    getRegion(slug).present
  }

  private static Region toRegion(args) {
    new Region(
        slug: args.slug,
        name: args.name,
        sizes: args.sizes,
        features: args.features,
        available: args.available
    )
  }
}
