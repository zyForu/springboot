package zyforu.data;

import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import zyforu.data.converter.BytesToMoneyConverter;
import zyforu.data.converter.MoneyToBytesConverter;
import zyforu.data.model.Coffee;
import zyforu.data.service.CoffeeService;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author zy
 * @date 2023/3/4 14:30
 */
@SpringBootApplication
@EnableJpaRepositories
@Slf4j
@EnableTransactionManagement
public class Application implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Coffee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer() {
        return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
    }

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));

    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Coffee> c = coffeeService.findOneCoffee("mocha");
        log.info("Coffee:{}", c);
        for(int i = 0; i < 5; i++) {
            c = coffeeService.findOneCoffee("mocha");
        }

        log.info("Value from Redis:{}", c);*/

        Optional<Coffee> c = coffeeService.findCoffeeFromCache("mocha");
        log.info("Coffee:{}", c);

        for (int i = 0; i < 5; i++) {
            c = coffeeService.findCoffeeFromCache("mocha");
        }
        log.info("Value from Redis:{}", c);
    }
}
