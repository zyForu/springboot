package zyforu.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import zyforu.cache.service.CoffeeService;

/**
 * @author zy
 * @date 2023/3/6 14:41
 */

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
@EnableCaching(proxyTargetClass=true)
public class Application  implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Count: {}", coffeeService.findAll().size());
        for (int i = 0; i < 10; i++) {
            log.info("Reading from cache.");
            coffeeService.findAll();
        }
        coffeeService.reloadCoffee();
        log.info("Reading after refresh.");
        coffeeService.findAll().forEach(c -> log.info("Coffee {}", c.getName()));
    }
}
