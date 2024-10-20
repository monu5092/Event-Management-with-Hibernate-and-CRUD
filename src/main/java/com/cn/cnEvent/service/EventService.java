package com.cn.cnEvent.service;

import com.cn.cnEvent.dal.EventDAL;
import com.cn.cnEvent.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class EventService {

	@Autowired
	EventDAL eventDAL;

	@Transactional
	public Event getEventById(Long id) {
		Event event=eventDAL.getById(id);
		if(event==null){
			throw new RuntimeException("No event found with id:  "+id);
		}
		return event;
	}

	@Transactional
	public List<Event> getAllEvents() {
		List<Event> events=eventDAL.getAllEvents();
		if(events==null){

			throw new RuntimeException("No events found.");
		}
		return events;
	}

	@Transactional
	public String saveEvent(Event newEvent) {

		List<Event> allEvents  = getAllEvents();
		for(Event event : allEvents)
		{
			if(Objects.equals(event.getId(), newEvent.getId()))
			{
				throw new RuntimeException("This event already exist.");
			}
		}
		try {
			return eventDAL.save(newEvent);
		}
		catch (Exception e)
		{
			throw new RuntimeException("The input entity for event is invalid.");
		}
	}

	@Transactional
	public String delete(Long id) {
		List<Event> allEvents = getAllEvents();

		boolean isEntityPresent=false;
		for(Event event : allEvents)
		{
            if (Objects.equals(event.getId(), id)) {
                isEntityPresent = true;
            }
		}
		if(!isEntityPresent)
		{
			throw new RuntimeException("This event doesn't exist.");
		}
		try{
			return eventDAL.delete(id);
		}
		catch (Exception e){
			throw new RuntimeException("Error in deleting event.");
		}
	}
	@Transactional
	public String update(Event updateEvent) {
		try{
			return eventDAL.update(updateEvent);
		}
		catch (Exception e){
			throw new RuntimeException("Error in deleting eventScheduleDetail from event.");
		}
	}
}
