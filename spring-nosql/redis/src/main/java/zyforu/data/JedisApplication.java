/*
jedis依赖与starter-redis冲突
 */


/*
package zyforu.data;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zyforu.data.service.CoffeeService;

import java.util.Map;



@SpringBootApplication
@EnableJpaRepositories
@Slf4j
@EnableTransactionManagement

public class JedisApplication implements ApplicationRunner {

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private CoffeeService coffeeService;

    public static void main(String[] args) {
        SpringApplication.run(JedisApplication.class);
    }

    @ConfigurationProperties("redis")
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host) {
        return new JedisPool(jedisPoolConfig(), host);
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(jedisPoolConfig.toString());

        try (Jedis jedis = jedisPool.getResource()) {
            coffeeService.findAllCoffee().forEach(c -> {
                jedis.hset("springbucks-menu",
                        c.getName(),
                        Long.toString(c.getPrice().getAmountMinorLong()));
            });

            Map<String, String> menu = jedis.hgetAll("springbucks-menu");
            log.info("Menu: {}", menu);

            String price = jedis.hget("springbucks-menu", "espresso");
            log.info("espresso - {}",
                    Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
        }
    }
}
*/
