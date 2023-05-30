package org.kosta.product.model;

public class FoodVO {
	private String name;
	private int price;
	public FoodVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FoodVO(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}
	public String getName() {
		System.out.println("getName()");
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		System.out.println("getPrice()");
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "FoodVO [name=" + name + ", price=" + price + "]";
	}
	// jsp el 로 접근 여부를 테스트 
	public String showValue() {
		return "test";
	}
	// jsp el 로 접근 여부를 테스트 
	public boolean isExist() {
		return true;
	}
}



















