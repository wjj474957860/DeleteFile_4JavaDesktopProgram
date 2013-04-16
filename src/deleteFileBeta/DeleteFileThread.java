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
 *文件名：DeleteFileThread
 * 作 用：作为后台删除文件的线程，只要程序没有被终止，就一直运行在后台。
 * 算法思路：用户界面接收到用户删除文件的频度和删除文件的跟路径，之后就处于
 * 后台每隔一段时间就会去删除目录下的所有文件，包括目录下文件夹下面的文件。
 * 然后这个程序作用范围不是很广。
 * @author Spy
 */
public class DeleteFileThread extends Thread {

    //保存路径的变量
    private File f;
    String path;
    int time;
    FileWriter outWriter;

    //构造函数，初始化工作。
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
            delete( f );
            try {
                //这里代码的作用就是产生删除日志，文件名为output.txt.
                outWriter = new FileWriter("output.txt");
                outWriter.write(path);
                outWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(DeleteFileThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //线程休息一段时间（用户输入的时间间隔）
                sleep(time);
            } catch (InterruptedException ex) {
                System.out.println("出错：" + ex);
            }
        }
    }

    //删除文件函数，比较重要的函数。
    //参数：删除路径
    public void delete(File file) {
        //判断当前目录下是否还存在文件夹，若有，则进去，然后删除文件。
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                delete(file2);
            }
        } 
        //不存在文件夹，则开始删除文件。
        else {
            path += file.getAbsolutePath() +"\r\n";
            System.out.println(file.getAbsolutePath());
            file.delete();
        }
    }
}
