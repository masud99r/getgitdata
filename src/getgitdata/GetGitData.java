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

/**
 *
 * @author Masud
 */
public class GetGitData {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
   public static void main(String[] args) throws IOException {
        // TODO code application logic here
        GetGitData ggd = new GetGitData();
     //   ggd.runGitCommand();
        
    }
    
    /*public static void main(String[] args) throws IOException {
    String folder = "C:\\Users\\Masud\\Documents\\patch_projects\\tomcat";
    String command = "git diff --function-context --ignore-space-change  --ignore-all-space --ignore-blank-lines 8f169e42b2b108439db1040a17ad47ca034b6727 66ea263371199818562e043f42a5aabb5c2de710";
    ProcessBuilder pb = new ProcessBuilder(command);
    pb.directory(new File(folder));
    pb.inheritIO();
    Process p = pb.start();
    try {
        p.waitFor();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}*/
  public String getPatchGitCommand(String batchfile, String commit_sh1, String commit_sh2){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+commit_sh1+" "+commit_sh2);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
     while ((s = stdInput.readLine()) != null) {
        // System.out.println(s);
       if(s.startsWith("diff --git")||s.startsWith("index")){
       continue;
        }
       if(s.trim().startsWith("+")==false && s.trim().startsWith("-")==false){
             continue;
         }
        if(s.trim().startsWith("+++")==true || s.trim().startsWith("---")==true){
             continue;
         }
         if(count>4){
             cmd_results=cmd_results+"\n"+s;
         }
         else{
             cmd_results=s;
         }
         count++;
       //  System.out.println(count);
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return cmd_results;
}
  public String getPaerntsGitCommand(String batchfile, String commit_sh){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+commit_sh);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
     while ((s = stdInput.readLine()) != null) {
         
        // System.out.println(s);
         if(count>0){
             cmd_results=cmd_results+"\n"+s;
         }
         else{
             cmd_results=s;
         }
         count++;
       //  System.out.println(count);
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return cmd_results;
}
  public String getGitLogs(String batchfile, String project,String date){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+project+" "+date);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
     while ((s = stdInput.readLine()) != null) {
         
         //System.out.println(s);
         if(count>0){
             cmd_results=cmd_results+"\n"+s;
         }
         else{
             cmd_results=s;
         }
         count++;
       //  System.out.println(count);
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return cmd_results;
}
}
