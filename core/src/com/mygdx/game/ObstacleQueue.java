package com.mygdx.game;

public class ObstacleQueue {
	//[zeile][bahn]
	//-1 = leeres Hindernis
	private int[][] bahn;
	//Anzahl an belegten Zeilen
	private int size;
	private int maxSize;
	
	//Konstruktor
	public ObstacleQueue(int max_size){
		size = 0;
		this.bahn = new int[max_size][7];
		maxSize = max_size;
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
		if (size<maxSize){
			bahn[size] = z;
			size++;
		}
	}
	
	public void addZeile(int a, int b, int c, int d, int e, int f, int g){
		int[] z = {a,b,c,d,e,f,g};
		if (size<maxSize){
			bahn[size] = z;
			size++;
		}
	}
	
	public void addLeerzeile(){
		if (size<maxSize){
			for (int i=0;i<7;i++){
				bahn[size][i] = -1;
			}
			size++;
		}
	}
	
	public void addLeerzeilen(int n){
		for (int i=0;i<n;i++){
			addLeerzeile();
		}
	}
	
	public void clear(){
		size = 0;
	}
	
	public void changeSize(int new_size){
		int[][] help = bahn;
		if (new_size>size){
			bahn = new int[new_size][7];
			for (int i=0;i<size;i++){
				bahn[i] = help[i];
			}
		}
		else
		{
			bahn = new int[new_size][7];
			for (int i=0;i<new_size;i++){
				bahn[i] = help[i];
			}
			size = new_size;
		}
		maxSize = new_size;
	}
	
}