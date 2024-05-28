package homework;

public class ReportCommand implements ShellCommand {
    private Repository repository;

    public ReportCommand(Repository repository) {
        this.repository = repository;
    }

    public void runCommand() throws Exception {
        RepositoryService service = new RepositoryService();
        service.generateTemplate(repository);
    }
}
