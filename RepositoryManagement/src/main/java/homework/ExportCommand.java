package homework;

public class ExportCommand implements ShellCommand {

    private Repository repo;
    private String path;

    public ExportCommand(Repository repo,String path){
        this.repo = repo;
        this.path=path;
    }

    public void runCommand() {

        var service = new RepositoryService();
        try{
        service.export(repo,path);
        }catch(Exception ex){
            System.err.println("Failed to export repository '"+this.repo.getDirectory()+"'. Error :"+ex);
        }
    }

}
