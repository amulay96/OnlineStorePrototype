package common;

import java.io.Serializable;

public class Product implements Serializable{
	private static final long serialVersionUID = -7196046581653730553L;
	private String type;
	private String description;
	private Double price;
	private Integer quantity;
	
	public Product(String type, String description, Double price, Integer quantity) {
		super();
		this.type = type;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.type);
		sb.append("|");
		sb.append(this.description);
		sb.append("|");
		sb.append(this.price);
		sb.append("|");
		sb.append(this.quantity);
		
		return sb.toString();
	}
}