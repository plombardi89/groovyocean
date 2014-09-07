package us.plombardi.groovyocean.core

import com.squareup.okhttp.ResponseBody
import groovy.json.JsonParserType
import groovy.json.JsonSlurper


class ResourceClient {

  protected final static JsonSlurper JSON = new JsonSlurper().setType(JsonParserType.INDEX_OVERLAY)

  protected final DigitalOceanClient doClient
  protected final ActionsClient actionsClient

  ResourceClient(DigitalOceanClient doClient, ActionsClient actionsClient) {
    if (!doClient) {
      throw new IllegalArgumentException("doClient:${DigitalOceanClient} cannot be null")
    }

    this.doClient = doClient
    this.actionsClient = actionsClient
  }

  protected static Map mapify(ResponseBody body) {
    JSON.parse(body.byteStream(), body.contentType().charset().toString()) as Map
  }
}
