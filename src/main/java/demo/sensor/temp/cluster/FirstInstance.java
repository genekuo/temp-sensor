package demo.sensor.temp.cluster;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstInstance {

  private static final Logger logger = LoggerFactory.getLogger(FirstInstance.class);

  public static void main(String[] args) {
    ClusterManager mgr = new InfinispanClusterManager();

    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    //Vertx.clusteredVertx(new VertxOptions(), ar -> {
    Vertx.clusteredVertx(options, ar -> {
      if (ar.succeeded()) {
        logger.info("First instance has been started");
        Vertx vertx = ar.result();
        vertx.deployVerticle("demo.sensor.temp.HeatSensor", new DeploymentOptions().setInstances(4));
        vertx.deployVerticle("demo.sensor.temp.HttpServer");
      } else {
        logger.error("Could not start", ar.cause());
      }
    });
  }
}
