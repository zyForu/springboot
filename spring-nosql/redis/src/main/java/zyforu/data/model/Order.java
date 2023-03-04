package zyforu.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @author zy
 * @date 2023/3/4 14:40
 */
@Entity
@Table(name = "T_ORDER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Order extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String customer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFE")
    @OrderBy("id")
    private List<Coffee> items;
    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}
