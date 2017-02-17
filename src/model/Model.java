package model;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

import static view.View.list;

/**
 * Created by Christopher Tse on 2/11/2017.
 */
public class Model {

    public static int currentImage;
    private boolean isLoaded = false;
    public static File[] imgs;
    private static String dir;
    private static String res;
    private static String[] filenames;
    private static ArrayList<Listener> listeners = new ArrayList<>();


    public Model() {
        filenames = new String[] {"Empty"};
        currentImage = 0;
    }

    public void imgUp() {
        if(isLoaded) {
            if (currentImage == imgs.length-1)
                currentImage = 0;
            else
                currentImage++;
            setImage(currentImage);
            list.setSelectedIndex(currentImage);
        }
    }

    public void imgDown() {
        if(isLoaded) {
            if (currentImage == 0)
                currentImage = imgs.length -1;
            else
                currentImage--;
            setImage(currentImage);
            list.setSelectedIndex(currentImage);
        }
    }

    public void openFolder() {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("."));
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("Current directory: " + jfc.getCurrentDirectory());
            System.out.println("Current file: " + jfc.getSelectedFile().getAbsolutePath());

            imgs = jfc.getSelectedFile().getAbsoluteFile().listFiles();
            currentImage = 0;

            dir = jfc.getSelectedFile().getAbsoluteFile().toString();
                for (File file : imgs) {
                    System.out.println(file.getName());
                }
            String firstpic = dir + imgs[0].getName();
            setImage(0);
            addItems();
            isLoaded = true;
            notifyOpenListener();
            System.out.println(imgs.length);

        } else {
            System.out.println("No selection");
        }
    }

    private void setImage(int index) {

        res = dir + "/" + imgs[index].getName();
    }

    private void addItems() {
        String[] arr;
        filenames = new String[imgs.length];
        int i = 0;
        for (File file : imgs) {
            arr = file.getName().split("/");
            filenames[i] = arr[arr.length - 1];
            i++;
        }
        System.out.println(filenames.length);
    }

    public void updateIndex(int index) {
        currentImage = index;
        setImage(index);
        notifyImageListener();
    }

    public void notifyImageListener() {
        for( final Listener listener : listeners ) {
            listener.imageChanged();
        }
    }

    public void notifyOpenListener() {
        for( final Listener listener : listeners ) {
            listener.folderOpened();
        }
    }

    public String[] getFilenames() {
        return filenames;
    }
    public String getRes() { return res;}
    public int getCurrentImage() {return currentImage;}

    public static void addListener(final Listener listener) {
        listeners.add(listener);
    }
}
