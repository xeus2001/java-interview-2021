package com.here.wikvaya.java.interview;

public class Config {

  public Config() {
    this.min = 10;
    this.max = 1_000_000_000;
    this.ascending = true;
    this.port = 8080;
    this.lanOnly = false;
  }

  public Config(int min, int max, boolean ascending, int port, boolean lanOnly) {
    this.min = min;
    this.max = max;
    this.ascending = ascending;
    this.port = port;
    this.lanOnly = lanOnly;
  }

  final int min;
  final int max;
  final boolean ascending;
  final int port;
  final boolean lanOnly;

  public int min() {
    return ascending ? min : max;
  }

  public int max() {
    return ascending ? max : min;
  }

  public int port() {
    return port;
  }

  public boolean lanOnly() {
    return lanOnly;
  }
}
