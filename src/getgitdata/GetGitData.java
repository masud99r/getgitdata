/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

import com.csvreader.CsvWriter;
import static getgitdata.BuggyCommits.rootpath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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
  public static String rootpath = "I:/Dev/NetbeanProjects/data/getgitdata/";
    
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
             //System.out.println("S="+s);
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
    int commit_entry_count=0;
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
        
         boolean isBug = isBuggyCommit(s);
         //boolean isFix = !isBug;
         String commit_message="";
         String sha="";
      
            String[] com_msg = s.trim().split(" ");
            sha = com_msg[0];
            commit_message=com_msg[1];
            for(int i=2;i<com_msg.length;i++){
                commit_message =commit_message+ " "+com_msg[i];
            }
            commit_message = commit_message.trim();
            
            
            
            //get author and date
            String gitshow_result = getGitShowCommand(project, sha);
            String[] gitshow_lines = gitshow_result.split("\n");
            String author_name= "";
            String commit_date ="";
            if(gitshow_lines.length>6){
                String[] author_parts = gitshow_lines[5].trim().split("Author:");
                if(author_parts.length>1){
                    author_name= author_parts[1].trim();
                }
                String[] commit_date_parts = gitshow_lines[6].trim().split("Date:");
                if(commit_date_parts.length>1){
                    commit_date = commit_date_parts[1].trim();
                }
                
            }
            
            
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
            //System.out.println("Number of file changed: "+patch_results.size());
            for(String processFile:patch_results){
                String[] result_lines = processFile.split("\n");
                int count_delete_line=0;
                int count_added_line=0;
                ArrayList<Integer>del_lines = new ArrayList<>();
                ArrayList<Integer>added_lines = new ArrayList<>();
                
                ArrayList<String>code_del_lines = new ArrayList<>();
                ArrayList<String>code_added_lines = new ArrayList<>();
                
                if(result_lines.length>0){
                    //System.out.println("Line tested="+result_lines[0]);
                    //System.out.println("Language="+language_extension);
                    if(result_lines[0].trim().endsWith(language_extension)){//specific file
                        for(int i=1;i<result_lines.length;i++){//skip different line
                            //testing
                            
                            //System.out.println("Line:"+result_lines[i]);
                            if(result_lines[i].startsWith("-")&&result_lines[i].startsWith("---")!=true){
                                String codeDel = result_lines[i].replaceFirst("-", "");
                                code_del_lines.add(codeDel);
                            }
                            if(result_lines[i].startsWith("+")&& result_lines[i].startsWith("+++")!=true){
                                String codeAdd = result_lines[i].replaceFirst("\\+", "");
                                code_added_lines.add(codeAdd);
                            }
                            
                            if(result_lines[i].trim().startsWith("@@")){
                                
                                String[] add_del_nums = result_lines[i].trim().split("@@");
                                if(add_del_nums.length>0){
                                    //System.out.println("add_del_nums[1]="+add_del_nums[1]);
                                    String middle_num = add_del_nums[1].trim();
                                    String[] middle_parts = middle_num.split(" ");
                                     //System.out.println("middle_parts="+middle_parts.length);
                                    if(middle_parts.length==2){
                                        if(middle_parts[0].startsWith("-")){ //deleted lines
                                            String textpart = middle_parts[0].replaceAll("-", "");
                                            String[] textparts = textpart.split(",");
                                            if(textparts.length==0 || textparts.length==1){
                                                //to do
                                                int line_no = Integer.parseInt(textparts[0].trim());
                                                count_delete_line++; //just one single line changed
                                                del_lines.add(line_no);
                                            }
                                            else if(textparts.length==2){
                                                int startFrom =  Integer.parseInt(textparts[0].trim());
                                                int count_next = Integer.parseInt(textparts[1].trim());
                                                count_delete_line +=count_next;
                                                for(int iline=0;iline<count_next;iline++){//add to arraylist
                                                    del_lines.add(startFrom);
                                                    startFrom++;
                                                }
                                            }
                                        }
                                        if(middle_parts[1].startsWith("+")){ //deleted lines
                                            String textpart = middle_parts[1].replaceAll("\\+", "");
                                            String[] textparts = textpart.split(",");
                                            if(textparts.length==0 || textparts.length==1){
                                                //to do
                                                int line_no = Integer.parseInt(textparts[0].trim());
                                                count_added_line++; //just one single line changed
                                                added_lines.add(line_no);
                                            }
                                            else if(textparts.length==2){
                                                int startFrom =  Integer.parseInt(textparts[0].trim());
                                                int count_next = Integer.parseInt(textparts[1].trim());
                                                count_added_line +=count_next;
                                                for(int iline=0;iline<count_next;iline++){//add to arraylist
                                                    added_lines.add(startFrom);
                                                    startFrom++;
                                                }
                                            }
                                        }
                                        //if same for + lines
                                    }
                                }
                            }
                            
                        }
                      String file_changed = result_lines[0].trim().split(" b")[1];//space b will give you the file name from source

                        MetaCommitData metadata = new MetaCommitData();
                        metadata.setSHA(sha);
                        metadata.setCommit_message(commit_message);
                        metadata.setbugFix(isBug);
                        //metadata.setisFix(isFix);
                        metadata.setAuthor_name(author_name);
                        metadata.setCommit_date(commit_date);

                        metadata.setFileChanged(file_changed);
                        metadata.setNum_added_line(count_added_line);
                        metadata.setNum_deleted_line(count_delete_line);
                        metadata.setLines_added(added_lines);
                        metadata.setLines_deleted(del_lines);
                        metadata.setCode_Lines_added(code_added_lines);
                        metadata.setCode_Lines_deleted(code_del_lines);
                        //System.out.println("Metadata entry: ******************");
                        //System.out.println(metadata);
                       // metaComData_list.add(metadata);//add to the link  
                       writeMetaDataTocvs(metadata,"meta_commit_data_context");//write to cvs file
                        commit_entry_count++;
                        if(commit_entry_count%1000==0){
                            System.out.println("Processed "+commit_entry_count+" commit entry");
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

private void writeMetaDataTocvs(MetaCommitData meta_data, String filename){
    String outputFile = rootpath+filename+".cvs";
    //System.out.println("Total Entry = "+commit_data.size());
    boolean alreadyExists = new File(outputFile).exists();
    try {
    // use FileWriter constructor that specifies open for appending
    CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
    // if the file didn't already exist then we need to write out the header line
    if (!alreadyExists)
    {
        csvOutput.write("SHA");
        csvOutput.write("Commit_message");
        //csvOutput.write("isFix");
        csvOutput.write("BugFix");
        csvOutput.write("FileChanged");
        csvOutput.write("Author_name");
        csvOutput.write("Commit_date");
        csvOutput.write("Num_deleted_line");
        
        csvOutput.write("Num_added_line");
        csvOutput.write("Array of Lines_deleted");
        csvOutput.write("Array of Lines_added");
        csvOutput.write("Array of Code Lines_deleted");
        csvOutput.write("Array of Code Lines_added");
        csvOutput.endRecord();
    }
    // else assume that the file already has the correct header line
   // for(MetaCommitData mdata:commit_data){
        csvOutput.write(meta_data.getSHA());
        csvOutput.write(meta_data.getCommit_message());
        //csvOutput.write(mdata.getisFix()+"");
        csvOutput.write(meta_data.getbugFix()+"");
        csvOutput.write(meta_data.getFileChanged());
        csvOutput.write(meta_data.getAuthor_name());
        csvOutput.write(meta_data.getCommit_date());
        csvOutput.write(meta_data.getNum_deleted_line()+"");
        csvOutput.write(meta_data.getNum_added_line()+"");
        csvOutput.write(meta_data.getLines_deleted().toString());
        csvOutput.write(meta_data.getLines_added().toString());
        
        csvOutput.write(meta_data.getCode_Lines_deleted().toString());
        csvOutput.write(meta_data.getCode_Lines_added().toString());
        csvOutput.endRecord();
    //}
    
    csvOutput.close();
    } catch (IOException e) {
            e.printStackTrace();
    }
    //System.out.println("Write succesfully cvs");
}

}
