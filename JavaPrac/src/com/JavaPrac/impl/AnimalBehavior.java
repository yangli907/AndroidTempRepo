package com.JavaPrac.impl;

public class AnimalBehavior {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dog d1 = new Dog();// TODO Auto-generated method stub
		Dog d2 = new Dog();
		d1.setName("Tom");
		d1.setCommand("Ruff!");
		d2.setName("Jerry");
		d2.setCommand("Scuffy!");
		d1.bark();
		d2.fly();
		d2.bark();
		d1.fly();
		Bird b1 = new Bird();
		b1.setName("Bruce");
		b1.setCommand("flying");
		b1.fly();
		b1.bark();
	}

}
