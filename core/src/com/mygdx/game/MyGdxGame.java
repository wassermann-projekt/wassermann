package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.utils.TimeUtils;


import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.Arrays;

import java.util.Timer;
import java.util.TimerTask;




public class MyGdxGame extends ApplicationAdapter {

	// Textures
	Texture anzeige;
	Texture felsen_unter_wasser;
	Texture hai_1;
	Texture hai_2;
	Texture herz_leer_tex;
	Texture herz_voll_tex;
	Texture hindernis_felsen;
	Texture rennschwan;
	Texture schwimmer_aufsicht_body;
	Texture schwimmer_aufsicht_linker_arm;
	Texture schwimmer_aufsicht_rechter_arm;
	Texture schwimmer_seitlich_body;
	Texture schwimmer_seitlich_rechtes_bein;
	Texture schwimmer_seitlich_linkes_bein;
	Texture luftblasen;
	Texture seerosen;
	Texture seerosen_mund;
	Texture seerose_zaehne;
	Texture hindernis_tauchbar_loch;
	Texture hindernis_tauchbar_strudel;
	Texture ufer;
	Texture unter_wasser_textur_1;
	Texture unter_wasser_textur_2;
	Texture unter_wasser_textur_3;
	Texture unter_wasser_textur_4;
	Texture wellen;
	Texture guteherzen;
	Texture coin;
	Texture stopwatch;
	Texture brille;
	Texture logo_mit_traegerform;


	private GameState state;

	// Hintergrund Schwimmwelt
	private Sprite wellen1;
	private Sprite wellen2;
	private Sprite ufer_links;
	private Sprite ufer_rechts;

	// Hintergrund Tauchwelt
	private Sprite hintergrund1;
	private Sprite hintergrund2;
	private Sprite hintergrund3;
	private Sprite hintergrund4;

	private Sprite herz_leer;
	private Sprite herz_voll;
	private Sprite swimmer;
	private Sprite swimmer_rechter_arm;
	private Sprite swimmer_linker_arm;

	private Sprite tauchersprite;
	private Sprite taucher_rechtes_bein;
	private Sprite taucher_linkes_bein;
	private Sprite taucher_luftblasen;

	private Sprite guteherzen_sprite;
	private Sprite coin_sprite;

	private Sprite taucherbrille_sprite; 
	public Sprite logo; 
	
	//Hindernis unter Wasser
	private Sprite hindernis_lowerworld_up;
	private Sprite hindernis_lowerworld_low;
	
	// logo
	private Sprite logo_sprite;
	private float logo_size;
	private long logo_start;

	private SpriteBatch batch;

	private World world;
	public Body body;

	// Schrift
	private BitmapFont font;
	private BitmapFont gameover;

	// Graphics Updates -> Variables to update positions
	private float wellen_y_pos;
	private float loop;
	private float unter_wasser_textur_pos;
	private float zeit_unter_wasser;

	// Hindernis-Array-Swim
	private Obstacle[] hindernis = new Obstacle[50];
	// Positionen aktiver Hindernisse in array
	private boolean[] hindernis_aktiv = new boolean[50];

	// Hindernis Dive
	private Obstacle hindernis_lowerworld_upper;
	private Obstacle hindernis_lowerworld_lower;
	// protected float[] wand_punkte = new float[2 * 10];
	// protected float[] wand_punkte_init = new float[2 * 10];

	// Hilfsvariable für den Hindernisgenerator
	// Bei Aufruf von Hindernis-Generator wird h auf 0 gesetzt
	// Bei jedem Aufruf von render wird geschwindigkeit auf h addiert
	// Bis h größer gleich der Länge eines Hindernisses ist, dann starte
	// Hindernisgenerator
	private float h;
	
	//Hindernis-Generator
	//maximale Anzahl unterschiedlicher Hindernisse
	private int n_obstacles = 9;
	//Schwierigkeit einzelner Typen von Hindernissen
	//Hindernis x kann ab Level difficulty[x] generiert werden

	private int[] difficulty = new int[n_obstacles];
	// Start-Wahrscheinlichkeit eines Hindernisses x in lvl difficulty[x]
	private double[] first_probability = new double[n_obstacles];

	//Nach so vielen Leveln ist probability des Hindernisses auf 0.1
	private int obstacle_ausdauer = 50;
	//Wahrscheinlichkeits-Verteilung des gemeinen Hindernisses: [Hindernis,lvl]
	private double[][] obstacle_probability = new double[n_obstacles][obstacle_ausdauer];
	//Art der W-Verteilung des Hindernisses
	/*0:=immer konstant
	  1:=linear steigend bis difficulty+ausdauer
	  2:=exponentiell fallend
	 */
	private int[] distribution_type = new int[n_obstacles];
	//Erwartungswert Anzahl Hindernisse pro Zeile

	private double generation_probability;
	// Poisson-Verteilung für Anzahl Hindernisse einer Zeile
	private double[] p = new double[8];
	//Hindernis-Buffer als queue zur Speicherung von zukünftigen Hindernissen
	private ObstacleQueue buffer = new ObstacleQueue(20);

	// Variablen für Schwimmer, Hintergrund, Hindernis
	private float geschwindigkeit;
	private float max_speed = 10.0f;
	private float hindernis_geschwindigkeit = 1.0f;
	// Aenderung der Geschwindigkeit
	private float beschleunigung;
	// Hilfsvariable, welche die Echtzeit messen soll
	// Geschwindigkeit kann dafür nicht verwendet werden, da sich diese erhöht
	private int realtime;

	// swimmer variables
	// Bahn des Schwimmers
	private int swimmer_position_swim;
	// swimmer Groesse
	private float swimmer_width;

	// Verwundbarkeit
	private boolean invulnerable;

	// Schwan_Geschwindigkeit
	private int schwan_speed = 20;

	// Abstand zur Bahn
	private float swimmer_offset;
	// Position der Arme
	private float arm_pos;
	private float arm_pos_x = 0.0f;
	private float arm_pos_y = 0.0f;

	// taucher variables
	// taucher Groesse
	private float taucher_width;
	private float taucher_body_width;
	private float luftblasen_x_pos;
	private float luftblasen_y_pos;

	// game variables
	private long score;
	private long level;
	private int health;
	private int brillen; 
	private boolean game_over;

	// highscore-management
	Highscore highscore;

	// Zählt wie viel weiter geschwommen wurde, in Länge eines Hindernisses
	private long Zeile;

	// Hilfsvariable: bei Kollision
	private boolean freeze;

	// Musik & Sound
	private Music music_upper;
	private Music shark;
	private Music music_lower;
	private Music current_music;
	public boolean music_enabled;
	private Music coin_collected;
	private Music clock; 
	private Music brille_collected;

	

	// shortcuts for graphics fields
	private int width, height;
	private float ppiX, ppiY;

	private float width2;

	// init Unterwasserhindernisse

	protected float[] wand_punkte_init = { height, 0, height, 0, height, 0,
			height, 0, height, 0, height, 0, height, 0, height, 0, height, 0,
			height, 0, height, 0, height, 0, height, 0, height, 0, height, 0,
			height, 0, height, 0, height, 0, height, 0, height, 0 };
	protected float[] wand_punkte = wand_punkte_init;

	// input
	private boolean paused;
	private InputMultiplexer multiplexer;

	private Menu menu;
	private EventListener steuerung;

	Timer timer = new Timer();
	private float Timestep;
	
	// feedback
	long anim_feedback_time;
	long anim_down_to_dive_time;


	@Override
	public void create() {

		// init Sounds
		music_upper = Gdx.audio.newMusic(Gdx.files.internal("wasserlily-upper.mp3"));
		music_upper.setLooping(true);
		music_upper.setVolume(0.3f);
		music_lower = Gdx.audio.newMusic(Gdx.files.internal("wasserlily-under.mp3"));
		music_lower.setLooping(true);
		music_lower.setVolume(0.3f);
		shark = Gdx.audio.newMusic(Gdx.files.internal("shark_bite.mp3"));
		shark.setVolume(0.3f);
		coin_collected = Gdx.audio.newMusic(Gdx.files.internal("coin_collected.wav"));
		coin_collected.setVolume(0.3f);
		clock = Gdx.audio.newMusic(Gdx.files.internal("clock.wav"));
		clock.setVolume(0.3f);
		brille_collected = Gdx.audio.newMusic(Gdx.files.internal("brille_collected.wav"));
		brille_collected.setVolume(1.0f);
		
		current_music = music_upper;
		music_enabled = true;


		// init state
		state = GameState.LOGO;


		// Infos Screen;
		readGraphics();

		// New Sprite Batch
		batch = new SpriteBatch();

		
		
		//Textures laden

		anzeige = new Texture("anzeige.png");
		felsen_unter_wasser = new Texture("felsen_unter_wasser.png");
		hai_1 = new Texture("hai_1.png");
		hai_2 = new Texture("hai_2.png");
		herz_leer_tex = new Texture("herz_leer.png");
		herz_voll_tex = new Texture("herz_voll.png");
		hindernis_felsen = new Texture("hindernis_felsen.png");
		rennschwan = new Texture("rennschwan.png");
		schwimmer_aufsicht_body = new Texture("schwimmer_aufsicht_body.png");
		schwimmer_aufsicht_linker_arm = new Texture(
				"schwimmer_aufsicht_linker_arm.png");
		schwimmer_aufsicht_rechter_arm = new Texture(
				"schwimmer_aufsicht_rechter_arm.png");
		schwimmer_seitlich_body = new Texture("schwimmer_seitlich_body.png");
		schwimmer_seitlich_rechtes_bein = new Texture(
				"schwimmer_seitlich_rechtes_bein.png");
		schwimmer_seitlich_linkes_bein = new Texture(
				"schwimmer_seitlich_linkes_bein.png");
		luftblasen = new Texture("luftblasen.png");
		seerosen = new Texture("seerosen.png");
		seerosen_mund = new Texture("seerosen_mund.png");
		seerose_zaehne = new Texture("seerosen_zaehne.png");
		hindernis_tauchbar_loch = new Texture("hindernis_tauchbar_loch.png");
		hindernis_tauchbar_strudel = new Texture(
				"hindernis_tauchbar_strudel.png");
		ufer = new Texture("ufer.png");
		unter_wasser_textur_1 = new Texture("unter_wasser_textur_1.png");
		unter_wasser_textur_2 = new Texture("unter_wasser_textur_2.png");
		unter_wasser_textur_3 = new Texture("unter_wasser_textur_3.png");
		unter_wasser_textur_4 = new Texture("unter_wasser_textur_4.png");
		wellen = new Texture("wellen.png");
		logo_mit_traegerform = new Texture("logo-mit-traegerform.png");

		
		//Gute Hindernisse
		guteherzen = new Texture ("herz_voll2.png");
		coin = new Texture("muenze.png");
		stopwatch = new Texture("uhr.png");
		guteherzen_sprite = new Sprite(guteherzen);
		coin_sprite = new Sprite(coin);
		brille = new Texture ("taucherbrille.png");
		taucherbrille_sprite = new Sprite(new Texture("taucherbrille.png"));
		taucherbrille_sprite.setSize(width/12, height/11);
		


		// init Wellentextur
		wellen1 = new Sprite(wellen);
		wellen1.setSize(width, height);
		wellen2 = new Sprite(wellen);
		wellen2.setSize(width, height);
		wellen_y_pos = 0;

		// init Unterwasserwelt Hintergrund

		hintergrund1 = new Sprite(unter_wasser_textur_1);
		hintergrund1.setSize(width, height);
		hintergrund2 = new Sprite(unter_wasser_textur_2);
		hintergrund2.setSize(width, height);
		hintergrund3 = new Sprite(unter_wasser_textur_3);
		hintergrund3.setSize(width, height);
		hintergrund4 = new Sprite(unter_wasser_textur_4);
		hintergrund4.setSize(width, height);
		unter_wasser_textur_pos = 0.0f;
		zeit_unter_wasser = 0.0f;

		// init Taucher
		tauchersprite = new Sprite(schwimmer_seitlich_body);
		taucher_rechtes_bein = new Sprite(schwimmer_seitlich_rechtes_bein);
		taucher_linkes_bein = new Sprite(schwimmer_seitlich_linkes_bein);
		taucher_width = width / 9;
		taucher_body_width = width / 12;
		luftblasen_x_pos = 0.0f - (taucher_width);
		luftblasen_y_pos = 0.0f;
		
		// init logo
		logo_sprite = new Sprite(logo_mit_traegerform);
		

		world = new World(new Vector2(0, -1), true);
		//Timestep = Gdx.graphics.getDeltaTime();
		
		BodyDef diver = new BodyDef();
		diver.type = BodyDef.BodyType.DynamicBody;

		diver.position.set(0, 0);
		body = world.createBody(diver);

		CircleShape circle = new CircleShape();
		circle.setRadius(6f);

		FixtureDef diverfixture = new FixtureDef();
		diverfixture.shape = circle;
		diverfixture.density = 0.1f;
		diverfixture.friction = 0.4f;
		diverfixture.restitution = 0.6f;

		body.createFixture(diverfixture);

		circle.dispose();

		// Anzeigen
		// init Lebens-Anzeige
		herz_leer = new Sprite(herz_leer_tex);
		herz_voll = new Sprite(herz_voll_tex);
		herz_voll.setSize(width / 18, height / 18);
		herz_leer.setSize(width / 18, height / 18);

		health = 5;

		// init Schrift für alle Anzeigen
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("Mecha_Bold.ttf"));
		FreeTypeFontParameter parameter1 = new FreeTypeFontParameter();
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter1.size = 27;
		parameter2.size = 50;
		parameter1.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:+ ";
		font = generator.generateFont(parameter1);
		gameover = generator.generateFont(parameter2);

		// init Swimmer_Grafik
		swimmer = new Sprite(schwimmer_aufsicht_body);
		swimmer_linker_arm = new Sprite(schwimmer_aufsicht_linker_arm);
		swimmer_rechter_arm = new Sprite(schwimmer_aufsicht_rechter_arm);

		swimmer_offset = ((width - 2) / 9) * 1 / 8;
		swimmer_width = ((width - 2) / 9) * 3 / 4;

		// init Ufertextur
		ufer_links = new Sprite(ufer);
		ufer_links.setSize(width / 9, height);
		ufer_rechts = new Sprite(ufer);
		ufer_rechts.setSize(width / 9, height);
		ufer_rechts.flip(true, false);
		ufer_rechts.setOrigin(width - ufer_rechts.getWidth(), 0);

		
		//init swimmer_position
		swimmer_position_swim = 4;

		// init score
		score = 0;
		level = 1;


		// init Highscore
		highscore = new Highscore("highscore.txt");
		highscore.load();
		
		//logo
		initLogo();
		
		// input
		paused = false;
		multiplexer = new InputMultiplexer();
		
		
		// erstelle menu
		menu = new Menu(multiplexer, this, highscore, font);
		//menu.loadMainMenu();

		// erstelle und registriere Steuerung
		steuerung = new EventListener();
		steuerung.setGame(this);
		steuerung.setMenu(menu);
		multiplexer.addProcessor(steuerung);
		Gdx.input.setInputProcessor(multiplexer);

		// initialisiere Spielvariablen
		resetGameVariables();
	}



	@Override
	public void render() {

		// Spielgrafik rendern
		if(state == GameState.LOGO){
			animateLogo();
		}
		
		if (state == GameState.UPPERWORLD) {
			render_upperworld();
		}

		if (state == GameState.LOWERWORLD)
			render_lowerworld();
		
		// Spiellogik updaten
		if (!(paused || game_over || freeze)) {

			if (state == GameState.UPPERWORLD) {
				update_variables_swim();
				// Graphik-Variablen updaten
				update_graphics();
			} else if (state == GameState.LOWERWORLD) {
				update_variables_dive();
				// Graphik-Variablen updaten
				update_graphics();
			}

		} else {
			if (game_over) {
				render_gameover();
			}
		}

if (state == GameState.MAINMENU){

}


		menu.render();

	}

	// setzt alle Variablen für den Spielstart
	public void resetGameVariables() {
		geschwindigkeit = 1.7f;
		beschleunigung = 0.02f;


		swimmer_position_swim = 4;

		score = 0;
		level = 1;
		health = 5;
		brillen = 0;

		paused = false;
		game_over = false;

		
		//lösche Hindernis buffer
		buffer.clear();
		
		//init Hindernisgenerator
		generation_probability = 2;
		p[0]=0;
		for (int i=1; i<8;i++){
			p[i] = Math.exp(-generation_probability)*Math.pow(generation_probability,i-1)/fact(i-1);
		}
		init_obstacle_type(0,1,0.5,1);
		init_obstacle_type(1,1,0.5,1);
		init_obstacle_type(2,2,0.5,1);
		init_obstacle_type(3,2,0.8,1);
		init_obstacle_type(4,5,0.02,0);
		init_obstacle_type(5,1,0.25,0);
		init_obstacle_type(6,7,0.02,0);
		init_obstacle_type(7,5,0.03,0);
		init_obstacle_type(8,4,0.5,1);

		for (int k=0;k<n_obstacles;k++){
			if (distribution_type[k]==2){
				for (int i=0; i<obstacle_ausdauer;i++){
					double b = Math.log(first_probability[k]);
					double a = Math.log(first_probability[k]*100);
					obstacle_probability[k][i] = Math.exp((-1/obstacle_ausdauer)*a*i+b);
				}
			}
			else if (distribution_type[k]==1){
				for (int i=0; i<obstacle_ausdauer;i++){
					obstacle_probability[k][i] = first_probability[k]*(i/obstacle_ausdauer+1);
				}
			}
			else{
				for (int i=0; i<obstacle_ausdauer;i++){
					obstacle_probability[k][i] = first_probability[k];
				}
			}
		}

		Arrays.fill(hindernis_aktiv, false);
		wand_punkte = wand_punkte_init;

	}

	// Methode um die Schwimmwelt zu rendern
	private void render_upperworld() {
		// Musik
//		music_lower.dispose();
//		music_upper.play();

		// Hintergrundfarbe
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();

		
		// Hintergrund
		batch.draw(wellen1, 0, wellen_y_pos % height, width, height);
		batch.draw(wellen2, 0, (wellen_y_pos % (height)) + height, width,
				height);
		batch.draw(ufer_links, 0, 0, width / 9, height);
		batch.draw(ufer_rechts, ufer_rechts.getOriginX(),
				ufer_rechts.getOriginY(), width / 9, height);


		// Animation Schwimmer
		if(!blinkInvuln()){
			batch.draw(swimmer_rechter_arm, (width - 2 * width / 9) / 7
					* (swimmer_position_swim - 1) + swimmer_offset + width / 9
					+ swimmer_width / 5.0f + (swimmer_width / 4.0f) - arm_pos_x
					* swimmer_width / 70, (swimmer_width / 6.0f - arm_pos_y
					* swimmer_width / 80), swimmer_width / 2, swimmer_width);
			batch.draw(swimmer_linker_arm, (width - 2 * width / 9) / 7
					* (swimmer_position_swim - 1) + swimmer_offset + width / 9
					+ swimmer_width / 5.0f - (swimmer_width / 4.0f) + arm_pos_x
					* swimmer_width / 70, (swimmer_width / 6.0f - arm_pos_y
					* swimmer_width / 80), swimmer_width / 2, swimmer_width);
			batch.draw(swimmer, (width - 2 * width / 9) / 7
					* (swimmer_position_swim - 1) + swimmer_offset + width / 9, 0,
					swimmer_width, swimmer_width);
		}


		// Hindernisse
		for (int i = 0; i < 50; i++) {
			if (hindernis_aktiv[i]) {
				Obstacle aktiv = hindernis[i];
				int aktiv_type = aktiv.getType();
				switch (aktiv_type) {
				case 0:
					batch.draw(aktiv.getSprite(),
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 9, width / 9);
					break;
				case 1:
					batch.draw(aktiv.getSprite(),
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 9, width / 9);
					batch.draw(aktiv.getSpritesAnim()[0],
							(width / 9) * aktiv.getBahn() + width / 34, height
									- aktiv.getY() + width / 30, width / 18,
							width / 25
									+ (float) (5 * (Math.sin(0.3 * realtime))));
					batch.draw(
							aktiv.getSpritesAnim()[1],
							(width / 9) * aktiv.getBahn() + width / 25,
							height
									- aktiv.getY()
									+ width
									/ 20
									+ (float) (2.5 * (Math.sin(0.3 * realtime))),
							width / 30, width / 60);
					batch.draw(
							aktiv.getSpritesAnim()[2],
							(width / 9) * aktiv.getBahn() + width / 25,
							height
									- aktiv.getY()
									+ width
									/ 28
									- (float) (2.5 * (Math.sin(0.3 * realtime))),
							width / 30, width / 60);
					break;
				case 2:
					batch.draw(aktiv.getSprite(),
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 9, width / 9);
					batch.draw(aktiv.getSpritesAnim()[0],
							(width / 9) * aktiv.getBahn() + (width / 17.5f)
									+ (realtime % 50 * 0.3f),
							height - (aktiv.getY() + width / 500), width / 25,
							width / 18);
					break;
				case 3:
					batch.draw(aktiv.getSprite(),
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 9, width / 9);
					break;
				case 4: 
					batch.draw(aktiv.getSprite(), 
							(width / 9) * aktiv.getBahn() + width/45,
							height - aktiv.getY() + width/45, width / 15 , width / 15);
					break; 
				case 5: 
					batch.draw(aktiv.getSprite(), 
							(width / 9) * aktiv.getBahn() + 0.5f*width/36,
							height - aktiv.getY(), width / 12, width / 12);
					break; 
				case 6: 
					batch.draw(aktiv.getSprite(), 
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 12, width / 12);
					break; 
				case 7: 
					batch.draw(aktiv.getSprite(), 
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 12, width / 12);
					break; 				
				case 8:
					batch.draw(aktiv.getSprite(),
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 9, width / 18);
					batch.draw(aktiv.getSpritesAnim()[0],
							(width / 9) * aktiv.getBahn() + width / 36, height
									- aktiv.getY(), width / 36, width / 36,
							width / 18, width / 18, 1, 1, (wellen_y_pos));
					break;
				default:
					batch.draw(aktiv.getSprite(),
							(width / 9) * aktiv.getBahn(),
							height - aktiv.getY(), width / 9, width / 9);
					break;
				}
			}
		}

		// Score-Anzeige
		font.setColor(Color.BLACK);

		font.draw(batch, "Score " + score, 460, 465);
		

		// Level-Anzeigen
		font.setColor(Color.BLACK);
		font.draw(batch, "Level " + level, 360, 465);

		if (score % 50 < 4) {
			gameover.draw(batch, "Level " + level, width / 2, height / 2);
		}

        //Taucherbrille Anzeige
        
		if (brillen == 1){
			batch.draw(taucherbrille_sprite, 40, 10, width/12, height/12);
		}
		if (brillen == 2){
			batch.draw(taucherbrille_sprite, 40, 10, width/12, height/12);
			batch.draw(taucherbrille_sprite, 95, 10, width/12, height/12);
		}
		if (brillen == 3){
			batch.draw(taucherbrille_sprite, 40, 10, width/12, height/12);
			batch.draw(taucherbrille_sprite, 95, 10, width/12, height/12);
			batch.draw(taucherbrille_sprite, 150, 10, width/12, height/12);
		}
        
		// Herzen update
		if (health >= 5) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_voll, 90, 440, width / 18, height / 18);
			batch.draw(herz_voll, 125, 440, width / 18, height / 18);
			batch.draw(herz_voll, 160, 440, width / 18, height / 18);

		} else if (health == 4) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_voll, 90, 440, width / 18, height / 18);
			batch.draw(herz_voll, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);

		} else if (health == 3) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_voll, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);

		} else if (health == 2) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);

		} else if (health == 1) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_leer, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);
		}

		else if (health == 0) {
			batch.draw(herz_leer, 19, 440, width / 18, height / 18);
			batch.draw(herz_leer, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);
		}

		else {
			batch.draw(herz_leer, 19, 440, width / 18, height / 18);
			batch.draw(herz_leer, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);
			geschwindigkeit = 0;
		}
		
		long t = TimeUtils.timeSinceMillis(anim_feedback_time);
		if(t < 500 && (t % 200 < 100)){
			font.draw(batch, "SCORE +10!", width/2 - 40, 400);
		}
		t = TimeUtils.timeSinceMillis(anim_down_to_dive_time);
		if(t < 1000){
			font.draw(batch, "Press DOWN to dive!", width / 2 - 80, 350);
		}
		
		batch.end();
	}

	// Methode um die Tauchwelt zu rendern
	private void render_lowerworld() {
		// Hintergrundfarbe
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(Timestep, 6, 2);
		tauchersprite.setPosition(body.getPosition().x, body.getPosition().y);

		if (body.getPosition().y < 0) {
			body.setLinearVelocity(0, 0);
			body.setTransform(0, 0, 0);
		}


        batch.begin(); 	
        
        
        //Hintergrundanimation
  		batch.draw(hintergrund1, 0, -10 - unter_wasser_textur_pos, width, height - height/8);
  		batch.draw(hintergrund2, 0, -10 - 3*unter_wasser_textur_pos, width, height - height/8);
  		batch.draw(hintergrund3, 0, -10 - 7*unter_wasser_textur_pos, width, height - height/8);
  		batch.draw(hintergrund4, 0, -10 - 10*unter_wasser_textur_pos, width, height - height/8);
  		
        // Taucher
  		//Animation
  		if(!blinkInvuln())
  		{
	        batch.draw(taucher_linkes_bein, tauchersprite.getX()-taucher_body_width/3 +width/10, tauchersprite.getY() + taucher_body_width/2 + 3.5f*(float) Math.sin(8*unter_wasser_textur_pos), taucher_body_width/2, taucher_body_width/4);
	        batch.draw(taucher_rechtes_bein, tauchersprite.getX()-taucher_body_width/3 +width/10, tauchersprite.getY() + taucher_body_width/2.5f - 3.5f*(float) Math.sin(8*unter_wasser_textur_pos), taucher_body_width/2, taucher_body_width/4);             
	        batch.draw(tauchersprite, tauchersprite.getX()+width/10, tauchersprite.getY(), taucher_body_width, taucher_body_width);
  		}
        //Luftblasen
        if(luftblasen_x_pos<(0-luftblasen.getWidth()) || luftblasen_y_pos>height) init_luftblasen();
        batch.draw(luftblasen, luftblasen_x_pos, luftblasen_y_pos, taucher_width/2, taucher_width);
        
        //Hindernisse
        if(hindernis_lowerworld_lower.getLaenge()>0){
        	for(int i = 0; i<20; i=i+2){
        		batch.draw(hindernis_lowerworld_lower.getSprite(), hindernis_lowerworld_lower.getX() + i/2*(width/8), hindernis_lowerworld_lower.getY() - wand_punkte[i],width/8, height);
        		batch.draw(hindernis_lowerworld_upper.getSprite(), hindernis_lowerworld_upper.getX() + i/2*(width/8), hindernis_lowerworld_upper.getY() + wand_punkte[i+1],width/8, height);
        	}
        } else if(hindernis_lowerworld_lower.getLaenge()>(-10)){
        	for(int i = 0; i<(20+2*hindernis_lowerworld_lower.getLaenge()); i=i+2){
        		batch.draw(hindernis_lowerworld_lower.getSprite(), hindernis_lowerworld_lower.getX() + i/2*(width/8), hindernis_lowerworld_lower.getY() - wand_punkte[i],width/8, height);
        		batch.draw(hindernis_lowerworld_upper.getSprite(), hindernis_lowerworld_upper.getX() + i/2*(width/8), hindernis_lowerworld_upper.getY() + wand_punkte[i+1],width/8, height);
        	}
        }

		// Score-Anzeige
		font.setColor(Color.BLACK);
		font.draw(batch, "Score: " + score, 470, 465);
		
		// Level-Anzeigen
		font.setColor(Color.BLACK);
		font.draw(batch, "Level " + level, 360, 465);

		
		if (score % 50 < 4) {
			gameover.draw(batch, "Level " + level, width / 2, height / 2);
		}

		// Herzen update
		if (health >= 5) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_voll, 90, 440, width / 18, height / 18);
			batch.draw(herz_voll, 125, 440, width / 18, height / 18);
			batch.draw(herz_voll, 160, 440, width / 18, height / 18);

		} else if (health == 4) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_voll, 90, 440, width / 18, height / 18);
			batch.draw(herz_voll, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);

		} else if (health == 3) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_voll, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);

		} else if (health == 2) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_voll, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);

		} else if (health == 1) {
			batch.draw(herz_voll, 19, 440, width / 18, height / 18);
			batch.draw(herz_leer, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);
		}

		else if (health == 0) {
			batch.draw(herz_leer, 19, 440, width / 18, height / 18);
			batch.draw(herz_leer, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);
		}

		else {
			batch.draw(herz_leer, 19, 440, width / 18, height / 18);
			batch.draw(herz_leer, 55, 440, width / 18, height / 18);
			batch.draw(herz_leer, 90, 440, width / 18, height / 18);
			batch.draw(herz_leer, 125, 440, width / 18, height / 18);
			batch.draw(herz_leer, 160, 440, width / 18, height / 18);
			geschwindigkeit = 0;
		}

		batch.end();

	}
	
	
	private void initLogo(){
		logo_size = 1.0f;
		logo_start = TimeUtils.millis();
	}
	private void animateLogo(){
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(logo_sprite, (width - logo_size) / 2, (height - logo_size) / 2 + 100, logo_size, logo_size);
		batch.end();
		long t = TimeUtils.timeSinceMillis(logo_start);
		logo_size += t / 500;
		if(logo_size >= 256){
			logo_size = 256; 
		}
		if(t > 2500){
			returnToMainMenu();
		}
	}
	
	public void renderLogo(){
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(logo_sprite, (width - 256) / 2, (height - 256) / 2 + 100);
		batch.end();
	}
	
	//Helpermethods
	
	private void hindernis_Generator(){
		h = 0;

		
		//zweite Version des Hindernisgenerators
		//erstellt ein zufälliges Hindernis von Typ 0 bis n_obstacles-1 auf einer zufälligen Bahn
		//Auswahl des Typen des Hindernisses erfolgt über Exponentialverteilung
		//Auswahl der Anzahl Hindernisse in einer Zeile erfolgt über Poisson-Verteilung
		//zukünftige Hindernisse können in den "Hindernis-buffer" geladen werden
		//Falls dieser nichtleer ist, werden die Hindernisse aus dem buffer generiert, ansonsten mit oben beschriebener Zufälligkeit
		

		//Hindernisse aus buffer laden
		if (buffer.getSize()!=0){
			int[] akt_zeile = buffer.getNextZeile();
			for (int i=1;i<8;i++){
				gen_obstacle(akt_zeile[i-1],i);
			}
			return;
		}
		//Auswahl Anzahl Bahnen wo ein Hindernis generiert wird
		//sei p array mit Poissonverteilung bereits initialisiert
		//init p[0]=0;


		int[] counts = new int[]{6,21,35};
		int n=choice(p,7,1)-1;
		if (n==0){
			return;
		}
		// Auswahl Bahnen konkret
		// wird in array bahnen gespeichert
		int[] bahnen = new int[n];
		int[] bahnen_final = new int[n];

		int count = counts[(int)(-Math.abs(n-3.5)+3.5)-1];
		//m ist der Index der Liste aller Teilmengen der Mächtigkeit n von {1,..,7}
		int m = (int)(Math.random()*count);
		bahnen = get_bahnen(m,(int)(-Math.abs(n-3.5)+3.5));
		//falls es 4,5,6 Bahnen sind, müssen die ausgewählten/nicht ausgewählten Bahnen invertiert werden
		if (n>3){

			int j = 0;
			for (int i = 1; i < 8; i++) {
				boolean in_bahnen = false;
				for (int k = 0; k < 7 - n; k++) {
					if (bahnen[k] == i) {
						in_bahnen = true;
					}
				}
				if (!in_bahnen) {
					bahnen_final[j] = i;
					j++;
				}
			}
		} else {
			for (int i = 0; i < n; i++) {
				bahnen_final[i] = bahnen[i];
			}
		}
		// erzeuge Wahrscheinlichkeit-Verteilung zur Auswahl des Typen des
		// Hindernisses
		double[] p_typ = new double[n_obstacles + 1];
		p_typ[0] = 0;
		for (int i = 1; i < n_obstacles + 1; i++) {
			if (level < difficulty[i - 1]) {
				p_typ[i] = 0;

			}
			else if (level>=difficulty[i-1]+obstacle_ausdauer){
				switch (distribution_type[i-1]){
					case 2: p_typ[i] = 0.01;
							break;
					case 1: p_typ[i] = 2 * first_probability[i-1];
							break;
					case 0: p_typ[i] = first_probability[i-1];
							break;
				}
			}
			else {
				p_typ[i] = obstacle_probability[i-1][(int)(level)-difficulty[i-1]];
			}
		}
		double sum = 0;
		double sum_without_constant_obstacles = 0;
		for (int i=1;i<n_obstacles+1;i++){
			if (distribution_type[i-1]!=0){
				sum += p_typ[i];
			}
			else{
				sum_without_constant_obstacles += p_typ[i];
			}
		}
		sum_without_constant_obstacles = 1 - sum_without_constant_obstacles;
		for (int i=1;i<n_obstacles+1;i++){
			if (distribution_type[i-1]!=0){
				p_typ[i] /= (sum / sum_without_constant_obstacles);
			}
		}
		//iteriere i über jede ausgewählte Bahn
		for (int i=0;i<n;i++){
			gen_obstacle(choice(p_typ,n_obstacles,1)-1,bahnen_final[i]);

		}

		for (int i=0;i<50;i++){
			//wenn ein Schwan generiert wurde, entferne alle anderen Hindernisse dieser Zeile
			if (hindernis_aktiv[i]&&(hindernis[i].getType()==3)&&(hindernis[i].getLine()==score)){
				for (int k=0;k<50;k++){
					if (hindernis_aktiv[k]&&(hindernis[k].getLine()==score)&&(i!=k)){
						hindernis_aktiv[k] = false;
					}
				}
			}

			//teste, ob es einen path für den swimmer gibt, falls nicht, lösche ausgewählte Hindernisse
			else if (hindernis_aktiv[i]&&(hindernis[i].getType()!=3)&&(hindernis[i].getLine()==score)){
				for (int k=0;k<50;k++){
					if(hindernis_aktiv[k]&&(hindernis[k].getLine()==score-1)&&((hindernis[i].getBahn()==hindernis[k].getBahn()+1)||(hindernis[i].getBahn()==hindernis[k].getBahn()-1))){
						hindernis_aktiv[i] = false;
					}
				}
			}
		}
	}

	
	//ein neu generiertes Hindernis erzeugen
	private void gen_obstacle(int type,int bahn){
		if (type==-1){
			return;
		}
		/*if (type==6){
			int[] z = new int[7];
			for (int k=0;k<7;k++){
				z[k] = 1;
			}
			buffer.addZeile(z);
			int[] w = new int[7];
			for (int k=0;k<7;k++){
				w[k] = 2;
			}
			buffer.addZeile(w);
			int[] u = new int[7];
			u[0] = 0;
			u[1] = 2;
			u[2] = 1;
			u[3] = 1;
			u[4] = 1;
			u[5] = 3;
			u[6] = -1;
			buffer.addZeile(u);
		}*/

		int i = 0;
		while (hindernis_aktiv[i]) {
			i++;
		}

		if (i<50){
			hindernis[i] = init_obstacle(type,bahn);
			hindernis[i].setLine(score);
			hindernis_aktiv[i] = true;
		}
	}

	// Hilfsfunktion für den Hindernisgenerator
	private int[] get_bahnen(int m, int n) {
		int help = -1;
		int[] a = new int[n];
		int[] b = new int[3];
		if (n == 3) {
			for (b[0] = 1; b[0] < 8; b[0]++) {
				for (b[1] = b[0] + 1; b[1] < 8; b[1]++) {
					for (b[2] = b[1] + 1; b[2] < 8; b[2]++) {
						help++;
						if (help == m) {
							for (int i = 0; i < n; i++) {
								a[i] = b[2 - i];
							}
							return a;
						}
					}
				}
			}
		} else if (n == 2) {
			for (b[0] = 1; b[0] < 8; b[0]++) {
				for (b[1] = b[0] + 1; b[1] < 8; b[1]++) {
					help++;
					if (help == m) {
						for (int i = 0; i < n; i++) {
							a[i] = b[1 - i];
						}
						return a;
					}
				}
			}
		} else {
			for (b[0] = 1; b[0] < 8; b[0]++) {
				help++;
				if (help == m) {
					a[0] = b[0];
					return a;
				}
			}
		}
		return a;
	}

	// choice wählt zufällig einen Index des arrays d, welches die W-Verteilung
	// dieser Auswahl darstellt,
	// Wertebereich 1-L, gibt a aus, falls nichts ausgewählt wurde
	// init ar[0]=0;
	private int choice(double[] ar, int L, int a) {
		double help = 1;
		for (int i = 1; i < L + 1; i++) {
			double r = Math.random();
			help *= 1 - ar[i - 1];
			if (r < ar[i] / help) {
				return i;
			}
		}
		return a;
	}

	
	//Fakultätsfunktion für die Poissonverteilung
	private int fact(int n){
        int fact = 1;
        for (int i=1;i<=n;i++){
            fact *= i;
        }
        return fact;
    }
	
	//initialisiere Hindernistyp
	private void init_obstacle_type(int type, int first_lvl, double first_prob, int dist_type){
		difficulty[type] = first_lvl;
		first_probability[type] = first_prob;
		distribution_type[type] = dist_type;
	}

	
	private void generate_boss_obstacle(int type, int size){
		switch(type){
		//zufällig generierter enger Pfad der Länge size
		case 0:
			buffer.changeSize(buffer.getSize()+2*size+6);
			buffer.addLeerzeilen(5);
			int current_bahn = (int)(Math.random()*7);
			int[][] w = new int[2*size][7];
			for (int i=0;i<size;i++){
				int r = (int)(Math.random()*3)-1;
				Arrays.fill(w[2*i],0);
				w[2*i][current_bahn] = -1;
				buffer.addZeile(w[2*i]);
				Arrays.fill(w[2*i+1],0);
				if (r!=0){
					w[2*i+1][current_bahn] = -1;
				}
				current_bahn += r;
				if (current_bahn<0){
					current_bahn = 0;
				}
				if (current_bahn>6){
					current_bahn = 6;
				}
				w[2*i+1][current_bahn] = -1;
				buffer.addZeile(w[2*i+1]);
			}
			int[] z = new int[7];
			Arrays.fill(z,-1);
			z[current_bahn] = 4;
			buffer.addZeile(z);
			break;
		}
	}
	
	private void reset_obstacles(){
		for(int i=0;i<50;i++){
		    hindernis_aktiv[i]=false;
		}
	}


	public void render_gameover() {
		String gameoverstring = "GAME OVER";
		batch.begin();
		GlyphLayout gl = new GlyphLayout(gameover, gameoverstring);
		float left = (Gdx.graphics.getWidth() - gl.width) / 2;
		float bottom = Gdx.graphics.getHeight() - (gl.height + 10);
		gameover.draw(batch, gameoverstring, left, bottom);
		gameover.setColor(Color.WHITE);
		batch.end();
	}

	public GameState getState() {
		return state;
	}

	
	public int getBrillen(){
		return brillen;
	}




	private void hindernis_Generator_dive_init() {
		// Die beiden ersten Hindernisse generieren

		float w0 = 2 * height / 3 * (float) Math.random() + height / 3;
		float w1 = 2 * height / 3 * (float) Math.random() + height / 3;

		while ((w1 - (hindernis_lowerworld_lower.getSprite().getHeight() - w0) < taucher_width)){

			w0 = 2 * height / 3 * (float) Math.random() + height / 3;
			w1 = 2 * height / 3 * (float) Math.random() + height / 3;

		}

		wand_punkte[16] = w0;
		wand_punkte[17] = w1;


		while ((w1 - (hindernis_lowerworld_lower.getSprite().getHeight() - w0) < 5 / 6 * taucher_width)
				|| (hindernis_lowerworld_lower.getSprite().getHeight() - w0 + 5
						/ 6 * taucher_width > wand_punkte[17])
				|| (hindernis_lowerworld_lower.getSprite().getHeight()
						- wand_punkte[16] + 5 / 6 * taucher_width > w1)) {

			w0 = 2 * height / 3 * (float) Math.random() + height / 3;
			w1 = 2 * height / 3 * (float) Math.random() + height / 3;

		}

		wand_punkte[18] = w0;
		wand_punkte[19] = w1;

		for (int i = 0; i < 16; i++) {
			hindernis_Generator_dive();
		}
	}

	private void hindernis_Generator_dive() {

		verschiebe_wandpunkte();

		float w0 = 2 * height / 3 * (float) Math.random() + height / 3;
		float w1 = 2 * height / 3 * (float) Math.random() + height / 3;

		//Letzte beide generierte Hindernisse abfragen -> entsteht ein machbares Labyrinth?
		while ((w1 - (hindernis_lowerworld_lower.getSprite().getHeight() - w0) < taucher_width) || (hindernis_lowerworld_lower.getSprite().getHeight()- w0 +  taucher_width > wand_punkte[17]) || (hindernis_lowerworld_lower.getSprite().getHeight()-wand_punkte[16]+ taucher_width > w1)) {


			w0 = 2 * height / 3 * (float) Math.random() + height / 3;
			w1 = 2 * height / 3 * (float) Math.random() + height / 3;

		}

		wand_punkte[18] = w0;
		wand_punkte[19] = w1;

	}

	public void startGame() {

		resetGameVariables();
		state = GameState.UPPERWORLD;
		current_music.stop();
		current_music = music_upper;
		if(music_enabled){
			current_music.play();
		}
		menu.unloadMenu();
	}

	public void pauseGame(boolean p) {
		if (p && p != paused) {
			menu.loadPauseMenu();
		} else if (!p) {
			menu.unloadMenu();
		}
		setTimestep(p);
		paused = p;
	}
	
	public void toggleMusic(){
		if(current_music.isPlaying()){
			current_music.stop();
			music_enabled = false;
		}
		else{
			current_music.play();
			music_enabled = true;
		}
	}

	public void setGameOver() {
		game_over = true;
		setTimestep(true);
		current_music.stop();
		if (highscore.isHighscore(score)) {
			menu.loadHighscoreInput(score);
		} else {
			menu.loadGameOverMenu();
		}
	}
	
	public void setTimestep(boolean p) {
		
		if (p == true) {
			Timestep = 0;
		}
		
		if (p == false) {
			Timestep = Gdx.graphics.getDeltaTime();
		}
		
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean musicIsPlaying(){
		return current_music.isPlaying();
	}
	
	public boolean isGameOver() {
		return game_over;
	}

	public boolean isFrozen() {
		return freeze;
	}

	public void changeInvuln(){
		invulnerable = !invulnerable;
	}
	
	public void returnToMainMenu() {
		paused = false;
		current_music.stop();
		menu.loadMainMenu();
		state = GameState.MAINMENU;
		Arrays.fill(hindernis_aktiv, false);
		wand_punkte = wand_punkte_init;

	}

	public void endApplication() {
		menu.unloadMenu();
		Gdx.app.exit();
	}

	public void changeDiveState() {
	
		if (state == GameState.UPPERWORLD) {	
			//music ändern
			current_music.stop();
			current_music = music_lower;
			
			if(music_enabled){
				current_music.play();
			}

			//Brille verbrauchen
			brillen--;

			//wand_punkte = wand_punkte_init;
			
			Timestep = Gdx.graphics.getDeltaTime();
			state = GameState.LOWERWORLD;
			body.setLinearVelocity(0, 0);
			body.setTransform(0, 100, 0);
			
	
			//Unterwasser-Hindernis initialisieren
			//TODO: -> Dynamisch annpassen -> Obstacle ueber init_Obstacle_lowerworld-Methode erzeugen
			hindernis_lowerworld_low = new Sprite(felsen_unter_wasser);
			hindernis_lowerworld_up = new Sprite(felsen_unter_wasser);
			hindernis_lowerworld_up.flip(true, false);
	
			hindernis_lowerworld_lower  = new Obstacle(hindernis_lowerworld_low, 100, (float)2*width/3, 0.0f, 20);
			hindernis_lowerworld_upper  = new Obstacle(hindernis_lowerworld_up, 100, (float)2*width/3, 0.0f, 20);
			hindernis_lowerworld_upper.getSprite().flip(false, true);
	
			//Hindernis-Generator anwerfen
			hindernis_Generator_dive_init();

			// TODO Dispose einfügen
		} else {
			Arrays.fill(hindernis_aktiv, false);
			state = GameState.UPPERWORLD;
			
			// music_lower ändern
			current_music.stop();
			current_music = music_upper;
			if(music_enabled){
				current_music.play();
			}
		}
	
	}

	protected void changeSwimmerPosition_swim(int change) {
		swimmer_position_swim += change;
		if (swimmer_position_swim < 1) {
			swimmer_position_swim = 1;
		}
		if (swimmer_position_swim > 7) {
			swimmer_position_swim = 7;
		}
	}

	protected void changeSwimmerPosition_dive(int change) {

		body.applyForceToCenter(0, 20000 * change, true);

	}

	//Kollision und Unsterblichkeit
	public boolean meetObstacle(Obstacle obs, Sprite swimmer) {
		if (swimmer_position_swim == obs.getBahn()) {
			if (width * 8 / 9 - obs.getY() < 2.5 * swimmer_width) {
				return true;
			}
	 		
		}
		return false;
	}

		
	public boolean collision_dive(){
		
		if(hindernis_lowerworld_lower.getLaenge() > -10){
		
			if(body.getPosition().x + width/10 - width/36 >= hindernis_lowerworld_upper.getX() + width/8 - width/9 && body.getPosition().x + width/10 - width/36 <= hindernis_lowerworld_upper.getX() + 2*width/8) {
								
				if((body.getPosition().y + 0.25*width/12 < height - wand_punkte[2]) || (body.getPosition().y + 0.75*width/12 > wand_punkte[3])){
					
					return true;
					
				}
			

			}

			
			if(body.getPosition().x + width/10 - width/36 >= hindernis_lowerworld_upper.getX() + 2*width/8 - width/9 && body.getPosition().x + width/10 - width/36 <= hindernis_lowerworld_upper.getX() + 3*width/8) {

				if((body.getPosition().y + 0.25*width/12 < height - wand_punkte[4]) || (body.getPosition().y + 0.75*width/12 > wand_punkte[5])){
					
					
					return true;
					
				}
				
			}
			
			else {
				
				return false;
				
			}
		
		}	
		return false;
	}



	private void update_graphics() {				
			
		if (state == GameState.UPPERWORLD) {
			wellen_y_pos = (wellen_y_pos - geschwindigkeit) % height;
			arm_pos += 10 % 314;
			arm_pos_x = swimmer_width / 8
					* (float) Math.sin(0.01 * arm_pos - 1.5);
			arm_pos_y = swimmer_width / 8 * (float) Math.sin(0.01 * arm_pos);

			// Update Hindernisse
			for (int i = 0; i < 50; i++) {
				if (hindernis_aktiv[i]) {

					Obstacle aktiv = hindernis[i];
					int aktiv_type = aktiv.getType();
					switch (aktiv_type) {
					case 0:
					case 1:
					case 2:
						aktiv.setY(aktiv.getY() + geschwindigkeit);
						break;
					case 3:
						// Bahn wechseln -> nach rechts oder nach links?
						if (realtime % schwan_speed == 0
								&& aktiv.getRichtung() == 1) {
							// Richtungswechsel
							if (aktiv.getBahn() == 7) {
								aktiv.setRichtung(2);
								Sprite temp = aktiv.getSprite();
								temp.flip(true, false);
								aktiv.setSprite(temp);
							} else
								aktiv.setBahn(aktiv.getBahn() + 1);
						} else if (/* aktiv.getY() */realtime % schwan_speed == 0
								&& aktiv.getRichtung() == 2) {
							// Richtungswechsel
							if (aktiv.getBahn() == 1) {
								aktiv.setRichtung(1);
								Sprite temp = aktiv.getSprite();
								temp.flip(true, false);
								aktiv.setSprite(temp);
							} else
								aktiv.setBahn(aktiv.getBahn() - 1);
						}
						aktiv.setY(aktiv.getY() + geschwindigkeit);
						break;
					case 4:
					case 5:
					case 6: 
					case 7: 
					case 8:
						aktiv.setY(aktiv.getY() + geschwindigkeit);
						break;
					default:
						aktiv.setY(aktiv.getY() + geschwindigkeit);
						break;
					}
					// Hindernisse auf false setzen (= loeschen), wenn aus
					// Fenster
					if (aktiv.getY() > aktiv.getSprite().getHeight() + height)
						hindernis_aktiv[i] = false;
				}
			}

		} else if (state == GameState.LOWERWORLD) {
			// Bewegung Hintergrundtextur
			unter_wasser_textur_pos = ((float) Math.sin((double) 0.05f
					* zeit_unter_wasser));
			zeit_unter_wasser = (zeit_unter_wasser + 1) % 200;
			taucher_width = width / 9;
			taucher_body_width = width / 12;

			// Luftblasen
			luftblasen_x_pos -= hindernis_geschwindigkeit;
			luftblasen_y_pos += (hindernis_geschwindigkeit / 2 + Math
					.sin(0.2 * luftblasen_x_pos));

			// Bewegung Hindernisse
			hindernis_lowerworld_upper.setX(hindernis_lowerworld_upper.getX()
					- hindernis_geschwindigkeit);
			hindernis_lowerworld_lower.setX(hindernis_lowerworld_lower.getX()
					- hindernis_geschwindigkeit);
			if (hindernis_lowerworld_lower.getX() < 0 - width / 8
					&& hindernis_lowerworld_lower.getLaenge() > 0) {
				hindernis_Generator_dive();
				hindernis_lowerworld_lower.setX(hindernis_lowerworld_lower
						.getX() + width / 8);
				hindernis_lowerworld_upper.setX(hindernis_lowerworld_upper
						.getX() + width / 8);
				hindernis_lowerworld_lower.setLaenge(hindernis_lowerworld_lower
						.getLaenge() - 1);
				hindernis_lowerworld_upper.setLaenge(hindernis_lowerworld_upper
						.getLaenge() - 1);
			} else if (hindernis_lowerworld_lower.getX() < 0 - width / 8
					&& hindernis_lowerworld_lower.getLaenge() <= 0
					&& hindernis_lowerworld_lower.getLaenge() > (-10)) {
				verschiebe_wandpunkte();
				hindernis_lowerworld_lower.setX(hindernis_lowerworld_lower
						.getX() + width / 8);
				hindernis_lowerworld_upper.setX(hindernis_lowerworld_upper
						.getX() + width / 8);
				hindernis_lowerworld_lower.setLaenge(hindernis_lowerworld_lower
						.getLaenge() - 1);
				hindernis_lowerworld_upper.setLaenge(hindernis_lowerworld_upper
						.getLaenge() - 1);
			}

			loop = (loop - hindernis_geschwindigkeit);
		}

	}

	private void readGraphics() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		ppiX = Gdx.graphics.getPpiX();
		ppiY = Gdx.graphics.getPpiY();



	}

	private void startFreeze() {
		freeze = true;
		
		timer.schedule(new TimerTask(){
			public void run(){
				freeze = false;
			}
		}, 400);
	}
	private void startInvuln(){
		invulnerable = true;
		
		timer.schedule(new TimerTask(){
			public void run(){
				invulnerable = false;
			}
		}, 1900);
	}
	
	private boolean blinkInvuln(){
		boolean blink = TimeUtils.millis() % 200 > 100;
		return  invulnerable && blink;
	}

	private void update_variables_swim() {
		// Hindernis-Generator-Aufruf
		if (h >= width / 9) {
			hindernis_Generator();
			score++;
			geschwindigkeit += beschleunigung;
			if (geschwindigkeit > max_speed) {
				geschwindigkeit = max_speed;
		
			}
		}
		h += geschwindigkeit;
		realtime++;
		if (realtime == schwan_speed) {
			realtime = 0;
		}

		//Boss-Hindernis
		if (level<(score/50)+1&&level%10==9){
			generate_boss_obstacle(0,(int)(2*level));
		}

		//Andere Game-Variablen
		level = (score/50)+1;
		swimmer_offset = ((width-2) / 9) * 1/8;
		swimmer_width = ((width-2) / 9) * 3/4;


		// Kollisionsabfrage
		for (int i = 0; i < 50; i++) {
			if (hindernis_aktiv[i]) {
				if (meetObstacle(hindernis[i], swimmer)) {

					if (hindernis[i].getType()==4) {
						if (health < 5){
							health++;
						}
					}
					else if (hindernis[i].getType()==5) {
						score += 10;
						anim_feedback_time = TimeUtils.millis();

						if(music_enabled){
							coin_collected.play();
						}
				} 
					else if (hindernis[i].getType()==6){
						if (geschwindigkeit > 1f){
							geschwindigkeit -= 0.7f;
							if(music_enabled){
								clock.play();
							}
						}
					}
					else if (hindernis[i].getType()==7){
						if (brillen < 3){
						brillen ++;
						anim_down_to_dive_time = TimeUtils.millis();
						if(music_enabled){
							brille_collected.play();
						}
					}
						}	
					else if(!invulnerable){
					health--;
					if(music_enabled){
						shark.play();
					}	
					startFreeze();
					startInvuln();
					}
				    hindernis_aktiv[i]=false;  
				} 
			}
		}
		
		// GameOver check
		if (health <= 0) {
			setGameOver();
		}
	}

	private void update_variables_dive() {

		// TODO festlegen, ab wann der taucher wieder kollidieren kann

		// invulnerable = false;

		tauchersprite.setPosition(body.getPosition().x, body.getPosition().y);

		if (body.getPosition().y > height - taucher_width / 2) {
			changeDiveState();
		}
		if (body.getPosition().y < 0) {
			body.setLinearVelocity(0, 0);
			body.setTransform(0, 0, 0);
		}

		//Andere Game-Variablen
		level = (score/50)+1;
		if (h >= width / 9) {
			score++;
			h = 0;
		}
		h += hindernis_geschwindigkeit;


		// Kollisionsabfrage

		
		if(invulnerable == false){
			
			if(collision_dive()){
				
				health--;
				//freeze = true;
				invulnerable = true;
								
				timer.schedule(new TimerTask(){
					
					public void run() {
						changeInvuln();
					}
					
				}, 1000);
				
			}
		}
		
		//auftauchen
		if(hindernis_lowerworld_lower.getLaenge() <= (-10)) body.setLinearVelocity(0, 1000);
		// GameOver check
		if (health <= 0) {
			setGameOver();

		}


	}

	
	
	// init Klasse, um Obstacle-Objekte zu erzeugen
	private Obstacle init_obstacle(int type, int bahn) {
		Obstacle new_obstacle;

		switch(type){
			case 0: 
				Sprite felsen_sprite = new Sprite(hindernis_felsen);
				felsen_sprite.setSize(width/9, height/9);
				new_obstacle = new Obstacle(felsen_sprite, 0, bahn, 0.0f);
				break;
			case 1:
				Sprite seerosen_sprite = new Sprite(seerosen);
				seerosen_sprite.setSize(width/9, height/9);
				Sprite seerosen_mund_sprite = new Sprite(seerosen_mund);
				seerosen_mund_sprite.setOriginCenter();
				Sprite zaehne_oben = new Sprite(seerose_zaehne);
				Sprite zaehne_unten = new Sprite(seerose_zaehne);
				zaehne_unten.flip(false, true);
				Sprite[] sprites_anim = new Sprite[3];
				sprites_anim[0] = seerosen_mund_sprite;
				sprites_anim[1] = zaehne_oben;
				sprites_anim[2] = zaehne_unten;
				new_obstacle = new Obstacle(seerosen_sprite, 1, bahn, 0.0f, 3, sprites_anim);
				break;
			case 2:
				Sprite hai_sprite = new Sprite(hai_1);
				hai_sprite.setSize(width/9, height/9);
				Sprite haikinn = new Sprite(hai_2); 
				Sprite[] sprites_anim_2 = new Sprite[1];
				sprites_anim_2[0] = haikinn;
				new_obstacle = new Obstacle(hai_sprite, 2, bahn, 0.0f, 1, sprites_anim_2);
				break;
			case 3:
				Sprite schwan_sprite = new Sprite(rennschwan);
				schwan_sprite.setSize(width/9, height/9);
				new_obstacle = new Obstacle(schwan_sprite, 3, bahn, 0.0f);
				//Richtung
				new_obstacle.setRichtung((int)(Math.random()*2+1));
				if (new_obstacle.getRichtung()==1){
				  new_obstacle.getSprite().flip(true, false);
				}
				break;
			case 4:
				Sprite herz_voll2 = new Sprite(guteherzen);
				herz_voll2.setSize(width/15, height/15);
				new_obstacle = new Obstacle(herz_voll2, 4, bahn, 0.0f);
				break;
			case 5: 
				Sprite muenze = new Sprite(coin);
				muenze.setSize(width/12, height/12);
				new_obstacle = new Obstacle(muenze, 5, bahn, 0.0f);
				break;
			case 6: 
				Sprite stopuhr = new Sprite(stopwatch);
				stopuhr.setSize(width/12, height/11);
				new_obstacle = new Obstacle(stopuhr, 6, bahn, 0.0f);
				break;
			case 7: 
				Sprite taucherbrille = new Sprite(brille);
				taucherbrille.setSize(width/12, height/11);
				new_obstacle = new Obstacle(taucherbrille, 7, bahn, 0.0f);
				break; 
			case 8:
				Sprite loch_sprite = new Sprite(hindernis_tauchbar_loch);
				loch_sprite.setSize(width/9, height/9);
				Sprite strudel_sprite = new Sprite(hindernis_tauchbar_strudel); 
				strudel_sprite.setOrigin(width/18, width/18);
				Sprite[] sprites_anim_3 = new Sprite[1];
				sprites_anim_3[0] = strudel_sprite;
				new_obstacle = new Obstacle(loch_sprite, 8, bahn, 0.0f, 1, sprites_anim_3);

				break;
			default: 
				Sprite default_sprite = new Sprite(hindernis_felsen);
				default_sprite.setSize(width/9, height/9);
				new_obstacle = new Obstacle(default_sprite, 0, bahn, 0.0f);
				break;

		}
		return new_obstacle;

	}

	private void init_luftblasen() {
		luftblasen_x_pos = tauchersprite.getX() + taucher_width + taucher_width
				/ 8;
		luftblasen_y_pos = tauchersprite.getY() + taucher_width;
	}

	@Override
	public void dispose() {
		music_upper.dispose();
		music_lower.dispose();
		batch.dispose();


	}

	// Hilfsfunktion wandpunkte verschieben
	private void verschiebe_wandpunkte() {
		for (int i = 0; i < 18; i++) {

			wand_punkte[i] = wand_punkte[i + 2];

		}
	}

}