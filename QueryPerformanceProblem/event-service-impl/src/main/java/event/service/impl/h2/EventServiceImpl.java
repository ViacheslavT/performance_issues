package event.service.impl.h2;

import event.service.api.EventService;
import event.service.domain.Event;
import event.service.repository.h2.H2EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 03/17/2019.
 */
@Service("h2EventServiceImpl")
public class EventServiceImpl implements EventService {

    /**
     * Repository of data storage.
     */
    private final H2EventRepository repository;

    @Autowired
    public EventServiceImpl(final H2EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event createEvent(Event event) {
        return fromH2Event(repository.save(toH2Event(event)));
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Optional<event.service.dto.h2.Event> eventInst = repository.findById(id);
        Event result = null;
        if (eventInst.isPresent()) {
            event.setEventId(eventInst.get().getEventId());
            result = fromH2Event(repository.save(toH2Event(event)));
        }
        return result;
    }

    @Override
    public Event getEvent(Long id) {
        return fromH2Event(repository.findById(id).orElse(null));
    }

    @Override
    public Event deleteEvent(Long id) {
        Optional<event.service.dto.h2.Event> event = repository.findById(id);
        Event result = null;
        if (event.isPresent()) {
            result = fromH2Event(event.get());
            repository.deleteById(id);
        }
        return result;
    }

    @Override
    public void deleteAllEvents() {
        repository.deleteAll();
    }

    @Override
    public List<Event> getAllEvents() {
        List<event.service.dto.h2.Event> eventList = (List<event.service.dto.h2.Event>) repository.findAll();
        if (eventList != null) {
            return eventList.stream().map(this::fromH2Event).collect(Collectors.toList())
;       } else {
            return null;
        }
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        List<event.service.dto.h2.Event> eventList = repository.findByTitle(title);
        if (eventList != null) {
            return eventList.stream().map(this::fromH2Event).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
