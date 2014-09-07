package us.plombardi.groovyocean.model

import org.junit.Test

import static org.junit.Assert.*


class RegionTest {

  @Test
  void canCreate() {
    def reg = new Region('spg1', 'Singapore 1', ['512mb', '1024mb'], ['ipv6', 'virtio', 'private_networking'], true)

    assertEquals('spg1', reg.slug)
    assertEquals('Singapore 1', reg.name)
    assertTrue(reg.available)
    assertEquals(['512mb', '1024mb'], reg.sizes)
    assertEquals(['ipv6', 'virtio', 'private_networking'], reg.features)
  }
}
