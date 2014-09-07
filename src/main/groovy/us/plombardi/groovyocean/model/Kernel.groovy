package us.plombardi.groovyocean.model

import groovy.transform.Immutable
import groovy.transform.ToString


@ToString
@Immutable
class Kernel implements Serializable {

  final static serialVersionUID = 20140830L

  String id
  String name
  String version
}
