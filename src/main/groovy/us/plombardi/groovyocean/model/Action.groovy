package us.plombardi.groovyocean.model

import groovy.transform.TupleConstructor
import us.plombardi.groovyocean.core.ActionsClient

import java.time.Instant


@TupleConstructor
class Action {

  private final ActionsClient client

  final String id
  final Status status
  final String region
  final Instant startTime
  private final Instant completionTime
  final String resourceId
  final ResourceType resourceType

  Optional<Instant> getCompletionTime() {
    Optional.ofNullable(completionTime)
  }

  Action refresh() {
    def action = client.getAction(id)
    if (action.present) {
      action.get()
    } else {
      throw new RuntimeException("Failed to refresh") // todo: there has to be a better way to handle this?
    }
  }

  enum Status {

    /**
     * Indicates an Action is still being processed and has not completed yet.
     */
    IN_PROGRESS,

    /**
     * Indicates an Action has completed successfully.
     */
    COMPLETED,

    /**
     * Indicates an Action did not complete successfully.
     */
    ERRORED,

    /**
     * Indicates an Action is in an Unknown state.
     */
    UNKNOWN;

    static Status fromString(String alias) {
      switch(alias?.toLowerCase(Locale.ENGLISH)) {
        case 'in-progress': return IN_PROGRESS
        case 'completed': return COMPLETED
        case 'errored': return ERRORED
        default: return UNKNOWN
      }
    }
  }
}
