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
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

//import org.junit.Before;
//import org.junit.Test;

public class TestJGit {

    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;


    public void init() throws IOException {
       // localPath = "/home/me/repos/mytest";
        localPath = "C:/Users/Masud/Documents/GitHub/tomcat/.git";
        remotePath = "git@github.com:me/mytestrepo.git";
        //localRepo = new FileRepository(localPath + "/.git");
        localRepo = new FileRepository(localPath);
        git = new Git(localRepo);
    }
    public void getCommand(){
        int count_commit=0;
        try {
            
            git.diff().call();
            git.log().call();
            git.reflog().call();
            
           // Git git = new Git(db);
            Iterable<RevCommit> log = git.log().call();
            // Collection<ReflogEntry> log = git.reflog().call();
            count_commit=0;
            while(log.iterator().hasNext()){
                count_commit++;
            String str_msg = log.iterator().next().toString()+"";
                System.out.println("GitLog: "+str_msg);
        }
        } catch (GitAPIException ex) {
            Logger.getLogger(TestJGit.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Total Entry: "+count_commit);
    }
   
    public void testCreate() throws IOException {
        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }

  
    public void testClone() throws IOException, GitAPIException {
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }

   
    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }

    
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }

   
    public void testPush() throws IOException, JGitInternalException,
            GitAPIException {
        git.push().call();
    }

    
    public void testTrackMaster() throws IOException, JGitInternalException,
            GitAPIException {
        git.branchCreate().setName("master")
                .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/master").setForce(true).call();
    }

    
    public void testPull() throws IOException, GitAPIException {
        git.pull().call();
    }
}
