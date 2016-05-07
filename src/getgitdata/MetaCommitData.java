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
    private String SHA;
    private String Commit_message;
    //private boolean isFix;
    private boolean bugFix;
    private String FileChanged;
    private String Author_name;
    private String Commit_date;
    private int Num_deleted_line;
    private int Num_added_line;
    private ArrayList<Integer> Lines_deleted;
    private ArrayList<Integer> Lines_added;
    private ArrayList<String> Code_Lines_deleted;
    private ArrayList<String> Code_Lines_added;
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
    void setFileChanged(String filename){
        this.FileChanged=filename;
    }
    void setAuthor_name(String name){
        this.Author_name=name;
    }
    void setCommit_date(String cdate){
        this.Commit_date=cdate;
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
    void setCode_Lines_deleted(ArrayList<String>code_del_lines){
        this.Code_Lines_deleted=code_del_lines;
    }
    void setCode_Lines_added(ArrayList<String> code_added_lines){
        this.Code_Lines_added=code_added_lines;
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
    String getFileChanged(){
        return this.FileChanged;
    }
    String getAuthor_name(){
        return this.Author_name;
    }
    String getCommit_date(){
        return this.Commit_date;
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
    ArrayList<String> getCode_Lines_deleted(){
        return this.Code_Lines_deleted;
    }
    ArrayList<String> getCode_Lines_added(){
        return this.Code_Lines_added;
    }
    
    @Override
    public String toString(){
        return "*****************************\n"+
        	"SHA: "+this.SHA+"\n"+
        	"Commit_message: "+this.Commit_message+"\n"+
        	//"isFix: "+this.isFix+"\n"+
        	"bugFix: "+this.bugFix+"\n"+
        	"FileChanged: "+this.FileChanged+"\n"+
        	"Author_name: "+this.Author_name+"\n"+
        	"Commit_date: "+this.Commit_date+"\n"+
        	"Num_deleted_line: "+this.Num_deleted_line+"\n"+
        	"Num_added_line: "+this.Num_added_line+"\n"+
        	"Lines_deleted: "+this.Lines_deleted.toString()+"\n"+
                "Code_Lines_deleted: "+this.Code_Lines_deleted.toString()+"\n"+
        	"Code_Lines_added: "+this.Code_Lines_added.toString()+"\n"+
        	"**********************************\n";

    }
    
}
