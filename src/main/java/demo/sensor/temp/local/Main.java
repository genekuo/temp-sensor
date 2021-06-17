package demo.sensor.temp.local;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle("demo.sensor.temp.HeatSensor", new DeploymentOptions().setInstances(4));
    vertx.deployVerticle("demo.sensor.temp.Listener");
    vertx.deployVerticle("demo.sensor.temp.SensorData");
    vertx.deployVerticle("demo.sensor.temp.HttpServer");
  }
}
