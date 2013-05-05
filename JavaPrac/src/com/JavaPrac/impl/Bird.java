package com.JavaPrac.impl;

public class Bird implements Animal {
	private String name;
	
	private String command;
	@Override
	public void bark() {
		System.out.println(getName()+" can't bark!");// TODO Auto-generated method stub
	}

	@Override
	public void fly() {
		System.out.println(getName()+" is "+getCommand());// TODO Auto-generated method stub

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
