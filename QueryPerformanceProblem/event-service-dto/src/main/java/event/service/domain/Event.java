package event.service.domain;

import event.service.domain.EventType;
import event.service.domain.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 03/17/2019.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Event {

    private Long eventId;

    private String title;

    private Address address;

    private EventType eventType;

    private LocalDateTime dateTime;

    public Event(String title, Address address, EventType eventType, LocalDateTime dateTime) {
        this.title = title;
        this.address = address;
        this.eventType = eventType;
        this.dateTime = dateTime;
    }
}
