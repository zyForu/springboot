package zyforu.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import zyforu.demo.test.AProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zy
 * @date 2023/2/18 13:38
 */
@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(AProperties.class)
public class MyApplication implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcProperties jdbcProperties;

    @Autowired
    private AProperties aProperties;

    /*@Autowired
    @Qualifier("secondJdbcTemplate")
    private JdbcTemplate secondJdbcTemplate;*/

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class);
    }


    @Bean
    @Primary
    @ConfigurationProperties("first.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource firstDataSource() {
        DataSourceProperties dataSourceProperties = firstDataSourceProperties();
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        log.info("first dataSource:{},value:{}", dataSourceProperties.getUrl(), dataSource);
        return dataSource;
    }


    @Bean
    @ConfigurationProperties("second.datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondDataSource() {
        DataSourceProperties dataSourceProperties = secondDataSourceProperties();
        DataSource datasource = dataSourceProperties.initializeDataSourceBuilder().build();
        log.info("second dataSource:{},value:{}", dataSourceProperties.getUrl(), datasource);
        return datasource;
    }



    public void showdataSource() throws SQLException {
        log.info("dataSource:{}", dataSource);
        Connection connection = dataSource.getConnection();
        log.info("connection:{}", connection);
    }

    /*public void showJdbcTemplate() {
        log.info("jdbcTemplate:{}", jdbcTemplate);
        log.info("jdbcTemplate2:{}", secondJdbcTemplate);
    }*/


    public void selectBySecondDataSource() throws SQLException {
        Connection connection = secondDataSource.getConnection();
        log.info("connection2:{}", connection);
        ResultSet resultSet = connection.prepareStatement("select * from game").executeQuery();
        while(resultSet.next()) {
            log.info("id:{},name:{}", resultSet.getInt(1), resultSet.getString(2));
        }
    }

    @Override
    public void run(String... args) throws Exception {
        showdataSource();
        log.info("select for book:{}", jdbcTemplate.queryForList("select * from book"));
        log.info(aProperties.toString());
       //selectBySecondDataSource();
    }
}
