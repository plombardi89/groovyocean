package us.plombardi.groovyocean.model

import groovy.transform.Immutable


@Immutable(knownImmutableClasses = [Currency])
class MonetaryAmount implements Serializable {

  final static serialVersionUID = 20140830L

  final BigDecimal value
  final Currency currency

  String toString() {
    "${currency.symbol}${value} ${currency.currencyCode}"
  }
}
