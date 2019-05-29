package event.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventServiceImplUpdater {

    @Autowired
    private Map<String, EventServiceReloader> reloaders;

    @Autowired
    private EventServiceImplsHolder holder;

    public void updateImplementations(String implBeanName) {
        this.reloaders.forEach((k, v) ->
                v.changeImplementation(this.holder.get(implBeanName)));
    }
}
