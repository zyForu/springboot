package zyforu.cache.data;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author zy
 * @date 2023/3/6 14:47
 */
@Entity
@Table(name = "t_order")
@Builder
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    private String customer;

    @JoinTable(name = "t_order_coffee")
    @ManyToMany
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}
