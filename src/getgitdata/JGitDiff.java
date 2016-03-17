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
/** Copyright Steve Jin 2013 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
 
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
 
public class JGitDiff 
{
  public static void main(String[] args) throws Exception
  {
    File gitWorkDir = new File("C:/Users/Masud/Documents/GitHub/tomcat");
    Git git = Git.open(gitWorkDir);
 
    String newHash = "278a36a";
    String oldHash = "1b46e37b92705159ddc22fd8a28ee1d2b7499072";
    
    //ObjectId headId = git.getRepository().resolve("HEAD^{tree}");
   // ObjectId headId = git.getRepository().resolve(newHash + "^{tree}");
    ObjectId headId = git.getRepository().resolve(newHash+"^{tree}");
    ObjectId oldId = git.getRepository().resolve(newHash + "^^{tree}");
    ObjectReader reader = git.getRepository().newObjectReader();
     
    CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
    oldTreeIter.reset(reader, oldId);
    CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
    newTreeIter.reset(reader, headId);
 
    List<DiffEntry> diffs= git.diff()
            .setNewTree(newTreeIter)
            .setOldTree(oldTreeIter)
            .call();
     
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    DiffFormatter df = new DiffFormatter(out);
    df.setRepository(git.getRepository());
 int count=0;
    for(DiffEntry diff : diffs)
    {
        count++;
        System.out.println("DIff: "+diff.toString());
      df.format(diff);
     
     // diff.getOldId();
      String diffText = out.toString("UTF-8");
      System.out.println(diffText);
      out.reset();
    }
      System.out.println("Count: "+count);
  }
}
