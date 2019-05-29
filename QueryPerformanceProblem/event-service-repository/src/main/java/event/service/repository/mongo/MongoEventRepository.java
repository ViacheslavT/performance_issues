package event.service.repository.mongo;

import event.service.dto.mongo.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 04/24/2019.
 */
@Repository
public interface MongoEventRepository extends MongoRepository<Event, String>{

    List<Event> findByTitle(String title);
}
