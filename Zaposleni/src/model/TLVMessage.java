package model;

import java.util.Arrays;

public class TLVMessage {

	char type;
	byte[] value;
	
	public TLVMessage(){
		super();
	}
	public TLVMessage(char type,byte[] value) {
		super();
		this.type = type;
		this.value = value;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "TLVMessage [type=" + type + ", value=" + Arrays.toString(value) + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		result = prime * result + Arrays.hashCode(value);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TLVMessage other = (TLVMessage) obj;
		if (type != other.type)
			return false;
		if (!Arrays.equals(value, other.value))
			return false;
		return true;
	}
	
	
	
	
	
}
