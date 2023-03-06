package zyforu.cache.data;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zy
 * @date 2023/3/6 14:47
 */
@Entity
@Table(name = "t_coffee")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Coffee extends BaseEntity implements Serializable {
    private String name;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;

}
