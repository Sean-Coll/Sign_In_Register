/* This class provides simplified use of the SoundPool class which is used to play a collection
 * of short audio files. The sequence for setting up a CustomSoundPool is:
 * CustomSoundPool() the default constructor
 * setCon(Context con) this sets the context for the other methods. Calling this is required for
 * the object to work.
 * initialise() this initialises the attributes for the object and specifies that its usage is for
 * accessibility purposes and its content type is speech.
 * load(int resId) the resId should be R.raw.fileId. The priority for each file loaded will always
 * be 1.
 * play(int soundId) this will play the sound specified with a volume of 1, it won't loop and
 * the speed is normal.
 * release() use this when the activity is ending or the SoundPool isn't needed anymore.
 *
 * Author: Seán Coll
 * Created: 16/3/21
 * Last Edited: 16/3/21
 */

package com.example.sign_in_register;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class CustomSoundPool {

    SoundPool soundPool;
    int loginSound;
    private Context con;

    // Sets the context for the other methods. VERY IMPORTANT!
    public void setCon(Context con) {
        this.con = con;
    }

    // Sets up the objects attributes and builds the object
    public void initialise() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        // Only one sound can be played at one time. Trying to play a second will end the first.
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    // Loads a sound. The resId is in the format R.raw.fileId. The priority is always 1
    public int load(int resId) {
        // Returns the id that is used to identify the sound for playing
        return soundPool.load(this.con, resId, 1);
    }

    // Plays the specified sound without looping at a normal rate
    public void play(int soundId) {
        soundPool.play(soundId,1 ,1 ,1, 0, 1);
    }

    // Releases all memory being used by the object
    public void release() {
        soundPool.release();
    }
}
