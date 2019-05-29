package event.service.helper;

import event.service.api.EventService;

public interface EventServiceReloader {

    void changeImplementation(EventService impl);
}
