package us.plombardi.groovyocean.model


class SizesCollection extends ResourceCollection {

  private final static Currency DEF_CURRENCY = Currency.getInstance('USD')

  @Delegate private final Map<String, Size> sizes;

  SizesCollection(meta, sizes) {
    super(meta)
    this.sizes = sizes.inject([:]) { map, size -> toSize(size) }
  }

  Optional<Size> getSize(String slug) {
    Optional.ofNullable(sizes[slug])
  }

  boolean hasSize(String slug) {
    getSize(slug).present
  }

  private static Size toSize(args) {
    new Size(
        slug: args.slug,
        memory: args.memory,
        cpus: args.vcpus,
        diskSize: args.disk,
        bandwidth: args.transfer,
        pricePerMonth: newMonetaryAmount(args.price_monthly),
        pricePerHour: newMonetaryAmount(args.price_hourly),
        regions: args.regions
    )
  }

  private static MonetaryAmount newMonetaryAmount(data) {
    new MonetaryAmount(new BigDecimal(data.toString()), DEF_CURRENCY)
  }
}
