package io.github.yellowfantt.libaryApi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class DatabaseConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return  dataSource;
    };

    // esse é o melhor banco pra trabalhar em produção
    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setDriverClassName(driverClassName);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10); // maximo de conex~~oes liberadas
        config.setMinimumIdle(1); // minimo de conexões que já vai começar então aqui eu to do dizendo que terei de 1- 10
        config.setPoolName("HikariPool");
     // o tempo que vai permanecer abera a conexão, a conexão nesse caso dura 10m e depois morre e vai ser criada uma nova
        config.setConnectionTimeout(100000); // é o tempo que ele vai usar pra obter uma nova
        config.setConnectionTestQuery("SELECT 1"); //query de teste
        return  new HikariDataSource(config);
    }
}
