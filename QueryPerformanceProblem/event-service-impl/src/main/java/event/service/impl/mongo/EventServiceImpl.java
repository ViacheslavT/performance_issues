package event.service.impl.mongo;

import event.service.api.EventService;
import event.service.domain.Event;
import event.service.repository.mongo.MongoEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 05/24/2019.
 */
@Service("mongoEventServiceImpl")
public class EventServiceImpl implements EventService {

    /**
     * Repository of data storage.
     */
    private final MongoEventRepository repository;

    @Autowired
    public EventServiceImpl(final MongoEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event createEvent(Event event) {
        return fromMongoEvent(repository.save(toMongoEvent(event)));
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Optional<event.service.dto.mongo.Event> eventInst = repository.findById(String.valueOf(id));
        Event result = null;
        if (eventInst.isPresent()) {
            event.setEventId(Long.valueOf(eventInst.get().getEventId()));
            result = fromMongoEvent(repository.save(toMongoEvent(event)));
        }
        return result;
    }

    @Override
    public Event getEvent(Long id) {
        return fromMongoEvent(repository.findById(String.valueOf(id)).orElse(null));
    }

    @Override
    public Event deleteEvent(Long id) {
        Optional<event.service.dto.mongo.Event> event = repository.findById(String.valueOf(id));
        Event result = null;
        if (event.isPresent()) {
            result = fromMongoEvent(event.get());
            repository.deleteById(String.valueOf(id));
        }
        return result;
    }

    @Override
    public void deleteAllEvents() {
        repository.deleteAll();
    }

    @Override
    public List<Event> getAllEvents() {
        List<event.service.dto.mongo.Event> eventList = repository.findAll();
        if (eventList != null) {
            return eventList.stream().map(this::fromMongoEvent).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        List<event.service.dto.mongo.Event> eventList = repository.findByTitle(title);
        if (eventList != null) {
            return eventList.stream().map(this::fromMongoEvent).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
