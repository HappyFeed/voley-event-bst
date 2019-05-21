package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javafx.scene.image.Image;

public class VolleyBallEvent {
	
	private Participant root;
	private Participant first;
	
	public static final String PATH = "data/data.csv";
	
	public VolleyBallEvent() {
		
	}
 
	//Spectators methods
	public void addParticipantIntoTree(Participant p) {
		addParticipantIntoTree(p, root);
	}
	
	private void addParticipantIntoTree(Participant part, Participant current) {
		if(root == null) {
			root = part;
		}
		else {
			if(part.compareTo(current) <= 0) {
				if(current.getLeft() == null) {
					current.setLeft(part);
				}else{
					addParticipantIntoTree(part, current.getLeft());
				}
			} else{
				if(current.getRigth() == null) {
					current.setRigth(part);
				} else {
					addParticipantIntoTree(part, current.getRigth());
				}
			}
			
		}
	}
	
	public String LoadFileAndAddToTree() throws IOException {
		File file = new File(PATH);
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		String line = br.readLine();
		line = br.readLine();
		int times=0;
		while(line != null){
			String[] parts = line.split(",");
			URL url = new URL(parts[6]);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			Image img = new Image(in);	
			Participant nParticipant = new Participant(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],parts[5],img,parts[7]);
			addParticipantIntoTree(nParticipant);
			line = br.readLine();
			times++;
		}
		fileReader.close();
		br.close();
		choiceAleatoryParticipants(times);
		return PATH;
	}
	
	public Participant searchSpectador(int id) {
		Participant s= new Participant(id,"","","","","",null,"");
		return searchSpectador(root,s);
	}
	
	private Participant searchSpectador(Participant current, Participant s) {
		if(current!=null) {
			if(s.compareTo(current)<0) {
				if(current.getLeft()!=null){
					return searchSpectador(current.getLeft(),s);
				}else {
					return searchSpectador(current.getRigth(), s);
				}
			}else if(s.compareTo(current)>0){
				if(current.getRigth()!=null) {
					return searchSpectador(current.getRigth(), s);
				}else {
					return searchSpectador(current.getLeft(), s);
				}
			}else {
				return current;
			}
		}
		return current;
	}
	
	
	//Official participants methods
	public void choiceAleatoryParticipants(int size) {
		System.out.println(size+""+(size*0.5));
		int[] p= new int[10];
		for(int i=0;i<10;i++) {
			int n=(int) (Math.random() * size) + 1;
			if(i>0) {
				if(notChoice(n,p)) {
					Participant s=searchSpectador(n);
					addingOficialParticipants(s);
					System.out.println(n+" si lista "+i);
					p[i]=n;
				}
			}else {
				p[i]=n;
			}

		}
	}
	public boolean notChoice(int n, int[]a) {
		boolean flag=true;
		for(int i=0;i<a.length;i++) {
			if(n==a[i]) {
				flag=false;
			}
		}
		return flag;
	}
	public void addingOficialParticipants(Participant newOne){
		if(first == null){
			first = newOne;
			System.out.println("null");
		}else{
			Participant current = first;
			while(current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(newOne);
			Participant temp = current;
			current = current.getNext();
			current.setPrev(temp);
			System.out.println("si");
		}
	}
	public Participant searchOficialParticipant(int n) {
		Participant current = first;
		Participant returned = null;
		boolean stop = false;
		while(current != null && !stop) {
			if(current.getId()==n) {
				stop = true;
				returned = current;
			}else {
					current = current.getNext();
			}
		}
		
		return returned;
	}
	
	
	//Getters and Setters
	public Participant getRoot() {
		return root;
	}
	
	public void setRoot(Participant root) {
		this.root = root;
	}
	
	public Participant getFirst() {
		return first;
	}
	
	public void setFirst(Participant first) {
		this.first = first;
	}

}
