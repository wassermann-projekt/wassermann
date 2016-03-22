package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Arrays;
import java.util.Comparator;

public class Highscore {
	
	class Item{
		public String name;
		public long score;
		public boolean is_new;
		
		public Item(String n, long s){
			name = n;
			score = s;
			is_new = false;
		}
	}
	
	class ItemComp implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2){
			int ret = 0;
			if(i1.score < i2.score){
				ret = 1;
			}
			else if(i2.score < i1.score){
				ret = -1;
			}
			return ret;
		}
	}
	
	private String filename;
	private BitmapFont font;
	private Item[] items;
	private int numitems;
	
	
	public Highscore(BitmapFont f, String file){
		
		font = f;
		filename = file;
		numitems = 5;
		
		items = new Item[numitems];
		if(!load()){
			resetScores();
		}
		
		sort();
		
	}
	
	public void save(){
		String save_string = "";
		for(int i = 0; i < numitems; ++i){
			save_string += items[i].name + System.lineSeparator() + items[i].score + System.lineSeparator();
		}
		FileHandle file = Gdx.files.local(filename);
		file.writeString(save_string, false);
	}
	
	public boolean load(){
		FileHandle file = Gdx.files.local(filename);
		if(!file.exists()){
			return false;
		}
		String save_string = file.readString();
		String[] str_items = save_string.split(System.lineSeparator());
		int j = 0;
		
		if(str_items.length < (2 * numitems)){
			return false;
		}
		else{
			for(int i = 0; i < str_items.length && j < numitems; i += 2){
				items[j] = new Item(str_items[i], Long.parseLong(str_items[i + 1]));
				++j;		
			}
		}
		
		return true;
	}
	
	public boolean isHighscore(long score){
		sort();
		return score > items[numitems - 1].score;
	}
	
	public void enterHighscore(String name, long score){
		items[numitems - 1] = new Item(name, score);
		sort();
		save();
	}
	
	public int numItems(){
		return numitems;
	}
	
	public String getName(int index){
		String name = "";
		if(index >= 0 && index < numitems){
			name = items[index].name;
		}
		return name;
	}
	
	public String getScoreString(int index){
		String scorestr = "";
		if(index >= 0 && index < numitems){
			if(items[index].score > 0){
				scorestr += items[index].score;
			}
		}
		return scorestr;
	}
	
	public boolean isNewScore(int index){
		boolean isnew = false;
		if(index >= 0 && index < numitems){
			isnew = items[index].is_new; 
		}
		return isnew;
	}
	
	public void resetScores(){
		for(int i = 0; i < numitems; ++i){
			items[i] = new Item("", 0);
		}
		save();
	}
	
	private void sort(){
		Arrays.sort(items, new ItemComp());
	}
	

}
