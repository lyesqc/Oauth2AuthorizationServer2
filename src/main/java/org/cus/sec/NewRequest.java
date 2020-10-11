package org.cus.sec;

import org.springframework.context.ApplicationEvent;

public class NewRequest extends ApplicationEvent{
	private String name;
	public NewRequest(Object source, String name){
		super(source);
		this.name = name;
	}
	

}
