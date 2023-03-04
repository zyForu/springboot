package zyforu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zyforu.data.model.Coffee;

/**
 * @author zy
 * @date 2023/3/4 14:52
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
