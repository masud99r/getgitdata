/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

/**
 *
 * @author Masud
 */
public class BuggyCommits {
    public static void main(String[] args) {
         GetGitData gitdata = new GetGitData();
         gitdata.getGitLogs("gitlog.bat", "commons-math","1990-01-01");
         
        // gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // String git_logs = gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // System.out.println("Logs\n"+git_logs);
    }
   
    
}
