/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Masud
 */
public class GitTest {
    public static void main(String args[]) throws IOException{
        GitTest gt = new GitTest();
        System.out.print("Start collecting data");
        gt.getdata();
        System.out.println("End collecting data");
    }
    public void getdata(){
        try {
            String command = "git log";
            
            
            File gitPath = new File("C:\\Users\\Masud\\Documents\\GitHub\\tomcat");
            //Runtime.exec("git logs", null, gitPath);
            Process p = Runtime.getRuntime().exec(command, null, gitPath);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String text = command + "\n";
            System.out.println(text);
            while ((line = input.readLine()) != null) {
                text += line;
                System.out.println("Line: " + line);
            }
        } catch (IOException ex) {
            Logger.getLogger(GitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
