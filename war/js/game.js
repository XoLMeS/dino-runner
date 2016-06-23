var objects = [];
var ground_array = [];
var local_scores = [];
var ground_speed = GROUND_SPEED;
var id = 1;
var ticks = 0;
var last_tick = 0;
var coins_cap = 0;
var paused = false;
var started = false;
var failed = false;
var debug;
var renderer = PIXI.autoDetectRenderer(WIDTH, HEIGHT, {
    backgroundColor: 0xffffff
}, true);
var dino_skin = PIXI.Texture.fromImage('images/dino_default.png');
var dino_run_1 = PIXI.Texture.fromImage('images/dino_run_1.png');
var dino_run_2 = PIXI.Texture.fromImage('images/dino_run_2.png');
var dino_jump = PIXI.Texture.fromImage('images/dino_jump.png');
var dino_fall = PIXI.Texture.fromImage('images/dino_fall.png');
var basic_ground = PIXI.Texture.fromImage('images/basic_ground.png');
var dino_duck = PIXI.Texture.fromImage('images/dino_duck_1.png');
var dino_duck_1 = PIXI.Texture.fromImage('images/dino_duck_2.png');
var dino_duck_2 = PIXI.Texture.fromImage('images/dino_duck_3.png');
var sound_on_icon = PIXI.Texture.fromImage('images/sound_on_icon.png');
var sound_off_icon = PIXI.Texture.fromImage('images/sound_off_icon.png');
var music_on_icon = PIXI.Texture.fromImage('images/music_on_icon.png');
var music_off_icon = PIXI.Texture.fromImage('images/music_off_icon.png');
var realSwap = true;
var shadowSwap = true;
var stage = new PIXI.Container();
var score = new PIXI.Text('Score: 0', {
    font: '24px Arial',
    fill: SCORE_COLOR,
    align: 'center',
    dropShadow: true,
    dropShadowDistance: 2
});
var record = new PIXI.Text('Record: 0', {
    font: '24px Arial',
    fill: RECORD_COLOR,
    align: 'center',
    dropShadow: true,
    dropShadowDistance: 2
});
var alert = new PIXI.Text('Press "Space" to start', {
    font: ' bold 34px Arial',
    fill: ALERT_COLOR,
    align: 'center',
    dropShadow: true,
    dropShadowDistance: 2
});
var coins_score = new PIXI.Text('X0', {
    font: ' bold 40px Arial',
    fill: COINS_SCORE_COLOR,
    align: 'center',
    dropShadow: true,
    dropShadowDistance: 4
});
var SHADOW_TEST = "N4Ig9gRgVgpgxgFwM4gFygKwAY0G1QCeaAzAJxYA0ICBADjGiAIYBOLYA7iAL4C6VARiw5U+EEVQAWAGyVqdBqhBwwASwB2PfiAHY8hEuSo16jVuy58qAJmH7xaGXJOKQSABYBXFqoDWMTSsQaz1RA1QyZwVGD28/AK0qYjswhwijeVMlc05EkGJQsQknY2ilWJ9/QO1JFKLDKKzmNlygyULwyNKmivjqqmwReqlZbtdeqryMDrSuzPGvSoSg2SHOjJczFsttaRmJDGSxrYs8gHY68JL5mMW+8/2G4/K7yaCADkvZjbK3V+XtO9HqhDo1XDkdlRyGs0qDns1TkFSMC5psXnE3tohF8DkcbkoVBo8kIUT8ev9+jpsfYJKjfhMAYJdDDiqN8QjWljbCynuzCZSBCEeSMwbcMYydMlhdc0RzIZLgTLfhDibVhXC+WoBe1hXTyeKBYMaY42bL+cTprqyQsDcTVsaRfCVUEBHt1XjZQyBRdpab6RTiWdFX79UsBZ93aL0WHiUDI/CvcToQ6laH7i7kVao3K8tys/DzUFbKTs87tNZqalWaXtrnmSmQ+Da0XuQ69U3EeWhW3rWKY0WpQ6NbKyzYCr6a52bGqhx7lc3yzqe5POTYjVW0MP/bai5blwmA0X7RuQXOmqPgm7ZyudtwqP4CCh0CARKABHhiNZeHedNY0KAhk/b9BGkSR/x0D8vx/AR3necDAKgwRyHA99RHeYhgOCARiHgvB0Mw6xbBQyCCNqXDRCAn9rEkP9n1Q3B8J/ZIznIhiMKY6QWLovD2KSUhaIAnjMMkXRiLQ3iQEkSQcOfIZGKoSQzgEiDxMwjBBVY+SQAwCsxLYtT2k0iSMGkVC3yEn8MH4ozMOkUguPMijEJAM4NNkkifyUuDuNUzzSFIGyf3eZE9MoqEBDA9ynMw/iMBswQLKhKTQuc5EAqi3AwpAUhOL0rT7PSwTfKZUhIsc/ToIrByX0SyUIpSzDsOIGSisy5zsOSnyKsEYgzmquSJOw0gzJU7qdBEsqauK8aMGq+isois4WqmtrGskHK8sGnSRoGxrDmW+itN0MiMqOjAZE2vbQlas7zsu6DpkKlazukA7at0aRvJuralPuwRZv696MHeEbDq20rAv+kKuqOzjlt26ClLmjzBDOaTWIWs5ZD+nQ40h3Hfq6hbYKehDGuGuKifa0hmox6mTJxgR/MmhGbAi5SwYI114dqisQZxwjRNOiTCM68qtMIqz8cIs4nvm5zCOQjKsusYhJC+0aVeIaRlLJqjiHQgWRMp77SPVhqqMU3WUeCdbX1GiXDmt6aQj6i21yV1qVasjXOao0D7YRn8EFUOBfCfaw9mkbggA==";
$(function() {
    var main_theme;
    var arrow_sound_muted = false;
    var jump_sound_muted = false;
    var shuriken_sound_muted = false;
    var coin_sound_muted = false;
    createjs.Sound.on('fileload', handleLoad);
    createjs.Sound.registerSound('sounds/jump_sound.mp3', 'jump_sound', 2);
    createjs.Sound.registerSound('sounds/coin_sound.mp3', 'coin_sound', 2);
    createjs.Sound.registerSound('sounds/arrow_sound.mp3', 'arrow_sound', 2);
    createjs.Sound.registerSound('sounds/shuriken_sound.mp3', 'shuriken_sound', 2);
    createjs.Sound.registerSound('sounds/main_theme.mp3', 'main_theme', 3);

    function handleLoad(event) {
        main_theme = createjs.Sound.play('main_theme', {
            interrupt: createjs.Sound.INTERRUPT_ANY,
            loop: -1,
            volume: 0.1
        });
        main_theme.play('main_theme');
        var mute_music = localStorage.getItem('mute_music');
        if(mute_music){
            if(mute_music=='true'){
                music_button.dis = true;
                music.texture = music_off_icon;
                main_theme.muted = true;
            }
        }
    }
    var sound = new PIXI.Sprite(sound_on_icon);
    var music = new PIXI.Sprite(music_on_icon);
    var sound_button = new PIXI.Graphics();
    var music_button = new PIXI.Graphics();
    sound_button.dis = false;
    music_button.dis = false;
    sound_button.lineStyle(1, 0x000);
    music_button.lineStyle(1, 0x000);
    sound_button.interactive = true;
    music_button.interactive = true;
    sound_button.hitArea = new PIXI.Rectangle(250, 0, 300, 50);
    music_button.hitArea = new PIXI.Rectangle(350, 0, 400, 50);
    sound_button.addChild(sound);
    music_button.addChild(music);
    sound.width = 50;
    sound.height = 50;
    music.width = 50;
    music.height = 50;
    sound.x = 250;
    music.x = 350;
    sound_button.click = function(event) {
        if (sound_button.dis) {
            sound_button.dis = false;
            sound.texture = sound_on_icon;
            coin_sound_muted = false;
            jump_sound_muted = false;
            arrow_sound_muted = false;
            shuriken_sound_muted = false;
            localStorage.setItem('mute_sound',false);
        } else {
            sound_button.dis = true;
            sound.texture = sound_off_icon;
            coin_sound_muted = true;
            jump_sound_muted = true;
            arrow_sound_muted = true;
            shuriken_sound_muted = true;
            localStorage.setItem('mute_sound',true);
        }
    };
    music_button.click = function(event) {
        if (music_button.dis) {
            music_button.dis = false;
            music.texture = music_on_icon;
            main_theme.muted = false;
            localStorage.setItem('mute_music',false);
        } else {
            music_button.dis = true;
            music.texture = music_off_icon;
            main_theme.muted = true;
            localStorage.setItem('mute_music',true);
        }
    };
    
    var mute_sound = localStorage.getItem('mute_sound');
    if(mute_sound){
        if(mute_sound=='true'){
            sound_button.dis = true;
            sound.texture = sound_off_icon;
            coin_sound_muted = true;
            jump_sound_muted = true;
            arrow_sound_muted = true;
            shuriken_sound_muted = true;
        }
    }
    
   
    
    stage.addChild(sound_button);
    stage.addChild(music_button);
    var ground = new PIXI.Sprite(PIXI.Texture.fromImage('images/basic_ground_base.png'));
    for (var i = -50; i < 1000 + 50;) {
        var ground_sprite = new PIXI.Sprite(basic_ground);
        ground_sprite.width = 50;
        ground_sprite.height = 50;
        ground_sprite.position.y = 600 - ground_sprite.height;
        ground_sprite.position.x = i;
        ground_array.push(ground_sprite);
        ground.addChild(ground_sprite);
        i += 50;
    }
    stage.addChild(ground);
    record.position.x = 1000 - 333;
    record.position.y = 70;
    score.position.x = 1000 - 333;
    score.position.y = 30;
    alert.position.x = 500 - alert.width / 2;
    alert.position.y = 140;
    coins_score.position.x = 80;
    coins_score.position.y = 23;
    stage.addChild(alert);
    stage.addChild(score);
    stage.addChild(record);
    stage.addChild(coins_score);
    document.body.appendChild(renderer.view);
    stage.interactive = true;
    var texture = PIXI.Texture.fromImage('images/dino_default.png');
    var dino = new PIXI.Sprite(texture);
    addTexture(dino, 100, 450);
    var shadowDino = new PIXI.Sprite(texture);
    addTexture(shadowDino, 115, 450);
    var texture = PIXI.Texture.fromImage('images/coin.png');
    var coin = new PIXI.Sprite(texture);
    addTexture(coin, 10, 10);
    coin.width = 70;
    coin.height = 70;
    dino.isJumping = false;
    dino.isFalling = false;
    shadowDino.isFalling = false;
    shadowDino.isJumping = false;
    shadowDino.alpha = 0.3;
    // Obstacles and collectables registry
    // objects[ticks] - array of objects, spawned "ticks" afters start
    // objects[ticks].y - y pos, objects[ticks].type - object type
    var objects_record = {};
    // Key registring for shadow player
    // keys_record[ticks] - key events for current tick
    // keys_record[ticks][0] - up events
    // keys_record[ticks][1] - down events
    // keys_record[ticks][0] or keys_record[ticks][1] - array of keycodes.
    //
    // keys_record[35][0][32] means space key is down on tick 35
    var keys_record = {};

    function keyboard(keyCode) {
        var key = {};
        key.code = keyCode;
        key.isDown = false;
        key.isUp = true;
        key.press = undefined;
        key.release = undefined;
        key.downHandler = function(event) {
            if (event.keyCode === key.code) {
                if (key.isUp && key.press) key.press();
                key.isDown = true;
                key.isUp = false;
            }
            event.preventDefault();
        };
        key.upHandler = function(event) {
            if (event.keyCode === key.code) {
                if (key.isDown && key.release) key.release();
                key.isDown = false;
                key.isUp = true;
            }
            event.preventDefault();
        };
        window.addEventListener('keydown', key.downHandler.bind(key), false);
        window.addEventListener('keyup', key.upHandler.bind(key), false);
        return key;
    }
    var left = keyboard(37),
        up = keyboard(38),
        right = keyboard(39),
        down = keyboard(83),
        pause = keyboard(80),
        restart = keyboard(82),
        space = keyboard(32);

    function putEvent(time, eventCode, keyCode) {
        if (keyCode === restart.code || keyCode === pause.code || failed) {
            return;
        }
        var k = keys_record[time];
        if (!k) {
            k = {};
        }
        var e = k[eventCode];
        if (!e) {
            e = [];
        }
        e.push(keyCode);
        k[eventCode] = e;
        keys_record[time] = k;
        //console.log(keys_record);
    }

    function dinoJumpPress(dino, playSound) {
        if (!started) {
            started = true;
            alert.text = '';
        }
        if (!dino.isJumping && !failed && !paused) {
            dino.vy = -10;
            dino.isJumping = true;
            if (!jump_sound_muted && playSound) {
                createjs.Sound.play('jump_sound', {
                    volume: 0.3
                });
            }
            dino.texture = dino_jump;
        }
    }
    space.press = function() {
        dinoJumpPress(dino, true);
        putEvent(ticks, 1, space.code);
    }

    function dinoJumpRelease(dino) {
        if (dino.isJumping) {
            dino.isFalling = true;
            dino.texture = dino_fall;
            if (!down.isDown) {
                dino.vy = 10;
            }
        }
    }
    space.release = function() {
        dinoJumpRelease(dino);
        putEvent(ticks, 0, space.code);
    }

    function dinoDownPress(dino) {
        if (dino.isJumping) {
            dino.vy = 20;
            dino.texture = dino_fall;
        }
        dino.duck = true;
    }
    down.press = function() {
        dinoDownPress(dino);
        putEvent(ticks, 1, down.code);
    };

    function dinoDownRelease(dino) {
        if (dino.isFalling) {
            dino.vy = 10;
        }
        dino.duck = false;
    }
    down.release = function() {
        dinoDownRelease(dino);
        putEvent(ticks, 0, down.code);
    }
    var keyActionMap = {};
    keyActionMap[space.code] = [];
    keyActionMap[space.code][0] = dinoJumpRelease;
    keyActionMap[space.code][1] = dinoJumpPress;
    keyActionMap[down.code] = [];
    keyActionMap[down.code][0] = dinoDownRelease;
    keyActionMap[down.code][1] = dinoDownPress;
    console.log("keyMap", keyActionMap);
    pause.press = function() {
        if (started && !failed) {
            paused = !paused;
            if (paused) {
                alert.text = 'Paused';
                alert.position.x = 500 - alert.width / 2;
            } else if (!failed) {
                alert.text = ''
            }
        }
    };
    restart.press = function() {
        var l = objects.length;
        for (var i = 0; i < l; i++) {
            stage.removeChild(objects[0]);
            objects.splice(0, 1);
        }
        l = shadowObjects.length;
        for (var i = 0; i < l; i++) {
            stage.removeChild(shadowObjects[0]);
            shadowObjects.splice(0, 1);
        }
        dino.texture = dino_skin;
        shadowDino.texture = dino_skin;
        dino.x = 100;
        shadowDino.x = 115;
        dino.y = 600 - 150;
        shadowDino.y = 600 - 150;
        ticks = 0;
        last_tick = 0;
        score.text = 'Score: 0';
        alert.text = 'Press "Space" to start';
        alert.position.x = 500 - alert.width / 2;
        started = false;
        paused = false;
        failed = false;
        dino.isJumping = false;
        shadowDino.isJumping = false;
        dino.duck = false;
        shadowDino.duck = false;
        dino.isFalling = false;
        shadowDino.isFalling = false;
        objects_record = new Object();
        keys_record = new Object();
        shadowInfo = parseShadow(SHADOW_TEST);
    };
    renderer.render(stage);

    function renderObjects(objects, dino, skipOver) {
        objects.forEach(function(item, i, objects) {
            if (item.isRotating) {
                item.rotation += item.rotSpeed;
            }
            item.x += item.vx;
            if (item.x <= -20) {
                objects.splice(i, 1);
                stage.removeChild(item);
            }
        });
        objects.forEach(function(item, i, objects) {
            var vx = 0;
            var vy_duck = 0;
            if (item.type == 'arrow') {
                vx = 65;
            }
            var vy = 0;
            if (item.type == 'shuriken') {
                vy = 35;
            }
            if (dino.duck) {
                vy_duck = 30;
            }
            if (item.x + 5 < (dino.x + dino.width) && (item.x + item.width - 35 - vx) > dino.x + 30) {
                if ((item.y < dino.y + vy_duck && (item.y + item.height - vy) > (dino.y + vy_duck)) || (item.y + 5 < dino.y + dino.height + vy_duck && (item.y + item.height - vy) > (dino.y + vy_duck))) {
                    if (!skipOver)
                        if ((item.type == 'arrow' || item.type == 'shuriken')) {
                            failed = true;
                            alert.text = 'Game Over';
                            saveScore(getTicks());
                            saveLastAttempt(objects_record, keys_record, ticks);
                            alert.position.x = 500 - alert.width / 2;
                            objects_record = {};
                            keys_record = {};
                            var scoreToSave = user_score;
                            if(getTicks() > user_score){  
                                scoreToSave = getTicks();
                            }
                            else {
                                scoreToSave = user_score;
                            }
                            if(local_scores[0] > scoreToSave){
                                scoreToSave = local_scores[0];
                            }
                            
                            if(user_name){
                                postToDataBase("update",user_name,user_link,{coins: coins_cap, shadow: "", score: scoreToSave});
                                localStorage.setItem('shadow',user_shadow);
                                //$('meta[property=\'og:description\']').attr('content', 'My Score Is' + getTicks());
                                 scoreToSave = getTicks();
                                FB.init({
                                    appId      : '626764410807763',
                                    status     : false, 
                                    cookie     : true,
                                    xfbml      : true,
                                    oauth      : true
                                }); 
                                FB.ui({
                                  method: 'share',
                                  href: 'http://dino-runner.appspot.com/',
                                }, function(response){});
                            }
                        }
                    if (item.type == 'coin') {
                        if (!skipOver){
                            if (!coin_sound_muted) {
                                createjs.Sound.play('coin_sound', {
                                    volume: 0.2
                                });
                                
                            }
                        coins_score.text = 'X' + (++coins_cap);
                        }
                        objects.splice(i, 1);
                        stage.removeChild(item);
                       
                    }
                }
            }
        });
    }
    debug = [];
    function renderDino(dinossos, swap, isSwap, shadow) {
        if (shadow){
            debug[0] = dinossos;
        } else {
            debug[1] = dinossos;
        }
        if (dinossos.isJumping || dinossos.isFalling) {
            dinossos.y += dinossos.vy;
        } else if (getTicks() % 2 == 0 && !dinossos.duck) {
            if (isSwap) {
                dinossos.texture = dino_run_1;
                swap();
            } else {
                dinossos.texture = dino_run_2;
                swap();
            }
        }
        if (dinossos.y < 150) {
            dinossos.vy = 10;
            dinossos.isFalling = true;
            dinossos.texture = dino_fall;
        }
        if (dinossos.y >= (600 - 150)) {
            dinossos.y = 600 - 150;
            dinossos.vy = 0;
            dinossos.isJumping = false;
            dinossos.isFalling = false;
        }
        if (dinossos.duck) {
            if (getTicks() % 2 == 0) {
                if (isSwap) {
                    dinossos.texture = dino_duck_1;
                    swap();
                } else {
                    dinossos.texture = dino_duck_2;
                    swap();
                }
            }
        }
    }
    animate();
    var shadowInfo = parseShadow(SHADOW_TEST);
    var shadowObjects = [];

    function animate() {
        requestAnimationFrame(animate);
        if (started && !paused && !failed) {
            renderObjects(objects, dino);
            renderObjects(shadowObjects, shadowDino, true);
            ground_array.forEach(function(item, i, ground_array) {
                item.x -= ground_speed;
                if (item.x <= -50) {
                    item.x = 1000;
                }
            });
            if (getTicks() % 10 == 0 && getTicks() != last_tick) {
                last_tick = getTicks();
                var type;
                var y;
                var x = 1000;
                var speed;
                var rotation;
                var rotSpeed;
                var image;
                var height;
                var width;
                var random = parseInt(Math.random() * 10);
                switch (random) {
                    case 1:
                        image = 'coin.png';
                        type = "coin";
                        speed = -3;
                        rotation = true;
                        rotSpeed = 0.1;
                        height = 50;
                        width = 50;
                        break;
                    case 2:
                    case 3:
                    case 4:
                        if (!arrow_sound_muted) {
                            createjs.Sound.play('arrow_sound', {
                                volume: 0.3
                            });
                        }
                        image = "arrow.png";
                        type = "arrow";
                        speed = -15;
                        rotation = false;
                        height = 16;
                        width = 75;
                        break;
                    default:
                        if (!shuriken_sound_muted) {
                            createjs.Sound.play('shuriken_sound', {
                                volume: 0.2
                            });
                        }
                        image = "shuriken.png";
                        type = "shuriken";
                        speed = -10;
                        rotation = true;
                        rotSpeed = 5.9;
                        height = 50;
                        width = 50;
                        break;
                }
                random = parseInt(Math.random() * 10);
                switch (random) {
                    case 1:
                    case 2:
                    case 3:
                        y = 600 - 70;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        y = 600 - 140;
                        break;
                    default:
                        y = 600 - 210;
                        break;
                }
                var texture = PIXI.Texture.fromImage('images/' + image);
                var object1 = new PIXI.Sprite(texture);
                spawnObject(object1, x, y, speed, rotation, rotSpeed, type, height, width, objects_record);
                objects.push(object1);
                putObjectsForShadow(ticks, shadowInfo, shadowObjects);
                id++;
            }
            renderDino(dino, function() {
                realSwap = !realSwap;
            }, realSwap);
            renderDino(shadowDino, function() {
                shadowSwap = !shadowSwap;
            }, shadowSwap, true);
            ticks++;
            simulateDinoActions(ticks, shadowInfo, keyActionMap, shadowDino);
            score.text = 'Score: ' + getTicks();
        }
        renderer.render(stage);
    }
    getFromLocal();
});

function simulateDinoActions(ticks, shadowInfo, keyActionMap, shadowDino) {
    //console.log("ticks ", ticks);
    if (ticks > shadowInfo.ticks) {
        console.log("Too high ticks!");
        return;
    }
    var actionsReg = shadowInfo.keys;
    //console.log(actionsReg);
    var actions = actionsReg[ticks];
    if (!actions) {
        return;
    }
    var pressActions = actions[1];
    var releaseActions = actions[0];

    function simulateActions(actions, action) {
        if (actions) {
            actions.forEach(function(item) {
                //console.log("Camera! Action! ", item, " ", action);
                keyActionMap[item][action](shadowDino, false);
            });
        }
    }
    simulateActions(pressActions, 1);
    simulateActions(releaseActions, 0);
}

function getTicks() {
    return parseInt((ticks) / 5);
}

function addTexture(name, x, y) {
    name.position.x = x;
    name.position.y = y;
    stage.addChild(name);
}

function spawnObject(name, x, y, speed, rotation, rotSpeed, type, height, width, objects_rec) {
    name.vx = speed;
    name.isRotating = rotation;
    name.rotSpeed = rotSpeed;
    name.type = type;
    name.anchor.x = 0.5;
    name.anchor.y = 0.5;
    name.position.x = x;
    name.position.y = y;
    name.height = height;
    name.width = width;
    if (objects_rec) {
        var k = objects_rec[ticks];
        if (!k) {
            k = [];
        }
        k.push({
            "y": y,
            "type": type
        });
        objects_rec[ticks] = k;
    }
    //console.log(objects);
    stage.addChild(name);
}

function putObjectsForShadow(ticks, shadowInfo, shadowObjectsRegistry) {
    if (ticks > shadowInfo.ticks) {
        return;
    }
    var objectsReg = shadowInfo.objects;
    var objects = objectsReg[ticks];
    if (objects) {
        objects.forEach(function(item, i, objects) {
            var type;
            var y;
            var x = 1015;
            var speed;
            var rotation;
            var rotSpeed;
            var image;
            var height;
            var width;
            switch (item.type) {
                case "coin":
                    image = 'coin.png';
                    type = "coin";
                    speed = -3;
                    rotation = true;
                    rotSpeed = 0.1;
                    height = 50;
                    width = 50;
                    break;
                case "arrow":
                    image = "arrow.png";
                    type = "arrow";
                    speed = -15;
                    rotation = false;
                    height = 16;
                    width = 75;
                    break;
                case "shuriken":
                    image = "shuriken.png";
                    type = "shuriken";
                    speed = -10;
                    rotation = true;
                    rotSpeed = 5.9;
                    height = 50;
                    width = 50;
                    break;
            }
            var texture = PIXI.Texture.fromImage('images/' + image);
            var object = new PIXI.Sprite(texture);
            object.alpha = 0.3;
            spawnObject(object, x, item.y, speed, rotation, rotSpeed, type, height, width);
            shadowObjectsRegistry.push(object);
            //console.log(shadowObjectsRegistry);
        });
    }
}

function saveScore(new_score) {
    for (var i = 0; i < 10; i++) {
        var f = local_scores[i];
        if (f) {
            if (new_score > f) {
                if (i == 0) {
                    record.text = 'Record: ' + new_score;
                }
                for (var j = 8; j >= i; j--) {
                    var buff = local_scores[j];
                    if (buff != null) {
                        local_scores.splice((j + 1), 1, buff);
                    }
                }
                local_scores.splice(i, 1, new_score);
                save();
                return;
            }
        } else {
            local_scores.splice(i, 1, new_score);
            save();
            return;
        }
    }
    save();
}

function save() {
    localStorage.setItem('local_scores', local_scores);
}

function getFromLocal() {
    var ar = localStorage.getItem('local_scores');
    if (ar) {
        ar = ar.split(',');
        ar.forEach(function(item, i, ar) {
            console.log('local ', item);
            if (item) {
                if (i == 0) {
                    record.text = 'Record: ' + item;
                }
                local_scores.splice(i, 1, parseInt(item));
            }
        });
        
        if(localStorage.getItem('shadow')){
            user_shadow = localStorage.getItem('shadow');
        }
    }
    
    var coins_from_local = localStorage.getItem('coins');
    if(coins_from_local){
        coins_score.text = 'X' + coins_from_local;
        coins_cap = coins_from_local;
    }
    
    
   
}

function parseShadow(shadowCode) {
    var toLoad = JSON.parse(LZString.decompressFromBase64(shadowCode));
    console.log(toLoad);
    return toLoad;
}

function saveLastAttempt(objects, keys_record, ticks) {
    var toSave = {
        "objects": objects,
        "keys": keys_record,
        "ticks": ticks
    };
    console.log(JSON.stringify(toSave));
    toSave = LZString.compressToBase64(JSON.stringify(toSave));
    //if(getTicks() > user_score){
        user_shadow = toSave;
    //}
    console.log("To save: length - ", toSave.length, ", content: ", toSave);
    console.log("Decompress test: ", LZString.decompressFromBase64(toSave));
}

var user_name;
var user_link;
var user_coins;
var user_shadow;
var user_score;

function postToDataBase(method, userName, userLink, misc ) {

   console.log("Post request");

    $.ajax({
        url: "http://dino-runner.appspot.com/Service",
        type: "POST",
        data: {method: method, user_name: userName, user_link: userLink, shadow: misc.shadow, coins: misc.coins, score: misc.score},
        dataType: "json",
        success: function (result) {
            switch (result) {
                case true:
                     console.log(result);
                    break;
                default:
                    console.log(result);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
       
        }
    });
};

function getFromDataBase(userName, userLink, cap) {
    console.log('get request');
    $.ajax({
        url: "http://dino-runner.appspot.com/Service",
        type: "GET",
        data: {user_name: userName, user_link: userLink, capacity: cap},
        dataType: "json",
        success: function (result) {
                
            if(cap == null){
                var list = result;
                console.log(list);
                if(!list){
                    postToDataBase('saveNewUser', user_name, user_link, {shadow: null, coins: 0, score: 0});
                    console.log('New User');
                }
                else {                     
                    user_coins = list[0].propertyMap.Coins;
                    coins_score.text = 'X' + user_coins;
                    coins_cap = user_coins;
                    localStorage.setItem('coins',user_coins);
                    user_score = list[0].propertyMap.Highscore;
                    if(local_scores[0] < user_score){
                        record.text = 'Record: ' + user_score;
                        local_scores[0] = user_score;
                    }
                    
                    
                    console.log(user_coins,user_score, user_shadow);
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        
        }
    });
};
//LZString.compress
//LZString.decompress
