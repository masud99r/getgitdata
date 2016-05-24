/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Masud
 */
public class BuggyCommits {
    public static String rootpath = "I:/Dev/NetbeanProjects/data/getgitdata/";
    
    public static void main(String[] args) {
        BuggyCommits bc = new BuggyCommits();
         GetGitData gitdata = new GetGitData();
         gitdata.setLang(".java");
         //gitdata.getGitLogs("gitlog.bat", "commons-math","1990-01-01");
         ArrayList<String> st = new ArrayList<>();
         ArrayList<MetaCommitData> mcd =new ArrayList<>();
        gitdata.processGitCommitsData("commons-math",rootpath+"gitlogs/commons-math/git_log.txt");
     //  gitdata.gitBlameSHA("commons-math", "gitblame_sha.bat","195", "e0b2c86c8754312d1e89307fbf84e6efbfa9fe0b", "src/test/java/org/apache/commons/math4/PerfTestUtils.java");
      // gitdata.getGitCommitUniform("git_dif_patch_uniform.bat","commons-math", "7c31eb6634d55db044b6c4297d38550a9b248046", "df46ed5edde4aea856cb16b8bc4ab791fef206ef");
     //ArrayList<Integer>al =  gitdata.getDeletedLinesBlame("commons-math", "src/main/java/org/apache/commons/math4/random/Well1024a.java", "7c31eb6634d55db044b6c4297d38550a9b248046", "df46ed5edde4aea856cb16b8bc4ab791fef206ef");
    //    System.out.println("List="+al.toString());
// mcd = gitdata.getGitCommitsData("commons-math",rootpath+"gitlogs/commons-math/git_log.txt");
          //bc.writeTocvs(mcd,"meta_commit_data_context");
         /*for(MetaCommitData m:mcd){
             System.out.println("Entry: "+m);
         }*/
        // gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // String git_logs = gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // System.out.println("Logs\n"+git_logs);
    }
  
}
