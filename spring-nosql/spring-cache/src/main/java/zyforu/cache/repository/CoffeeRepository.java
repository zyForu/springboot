package zyforu.cache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zyforu.cache.data.Coffee;

/**
 * @author zy
 * @date 2023/3/6 15:40
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
