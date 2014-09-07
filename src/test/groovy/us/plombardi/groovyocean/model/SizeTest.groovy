package us.plombardi.groovyocean.model

import org.junit.Test

import static org.junit.Assert.*


class SizeTest {

  @Test
  void canCreate() {
    def size = new Size(
        '512mb',
        512, 1, 20, 2,
        new MonetaryAmount(5.0, Currency.getInstance('USD')),
        new MonetaryAmount(0.0074, Currency.getInstance('USD')),
        ['nyc1', 'br1', 'sfo1', 'ams4'])

    assertEquals('512mb', size.slug)
    assertEquals(512, size.memory)
    assertEquals(1, size.cpus)
    assertEquals(20, size.diskSize)
    assertEquals(2, size.bandwidth)
    assertEquals(new MonetaryAmount(5.0, Currency.getInstance('USD')), size.pricePerMonth)
    assertEquals(new MonetaryAmount(0.0074, Currency.getInstance('USD')), size.pricePerHour)
    assertEquals(['nyc1', 'br1', 'sfo1', 'ams4'], size.regions)
  }
}
