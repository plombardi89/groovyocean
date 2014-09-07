package us.plombardi.groovyocean.model


public enum ResourceType {

  ACTION, BACKEND, DOMAIN_RECORD, DOMAIN, DROPLET_ACTION, DROPLET, IMAGE_ACTION, IMAGE, KEY, REGION, SIZE

  static ResourceType fromString(String alias) {
    switch(alias?.toLowerCase(Locale.ENGLISH)) {
      case 'action'         : return ACTION
      case 'backend'        : return BACKEND
      case 'domain-record'  : return DOMAIN_RECORD
      case 'domain'         : return DOMAIN
      case 'droplet'        : return DROPLET
      case 'droplet-action' : return DROPLET_ACTION
      case 'image'          : return IMAGE
      case 'image-action'   : return IMAGE_ACTION
      case 'key'            : return KEY
      case 'region'         : return REGION
      case 'size'           : return SIZE
      default               : throw new IllegalArgumentException("Action.Status with alias '${alias}' not found")
    }
  }
}