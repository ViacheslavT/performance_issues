package event.service.controller;

import event.service.api.EventService;
import event.service.domain.Event;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 05/24/2019.
 */
@RestController
@RequestMapping("v1.0/event-service")
@Api(
        value = "event-service",
        description = "Service to provide CRUD for events"
)
public class EventServiceController {

    private EventService eventService;

    @Autowired
    public EventServiceController(@Qualifier("h2EventServiceImpl") final EventService eventService) {
        this.eventService = eventService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @ModelAttribute
    LocalDateTime initLocalDateTime() {
        return LocalDateTime.now();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = List.class,
            produces = "application/json",
            value = "Returns a list of all events."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events received", response = Event.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "No Events in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseBody
    public ResponseEntity<?> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        ResponseEntity entity;
        if (events != null && !events.isEmpty()) {
            List<Object> resp = new ArrayList<>(2);
            resp.add(events);
            entity = ResponseEntity.ok(resp);
        } else {
            entity = ResponseEntity.noContent().build();
        }
        return entity;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = Event.class,
            produces = "application/json",
            value = "Creates a new user and returns it."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event created", response = Event.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseBody
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        Event created = eventService.createEvent(event);
        ResponseEntity entity;
        if (created != null) {
            entity = ResponseEntity.ok(created);
        } else {
            entity = ResponseEntity.noContent().build();
        }
        return entity;
    }

    @GetMapping(value = "/{eventId}/event", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = Event.class,
            produces = "application/json",
            value = "Retrieves an event by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event received", response = Event.class),
            @ApiResponse(code = 204, message = "No Event in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> getEvent(@PathVariable final Long eventId) {
        Event event = eventService.getEvent(eventId);
        ResponseEntity entity;
        if (event != null) {
            entity = ResponseEntity.ok(event);
        } else {
            entity = ResponseEntity.noContent().build();
        }
        return entity;
    }

    @PutMapping(value = "/{eventId}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = Event.class,
            produces = "application/json",
            value = "Retrieves updated event."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event updated", response = Event.class),
            @ApiResponse(code = 204, message = "No Event in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> updateEvent(@PathVariable final Long eventId, final Event event) {
        Event updated = eventService.updateEvent(eventId, event);
        ResponseEntity entity;
        if (updated != null) {
            entity = ResponseEntity.ok(updated);
        } else {
            entity = ResponseEntity.noContent().build();
        }
        return entity;
    }

    @PostMapping(value = "/{eventId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = Event.class,
            produces = "application/json",
            value = "Retrieves deleted event."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event deleted.", response = Event.class),
            @ApiResponse(code = 204, message = "No Event in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> deleteEvent(@PathVariable final Long eventId) {
        Event event = eventService.deleteEvent(eventId);
        ResponseEntity entity;
        if (event != null) {
            entity = ResponseEntity.ok(event);
        } else {
            entity = ResponseEntity.noContent().build();
        }
        return entity;
    }

    @PostMapping(value="/deleteAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            produces = "application/json",
            value = "Returns operation information."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events deleted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> deleteAllEvents() {
        try {
            eventService.deleteAllEvents();
            return ResponseEntity.ok(Collections.singletonMap("message", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(value = "/{title}/title", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = Event.class,
            produces = "application/json",
            value = "Retrieves all events by their title."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events retrieved", response = Event.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "No Events in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> getAllEventsByTitle(@PathVariable final String title) {
        List<Event> events = eventService.getAllEventsByTitle(title);
        ResponseEntity entity;
        if (events != null && !events.isEmpty()) {
            List<Object> resp = new ArrayList<>(2);
            resp.add(events);
            entity = ResponseEntity.ok(resp);
        } else {
            entity = ResponseEntity.noContent().build();
        }
        return entity;
    }
}
