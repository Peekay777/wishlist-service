package com.koutsios.wishlistservice;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractIntegrationTestWithMongoDb {

  private static final MongodStarter starter = MongodStarter.getDefaultInstance();
  private static MongodExecutable mongodExecutable;
  private static MongodProcess mongod;

  @BeforeAll
  static void setUpClass() throws IOException {
    mongodExecutable = starter.prepare(new MongodConfigBuilder()
        .version(Version.Main.V4_0)
        .net(new Net("localhost", 27010, Network.localhostIsIPv6()))
        .build());
    mongod = mongodExecutable.start();
  }

  @AfterAll
  static void tearDown() {
    mongod.stop();
    mongodExecutable.stop();
  }

}
