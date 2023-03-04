package zyforu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zyforu.data.model.Order;

/**
 * @author zy
 * @date 2023/3/4 14:54
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
