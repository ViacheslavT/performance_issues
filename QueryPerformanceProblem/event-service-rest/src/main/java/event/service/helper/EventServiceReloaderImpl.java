package event.service.helper;

import event.service.api.EventService;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EventServiceReloaderImpl implements EventServiceReloader {

    private EventService eventService;

    @Override
    public void changeImplementation(EventService impl) {
        this.eventService = impl;
    }
}
