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
        
         mcd = gitdata.getGitCommitsData("gitlog.bat", "commons-math","2015-01-01");
          //bc.writeTocvs(mcd,"meta_commit_data_context");
         /*for(MetaCommitData m:mcd){
             System.out.println("Entry: "+m);
         }*/
        // gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // String git_logs = gitdata.getGitLogs("gitlog.bat", "commons-math","2012-01-01");
        // System.out.println("Logs\n"+git_logs);
    }
    
    private void writeTocvs(ArrayList<MetaCommitData> commit_data, String filename){
    String outputFile = rootpath+filename+".cvs";
    System.out.println("Total Entry = "+commit_data.size());
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
    for(MetaCommitData mdata:commit_data){
        csvOutput.write(mdata.getSHA());
        csvOutput.write(mdata.getCommit_message());
        //csvOutput.write(mdata.getisFix()+"");
        csvOutput.write(mdata.getbugFix()+"");
        csvOutput.write(mdata.getFileChanged());
        csvOutput.write(mdata.getAuthor_name());
        csvOutput.write(mdata.getCommit_date());
        csvOutput.write(mdata.getNum_deleted_line()+"");
        csvOutput.write(mdata.getNum_added_line()+"");
        csvOutput.write(mdata.getLines_deleted().toString());
        csvOutput.write(mdata.getLines_added().toString());
        
        csvOutput.write(mdata.getCode_Lines_deleted().toString());
        csvOutput.write(mdata.getCode_Lines_added().toString());
        csvOutput.endRecord();
    }
    
    csvOutput.close();
    } catch (IOException e) {
            e.printStackTrace();
    }
    System.out.println("Write succesfully cvs");
}
}
