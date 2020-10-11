package org.cus.sec;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
public class EventProcessService {
	
	@EventListener
	public void processNewRequestEvent(NewRequest req){
		System.out.println("NewRequest events is fired "+req.getSource().getClass().getName());
	}

}
