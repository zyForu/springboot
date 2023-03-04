package zyforu.data.repository;

import org.springframework.data.repository.CrudRepository;
import zyforu.data.model.CoffeeCache;

import java.util.Optional;

/**
 * @author zy
 * @date 2023/3/4 16:54
 */
public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {
    Optional<CoffeeCache> findOneByName(String name);
}
