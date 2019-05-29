package event.service.benchmarks;

import event.service.Application;
import event.service.api.EventService;
import event.service.domain.Event;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * This class {@link AbstractServiceBenchMark} contains common configuration logic
 * To provide setup and finalization to its child classes.
 *
 * Created by Viachaslau Tsitsiankou
 * Date on 05/27/2019.
 */
abstract class AbstractServiceBenchMark {

    /** Spring application context to hold injected services to test */
    private static ConfigurableApplicationContext context;
    /** A local interface instance to be initialized by any kind of implementation */
    EventService eventService;
    /** Some helper data to be tested on */
    List<Event> DATA_FOR_TESTING;

    /** Runs JMH from inside */
    public static void run(Options opt) throws RunnerException {
        new Runner(opt).run();
    }

    /**
     * Init spring application context and runs its container.
     * Populates test data and initializes {@link EventService} implementation instance to be bench marked.
     */
    @Setup(Level.Trial)
    public void setup() {
        URLClassLoader classLoader = (URLClassLoader) MongoServiceBenchMark.class.getClassLoader();
        StringBuilder classpath = new StringBuilder();
        for(URL url : classLoader.getURLs())
            classpath.append(url.getPath()).append(File.pathSeparator);
        System.out.print(classpath.toString());
        System.setProperty("java.class.path", classpath.toString());
        try {
            String args = "";
            if(context == null) {
                context = SpringApplication.run(Application.class, args);
            }
            eventService = context.getBean(getServiceImplementation());
            System.out.println(eventService);
        } catch(Exception e) {
            e.printStackTrace();
        }
        DATA_FOR_TESTING = createData();
    }

    /**
     * Releases spring resources to allow be used by another bench mmarks.
     * Returns database to initial state.
     */
    @TearDown
    public void tearDown() {
        eventService.deleteAllEvents();
        context.close();
    }

    /**
     * Creates any kind of test data and populates local {@link List} instance.
     *
     * @return populated {@link List} of test data
     */
    protected abstract List<Event> createData();

    /**
     * Returns class object to be used in spring context.
     *
     * @return an instance of {@link EventService} implementation.
     */
    protected abstract Class<? extends EventService> getServiceImplementation();
}
