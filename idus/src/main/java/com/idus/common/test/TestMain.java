package com.idus.common.test;

import java.util.ArrayList;
import java.util.List;

import com.idus.common.util.JsonStringBuilder;

public class TestMain {

	public static void main(String[] args) {
		
		JsonStringBuilder json = new JsonStringBuilder();
		
		List<Car> list = new ArrayList<Car>();
		list.add(new Car("a", "blue"));
		list.add(new Car("b", "blue"));
		list.add(new Car("c", "blue"));
		list.add(new Car("d", "blue"));
		list.add(new Car("e", "blue"));
		json.addEntry("list", list);
		
		System.out.println(json.toString());
		
	}

}


class Car {
	
	private String name;
	private String color;
	
	public Car() {}

	public Car(String name, String color) {
		super();
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}