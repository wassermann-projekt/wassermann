package com.mygdx.game;

public class ObstacleQueue {
	//[zeile][bahn]
	//-1 = leeres Hindernis
	private int[][] bahn = new int[10][7];
	//Anzahl an belegten Zeilen
	private int size;
	
	//Konstruktor
	public ObstacleQueue(){
		size = 0;
	}
	
	public int getSize(){
		return size;
	}
	
	public int[] getNextZeile(){
		int[] help = bahn[0];
		for (int i=0;i<size-1;i++){
			bahn[i] = bahn[i+1];
		}
		size--;
		return help;
	}
	
	public void addZeile(int[] z){
		if (size<10){
			bahn[size] = z;
			size++;
		}
	}
	
	public void addLeerzeile(){
		if (size<10){
			for (int i=0;i<7;i++){
				bahn[size][i] = -1;
			}
			size++;
		}
	}
	
}