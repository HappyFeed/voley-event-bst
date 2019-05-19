package model;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class VoleyEventTest {

	private VolleyBallEvent vbe;
	private Participant p;
	
	public void setUpEscenary1() {
		vbe= new VolleyBallEvent();
		p= new Participant(1,"Jairitox","Ocoro","ocoro@gmail.com","male","Colombia",null,"march");
	}
	
	public void setUpEscenary2() {
		vbe= new VolleyBallEvent();
	}
	public void setUpEscenary3() {
		vbe= new VolleyBallEvent();
		for(int i=0;i<4;i++) {
			Participant a= new Participant(i,"Jairitox","Ocoro","ocoro@gmail.com","male","Colombia",null,"march");
			vbe.addParticipantIntoTree(a);
		}    
	}
	
	@Test
	void testAddParticipantIntoTree() {
		setUpEscenary1();
		vbe.addParticipantIntoTree(p);
		assertTrue("The method do not add correct",vbe.getRoot()!=null);
		assertTrue("The method do not add correct id",vbe.getRoot().getId()==1);
		assertTrue("The method do not add correct first name",vbe.getRoot().getFirstName().equals("Jairitox"));
		assertTrue("The method do not add correct last name",vbe.getRoot().getLastName().equals("Ocoro"));
		assertTrue("The method do not add correct email",vbe.getRoot().getEmail().equals("ocoro@gmail.com"));
		assertTrue("The method do not add correct gender",vbe.getRoot().getGender().equals("male"));
		assertTrue("The method do not add correct country",vbe.getRoot().getCountry().equals("Colombia"));
		assertTrue("The method do not add correct birth",vbe.getRoot().getBirthday().equals("march"));
	}
	
	@Test
	void testSearchSpectador() {
		setUpEscenary1();
		vbe.setRoot(p);
		Participant test=vbe.searchSpectador(p.getId());
		assertTrue("The method fail",test!=null);
	}
	
	@Test
	void testSearchSpectador1() {
		setUpEscenary1();
		Participant test=vbe.searchSpectador(p.getId());
		assertTrue("The method fail",test==null);
	}
	
	@Test
	void testchoiceAleatoryParticipants() {
		setUpEscenary3();
		vbe.choiceAleatoryParticipants(4);
		Participant t=vbe.getFirst();
		for(int i=0;i<2;i++) {
			assertTrue("The method do not add the 50% of the tree",t!=null);
			t.getNext();
		}
		
	}

}
