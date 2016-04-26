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
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Masud
 */
public class GetGitData {
  private String language_extension=".java";
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
   public static void main(String[] args) throws IOException {
        // TODO code application logic here
        GetGitData ggd = new GetGitData();
     //   ggd.runGitCommand();
        
    }
public void setLang(String language){
    this.language_extension=language;
}
public String getLang(){
    return this.language_extension;
}
public String getPatchGitCommand(String project, String commit_sh1, String commit_sh2){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+"git_dif_patch.bat "+project+ " "+commit_sh1+" "+commit_sh2);
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
   public ArrayList<String> getPatchGitCommand_linenumber(String project, String commit_sh1, String commit_sh2){
     String cmd_results=null;
     ArrayList<String> changedFile_list = new ArrayList<>();
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+"git_dif_patch_uniform.bat "+project+ " "+commit_sh1+" "+commit_sh2);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      s = stdInput.readLine();
              s = stdInput.readLine();
                      s = stdInput.readLine();
                              s = stdInput.readLine();//skipping first few unnecessary lines
       // System.out.println("Start first: "+s);
        boolean firstCount=true;
        while ((s = stdInput.readLine()) != null) {
         if(s.trim().startsWith("diff")){
             if(!firstCount){
                 changedFile_list.add(cmd_results);
                 firstCount=true;
             }
             cmd_results="";//start over
         }
         if(firstCount){
              cmd_results=s;
             firstCount = false;
         }
         else{
             cmd_results=cmd_results+"\n"+s;
         }
         
     }
    if(cmd_results.isEmpty()==false){
        changedFile_list.add(cmd_results);
    }
 }catch(Exception e){
     e.printStackTrace();
 }
 return changedFile_list;
}
  public String getGitCommitInfo(String batchfile, String commit_sh1, String commit_sh2){
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
  public String getPaerntsGitCommand(String batchfile, String project, String commit_sh){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+project+" "+commit_sh);
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
  public String getGitShowCommand(String project, String commit_sh){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+"gitshow.bat"+ " "+project+" "+commit_sh);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
     while ((s = stdInput.readLine()) != null) {
         if(count>0){
             cmd_results=cmd_results+"\n"+s;
         }
         else{
             cmd_results=s;
         }
         count++;
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return cmd_results;
}
  public boolean isBuggyCommit(String commit_msg){
      //System.out.println("Commit="+commit_msg);
      String [] msg_tokens = commit_msg.trim().split(" ");
      HashSet<String> msg_tokens_set = new HashSet<>();
      for(int i=0;i<msg_tokens.length;i++){
          msg_tokens_set.add(msg_tokens[i].toLowerCase());
      }
      boolean flag =false;
      boolean bug_flag = false;
      boolean fix_flag = false;
      if(msg_tokens_set.contains("bug")||
         msg_tokens_set.contains("bugs")||
         msg_tokens_set.contains("buggy")||
         msg_tokens_set.contains("bugging")||
         msg_tokens_set.contains("bugid")||
              msg_tokens_set.contains("issue")||
              msg_tokens_set.contains("issues")||
              msg_tokens_set.contains("defect")||
               msg_tokens_set.contains("defects")||
              msg_tokens_set.contains("patch")
        ){
          bug_flag=true;
      }
      if(msg_tokens_set.contains("fix")||
         msg_tokens_set.contains("fixed")||
         msg_tokens_set.contains("fixes")||
         msg_tokens_set.contains("fixing")
        
        ){
          fix_flag=true;
      }
    //  flag = fix_flag & bug_flag;
    //  System.out.println("Flag = "+ flag + " Fix = "+fix_flag+" Bug = "+bug_flag);
    flag = fix_flag;
      return flag;
  }
  public String getGitLogs(String batchfile, String project,String date){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+project+" "+date);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
     while ((s = stdInput.readLine()) != null) {
         boolean isBug = isBuggyCommit(s);
         String commit_message="";
         String sha="";
         if(isBug){
            //System.out.println(s);
            String[] com_msg = s.trim().split(" ");
            sha = com_msg[0];
            commit_message=com_msg[1];
            for(int i=2;i<com_msg.length;i++){
                commit_message =commit_message+ " "+com_msg[i];
            }
            commit_message = commit_message.trim();
            System.out.println(sha+"="+commit_message);
            String parentinfo = getPaerntsGitCommand("gitparent.bat","commons-math", sha);
             System.out.println("Parent pair:"+parentinfo);
         }
         
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return cmd_results;
}
public ArrayList<MetaCommitData> getGitCommitsData(String batchfile, String project,String date){
    ArrayList<MetaCommitData> metaComData_list = new ArrayList<>();
    String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+project+" "+date);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
      s = stdInput.readLine();//skip
      s = stdInput.readLine();
      s = stdInput.readLine();
      s = stdInput.readLine();
     while ((s = stdInput.readLine()) != null) {
         MetaCommitData metadata = new MetaCommitData();
         boolean isBug = isBuggyCommit(s);
         boolean isFix = !isBug;
         String commit_message="";
         String sha="";
      
            String[] com_msg = s.trim().split(" ");
            sha = com_msg[0];
            commit_message=com_msg[1];
            for(int i=2;i<com_msg.length;i++){
                commit_message =commit_message+ " "+com_msg[i];
            }
            commit_message = commit_message.trim();
            
            metadata.setSHA(sha);
            metadata.setCommit_message(commit_message);
            metadata.setisBug(isBug);
            metadata.setisFix(isFix);
            
            //get author and date
            String gitshow_result = getGitShowCommand(project, sha);
            String[] gitshow_lines = gitshow_result.split("\n");
            String author_name= "";
            String commit_date ="";
            if(gitshow_lines.length>=6){
                author_name = gitshow_lines[4].trim().split("Author:")[0].trim();
                commit_date = gitshow_lines[5].trim().split("Date:")[0].trim();
            }
            metadata.setAuthor_name(author_name);
            metadata.setCommit_date(commit_date);
            
            //System.out.println("git show: "+gitshow_result);
           // System.out.println(sha+" "+commit_message);
            
            //find parent sha and diff info
            String commit_ssh = getPaerntsGitCommand("gitparent.bat",project,sha);
            //System.out.println("Commit pair *******start*******");
            //System.out.println(commit_ssh);
            //System.out.println("Commit pair *********end*****");
             String[] commit_parts = commit_ssh.trim().split("\n");
             if(commit_parts.length<4){
                 System.out.println("Cannot find parent commit for this commit SHA: "+sha);
                 continue;//skip ambiguous commit
             }
            String[] two_commits = commit_parts[3].trim().split(" ");
            /*must do it in reverse way to get proper commit order*/
            String commit_sh2 = two_commits[0].trim();//parent commit sha
            String commit_sh1 = two_commits[1].trim();//child/current commit sha
            
            ArrayList<String> patch_results = getPatchGitCommand_linenumber(project, commit_sh1,commit_sh2);
            //System.out.println("Patch:*********** \n"+patch_results.toString());
            System.out.println("Number of file changed: "+patch_results.size());
            for(String processFile:patch_results){
                String[] result_lines = processFile.split("\n");
                int count_delete_line=0;
                int count_added_line=0;
                ArrayList<Integer>del_lines = new ArrayList<>();
                ArrayList<Integer>added_lines = new ArrayList<>();
                if(result_lines.length>0){
                    if(result_lines[0].trim().endsWith(language_extension)){//specific file
                        for(int i=1;i<result_lines.length;i++){
                            if(result_lines[i].trim().startsWith("@@")){
                                String[] add_del_nums = result_lines[i].trim().split("@@");
                                if(add_del_nums.length>0){
                                    String middle_num = add_del_nums[0].trim();
                                    String[] middle_parts = middle_num.split(" ");
                                    if(middle_parts.length==2){
                                        if(middle_parts[0].startsWith("-")){ //deleted lines
                                            String textpart = middle_parts[0].replaceAll("-", "");
                                            String[] textpats = textpart.split(",");
                                            if(textpats.length==0 || textpats.length==1){
                                                //to do
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //String parentinfo = getPaerntsGitCommand("gitparent.bat","commons-math", sha);
         //    System.out.println("Parent pair:"+parentinfo);
        // }
         
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return metaComData_list;
}
}
