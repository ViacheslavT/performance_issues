package event.service.api;

import event.service.domain.Event;
import event.service.domain.EventType;
import event.service.domain.Address;

import java.util.List;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 05/24/2019.
 */
public interface EventService {

    /**
     * Creates new {@link Event} in data storage
     *
     * @param event to be created
     * @return newly added {@link Event} instance with id field generated
     */
    Event createEvent(Event event);

    /**
     * Updates event entity in data storage.
     *
     * @param id to find exact {@link Event} in data storage.
     * @param event instance with new fields to update.
     * @return updated {@link Event} if appropriate entity was found in storage by id, <i>null</i> otherwise.
     */
    Event updateEvent(final Long id, final Event event);

    /**
     * Finds {@link Event} by id.
     *
     * @param id of {@link Event} entity in data storage.
     * @return {@link Event} instance if entity was found by id, <i>null</i> otherwise.
     */
    Event getEvent(final Long id);

    /**
     * Deletes {@link Event} entity in data storage.
     *
     * @param id of {@link Event} entity to be deleted.
     * @return deleted {@link Event} instance if entity was found by id, <i>null</i> otherwise.
     */
    Event deleteEvent(Long id);

    /**
     * Deletes all events in data storage.
     */
    void deleteAllEvents();

    /**
     * Retrieves {@link List} of all {@link Event} from data storage.
     *
     * @return a {@link List} of {@link Event} or <i>empty</i>.
     */
    List<Event> getAllEvents();

    /**
     * Retrieves {@link List} of all {@link Event} from data storage by their title.
     *
     * @param title to find {@link Event} entities.
     * @return a {@link List} of {@link Event} or <i>empty</i>.
     */
    List<Event> getAllEventsByTitle(final String title);


    default event.service.dto.h2.Event toH2Event(Event event) {
        event.service.dto.h2.Event h2Event = new event.service.dto.h2.Event();
        if (event != null) {
            h2Event.setEventId(event.getEventId());
            if (event.getAddress() != null) {
                event.service.dto.h2.Address address = new event.service.dto.h2.Address();
                address.setCity(event.getAddress().getCity());
                address.setCountry(event.getAddress().getCountry());
                address.setStreet(event.getAddress().getStreet());
                h2Event.setAddress(address);
            }
            h2Event.setDateTime(event.getDateTime());
            if (event.getEventType() != null) {
                h2Event.setEventType(event.getEventType().toString());
            }
            h2Event.setTitle(event.getTitle());
        } else {
            return null;
        }
        return h2Event;
    }

    default event.service.dto.mongo.Event toMongoEvent(Event event) {
        event.service.dto.mongo.Event mongoEvent = new event.service.dto.mongo.Event();
        if (event != null) {
            mongoEvent.setEventId(String.valueOf(event.getEventId()));
            if (event.getAddress() != null) {
                event.service.dto.mongo.Address address = new event.service.dto.mongo.Address();
                address.setCity(event.getAddress().getCity());
                address.setCountry(event.getAddress().getCountry());
                address.setStreet(event.getAddress().getStreet());
                mongoEvent.setAddress(address);
            }
            mongoEvent.setDateTime(event.getDateTime());
            if (event.getEventType() != null) {
                mongoEvent.setEventType(event.getEventType().toString());
            }
            mongoEvent.setTitle(event.getTitle());
        } else {
            return null;
        }
        return mongoEvent;
    }

    default Event fromH2Event(event.service.dto.h2.Event h2Event) {
        Event event = new Event();
        if (h2Event != null) {
            event.setEventId(h2Event.getEventId());
            if (event.getAddress() != null) {
                Address address = new Address();
                address.setCity(h2Event.getAddress().getCity());
                address.setCountry(h2Event.getAddress().getCountry());
                address.setStreet(h2Event.getAddress().getStreet());
                event.setAddress(address);
            }
            event.setDateTime(event.getDateTime());
            if (h2Event.getEventType() != null) {
                event.setEventType(EventType.valueOf(h2Event.getEventType().toString()));
            }
            event.setTitle(h2Event.getTitle());
        } else {
            return null;
        }
        return event;
    }

    default Event fromMongoEvent(event.service.dto.mongo.Event mongoEvent) {
        Event event = new Event();
        if (mongoEvent != null) {
            try {
                event.setEventId(Long.valueOf(mongoEvent.getEventId()));
            } catch (NumberFormatException e) {
            }
            if (event.getAddress() != null) {
                Address address = new Address();
                address.setCity(mongoEvent.getAddress().getCity());
                address.setCountry(mongoEvent.getAddress().getCountry());
                address.setStreet(mongoEvent.getAddress().getStreet());
                event.setAddress(address);
            }
            event.setDateTime(event.getDateTime());
            if (mongoEvent.getEventType() != null) {
                event.setEventType(EventType.valueOf(mongoEvent.getEventType().toString()));
            }
            event.setTitle(mongoEvent.getTitle());
        } else {
            return null;
        }
        return event;
    }
}
