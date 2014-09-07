package us.plombardi.groovyocean.model

import groovy.transform.Immutable

/**
 * Represents a region as specified by the DigitalOcean RegionsCollection API
 *
 * @author plombardi
 */


@Immutable
class Region implements Serializable {

  final static serialVersionUID = 20140830L

  String slug
  String name
  List<String> sizes
  List<String> features
  boolean available

  String toString() {
    "$Region(slug=$slug, name=$name, sizes=$sizes, features=$features, available=$available)"
  }
}
