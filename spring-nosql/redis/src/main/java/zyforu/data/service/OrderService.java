package zyforu.data.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zyforu.data.model.Coffee;
import zyforu.data.model.Order;
import zyforu.data.model.OrderState;
import zyforu.data.repository.OrderRepository;

import java.util.Arrays;

/**
 * @author zy
 * @date 2023/3/4 15:17
 */
@Service
@Slf4j
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(String customer, Coffee...coffees) {
        Order order = Order.builder().customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT).build();

        Order save = orderRepository.save(order);
        log.info("New order:{}", save);
        return save;
    }

    public boolean updateState(Order order, OrderState state) {
        if(state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order:{}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        Order saved = orderRepository.save(order);
        log.info("Updated Order:{}", saved);
        return true;
    }
}
