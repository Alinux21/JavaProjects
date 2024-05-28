// package homework;

// import java.util.Optional;

// public class ViewCommand implements ShellCommand {

//     private Repository repo;
//     private int docId;

//     public ViewCommand(Repository repo,int docId){
//         this.repo=repo;
//         this.docId=docId;
//     }

//     public void runCommand() throws InvalidFile{
        
//         var service = new RepositoryService();

//         System.out.println("Contents of '"+repo.getDirectory()+"':");
//         service.print(repo);
        
//         Optional<Document> doc = repo.findDocument(docId);
//         if(doc.isEmpty()){
//             throw new InvalidFile(docId);
//         }

//         service.view(doc.get());

//     }

// }
