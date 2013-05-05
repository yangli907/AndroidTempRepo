package com.JavaPrac.impl;

public class Dog implements Animal {
	private String name;
	
	private String command;
	@Override
	public void bark() {
		System.out.println(getName()+": "+getCommand());// TODO Auto-generated method stub
	}

	@Override
	public void fly() {
		System.out.println(getName()+" can't fly!");
		// TODO Auto-generated method stub

	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
