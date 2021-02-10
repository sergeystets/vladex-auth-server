package com.vladex.authserver.config;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ServerConfiguration {

  // TCP port for remote connections, default 9092
  @Value("${h2.tcp.port:9092}")
  private String h2TcpPort;

  /**
   * TCP connection to connect with SQL clients to the embedded h2 database.
   *
   * Connect to "jdbc:h2:tcp://localhost:9092/mem:vladex_db"
   */
  @Bean
  @ConditionalOnExpression("${h2.tcp.enabled:false}")
  public Server h2TcpServer() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
  }
}
