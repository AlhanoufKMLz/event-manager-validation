package com.example.eventmanagervalidation.Controller;


import com.example.eventmanagervalidation.ApiResponse.ApiResponse;
import com.example.eventmanagervalidation.Model.Event;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    ArrayList<Event> events = new ArrayList<>();

    //BASIC CRUD ENDPOINTS
    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody @Valid Event newEvent, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        //check id
        for(Event e: events){
            if(e.getId().equalsIgnoreCase(newEvent.getId()))
                return ResponseEntity.status(400).body(new ApiResponse("The ID: " + newEvent.getId() + " is already used please enter another ID."));
        }

        //check dates
        if(newEvent.getStartDate().isAfter(newEvent.getEndDate())){
            return ResponseEntity.status(400).body(new ApiResponse("Start date must be before end date"));
        }

        events.add(newEvent);
        return ResponseEntity.status(200).body(new ApiResponse("Event added successfully."));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEvents(){
        return ResponseEntity.status(200).body(events);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable String id, @RequestBody @Valid Event newEvent, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        //check dates
        if(newEvent.getStartDate().isAfter(newEvent.getEndDate())){
            return ResponseEntity.status(400).body(new ApiResponse("Start date must be before end date"));
        }

        for(int i=0; i < events.size(); i++){
            if(events.get(i).getId().equalsIgnoreCase(id)){
                newEvent.setId(events.get(i).getId());//make sure the user doesn't change the id
                events.set(i, newEvent);
                return ResponseEntity.status(200).body(new ApiResponse("Event updated successfully."));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("Event with ID: " + id + " not found."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable String id){
        for(int i=0; i < events.size(); i++) {
            if (events.get(i).getId().equalsIgnoreCase(id)) {
                events.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Event deleted successfully"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("Event with ID: " + id + " not found."));
    }


    //EXTRA ENDPOINTS
    @PutMapping("/update-capacity/{id}/{capacity}")
    public ResponseEntity<?> updateCapacity(@PathVariable String id, @PathVariable int capacity){
        //check capacity
        if(capacity < 26)
            return ResponseEntity.status(400).body(new ApiResponse("Capacity should be greater than 25."));

        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                event.setCapacity(capacity);
                return ResponseEntity.status(200).body(new ApiResponse("Event capacity updated successfully."));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("Event with ID: " + id + " not found."));
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return ResponseEntity.status(200).body(event);
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("There is no event with ID: " + id));
    }

}
