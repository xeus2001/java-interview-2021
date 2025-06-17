package com.here.wikvaya.java.interview;

public interface Server {
  /**
   * BONUS: Read the resource file 'service.cfg' and return the config from it.
   *
   * @return the service configuration.
   */
  default Config config() {
    return new Config();
  }
}
