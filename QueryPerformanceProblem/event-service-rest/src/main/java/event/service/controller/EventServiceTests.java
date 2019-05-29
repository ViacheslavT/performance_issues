package event.service.controller;

import event.service.helper.EventServiceImplUpdater;
import event.service.helper.EventServiceImplsHolder;
import event.service.benchmarks.H2ServiceBenchMark;
import event.service.benchmarks.MongoServiceBenchMark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 05/24/2019.
 */
@RestController
@RequestMapping("v1.0/event-service/test")
@Api(
        value = "event-service-test",
        description = "Service to provide performance benchmarks for existing apis"
)
public class EventServiceTests {

    @Autowired
    private EventServiceImplsHolder holder;

    @Autowired
    private EventServiceImplUpdater updater;

    @PostMapping(value = "/{impl}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = List.class,
            produces = "application/json",
            value = "Changes implementation. Updates implementation to passed with path variable"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Implementation changed", response = String.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseBody
    public ResponseEntity<?> updateImpl(@PathVariable final String impl) {
        updater.updateImplementations(impl);
        return ResponseEntity.ok(Collections.singletonMap("changed to", impl));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = List.class,
            produces = "application/json",
            value = "Changes implementation. Updates implementation to passed with path variable"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns list of implementations", response = String.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseBody
    public ResponseEntity<?> getAllImplementations() {
        return ResponseEntity.ok(holder.getImpls());
    }

    @PostMapping(value = "/runmongo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = List.class,
            produces = "application/json",
            value = "Launches Mongo Service Benach Marks"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseBody
    public ResponseEntity<?> runMongoBenchMark(
            @RequestParam(name = "forks", required = false) final Integer forks,
            @RequestParam(name = "warmupIterations", required = false) final Integer warmupIterations,
            @RequestParam(name = "measurementIterations", required = false) final Integer measurementIterations) throws RunnerException {
        MongoServiceBenchMark.run(
                createOptions(
                        forks,
                        warmupIterations,
                        measurementIterations,
                        MongoServiceBenchMark.class.getSimpleName(),
                        "mongoResults.json"
                )
        );
        return ResponseEntity.ok(getBenchMarkResponse("mongoResults.json"));
    }

    @PostMapping(value = "/runh2", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            response = List.class,
            produces = "application/json",
            value = "Launches H2 Service Benach Marks"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseBody
    public ResponseEntity<?> runH2BenchMark(
            @RequestParam(name = "forks", required = false) final Integer forks,
            @RequestParam(name = "warmupIterations", required = false) final Integer warmupIterations,
            @RequestParam(name = "measurementIterations", required = false) final Integer measurementIterations) throws RunnerException {
        H2ServiceBenchMark.run(
                createOptions(
                        forks,
                        warmupIterations,
                        measurementIterations,
                        H2ServiceBenchMark.class.getSimpleName(),
                        "h2Results.json"
                )
        );
        return ResponseEntity.ok(getBenchMarkResponse("h2Results.json"));
    }

    private Options createOptions(final Integer forks, final Integer warmupIterations, final Integer measurementIterations, final String className, final String fileName) {
        return new OptionsBuilder()
                .include("\\." + className + "\\.")
                .warmupIterations((warmupIterations == null) ? 1 : warmupIterations)
                .measurementIterations((measurementIterations == null) ? 1 : measurementIterations)
                .measurementTime(TimeValue.milliseconds(100))
                .forks((forks == null) ? 1 : forks)
                .result(fileName)
                .resultFormat(ResultFormatType.JSON)
                .build();
    }

    private JSONArray getBenchMarkResponse(final String fileName) {
        JSONParser jsonParser = new JSONParser();
        JSONArray response = null;
        try (FileReader reader = new FileReader(fileName))
        {
            Object obj = jsonParser.parse(reader);
            response = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}
