package us.plombardi.groovyocean.model

import org.junit.Test

import static org.junit.Assert.*;


class MonetaryAmountTest {

  @Test
  void canCreate() {
    def money = new MonetaryAmount(15.0, Currency.getInstance('USD'))
    assertEquals('$15.0 USD', money.toString())
  }
}
