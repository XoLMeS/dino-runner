package dino;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dino_Runner_Servlet extends HttpServlet{
	
	// ATTRS
	private static final int HEIGHT = 600;
	private static final int WIDTH = 1000;
	
	// OBJECTS
	private ArrayList<String> objects = new ArrayList<String>();
	
	
	// COLORS
	private String alert_color = "0xf88600";
	private String record_color = "0x800080";
	private String score_color = "0x0010ff";
	private String coins_score_color = "0xf8ed67";
	// OTHER
	private int id = 1;
	private int ground_speed = 5;
	
	// USER INFO
	private int conins_cap = 0;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		resp.getWriter().println(getContent(req, resp));
	}
		
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{ 
	
	}

	private String getContent(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return 
				"<html> \n" 
						+"<head> \n" 
							+"<title>DINO-RUNNER</title> \n"
							+"<script type='text/javascript' src='js/jquery-2.2.3.js'></script> \n"
							+"<script src='https://code.createjs.com/soundjs-0.6.2.min.js'></script>"
							+"<link rel='stylesheet' type='text/css' href='css/main.css' />"
							//+"<link rel='stylesheet' type='text/css' href='css/game.css' />"
							//+"<script src='js/game.js'></script>"
							//+"<script src='js/main.js'></script>"
							+"<script src='js/pixi.js'></script> \n"
							+ getScript(req, resp)
						+"</head> \n"
						+"<body> \n"
							+"<div clas='header'>Header</div> \n"
							+"<div class='local_scores'>Local Scores</div> \n"
							+"<div class='footer'>Footer</div> \n"
						+"</body> \n"
				+"</html> \n";
		
	}
	
	private String getScript(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return 
				 "<script> \n"
					+"var objects = [];"
					+"var ground_array = [];"
					+"var local_scores = [];"
					+"var ground_speed = "+ground_speed+";"
					+"var id = 1;"
					+"var ticks = 0;"
					+"var last_tick = 0;"
					+"var coins_cap = "+conins_cap+";"
					+"var paused = false;"
					+"var started = false;"
					+"var failed = false;"
					+"var renderer = PIXI.autoDetectRenderer(" + WIDTH + ", " + HEIGHT + " ,{backgroundColor : 0xffffff}, true); \n" 
					
					// TEXTURES
					+"var dino_skin = PIXI.Texture.fromImage('images/dino_default.png'); \n" 
					+"var dino_run_1 = PIXI.Texture.fromImage('images/dino_run_1.png'); \n" 
					+"var dino_run_2 = PIXI.Texture.fromImage('images/dino_run_2.png'); \n" 
					+"var dino_jump = PIXI.Texture.fromImage('images/dino_jump.png'); \n" 
					+"var dino_fall = PIXI.Texture.fromImage('images/dino_fall.png'); \n" 
					+"var basic_ground = PIXI.Texture.fromImage('images/basic_ground.png'); \n" 
					+"var dino_duck = PIXI.Texture.fromImage('images/dino_duck_1.png'); \n" 
					+"var dino_duck_1 = PIXI.Texture.fromImage('images/dino_duck_2.png'); \n" 
					+"var dino_duck_2 = PIXI.Texture.fromImage('images/dino_duck_3.png'); \n" 
					+"var sound_on_icon = PIXI.Texture.fromImage('images/sound_on_icon.png'); \n" 
					+"var sound_off_icon = PIXI.Texture.fromImage('images/sound_off_icon.png'); \n" 
					+"var music_on_icon = PIXI.Texture.fromImage('images/music_on_icon.png'); \n" 
					+"var music_off_icon = PIXI.Texture.fromImage('images/music_off_icon.png'); \n" 
					
					+"var swap = true;"
					+"var stage = new PIXI.Container(); \n"
				
					
					// TEXT FIELDS
					+"var score = new PIXI.Text('Score: 0',{font : '24px Arial', fill : "+score_color+", align : 'center', dropShadow: true, dropShadowDistance: 2}); \n"
					+"var record = new PIXI.Text('Record: 0',{font : '24px Arial', fill : "+record_color+", align : 'center', dropShadow: true, dropShadowDistance: 2}); \n"
					+"var alert = new PIXI.Text('Press \"Space\" to start',{font : ' bold 34px Arial', fill : "+alert_color+", align : 'center', dropShadow: true, dropShadowDistance: 2}); \n"
					+"var coins_score = new PIXI.Text('X0',{font : ' bold 40px Arial', fill : "+coins_score_color+", align : 'center', dropShadow: true, dropShadowDistance: 4}); \n"
					//+"console.log(PIXI); \n"
						
						+"$(function(){ \n"
						
						// LOAD SOUNDS
							+"var main_theme;"
							+"var arrow_sound_muted = false;"
							+"var jump_sound_muted = false;"
							+"var shuriken_sound_muted = false;"
							+"var coin_sound_muted = false;"
							
							+"createjs.Sound.on('fileload', handleLoad);"
							
							+"createjs.Sound.registerSound('sounds/jump_sound.mp3', 'jump_sound',2);"
							+"createjs.Sound.registerSound('sounds/coin_sound.mp3', 'coin_sound',2);"
							+"createjs.Sound.registerSound('sounds/arrow_sound.mp3', 'arrow_sound',2);"
							+"createjs.Sound.registerSound('sounds/shuriken_sound.mp3', 'shuriken_sound',2);"
							+"createjs.Sound.registerSound('sounds/main_theme.mp3', 'main_theme', 3);"
							
							
							
							+" function handleLoad(event) {"
								+"main_theme = createjs.Sound.play('main_theme', {interrupt: createjs.Sound.INTERRUPT_ANY, loop:-1, volume: 0.1});"
							
								+"main_theme.play('main_theme');"   
							+"}"
								
							+addMusicAndSoundButtons(req, resp)
						  	+addGround()
						// SET POSITION
							+"record.position.x = " + WIDTH + " - " + WIDTH/3 + "; \n"
							+"record.position.y = 70; \n"
							+"score.position.x = " + WIDTH + " - " + WIDTH/3 + ";\n"
							+"score.position.y = 30; \n"
							+"alert.position.x = " + WIDTH/2 + " -alert.width/2 ;\n"
							+"alert.position.y = 140; \n"
							+"coins_score.position.x = 80;\n"
							+"coins_score.position.y = 23; \n"
							
						// ADD TO STAGE
							+"stage.addChild(alert);  \n"
							+"stage.addChild(score);  \n"
							+"stage.addChild(record); \n"
							+"stage.addChild(coins_score); \n"
							
							
							
							+"document.body.appendChild(renderer.view); \n"
							+"stage.interactive = true; \n"
							
							//+ addTexture(req, resp, "bunny", "bunny.png", 150 , 200)
							+ addTexture(req, resp, "dino", "dino_default.png", 100 , HEIGHT-150)
							+ addTexture(req, resp, "coin", "coin.png", 10 , 10)
							+"coin.width = 70;"
							+"coin.height = 70;"
							+"dino.isJumping = false; \n"
							+"dino.isFalling = false; \n"
							
							+ addClick(req, resp)
							+ bindKeys()
							
							+"renderer.render(stage); \n"
							+"requestAnimationFrame( animate ); \n"
							+ animate(req, resp)
							+"getFromLocal();"
						+"}); \n"
						
					// FUNCTIONS
					+ getTicksFunction()
					+ addTextureFunction()
					+ spawnObjectFunction()
					+ saveScoreFunction()
					+ saveFunction()
					+ getFromLocalFunction()
					
				+"</script> \n";
	}
	
	private String addTexture(HttpServletRequest req, HttpServletResponse resp, String name, String image, int x, int y) throws IOException{
		objects.add(name);
		return 	
				"var texture = PIXI.Texture.fromImage('images/"+ image +"'); \n" 
				+"var "+ name +"= new PIXI.Sprite(texture); \n"
				+"addTexture("+name+","+x+","+y+"); \n"
				;
	}
	
	private String addGround(){
		return 
			        "var ground = new PIXI.Sprite(PIXI.Texture.fromImage('images/basic_ground_base.png'));"
					
					+ "for(var i = -50; i < " + WIDTH + " + 50; ){"
							
							+"var ground_sprite = new PIXI.Sprite(basic_ground); \n"
							+ "ground_sprite.width = 50;"
							+ "ground_sprite.height = 50;"
							+ "ground_sprite.position.y = " + HEIGHT + " - ground_sprite.height ;"
							+ "ground_sprite.position.x = i;"
							+ "ground_array.push(ground_sprite);"
							+ "ground.addChild(ground_sprite);"
							+ "i+=50;"
					+ "}"
							
					+"stage.addChild(ground); \n"
				;
	}
	
	private String addTextureFunction(){
		return
				"function addTexture(name,x,y){ \n"
				+"name.position.x = x; \n"
				+"name.position.y = y; \n"
				+"stage.addChild(name);} \n"
				;
	}
	
	private String spawnObject(HttpServletRequest req, HttpServletResponse resp, String name){
		
		
		id++;
		
		return 
				"var type;"
				+"var y;"
				+"var x = "+WIDTH+";"
				+"var speed;"
				+"var rotation;"
				+"var rotSpeed;"
				+"var image;"
				+"var height;"
				+"var width;"
				
				+"var random = parseInt(Math.random()*10);"
				
				+"switch(random){"
					+"case 1:"
							+"image = 'coin.png';"
							+"type = \"coin\";"
							+"speed  = -3;"
							+"rotation = true;"
							+"rotSpeed = 0.1;"
							+"height = 50;"
							+"width = 50;"
							+"break;"
					+"case 2:"		
					+"case 3:"
					+"case 4:"
						+ "if(!arrow_sound_muted){"
							+"createjs.Sound.play('arrow_sound', {volume:0.3});"
						+ "}"   
						+"image = \"arrow.png\";"
						+"type = \"arrow\";"
						+"speed = -15;"
						+"rotation = false;"
						+"height = 16;"
						+"width = 75;"
						+"break;"
					+"default:"
						+ "if(!shuriken_sound_muted){"
							+"createjs.Sound.play('shuriken_sound', {volume:0.2});"
						+ "}"   
						+"image = \"shuriken.png\";"
						+"type = \"shuriken\";"
						+"speed = -10;"
						+"rotation = true;"
						+"rotSpeed = 5.9;"
						+"height = 50;"
						+"width = 50;"
						+"break;"
				+"}"
				+"random = parseInt(Math.random()*10);"
				+"switch(random){"
					+"case 1:"
					+"case 2:"
					+"case 3:"
						+"y = "+HEIGHT+"-70;"
						+"break;"
					+"case 4:"
					+"case 5:"
					+"case 6:"
						+"y = "+HEIGHT+"-140;"
						+"break;"
					+"default:"
						+"y = "+HEIGHT+"-210;"
						+"break;"
				+"}"
						
				+"var texture = PIXI.Texture.fromImage('images/'+image); \n" 
				+"var "+ name +"= new PIXI.Sprite(texture); \n"
				+"spawnObject("+name+",x,y,speed,rotation,rotSpeed,type,height,width); \n"
				+"objects.push("+name+");"
				;
	}
	
	private String spawnObjectFunction(){
		return 
				"function spawnObject(name,x,y,speed,rotation,rotSpeed,type,height,width){"
					+"name.vx = speed;"
					+"name.isRotating = rotation; \n"
					+"name.rotSpeed = rotSpeed;"
					+"name.type = type; \n"
					+"name.anchor.x = 0.5; \n"
					+"name.anchor.y = 0.5; \n"
					+"name.position.x = x; \n"
					+"name.position.y = y; \n"
					+"name.height = height; \n"
					+"name.width = width; \n"
					
					+"stage.addChild(name); \n"
				+"}"
				;
	}
	
	private String getTicksFunction(){
		return 
				"function getTicks(){"
				+ "return  parseInt((ticks)/5);"
				+ "}";
	}
	
	private String addClick(HttpServletRequest req, HttpServletResponse resp){
		
		return 
				""
				;
	}
	
private String addMusicAndSoundButtons(HttpServletRequest req, HttpServletResponse resp){
		
		return 
				// CREATE OBJECTS
				 "var  sound = new PIXI.Sprite(sound_on_icon); \n"
				+"var  music = new PIXI.Sprite(music_on_icon); \n"
			
				+"var sound_button = new PIXI.Graphics(); \n" 
				+"var music_button = new PIXI.Graphics(); \n" 
				
				// DEFAULT SETTINGS
				+"sound_button.dis = false;"
				+"music_button.dis = false;"
				+"sound_button.lineStyle(1, 0x000); \n"
				+"music_button.lineStyle(1, 0x000); \n"
				+"sound_button.interactive = true; \n"
				+"music_button.interactive = true; \n"
				+"sound_button.hitArea = new PIXI.Rectangle(250,0,300,50 ); \n"
				+"music_button.hitArea = new PIXI.Rectangle(350,0,400,50); \n"
				
				+"sound_button.addChild(sound);"
				+"music_button.addChild(music);"
				+"sound.width = 50;"
				+"sound.height = 50;"
				+"music.width = 50;"
				+"music.height = 50;"
				+"sound.x = 250;"
				+"music.x = 350;"
				
				+"sound_button.click = function(event){"
					+ "if(sound_button.dis){"
						+ "sound_button.dis = false;"
						+ "sound.texture = sound_on_icon;"
						
						+ "coin_sound_muted = false;"
						+ "jump_sound_muted = false;"
						+ "arrow_sound_muted = false;"
						+ "shuriken_sound_muted = false;"
					+ "}"
					+ "else {"
						+ "sound_button.dis = true;"
						+ "sound.texture = sound_off_icon;"
						
						+ "coin_sound_muted = true;"
						+ "jump_sound_muted = true;"
						+ "arrow_sound_muted = true;"
						+ "shuriken_sound_muted = true;"
					+ "}"
				+ "}; \n"
					
				+"music_button.click = function(event){"
					+ "if(music_button.dis){"
						+ "music_button.dis = false;"
						+ "music.texture = music_on_icon;"
						+ "main_theme.muted = false;"
					+ "}"
					+ "else {"
						+ "music_button.dis = true;"
						+ "music.texture = music_off_icon;"
						+ "main_theme.muted = true;"
					+ "}"
				+ "}; \n"
					
				+"stage.addChild(sound_button); \n"
				+"stage.addChild(music_button); \n"
				;
	}
	
	private String animate(HttpServletRequest req, HttpServletResponse resp){
		return 
				"function animate() { \n"
				+ "requestAnimationFrame( animate ); \n"
				+ "if(started && !paused && !failed){\n"
				
				// MOVE OBJECTS
					+ "objects.forEach(function(item,i,objects){ \n"
						+ "if(item.isRotating){ \n"
							+ "item.rotation +=item.rotSpeed; \n" 
						+ "} \n"
						+ "item.x += item.vx; \n"
						+ "if(item.x <=-20){objects.splice(i,1);stage.removeChild(item);} \n"
					+ "}); \n"
					+ checkCollisions()
				// MOVE GROUND
					+ "ground_array.forEach(function (item,i,ground_array) { \n"
						+ "item.x -= ground_speed; \n"
						+ "if(item.x <= -50){item.x = "+WIDTH +";} \n"
					+ "}); \n"
					
				// RANDOM NEW OBJECT

				// SPAWN NEW OBJECT
					+ "if(getTicks()%10==0 && getTicks()!=last_tick){ \n"
						+ "last_tick = getTicks(); \n"
						+ spawnObject(req, resp, ("object" + id))
						+ "id++; \n"
					+ "} \n"
					
					
					
				// DINO ANIMATION
					+ "if((dino.isJumping && space.isDown) || dino.isFalling){dino.y +=dino.vy;} \n"
					+ "else if(getTicks()%2==0 && !dino.duck){ \n"
						+"if(swap){ \n"
							+"dino.texture = dino_run_1; \n"
							+ "swap = !swap; \n"
						+"} \n"
						+ "else {dino.texture = dino_run_2; \n"
						+ "swap = !swap;} \n"
					+ "} \n"
					+ "if(dino.y < 150 && !down.isDown){dino.vy = 10; dino.isFalling = true; dino.texture = dino_fall;}"
					+ "if(dino.y >= ("+HEIGHT+"-150)){dino.y = "+HEIGHT+"-150; dino.vy = 0; dino.isJumping = false; dino.isFalling = false;} \n"
					
			    	+"if(dino.duck){	"
			    	+ "if(getTicks()%2==0){ \n"
							+"if(swap){ \n"
								+"dino.texture = dino_duck_1; \n"
								+ "swap = !swap; \n"
							+"} \n"
							+ "else {dino.texture = dino_duck_2; \n"
							+ "swap = !swap;} \n"
						+ "}"
					+ "}"
						
				// OTHER
					+ "ticks++;"
					+ "score.text = 'Score: ' + getTicks(); \n" 
				+ "}"
					
				// UPDATE
				+ "renderer.render(stage); \n"	
			+"}"
			;
	}
	
	private String bindKeys(){
		return 
			"function keyboard(keyCode) {"
			  +"var key = {};"
			  +"key.code = keyCode;"
			  +"key.isDown = false;"
			  +"key.isUp = true;"
			  +"key.press = undefined;"
			  +"key.release = undefined;"
			  //The `downHandler`
			  +"key.downHandler = function(event) {"
				  +"if (event.keyCode === key.code) {"
			    	+" if (key.isUp && key.press) key.press();"
			      +" key.isDown = true;"
			      +" key.isUp = false;"
			      +" }"
			    +"event.preventDefault();"
			    +"};"
			  //The `upHandler`
			  +"key.upHandler = function(event) {"
				  +"if (event.keyCode === key.code) {"
			    	+" if (key.isDown && key.release) key.release();"
			      +" key.isDown = false;"
			      +"  key.isUp = true;"
			      +"}"
			    +" event.preventDefault();"
			    +" };"
			  //Attach event listeners
			  +"window.addEventListener("
					  +"'keydown', key.downHandler.bind(key), false"
			    +");"
			  +" window.addEventListener("
					  +"'keyup', key.upHandler.bind(key), false"
			    +");"
			  +"return key;"
			  +"}"
				+"var left = keyboard(37), \n"
		 		+"up = keyboard(38), \n"
			    +"right = keyboard(39), \n"
			    +"down = keyboard(83),"
			    +"pause = keyboard(80),"
			    + "restart = keyboard(82); \n"
			    +"space = keyboard(32); \n"
			    +"space.press = function(){ \n"
			    +"if(!started){"
			    	+"started = true;"
			    	+"alert.text = '';} \n"
			    +"if(!dino.isJumping && !down.isDown && !failed && !paused){"
			    	+"dino.vy = -10; \n"
			    	+"dino.isJumping = true;"
			    	+ "if(!jump_sound_muted){"
			    		+"createjs.Sound.play('jump_sound',{volume:0.3});"
			    	+ "}"
			    	+ "dino.texture = dino_jump;}"
			    +"} \n"
			    +"space.release = function(){"
			    	+"if(dino.isJumping){"
			    		+"dino.isFalling = true; "
			    		+"dino.texture = dino_fall; "
			    		+"if(!down.isDown){"
			    			+"dino.vy = 10;"
			    		+"}"
			    	+"}"
			    +"} \n"
			    +"down.press = function(){"
			    	+"if(dino.isJumping){"
			    		+"dino.vy = 20;"
			    		+"dino.texture = dino_fall;"
			    	+"}"
			    	
			    	+ "dino.duck = true;"
			    
			    	
			    +"};"
			    +"down.release = function(){"
			    	+"if(dino.isFalling){ " 
			    		+"dino.vy = 10;"
			    	+ "}"
			    	
			    	+ "dino.duck = false;"
			    	
			    +"} \n"
			    +"pause.press = function(){"
			    //+ "console.log('key pause'); \n"
				    +"if(started && !failed){ "
				    	+"paused = !paused; "
				    	+"if(paused){ "
				    		+"alert.text = 'Paused'; "
				    		+"alert.position.x = " + WIDTH/2 + " -alert.width/2 ; "
				    	+"}"
				    	+"else if(!failed)"
				    	+"{alert.text = ''}"
				   +"} \n"
				+"}; \n"
				  +restartFunction() ;
			 
	}
	
	private String checkCollisions(){
		return 
				"objects.forEach(function(item,i,objects){ \n"
				+"var vx = 0;"
				+"var vy_duck = 0;"
				+"if(item.type == 'arrow'){vx = 65;}"
				+"var vy = 0;"
				+"if(item.type == 'shuriken'){vy = 35;}"
				+"if(dino.duck){vy_duck = 30;}"
					+ "if(item.x+5 < (dino.x+dino.width) && (item.x+item.width-35-vx) > dino.x+30){ \n"
					
						 +"if((item.y < dino.y+vy_duck && (item.y+item.height-vy) > (dino.y+vy_duck)) || (item.y+5 < dino.y+dino.height+vy_duck && (item.y+item.height-vy) > (dino.y+vy_duck))){"
							+ "if(item.type == 'arrow' || item.type == 'shuriken' ){"
								+ "failed = true;"
								+ "alert.text = 'Game Over';"
								+ "saveScore(getTicks());"
								+ "alert.position.x = " + WIDTH/2 + " -alert.width/2 ; "
							+ "}"
						 	+ "if(item.type == 'coin'){"
						 		+ "if(!coin_sound_muted){"
						 			+ "createjs.Sound.play('coin_sound',{volume:0.2});"
						 		+ "}"   
							 	+ "coins_score.text = 'X'+(++coins_cap);"
							 	+ "objects.splice(i,1);"
							 	+ "stage.removeChild(item);"
							 	
						 	+ "}"
						 + "}"
					+ "} \n"
					
				+ "}); \n"
				;
	}
	
	private String restartFunction(){
		return 
				"restart.press = function(){"
				
					// REMOVE ALL OBJECTS
					+"var l = objects.length;"
					+ "for(var i = 0; i < l; i++){"
						+ "stage.removeChild(objects[0]);"
						+ "objects.splice(0,1);"			
					+ "} \n"
					
					// SET DEFAULT DINO TEXTURE AND POSITION
					+ "dino.texture = dino_skin; "
					+ "dino.x =  100; "
					+ "dino.y = "+HEIGHT+"-150;"
					
					// SET UP SCORE, COINS, ALERT FIELDS
					+ "ticks = 0;"
					+ "last_tick = 0;"
					+ "score.text = 'Score: 0';"
					+ "coins_score.text = 'X0';"
					+ "coins_cap = 0;"
					+ "alert.text = 'Press \"Space\" to start';"
					+ "alert.position.x = " + WIDTH/2 + " -alert.width/2 ; "
					
					// SET UP BOOLEANS
					+ "started = false;"
					+ "paused = false;"
					+ "failed = false;"
					+ "dino.isJumping = false;"
					+ "dino.duck = false;"
					+ "dino.isFalling = false;"
				+ "}; \n"
				;
	}
	
	private String getFromLocalFunction(){
		return 
				"function getFromLocal(){"
					+ "var ar = localStorage.getItem('local_scores');"
					+ "if(ar){"
						+ "ar = ar.split(',');"
						+ "ar.forEach(function(item,i,ar){"
							+ "console.log('local ',item);"
							+ "if(item){"
								+ "if(i==0){"
									+ "record.text = 'Record: ' + item;"
								+ "}"
								+ "local_scores.splice(i,1,parseInt(item));"
							+ "}"
						+ "});"
					+ "}"
				+ "}"
			
				;
	}
	
	private String saveScoreFunction(){
		return 
				"function saveScore(new_score){"
					+"for(var i = 0; i < 10; i++){"
						+"var f = local_scores[i];"
				        +"if(f){"
				            +"if(new_score > f){"
				            	+ "if(i==0){"
				            		+ "record.text = 'Record: ' + new_score;"
				            	+ "}"
				                +"for(var j = 8; j >= i; j--){"
				                    +"var buff = local_scores[j];"
				                    +"if(buff!=null){"
				                        +"local_scores.splice((j+1),1, buff);"
				                    +"}"
				                +"}"
				                +"local_scores.splice(i,1, new_score);"
				                +"save();"
				                +"return;"
				            +"}"
				        +"}"
				        +"else {"
				        	 +"local_scores.splice(i,1, new_score);"
				        	 +"save();"
				             +"return;"
				         +"}"
				     +"}"
				     +"save();"
				+"}"
				;
	}
	
	private String saveFunction(){
		return 
				"function save(){"			
					+ "localStorage.setItem('local_scores',local_scores);"
				+ "}"
				;
	}
	
	private String updateScores(){
		return "";
	}
	
}
