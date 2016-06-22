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
var swap = true;
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
        } else {
            sound_button.dis = true;
            sound.texture = sound_off_icon;
            coin_sound_muted = true;
            jump_sound_muted = true;
            arrow_sound_muted = true;
            shuriken_sound_muted = true;
        }
    };
    music_button.click = function(event) {
        if (music_button.dis) {
            music_button.dis = false;
            music.texture = music_on_icon;
            main_theme.muted = false;
        } else {
            music_button.dis = true;
            music.texture = music_off_icon;
            main_theme.muted = true;
        }
    };
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
    var texture = PIXI.Texture.fromImage('images/coin.png');
    var coin = new PIXI.Sprite(texture);
    addTexture(coin, 10, 10);
    coin.width = 70;
    coin.height = 70;
    dino.isJumping = false;
    dino.isFalling = false;

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
        restart = keyboard(82);
    space = keyboard(32);
    space.press = function() {
        if (!started) {
            started = true;
            alert.text = '';
        }
        if (!dino.isJumping && !down.isDown && !failed && !paused) {
            dino.vy = -10;
            dino.isJumping = true;
            if (!jump_sound_muted) {
                createjs.Sound.play('jump_sound', {
                    volume: 0.3
                });
            }
            dino.texture = dino_jump;
        }
    }
    space.release = function() {
        if (dino.isJumping) {
            dino.isFalling = true;
            dino.texture = dino_fall;
            if (!down.isDown) {
                dino.vy = 10;
            }
        }
    }
    down.press = function() {
        if (dino.isJumping) {
            dino.vy = 20;
            dino.texture = dino_fall;
        }
        dino.duck = true;
    };
    down.release = function() {
        if (dino.isFalling) {
            dino.vy = 10;
        }
        dino.duck = false;
    }
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
        dino.texture = dino_skin;
        dino.x = 100;
        dino.y = 600 - 150;
        ticks = 0;
        last_tick = 0;
        score.text = 'Score: 0';
        coins_score.text = 'X0';
        coins_cap = 0;
        alert.text = 'Press "Space" to start';
        alert.position.x = 500 - alert.width / 2;
        started = false;
        paused = false;
        failed = false;
        dino.isJumping = false;
        dino.duck = false;
        dino.isFalling = false;
    };
    renderer.render(stage);
    requestAnimationFrame(animate);

    function animate() {
        requestAnimationFrame(animate);
        if (started && !paused && !failed) {
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
                        if (item.type == 'arrow' || item.type == 'shuriken') {
                            failed = true;
                            alert.text = 'Game Over';
                            saveScore(getTicks());
                            alert.position.x = 500 - alert.width / 2;
                        }
                        if (item.type == 'coin') {
                            if (!coin_sound_muted) {
                                createjs.Sound.play('coin_sound', {
                                    volume: 0.2
                                });
                            }
                            coins_score.text = 'X' + (++coins_cap);
                            objects.splice(i, 1);
                            stage.removeChild(item);
                        }
                    }
                }
            });
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
                spawnObject(object1, x, y, speed, rotation, rotSpeed, type, height, width);
                objects.push(object1);
                id++;
            }
            if ((dino.isJumping && space.isDown) || dino.isFalling) {
                dino.y += dino.vy;
            } else if (getTicks() % 2 == 0 && !dino.duck) {
                if (swap) {
                    dino.texture = dino_run_1;
                    swap = !swap;
                } else {
                    dino.texture = dino_run_2;
                    swap = !swap;
                }
            }
            if (dino.y < 150 && !down.isDown) {
                dino.vy = 10;
                dino.isFalling = true;
                dino.texture = dino_fall;
            }
            if (dino.y >= (600 - 150)) {
                dino.y = 600 - 150;
                dino.vy = 0;
                dino.isJumping = false;
                dino.isFalling = false;
            }
            if (dino.duck) {
                if (getTicks() % 2 == 0) {
                    if (swap) {
                        dino.texture = dino_duck_1;
                        swap = !swap;
                    } else {
                        dino.texture = dino_duck_2;
                        swap = !swap;
                    }
                }
            }
            ticks++;
            score.text = 'Score: ' + getTicks();
        }
        renderer.render(stage);
    }
    getFromLocal();
});

function getTicks() {
    return parseInt((ticks) / 5);
}

function addTexture(name, x, y) {
    name.position.x = x;
    name.position.y = y;
    stage.addChild(name);
}

function spawnObject(name, x, y, speed, rotation, rotSpeed, type, height, width) {
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
    stage.addChild(name);
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
    }
}