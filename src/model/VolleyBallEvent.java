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
	private Participant oficialRoot;
	
	public static final String PATH = "data/data.csv";
	
	public VolleyBallEvent() {
		
	}
    public int retornarAltura() {
        return  retornarAltura(root, 1);
    }

    private int retornarAltura(Participant reco, int nivel) {
       int altura=0;
    	if (reco != null) {
            retornarAltura(reco.getLeft(), nivel + 1);
            if (nivel > altura) {
                altura = nivel;
            }
            retornarAltura(reco.getRigth(), nivel + 1);
        }
    	return altura;
    }
    public String imprimirNivel() {
  

        return imprimirNivel(root, 0);

    }

    private String imprimirNivel(Participant pivote, int nivel2) {
    	int info=0;
    	if (pivote != null) {
            imprimirNivel(pivote.getRigth(), nivel2 + 1);
            imprimirNivel(pivote.getLeft(), nivel2 + 1);
            info=pivote.getId();
        }
    	return ""+info;
    }

    public void imprimirAlturaDeCadaNodo() {
        imprimirAlturaDeCadaNodo(root, 1);

    }

    private void imprimirAlturaDeCadaNodo(Participant reco, int nivel) {
        if (reco != null) {
            System.out.println("Nodo contiene: " + reco.getId() + " y su altura es: " + nivel);
            imprimirAlturaDeCadaNodo(reco.getLeft(), nivel + 1);
            imprimirAlturaDeCadaNodo(reco.getRigth(), nivel + 1);
        }
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
		while(line != null){
			String[] parts = line.split(",");
			URL url = new URL(parts[6]);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			Image img = new Image(in);	
			Participant nParticipant = new Participant(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],parts[5],img,parts[7]);
			addParticipantIntoTree(nParticipant);
			line = br.readLine();
		}
		fileReader.close();
		br.close();
		choiceAleatoryParticipants();
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
	public void choiceAleatoryParticipants() {
		for(int i=0;i<25;i++) {
			int n=(int) (Math.random() * 50) + 1;
			Participant s=searchSpectador(n);
			addingOficialParticipants(s);
		}
		oficialParticipantsTree("China");
	}
	
	public void addingOficialParticipants(Participant newOne){
		if(first == null){
			first = newOne;
		}else{
			Participant current = first;
			while(current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(newOne);
			Participant temp = current;
			current = current.getNext();
			current.setPrev(temp);
		}
	}
	
	public void oficialParticipantsTree(String country) {
		while(first!=null) {
			if(first.getCountry().equalsIgnoreCase(country)) {
				oficialParticipantsTree(first,oficialRoot);
			}
			first.getNext();
		}
		
	}
	
	private void oficialParticipantsTree(Participant p, Participant current) {
		if(oficialRoot == null) {
			oficialRoot = p;
		}
		else {
			if(p.compareTo(current) <= 0) {
				if(current.getLeft() == null) {
					current.setLeft(p);
				}else{
					addParticipantIntoTree(p, current.getLeft());
				}
			} else{
				if(current.getRigth() == null) {
					current.setRigth(p);
				} else {
					addParticipantIntoTree(p, current.getRigth());
				}
			}
			
		}
	}
	
    public Participant searchOficialParticipant(int id) {
		Participant s= new Participant(id,"","","","","",null,"");
		return searchOficialPartcipant(oficialRoot,s);
    }
	
    private Participant searchOficialPartcipant(Participant current, Participant s) {
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
