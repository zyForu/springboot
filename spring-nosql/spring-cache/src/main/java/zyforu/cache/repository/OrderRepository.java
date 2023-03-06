package zyforu.cache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zyforu.cache.data.Order;

/**
 * @author zy
 * @date 2023/3/6 15:41
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
