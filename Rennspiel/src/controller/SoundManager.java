package controller;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class SoundManager {

	private static AudioClip ac;

	public static void playSound(URL url) {
		if (ac == null || !ac.isPlaying()) {
		ac = new AudioClip(url.toString());
			ac.play();
		}
	}

	public static void stopSound() {
		if (ac != null) {
			ac.stop();
		}
	}

}
