package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements  TimeEntryRepository {

    List<TimeEntry> timeEntryList = new ArrayList<>();

    long timeEntryId = 0L;

    public TimeEntry create(TimeEntry timeEntry){

        timeEntryId = timeEntryId + 1L;

        timeEntry.setId(timeEntryId);

        timeEntryList.add(timeEntry);
        return timeEntry;
    }

    public TimeEntry find(Long timeEntryId){

        for(TimeEntry timeEntry: timeEntryList){
            if(timeEntry.getId() == timeEntryId ){
                return  timeEntry;
            }
        }
        return null;
    }

    public List<TimeEntry> list(){
        return timeEntryList;
    }

    public TimeEntry update (Long timeEntryId, TimeEntry timeEntry){

        int foundIndex = -1;

        for(int i =0 ; i< timeEntryList.size() ; i++){
            if(timeEntryList.get(i).getId() == timeEntryId){
                    foundIndex = i;
                    break;
            }
        }

        if(foundIndex != -1){
            timeEntry.setId(timeEntryId);
            timeEntryList.add(foundIndex, timeEntry);
            return timeEntry;
        }
        else{
            return  null;
        }
    }

    public void delete(Long timeEntryId){

            int foundIndex = -1;

            for (int i = 0; i < timeEntryList.size(); i++) {
                if (timeEntryList.get(i).getId() == timeEntryId) {
                    foundIndex = i;
                    break;
                }
            }
            if (foundIndex != -1) {
                timeEntryList.remove(foundIndex);
            }
        }
}
