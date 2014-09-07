package us.plombardi.groovyocean.core

import us.plombardi.groovyocean.model.Action
import us.plombardi.groovyocean.model.ResourceType

import java.time.Instant


class ActionsClient extends ResourceClient {

  private final static String RESOURCE_PATH = '/actions'

  ActionsClient(DigitalOceanClient doClient) {
    super(doClient)
  }

  private toAction(args) {
    new Action(
        client: this,
        id: args.id,
        status: Action.Status.fromString(args.status?.toString()),
        type: args.type,
        startTime: Instant.parse(args.action.started_at.toString()),
        completionTime: args.action.completed_at ? Instant.parse(args.action.completed_at.toString()) : null,
        resourceId: args.action.resource_id,
        resourceType: ResourceType.fromString(args.action.resource_type.toString()),
        region: args.action.region
    )
  }

  Optional<Action> getAction(String id) {
    def resp = doClient.get("${RESOURCE_PATH}/${id}")
    if (resp.successful) {
      def content = mapify(resp.body())
      Optional.of(toAction(content.action))
    } else {
      Optional.empty()
    }
  }
}
