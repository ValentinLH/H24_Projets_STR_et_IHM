package fr.polytech.vgl.realtime;

import fr.polytech.vgl.model.Record;

public class test {

	public static void main(String[] args) {
		
		BufferedMemory<Record> test = new BufferedMemory<Record>(15, 5,() -> new Record(null));
		
		Record a = test.useObject();
		System.out.println(a.toString());
		
		
	}
}
