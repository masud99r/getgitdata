/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

import com.csvreader.CsvWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import util.ProcessData;

/**
 *
 * @author Masud
 */
public class GetGitData {
private String language_extension=".java";
public static String rootpath = "I:/Dev/NetbeanProjects/data/getgitdata/";

public ProcessData processData;  
public ArrayList<String> bugfix_keywords;
String file_path_buggy = "I:/Dev/NetbeanProjects/data/getgitdata/Projects_patch/commons-math/bug/";
String file_path_fixed = "I:/Dev/NetbeanProjects/data/getgitdata/Projects_patch/commons-math/fix/";
                        
public GetGitData(){
     processData = new ProcessData();
     bugfix_keywords = new ArrayList<>();
     bugfix_keywords.add("error");
     bugfix_keywords.add("bug");
     bugfix_keywords.add("fix");
     bugfix_keywords.add("fixing");
     bugfix_keywords.add("fixups");
     bugfix_keywords.add("fixed");
     bugfix_keywords.add("issue");
     bugfix_keywords.add("mistake");
     bugfix_keywords.add("blunder");
     bugfix_keywords.add("incorrect");
     bugfix_keywords.add("fault");
     bugfix_keywords.add("defect");
     bugfix_keywords.add("flaw");
     bugfix_keywords.add("glitch");
     bugfix_keywords.add("gremlin");
     bugfix_keywords.add("typo");
     bugfix_keywords.add("erroneous");
     
}
/**
 * @param args the command line arguments
 * @throws java.io.IOException
 */
public static void main(String[] args) throws IOException {
     // TODO code application logic here
     GetGitData ggd = new GetGitData();
  //   ggd.runGitCommand();
  ggd.storeGitLog("gitlog.bat", "commons-math","2015-01-01",rootpath+"gitlogs/commons-math/git_log.txt");

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
public ArrayList<String> getNewOldFile(String project, String commit_sh1, String commit_sh2){
    String cmd_results=null;
    ArrayList<String> changedFile_list = new ArrayList<>();
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+"git_dif_context.bat "+project+ " "+commit_sh1+" "+commit_sh2);
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
     //e.printStackTrace();
 }
 return changedFile_list;
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
public ArrayList<String> getBlammedCommits(String cmd_results){
    ArrayList<String> blameSHA = new ArrayList<>();
    String[]lines = cmd_results.split("\n");
    for(String line:lines){
        if(line.startsWith("I:\\Dev")){
            continue;
        }
        //String test = line.replaceFirst("(\\*)", "habijabi");
       // System.out.println("Test is = "+test);
        char[] charArray = line.toCharArray();
        char[] charConvertedArray= new char[charArray.length];
        boolean copyFlag =true;
        int flagCount=0;
        int j=0;
        for(int i=0;i<charArray.length;i++){
            if(charArray[i]=='(' && flagCount==0){
                flagCount++;
                copyFlag=false;
            }
            else if(charArray[i]==')' && flagCount==1){
                flagCount++;
                copyFlag=true; 
            }
            else if(copyFlag){
                charConvertedArray[j]=charArray[i];
                j++;
            } 
        }
      
        String modifiedLine = charConvertedArray[0]+"";
        for(int k=1;k<charConvertedArray.length;k++){
            modifiedLine = modifiedLine+""+charConvertedArray[k];
        }
        modifiedLine = modifiedLine.trim();
        String[]line_parts = modifiedLine.split(" ");
        String sha = line_parts[0];
        String codes = "";
        for(int p=2;p<line_parts.length;p++){
            codes = codes+line_parts[p];
        }
        String code_sha = sha+"_"+codes;
       // System.out.println(sha);
        blameSHA.add(code_sha);
    }
    return blameSHA;
}
public String gitBlameSHA(String projectname,String batchfile, int line_number, String bugfixed_sha, String fileName){
     String parentinfo = getPaerntsGitCommand("gitparent.bat",projectname, bugfixed_sha);
     //   System.out.println("Parent pair:"+parentinfo); 
    String[] parent_lines = parentinfo.split("\n");
    if(parent_lines.length<5){
        return null;
    }
    String parent_sha = parent_lines[4].split(" ")[1].trim();
    String bi_sha=null;
      String s = null;
      int count=0;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+" " +projectname+" "+line_number+" "+parent_sha+" "+fileName);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
     
     while ((s = stdInput.readLine()) != null) {
       if(count==4){
            //System.out.println("S is : "+s);
            bi_sha = s.split("\n")[0].split(" ")[0];
            break;
         }
         count++;
     }
 }catch(Exception e){
     e.printStackTrace();
 }
   // System.out.println("Result: "+bi_sha);
    //System.out.println("Lines: "+s);
 return s;
}
public String getGitBlame(String projectname,String batchfile, String commit_sh1, String fileName){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+" " +projectname+" "+commit_sh1+" "+fileName);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
      
     while ((s = stdInput.readLine()) != null) {
         if(count>4){
             cmd_results=cmd_results+"\n"+s;
             //System.out.println("S="+s);
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
    public String getGitCommitUniform(String batchfile, String project, String commit_sh1, String commit_sh2){
     String cmd_results=null;
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+project+" "+commit_sh1+" "+commit_sh2);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
     while ((s = stdInput.readLine()) != null) {
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
  //  System.out.println(cmd_results);
 return cmd_results;
}
public ArrayList<Integer> getDeletedLinesBlame(String project, String filepath,String parent_sha, String current_sha){
    ArrayList<Integer> delLinesNumber = new ArrayList<>();
    String resultDiff = getGitCommitUniform("git_dif_patch_uniform", project, parent_sha, current_sha);
   // System.out.println("Dif="+resultDiff);
    String[] lines = resultDiff.split("\n");
    int progress_flag=0;
    for (String s : lines) {
        // System.out.println("S="+s);
        if(s.startsWith("diff --git")&& s.endsWith(filepath)){
            if(progress_flag==0){
                progress_flag++;
            }
        }
        if(s.startsWith("diff --git")&& progress_flag>1){//next diff after given file done so skip
            break;
        }

        if(progress_flag>0){
             progress_flag++;
             //process given file deleted line number here
             if(s.startsWith("@@")){
                 String deladd = s.split("@@")[1];
                 String minus = deladd.trim().split(" ")[0];
                 String dellines = minus.replaceAll("-", "");
                 String[] delLines_part = dellines.split(",");
                 if(delLines_part.length==1){//single line deleted
                     delLinesNumber.add(Integer.parseInt(delLines_part[0]));
                 }else if(delLines_part.length==2){
                     int conseq = Integer.parseInt(delLines_part[1]);
                     int lineno = Integer.parseInt(delLines_part[0]);
                     for(int i=0;i<conseq;i++){
                         delLinesNumber.add(lineno);
                         lineno++;
                     }
                 }
             }
        }
        
    }
    return delLinesNumber;
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
  public boolean isBugFixedCommit(String commit_msg, ArrayList<String> bug_keywords){
    boolean flag=false;
    ArrayList<String> comsg_tokens = processData.TokenizeText(commit_msg);
    for(String bug_token:bug_keywords){
        if(flag==true){
            break;
        }
        bug_token = processData.ProcessToken(bug_token);
        if(bug_token.isEmpty()){
            continue;//skip empty keywords
        }
        for(String com_token:comsg_tokens){
            com_token = processData.ProcessToken(com_token);
            if(com_token.isEmpty()){
                continue;//skip empty commit message token
            }
            if(bug_token.equals(com_token)){
                flag=true;
            }
        }
    }  
      
    return flag;
  }
  public String getGitLogs(String batchfile, String project,String date){
     String cmd_results="";
    try{
     Process proc = Runtime.getRuntime().exec("cmd /c "+batchfile+ " "+project+" "+date);
     BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      int count=0;
      //skip first 5 lines
      if((s = stdInput.readLine()) != null && 
         (s = stdInput.readLine()) != null &&
         (s = stdInput.readLine()) != null &&
         (s = stdInput.readLine()) != null&&
         (s = stdInput.readLine()) != null);
      if((s = stdInput.readLine()) != null){
          cmd_results=s;//first coutput line
      }
     while ((s = stdInput.readLine()) != null) {
         cmd_results = cmd_results +"\n"+s;
     }
 }catch(Exception e){
     e.printStackTrace();
 }
 return cmd_results;
}
public void processGitCommitsData(String project,String shaFilePath){
    String cmd_results=null;
    int commit_entry_count=0;
    BufferedReader reader=null;
    try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(shaFilePath), "UTF-8"));

     String s = null;
     int dataLineCount=0;
     while ((s = reader.readLine()) != null) {
         dataLineCount++;
         String commit_message="";
         String sha="";
            String[] com_msg = s.trim().split(" ");
            sha = com_msg[0];
            commit_message=com_msg[1];
            for(int i=2;i<com_msg.length;i++){
                if(com_msg[i].isEmpty()){//skip additional white space
                    continue;
                }
                commit_message =commit_message+ " "+com_msg[i];//original message
            }
            commit_message = commit_message.trim();
            
            boolean bugFix = isBugFixedCommit(commit_message, bugfix_keywords);
            
            //get author and date
            String gitshow_result = getGitShowCommand(project, sha);
            String[] gitshow_lines = gitshow_result.split("\n");
            String author_name= "";
            String author_date ="";
            if(gitshow_lines.length>6){
                String[] author_parts = gitshow_lines[5].trim().split("Author:");
                if(author_parts.length>1){
                    author_name= author_parts[1].trim();
                }
                String[] commit_date_parts = gitshow_lines[6].trim().split("Date:");
                if(commit_date_parts.length>1){
                    author_date = commit_date_parts[1].trim();
                }
                
            }
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
            ArrayList<String> diff_result = getNewOldFile(project, commit_sh1,commit_sh2);
            int fileCount=0;//for each diff give different file name
            
            for(String diff_elem:diff_result){
                int totalAddedLine=0;
                int totalDeletedLine=0;
                
                int buggyFileLineNumber=0;
                int fixedFileLineNumber=0;
                
                ArrayList<Integer>del_lines = new ArrayList<>();
                ArrayList<Integer>added_lines = new ArrayList<>();
                String file_changed="";//space b will give you the file name from source
                String file_name="";
                fileCount++;//handle duplicate files
                
                //System.out.println("Each elem="+diff_elem);
                String[] result_lines = diff_elem.split("\n");
                if(result_lines.length>0){
                    //System.out.println("Line tested="+result_lines[0]);
                    //System.out.println("Language="+language_extension);
                    if(result_lines[0].trim().endsWith(language_extension)){//specific file
                        file_changed= result_lines[0].trim().split(" b/")[1];
                        file_name=sha+"_"+getFileName(result_lines[0].trim());
                         File file_buggy = new File(file_path_buggy+"buggy"+fileCount+"_"+file_name);
                         file_buggy.createNewFile();
                         
                         File file_fixed = new File(file_path_fixed+"fixed"+fileCount+"_"+file_name);
                         file_fixed.createNewFile();
	      
                    //    writeLine("",file_path_buggy+"buggy"+fileCount+"_"+file_name);
                    //    writeLine("",file_path_fixed+"fixed"+fileCount+"_"+file_name);
                        if(result_lines.length>=3){
                            String atLine = result_lines[4].trim();//@ ......@ code line here
                            String[] at_parts = atLine.split("@@");
                            /*for(int i=0;i<at_parts.length;i++){
                                System.out.println(i+" "+at_parts[i]);
                            }*/
                            if(at_parts.length==3){
                                String firstLine = at_parts[2].trim();
                                if(firstLine.startsWith("-")){
                                    firstLine = firstLine.replaceFirst("-", "");
                                   // writeLine(firstLine, file_path_buggy);
                                    writeLine(firstLine,file_path_buggy+"buggy"+fileCount+"_"+file_name);
                                    del_lines.add(buggyFileLineNumber);
                                    buggyFileLineNumber++;
                                    totalDeletedLine++;
                                }
                                else if(firstLine.startsWith("+")){
                                    firstLine = firstLine.replaceFirst("\\+", "");
                                   // writeLine(firstLine, file_path_fixed);
                                    writeLine(firstLine,file_path_fixed+"fixed"+fileCount+"_"+file_name);
                                    added_lines.add(fixedFileLineNumber);
                                    fixedFileLineNumber++;
                                    totalAddedLine++;
                                }
                                else{
                                //     writeLine(firstLine, file_path_buggy);
                                //     writeLine(firstLine, file_path_fixed);
                                     writeLine(firstLine,file_path_buggy+"buggy"+fileCount+"_"+file_name);
                                     writeLine(firstLine,file_path_fixed+"fixed"+fileCount+"_"+file_name);
                                     
                                   // del_lines.add(buggyFileLineNumber);
                                    buggyFileLineNumber++;
                                    //added_lines.add(fixedFileLineNumber);
                                    fixedFileLineNumber++;
                                    
                                }
                                
                            }
                        }
                        for(int i=5;i<result_lines.length;i++){//skip diffe
                            
                            String cline = result_lines[i];
                            if(cline.startsWith("@@")){//need special treatment
                                String atLine = cline.trim();//@ ......@ code line here
                            String[] at_parts = atLine.split("@@");
                           
                                if(at_parts.length==3){
                                    String firstLine = at_parts[2].trim();
                                    if(firstLine.startsWith("-")){
                                        firstLine = firstLine.replaceFirst("-", "");
                                       // writeLine(firstLine, file_path_buggy);
                                        writeLine(firstLine,file_path_buggy+"buggy"+fileCount+"_"+file_name);
                                        del_lines.add(buggyFileLineNumber);
                                        buggyFileLineNumber++;
                                        totalDeletedLine++;
                                    }
                                    else if(firstLine.startsWith("+")){
                                        firstLine = firstLine.replaceFirst("\\+", "");
                                       // writeLine(firstLine, file_path_fixed);
                                        writeLine(firstLine,file_path_fixed+"fixed"+fileCount+"_"+file_name);
                                        added_lines.add(fixedFileLineNumber);
                                        fixedFileLineNumber++;
                                        totalAddedLine++;
                                    }
                                    else{
                                    //     writeLine(firstLine, file_path_buggy);
                                    //     writeLine(firstLine, file_path_fixed);
                                         writeLine(firstLine,file_path_buggy+"buggy"+fileCount+"_"+file_name);
                                         writeLine(firstLine,file_path_fixed+"fixed"+fileCount+"_"+file_name);
                                         
                                         //del_lines.add(buggyFileLineNumber);
                                         buggyFileLineNumber++;
                                        // added_lines.add(fixedFileLineNumber);
                                         fixedFileLineNumber++;
                                    }

                                }
                            }
                               else if(cline.startsWith("-")){
                                    cline = cline.replaceFirst("-", "");
                                    //writeLine(cline, file_path_buggy);
                                    writeLine(cline,file_path_buggy+"buggy"+fileCount+"_"+file_name);
                                    del_lines.add(buggyFileLineNumber);
                                    buggyFileLineNumber++;
                                    totalDeletedLine++;
                                }
                                else if(cline.startsWith("+")){
                                    cline = cline.replaceFirst("\\+", "");
                                //    writeLine(cline, file_path_fixed);
                                    writeLine(cline,file_path_fixed+"fixed"+fileCount+"_"+file_name);
                                    added_lines.add(fixedFileLineNumber);
                                    fixedFileLineNumber++;
                                    totalAddedLine++;
                                }
                                else{
                                    // writeLine(cline, file_path_buggy);
                                    // writeLine(cline, file_path_fixed);
                                    writeLine(cline,file_path_buggy+"buggy"+fileCount+"_"+file_name);
                                    writeLine(cline,file_path_fixed+"fixed"+fileCount+"_"+file_name);
                                    //del_lines.add(buggyFileLineNumber);
                                    buggyFileLineNumber++;
                                    //added_lines.add(fixedFileLineNumber);
                                    fixedFileLineNumber++;
                                }
                        }
                              //added to metapost here
                
            MetaCommitData metadata = new MetaCommitData();
            metadata.setProjectName("commons-math");
            metadata.setSHA(sha);
            metadata.setCommit_message(commit_message);
            metadata.setbugFix(bugFix);
            metadata.setChangedFileName(file_changed);
            metadata.setAuthor_name(author_name);
            metadata.setAuthor_date(author_date);
            metadata.setNum_deleted_line(totalDeletedLine);
            metadata.setNum_added_line(totalAddedLine);
            metadata.setLines_deleted(del_lines);
            metadata.setLines_added(added_lines);
            metadata.setBuggyFilePath("Projects_patch/commons-math/buggy"+fileCount+"_"+file_name);
            metadata.setFixedFilePath("Projects_patch/commons-math/fixed"+fileCount+"_"+file_name);
            //System.out.println("Metadata entry: ******************");
            //System.out.println(metadata);
           // metaComData_list.add(metadata);//add to the link
           
           commit_entry_count++;
            if(commit_entry_count%100==0){
                System.out.println("Processed "+commit_entry_count+" commit entry");
            }
           
           ArrayList<String> blammedCommit = new ArrayList<>();
           ArrayList<String> blammedCommit_file = new ArrayList<>();
           ArrayList<String> blammedCommit_line = new ArrayList<>();
           ArrayList<Integer> lineToBlame = getDeletedLinesBlame(project, file_changed,commit_sh2, commit_sh1);
           for(int i=0;i<lineToBlame.size();i++){
              
               int linenum = lineToBlame.get(i);
                blammedCommit_line.add(linenum+"");
               String bi_info = gitBlameSHA(project,"gitblame_sha.bat", linenum, sha, file_changed);
              // String bi_info = bi_info_all.trim().split(" ")[0];
               if(bi_info==null){
                   continue;
               }
              // System.out.println("sss"+bi_info);
               String[] bi_info_parts = bi_info.split(" ");
              /* for(int j=0;j<bi_info_parts.length;j++){
                   System.out.println("part="+bi_info_parts[j]);
               }*/
               String bi_sha = null;
               String bi_file = null;
               
                   bi_sha = bi_info_parts[0];
                   bi_file = bi_sha+"_"+bi_info_parts[1];
             
               
                   if(blammedCommit.contains(bi_sha)==false){
                        blammedCommit.add(bi_sha);
                   }
             
                   if(blammedCommit_file.contains(bi_file)==false){
                        blammedCommit_file.add(bi_file); 
                   }
                   
               
     
           }
          //  System.out.println("Hey"+blammedCommit); 
          //  System.out.println("Hey file"+blammedCommit_file); 
           metadata.setbuggyCommitSHA(blammedCommit);
           metadata.setbuggyCommitSHA_file(blammedCommit_file);
           metadata.setbuggyCommitSHA_line(blammedCommit_line);
           writeMetaDataTocvs(metadata,"meta_commit_data_bugfix");//write to cvs file
              
                    }
                }
        
            }
            
            //String parentinfo = getPaerntsGitCommand("gitparent.bat","commons-math", sha);
         //    System.out.println("Parent pair:"+parentinfo);
        // }
       if(dataLineCount%50==0){
           System.out.println("dataLineCount: "+dataLineCount);
       }
       if(dataLineCount>=400){
           break;
       }
     }
 }catch(Exception e){
     e.printStackTrace();
 }
}
private void storeGitLog(String batchfile, String project,String date,String filePath){
   String cmd_output = getGitLogs(batchfile, project, date);
   String[] out_part = cmd_output.split("\n");
   int count=0;
      for (String out_part1 : out_part) {
          writeLine(out_part1, filePath);
          count++;
          if(count%500==0){
              System.out.println("Completed = "+count);
          }
      }
}
private void writeLine(String text, String filePathName){
   FileWriter fw=null;
    try {
        fw = new FileWriter(filePathName,true);
        fw.write(text+"\n");
        
    }catch(Exception e){
        e.printStackTrace();
    }
   finally{
        
        if (fw != null)
            try{     
                fw.close();
            } catch (Exception e) {
             e.printStackTrace();
            }   
   }
}
private String getFileName(String str){
    String name=null;
    String [] name_parts = str.split("/");
    if(name_parts.length>0){
        name = name_parts[name_parts.length-1];
    }
    return name;
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
        csvOutput.write("ProjectName");
        csvOutput.write("SHA");
        csvOutput.write("Commit_message");
        csvOutput.write("bugFix");
        csvOutput.write("ChangedFileName");
        csvOutput.write("Author_name");
        csvOutput.write("Author_date");
        csvOutput.write("Num_deleted_line");
        csvOutput.write("Num_added_line");
        csvOutput.write("Lines_deleted");
        csvOutput.write("Lines_added");
        csvOutput.write("BuggyFilePath");
        csvOutput.write("FixedFilePath");
        csvOutput.write("buggyCommitSHA");
        csvOutput.write("buggyCommitSHA_file");
        csvOutput.write("buggyCommitSHA_line");
        csvOutput.endRecord();
    }
    // else assume that the file already has the correct header line
   csvOutput.write(meta_data.getProjectName());
        csvOutput.write(meta_data.getSHA());
        csvOutput.write(meta_data.getCommit_message());
        csvOutput.write(meta_data.getbugFix()+"");
        csvOutput.write(meta_data.getChangedFileName());
        csvOutput.write(meta_data.getAuthor_name());
        csvOutput.write(meta_data.getAuthor_date());
        csvOutput.write(meta_data.getNum_deleted_line()+"");
        csvOutput.write(meta_data.getNum_added_line()+"");
        String deletedLines = "";
        for(Integer lineNum:meta_data.getLines_deleted()){
            deletedLines +=" "+lineNum.toString();
        }
        csvOutput.write(deletedLines.trim());
        String addedLines = "";
        for(Integer lineNum:meta_data.getLines_added()){
            addedLines +=" "+lineNum.toString();
        }
        csvOutput.write(addedLines.trim());
        
        csvOutput.write(meta_data.getBuggyFilePath());
        csvOutput.write(meta_data.getFixedFilePath());
        csvOutput.write(meta_data.getbuggyCommitSHA().toString());
        csvOutput.write(meta_data.getbuggyCommitSHA_file().toString());
        csvOutput.write(meta_data.getbuggyCommitSHA_line().toString());
        csvOutput.endRecord();
    
    csvOutput.close();
    } catch (IOException e) {
            e.printStackTrace();
    }
    //System.out.println("Write succesfully cvs");
}

}
