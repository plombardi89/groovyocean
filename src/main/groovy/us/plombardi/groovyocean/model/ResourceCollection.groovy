package us.plombardi.groovyocean.model


abstract class ResourceCollection {

  final meta
  final links

  ResourceCollection(meta, links = null) {
    if (!meta) { throw new IllegalArgumentException("meta cannot be null") }

    this.meta = meta
  }

  int getSize() {
    (meta['total'] ?: 0) as int
  }

  boolean isFirstPage() {
    links?.pages?.first == null
  }

  boolean isLastPage() {
    links?.pages?.last == null
  }

  boolean hasNextPage() {
    links?.pages?.next != null
  }

  boolean hasPreviousPage() {
    links?.pages?.prev != null
  }
}
