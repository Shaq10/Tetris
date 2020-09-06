import sun.audio.AudioPlayer;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Shaquille on 09/12/2016.
 */

// code copied from Simon Lucas
// code copied by Udo Kruschwitz

public class TetrisViewTest{
    private static AudioPlayer AP = AudioPlayer.player;
    private static ContinuousAudioDataStream loop = null;
    public static void main(String[] args) {
        // test the view component
        int w = 10; //value for width
        int h = 20; //value for height
        int[][] a = new int[w][h];
        int[][] mb = new int[w][h];
        try
        {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/Super.wav")); //get source of audio file
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Thread.sleep(100);
        }
        catch(FileNotFoundException e1){
            System.out.print(e1.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        AP.start(loop);

        TetrisView tv = new TetrisView(a, mb);

        new JEasyFrame(tv, "SHAQUILLE THOMAS 1504471");
    }
}
