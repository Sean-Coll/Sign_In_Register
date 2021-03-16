package com.example.sign_in_register;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class CustomSoundPool {

    SoundPool soundPool;
    int loginSound;
    private Context con;


    public void setCon(Context con) {
        this.con = con;
    }

    public void initialise() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        loginSound = soundPool.load(this.con, R.raw.login, 1);
    }

    public int load(int resId, int priority) {
        int soundId;

        soundId = soundPool.load(this.con, resId, priority);

        return soundId;
    }

    public void play(int soundId) {
        soundPool.play(soundId,1 ,1 ,1, 0, 1);
    }
}
