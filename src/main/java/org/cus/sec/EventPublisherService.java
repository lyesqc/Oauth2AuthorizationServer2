package org.cus.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherService {
	@Autowired
	ApplicationEventPublisher appEventPublisher;
    public void publish(){
    	appEventPublisher.publishEvent(new NewRequest(this, "one"));
    }
	
}
