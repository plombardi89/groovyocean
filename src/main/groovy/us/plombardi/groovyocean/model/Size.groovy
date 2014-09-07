package us.plombardi.groovyocean.model

import groovy.transform.Immutable

/**
 * Represents a size as specified by the DigitalOcean Sizes API
 *
 * @author plombardi
 */


@Immutable
class Size implements Serializable {

  final static serialVersionUID = 20140830L

  String slug
  long memory
  long cpus
  long diskSize
  long bandwidth
  MonetaryAmount pricePerMonth
  MonetaryAmount pricePerHour
  List<String> regions
}
