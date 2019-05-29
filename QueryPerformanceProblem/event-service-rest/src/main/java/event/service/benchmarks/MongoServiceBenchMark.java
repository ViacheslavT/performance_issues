package event.service.benchmarks;
import event.service.api.EventService;
import event.service.domain.EventType;
import event.service.domain.Address;
import event.service.domain.Event;
import event.service.impl.mongo.EventServiceImpl;
import lombok.Setter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 05/27/2019.
 */
@Setter
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms8G", "-Xmx8G"})
@Warmup(iterations = 1)
@Measurement(iterations = 3)
public class MongoServiceBenchMark extends AbstractServiceBenchMark {

    @Param({"2"})
    private int N;

    /**
     * Simple bench mark to be analyzed with report of performance for
     * Storing data in database.
     *
     * @param bh to consume algorithm.
     */
    @Threads(1)
    @Benchmark
    public void addEvents(Blackhole bh) {
        for (int i = 0; i < DATA_FOR_TESTING.size(); i++) {
            Event event = DATA_FOR_TESTING.get(i);
            event = eventService.createEvent(event);
            bh.consume(event);
        }
    }

    @Override
    protected List<Event> createData() {
        List<Event> data = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Event event = new Event();
            event.setAddress(new Address("City " + i, "Street " + i, "ASad " + i));
            event.setDateTime(LocalDateTime.now());
            event.setEventType(EventType.TECH_TALK);
            event.setTitle("Bench mark title " + i);
            data.add(event);
        }
        return data;
    }

    @Override
    protected Class<? extends EventService> getServiceImplementation() {
        return EventServiceImpl.class;
    }
}
