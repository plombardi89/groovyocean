package us.plombardi.groovyocean.core

import us.plombardi.groovyocean.model.Droplet


class DropletsClient extends ResourceClient {

  DropletsClient(DigitalOceanClient doClient, ActionsClient actionsClient) {
    super(doClient)
  }

  Optional<Droplet.ActiveDroplet> getDroplet(String id) {

  }

  void deleteDroplet(String id) {

  }
}
