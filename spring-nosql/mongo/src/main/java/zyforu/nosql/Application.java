package zyforu.nosql;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import zyforu.nosql.convert.MoneyConverter;
import zyforu.nosql.model.Coffee;
import zyforu.nosql.repository.CoffeeRepository;

import org.springframework.data.mongodb.core.query.Criteria;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zy
 * @date 2023/3/3 15:53
 */
@EnableMongoRepositories
@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {


    @Autowired
    private CoffeeRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyConverter()));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee latte = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30))
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        repository.save(latte);

        Coffee cappuccino = Coffee.builder().name("capuccino")
                .price(Money.of(CurrencyUnit.of("CNY"), 50))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        repository.save(cappuccino);

        List<Coffee> coffees = repository.findAll();
        log.info("Coffees:{}", coffees);


        List<Coffee> coffees1 = repository.findByName("latte");
        log.info("latte:{}", coffees1);


        List<Coffee> list = mongoTemplate.find(Query.query(Criteria.where("name").is("capuccino")), Coffee.class);

        log.info("result:{}", list);


        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("capuccino")), new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30)).currentDate("updateTime"), Coffee.class );


        List<Coffee> list1 = mongoTemplate.find(Query.query(Criteria.where("name").is("capuccino")), Coffee.class);

        log.info("result:{}", list1);

        repository.deleteAll();
    }
}
