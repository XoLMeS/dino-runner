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
							+"<link rel='stylesheet' type='text/css' href='css/main.css' />"
							//+"<link rel='stylesheet' type='text/css' href='css/game.css' />"
							//+"<script src='js/game.js'></script>"
							//+"<script src='js/main.js'></script>"
							+"<script src='js/pixi.js'></script> \n"
							+ getScript(req, resp)
						+"</head> \n"
						+"<body> \n"
							+"<div clas='header'>Header</div> \n"
							+"<div class='game'>Game</div> \n"
							+"<div class='footer'>Footer</div> \n"
						+"</body> \n"
				+"</html> \n";
		
	}
	
	private String getScript(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return 
				 "<script> \n"
					+"var objects = [];"
					+"var ground_array = [];"
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
					
					
					+"var swap = true;"
					+"var stage = new PIXI.Container(); \n"
				
					
					// TEXT FIELDS
					+"var score = new PIXI.Text('Score: 0',{font : '24px Arial', fill : "+score_color+", align : 'center', dropShadow: true, dropShadowDistance: 2}); \n"
					+"var record = new PIXI.Text('Record: 0',{font : '24px Arial', fill : "+record_color+", align : 'center', dropShadow: true, dropShadowDistance: 2}); \n"
					+"var alert = new PIXI.Text('Press \"Space\" to start',{font : ' bold 34px Arial', fill : "+alert_color+", align : 'center', dropShadow: true, dropShadowDistance: 2}); \n"
					+"var coins_score = new PIXI.Text('X0',{font : ' bold 40px Arial', fill : "+coins_score_color+", align : 'center', dropShadow: true, dropShadowDistance: 4}); \n"
					//+"console.log(PIXI); \n"
						
						+"$(function(){ \n"
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
						+"}); \n"
						
					// FUNCTIONS
					+ getTicksFunction()
					+ addTextureFunction()
					+ spawnObjectFunction()
					
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
				//+"bunny.anchor.x = 0.5; \n"
				//+"bunny.anchor.y = 0.5; \n"
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
						+"image = \"arrow.png\";"
						+"type = \"arrow\";"
						+"speed = -15;"
						+"rotation = false;"
						+"height = 16;"
						+"width = 75;"
						+"break;"
					+"default:"
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
				 "var rect = new PIXI.Graphics(); \n" 
				+"rect.lineStyle(1, 0x000); \n"
				+"rect.interactive = true; \n"
				+"rect.hitArea = new PIXI.Rectangle(0,0,"+WIDTH +","+ HEIGHT + " ); \n"
				+"rect.click = function(event){console.log('click');}; \n"
				+"stage.addChild(rect); \n"
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
			    +"down = keyboard(40),"
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
							+ "alert.position.x = " + WIDTH/2 + " -alert.width/2 ; "
							+ "}"
						 	+ "if(item.type == 'coin'){coins_score.text = 'X'+(++coins_cap);objects.splice(i,1);stage.removeChild(item);}"
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
				+ "alert.text = 'Press \"Space\" to start';"
				+ "alert.position.x = " + WIDTH/2 + " -alert.width/2 ; "
				// SET UP BOOLEANS
				+ "started = false;"
				+ "paused = false;"
				+ "failed = false;"
				+ "dino.isJumping = false;"
				+ "dino.duck = false;"
				+ "dino.isFalling = false;"

				+ "};"
				;
	}
	
}
