package zyforu.data.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zyforu.data.model.Coffee;
import zyforu.data.model.CoffeeCache;
import zyforu.data.repository.CoffeeCacheRepository;
import zyforu.data.repository.CoffeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * @author zy
 * @date 2023/3/4 14:57
 */

@Service
@Slf4j
public class CoffeeService {
    private static final String CACHE = "springbucks-coffee";

    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;
    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAll() {
        return coffeeRepository.findAll();
    }


    public Optional<Coffee> findOneCoffee(String name) {
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if(redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, name)) {
            log.info("Get coffee{} from Redis.", name);
            return  Optional.of(hashOperations.get(CACHE, name));
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found:{}", coffee);
        if(coffee.isPresent()) {
            log.info("Put coffee {} to Redis.", name);
            hashOperations.put(CACHE, name, coffee.get());
            redisTemplate.expire(CACHE, 3, TimeUnit.MINUTES);
        }
        return  coffee;
    }


    public Optional<Coffee> findCoffeeFromCache(String name) {
        Optional<CoffeeCache> cached = coffeeCacheRepository.findOneByName(name);
        if(cached.isPresent()) {
            CoffeeCache coffeeCache = cached.get();
            Coffee coffee = Coffee.builder().name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
            coffee.setId(coffeeCache.getId());
            log.info("Coffee {} found in cache.", coffeeCache);
            return Optional.of(coffee);
        }else {
            Optional<Coffee> raw = findCoffeeFromDb(name);
            raw.ifPresent(c -> {
                CoffeeCache build = CoffeeCache.builder().id(c.getId())
                        .name(c.getName())
                        .price(c.getPrice())
                        .build();
                log.info("Save Coffee{} to cache.", build);
                coffeeCacheRepository.save(build);
            });
            return raw;
        }
    }

    public Optional<Coffee> findCoffeeFromDb(String name) {
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), ExampleMatcher.matching().withMatcher("name", exact().ignoreCase())));

        log.info("Coffee Found: {}", coffee);
        return coffee;
    }


}
