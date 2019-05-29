package event.service.helper;

import event.service.api.EventService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class EventServiceImplsHolder {

    @Autowired
    private Map<String, EventService> implementations;

    public EventService get(String impl) {
        return this.implementations.get(impl);
    }

    public List<String> getImpls() {
        List<String> list = new ArrayList<>();
        implementations.forEach((k, v) -> list.add(k));
        return list;
    }
}
