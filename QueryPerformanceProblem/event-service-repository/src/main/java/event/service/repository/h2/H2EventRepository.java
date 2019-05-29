package event.service.repository.h2;

import event.service.dto.h2.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 03/17/2019.
 */
@Repository
public interface H2EventRepository extends CrudRepository<Event, Long> {

    List<Event> findByTitle(String title);
}
