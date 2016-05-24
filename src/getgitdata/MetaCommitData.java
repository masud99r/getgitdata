/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

import java.util.ArrayList;

/**
 *
 * @author Masud
 */
public class MetaCommitData {
    private String ProjectName;
    private String SHA;
    private String Commit_message;
    private boolean bugFix;
    private String ChangedFileName;
    private String Author_name;
    private String Author_date;
    private int Num_deleted_line;
    private int Num_added_line;
    private ArrayList<Integer> Lines_deleted;
    private ArrayList<Integer> Lines_added;
    private String BuggyFilePath;
    private String FixedFilePath;
    private ArrayList<String> buggyCommitSHA;
    private ArrayList<String> buggyCommitSHA_file;
    private ArrayList<String> buggyCommitSHA_line;
    
    void setProjectName(String project){
        this.ProjectName=project;
    }
    void setSHA(String sha){
        this.SHA=sha;
    }
    void setCommit_message(String com_msg){
        this.Commit_message=com_msg;
    }
    /*void setisFix(boolean fix){
        this.isFix=fix;
    }*/
    void setbugFix(boolean bug){
        this.bugFix=bug;
    }
    void setChangedFileName(String filename){
        this.ChangedFileName=filename;
    }
    void setAuthor_name(String name){
        this.Author_name=name;
    }
    void setAuthor_date(String cdate){
        this.Author_date=cdate;
    }
    void setNum_deleted_line(int nd_lines){
        this.Num_deleted_line=nd_lines;
    }
    void setNum_added_line(int na_lines){
        this.Num_added_line=na_lines;
    }
    void setLines_deleted(ArrayList<Integer>del_lines){
        this.Lines_deleted=del_lines;
    }
    void setLines_added(ArrayList<Integer> added_lines){
        this.Lines_added=added_lines;
    }
    void setBuggyFilePath(String FilePath){
        this.BuggyFilePath=FilePath;
    }
    void setFixedFilePath(String FilePath){
        this.FixedFilePath=FilePath;
    }
    void setbuggyCommitSHA(ArrayList<String> sha_list){
        buggyCommitSHA=sha_list;
    }
    void setbuggyCommitSHA_file(ArrayList<String> sha_list_file){
        buggyCommitSHA_file=sha_list_file;
    }
    void setbuggyCommitSHA_line(ArrayList<String> sha_list_line){
        buggyCommitSHA_line=sha_list_line;
    }
    String getProjectName(){
    	return this.ProjectName;
    }
    String getSHA(){
    	return this.SHA;
    }
    String getCommit_message(){
        return this.Commit_message;
    }
    /*boolean getisFix(){
        return this.isFix;
    }*/
    boolean getbugFix(){
        return this.bugFix;
    }
    String getChangedFileName(){
        return this.ChangedFileName;
    }
    String getAuthor_name(){
        return this.Author_name;
    }
    String getAuthor_date(){
        return this.Author_date;
    }
    int getNum_deleted_line(){
        return this.Num_deleted_line;
    }
    int getNum_added_line(){
        return this.Num_added_line;
    }
    ArrayList<Integer> getLines_deleted(){
        return this.Lines_deleted;
    }
    ArrayList<Integer> getLines_added(){
        return this.Lines_added;
    }
    String getBuggyFilePath(){
        return this.BuggyFilePath;
    }
    String getFixedFilePath(){
        return this.FixedFilePath;
    }
    ArrayList<String> getbuggyCommitSHA(){
        return buggyCommitSHA;
    }
    ArrayList<String> getbuggyCommitSHA_file(){
        return buggyCommitSHA_file;
    }
    ArrayList<String> getbuggyCommitSHA_line(){
        return buggyCommitSHA_line;
    }
    
    @Override
    public String toString(){
        return "*****************************\n"+
        	"SHA: "+this.SHA+"\n"+
        	"Commit_message: "+this.Commit_message+"\n"+
        	//"isFix: "+this.isFix+"\n"+
        	"bugFix: "+this.bugFix+"\n"+
        	"ChangedFileName: "+this.ChangedFileName+"\n"+
        	"Author_name: "+this.Author_name+"\n"+
        	"Author_date: "+this.Author_date+"\n"+
        	"Num_deleted_line: "+this.Num_deleted_line+"\n"+
        	"Num_added_line: "+this.Num_added_line+"\n"+
                "BuggyFilePath: "+this.BuggyFilePath+"\n"+
                "FixedFilePath: "+this.FixedFilePath+"\n"+
                "buggyCommitSHA: "+this.buggyCommitSHA+"\n"+
                "buggyCommitSHA_file: "+this.buggyCommitSHA_file+"\n"+
                "buggyCommitSHA_line: "+this.buggyCommitSHA_line+"\n"+
        	"**********************************\n";

    }
    
}
