package us.plombardi.groovyocean.model


class Droplet {
  final String id
  final String name
  final long memory
  final long cpus
  final long diskSize
  final Region region

  static class ActiveDroplet implements Serializable {

    final static serialVersionUID = 20140830L

    void delete() {

    }

    Action reboot() {

    }

    Action powerCycle() {

    }

    Action shutdown() {

    }

    Action powerOff() {

    }

    Action powerOn() {

    }

    Action resetPassword() {

    }

    Action resize(String sizeSlug) {

    }

    Action resize(Size size) {

    }

    Action rebuild(String imageIdOrSlug) {

    }

    Action rebuild(Image image) {

    }

    Action rename(String newName) {

    }

    Action replaceKernel(String kernelId) {

    }

    Action replaceKernel(Kernel kernel) {

    }

    Action enableIPv6() {

    }

    Action disableBackups() {

    }

    Action enablePrivateNetworking() {

    }

    Action snapshot() {

    }
  }
}
