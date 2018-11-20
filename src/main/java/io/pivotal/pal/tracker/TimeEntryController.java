package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
     private TimeEntryRepository timeEntryRepository;

     public TimeEntryController(TimeEntryRepository timeEntryRepository){
         this.timeEntryRepository=timeEntryRepository;
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
