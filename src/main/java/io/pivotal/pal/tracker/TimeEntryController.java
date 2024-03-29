package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
     private TimeEntryRepository timeEntryRepository;
     private final DistributionSummary timeEntrySummary;
     private final Counter actionCounter;


    public TimeEntryController(
            TimeEntryRepository timeEntryRepository,
            MeterRegistry meterRegistry
    ) {
        this.timeEntryRepository = timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody(required = true) TimeEntry timeEntry){

         TimeEntry timeEntryResponse = timeEntryRepository.create(timeEntry);

         return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryResponse);

     }
     @GetMapping("/{id}")
     public ResponseEntity read(@PathVariable (value = "id") Long timeEntryId){
         TimeEntry timeEntry =timeEntryRepository.find(timeEntryId);
         if(timeEntry != null)
            return ResponseEntity.status(HttpStatus.OK).body(timeEntry);
         else
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

     }

    @GetMapping
    public ResponseEntity list(){
        List<TimeEntry> timeEntryList =timeEntryRepository.list();
        return ResponseEntity.status(HttpStatus.OK).body(timeEntryList);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value = "id") Long timeEntryId,@RequestBody(required = true) TimeEntry timeEntry) {
        TimeEntry timeEntryResponse= timeEntryRepository.update(timeEntryId,timeEntry);

        if(timeEntryResponse != null)
            return ResponseEntity.status(HttpStatus.OK).body(timeEntryResponse);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value="id") Long timeEntryId){

         timeEntryRepository.delete(timeEntryId);

         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
