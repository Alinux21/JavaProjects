// package homework;

// import java.io.File;
// import java.io.IOException;
// import java.nio.file.*;
// import java.util.*;

// public class Repository extends SimpleFileVisitor<Path> {

//     private int documentId = 0;

//     private String directory;
//     private Map<Person, List<Document>> documents = new HashMap<>();

//     public Repository(String directory) {

//         this.directory = directory;
//         loadDocuments();

//     }

//     public Map<Person,List<Document>> getDocuments(){
//         return this.documents;
//     }

//     public String getDirectory() {
//         return this.directory;
//     }

//     public Optional<Document> findDocument(int id) {
//         for (Map.Entry<Person, List<Document>> entry : documents.entrySet()) {
//             Optional<Document> documentFound = entry.getValue().stream()
//                     .filter(doc -> doc.id() == id)
//                     .findAny();
//             if (documentFound.isPresent()) {
//                 return documentFound;
//             }
//         }

//         throw new InvalidFile(id);

//     }

//     private void loadDocuments() {
//         Path startingDir = Paths.get(this.directory);

//         try {
//             Files.walk(startingDir)
//                     .filter(Files::isDirectory)
//                     .forEach(this::mapFiles);
//         } catch (IOException exception) {
//             System.err.println(exception);
//         }
//     }

//     private void mapFiles(Path path) {
//         File file = path.toFile();

//         if (file.isDirectory()) {
//             if (isPersonDirectory(file.getName())) {

//                 Person person = extractPerson(file.getName());
//                 List<Document> personDocuments = new ArrayList<>();
//                 Arrays.stream(file.listFiles())
//                         .filter(File::isFile)
//                         .forEach(documentFile -> personDocuments.add(new Document(documentId++,documentFile.getName(),documentFile.getAbsolutePath())));
//                 documents.put(person, personDocuments);
//             }

//         }
//     }

//     private boolean isPersonDirectory(String directory) {
//         String[] tokens = directory.split("_");
//         return tokens.length == 2; // name_id format
//     }

//     private Person extractPerson(String directory) {
//         String[] tokens = directory.split("_");
//         String name = tokens[0];
//         int id = Integer.parseInt(tokens[1]);
//         return new Person(id, name);
//     }

// }
