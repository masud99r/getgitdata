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
         GetGitData gitdata = new GetGitData();
         //gitdata.getGitLogs("gitlog.bat", "commons-math","1990-01-01");
         ArrayList<String> st = new ArrayList<>();
         ArrayList<MetaCommitData> mcd ;
         mcd = gitdata.getGitCommitsData("gitlog.bat", "commons-math","1990-01-01");
         
        // gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // String git_logs = gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // System.out.println("Logs\n"+git_logs);
    }
private void writeTocvs(ArrayList<MetaCommitData> commit_data, String filename){
    String outputFile = rootpath+filename;
    boolean alreadyExists = new File(outputFile).exists();
    try {
    // use FileWriter constructor that specifies open for appending
    CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
    // if the file didn't already exist then we need to write out the header line
    if (!alreadyExists)
    {
            csvOutput.write("id");
            csvOutput.write("name");
            csvOutput.endRecord();
    }
    // else assume that the file already has the correct header line

    // write out a few records
    csvOutput.write("1");
    csvOutput.write("Bruce");
    csvOutput.endRecord();

    csvOutput.write("2");
    csvOutput.write("John");
    csvOutput.endRecord();

    csvOutput.close();
    } catch (IOException e) {
            e.printStackTrace();
    }

}
}
