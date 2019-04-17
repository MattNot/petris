package com.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Noise {
	
	private Sound gameover;
	private Sound music;
	private Sound pause;
	private Sound rotate;
	private Sound start;
	
	public Noise() {
		gameover = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
		music = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
		pause = Gdx.audio.newSound(Gdx.files.internal("pause.mp3"));
		rotate = Gdx.audio.newSound(Gdx.files.internal("rotate.mp3"));
		start = Gdx.audio.newSound(Gdx.files.internal("start.mp3"));
	}
	
	public void playGameover() {
		gameover.play();
	}
	
	public void playMusic() {
		music.play();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playPause() {
		pause.play();
	}
	
	public void playRotate(){
		rotate.play();
	}
	
	public void playStart() {
		start.play();
	}
	
	public void dispose() {
		gameover.dispose();
		music.dispose();
		pause.dispose();
		rotate.dispose();
		start.dispose();
	}
	
}
