package event.service.dto.mongo;

import event.service.dto.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 03/17/2019.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "event")
public class Event {

    @EqualsAndHashCode.Exclude
    @Id
    private String eventId;

    private String title;

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
