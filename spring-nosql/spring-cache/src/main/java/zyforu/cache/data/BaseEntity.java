package zyforu.cache.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zy
 * @date 2023/3/6 14:47
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    @Id
    private Long id;
    private Date createTime;
    private Date updateTime;

}
