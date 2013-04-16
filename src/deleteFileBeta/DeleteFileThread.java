/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deleteFileBeta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Spy
 */
public class DeleteFileThread extends Thread {

    private File f;
    String path;
    int time;
    FileWriter outWriter;

    DeleteFileThread(String filePath,String Time) {
        f = new File(filePath);
        time = 1000*Integer.parseInt(Time);
        path = "";
        outWriter = null;
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            System.out.println("删除线程在运行：" + i);
            delete(f);
            try {
                outWriter = new FileWriter("output.txt");
                outWriter.write(path);
                outWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(DeleteFileThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sleep(time);
            } catch (InterruptedException ex) {
                System.out.println("出错：" + ex);
            }
        }
    }

    public void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                delete(file2);
            }
        } else {
            path += file.getAbsolutePath() +"\r\n";
            System.out.println(file.getAbsolutePath());
            file.delete();
        }
    }
}
