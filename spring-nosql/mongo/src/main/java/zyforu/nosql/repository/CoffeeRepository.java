package zyforu.nosql.repository;

import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import zyforu.nosql.model.Coffee;

import java.util.List;

/**
 * @author zy
 * @date 2023/3/3 16:35
 */

public interface CoffeeRepository extends MongoRepository<Coffee, String> {
    List<Coffee> findByName(String name);
}
