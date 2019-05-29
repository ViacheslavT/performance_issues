package event.service.dto.h2;

import event.service.dto.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 03/17/2019.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "event")
public class Event {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    private String title;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDateTime dateTime;

    public Event(String title, Address address, EventType eventType, LocalDateTime dateTime) {
        this.title = title;
        this.address = address;
        this.eventType = eventType;
        this.dateTime = dateTime;
    }

    public void setEventType(String eventType) {
        this.eventType = EventType.valueOf(eventType);
    }
}
