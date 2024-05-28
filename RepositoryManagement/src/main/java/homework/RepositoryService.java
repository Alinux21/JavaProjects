// package homework;

// import java.awt.Desktop;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.TimeZone;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import freemarker.template.*;

// public class RepositoryService {

//     public void print(Repository repo) {

//         var documents = repo.getDocuments();
//         documents.forEach((person, docs) -> {
//             System.out.println(person.name() + "_" + person.id());
//             if (docs.size() != 0) {
//                 docs.stream().forEach(doc -> System.out.println("|_-> id:" + doc.id() + " " + doc.name()));
//                 System.out.println();
//             } else
//                 System.out.println("|_-> (empty)\n");

//         });

//     }

//     public void view(Document doc) {

//         File document = new File(doc.path());

//         Desktop d = Desktop.getDesktop();
//         try {
//             d.open(document);
//         } catch (IOException ex) {
//             System.out.println("Failed to open document: " + document.getName());
//         }

//     }

//     private Configuration setupTemplate() {

//         Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

//         // Specify the source where the template files come from. Here I set a
//         // plain directory for it, but non-file-system sources are possible too:

//         try {
//             cfg.setDirectoryForTemplateLoading(
//                     new File("C:\\Users\\alinm\\Desktop\\java_course_fii_2024\\lab5\\homework+bonus\\templates"));
//         } catch (IOException ex) {
//             System.err.println("Failed to create template at the set path : " + ex);
//         }

//         cfg.setDefaultEncoding("UTF-8");

//         // Sets how errors will appear.
//         // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is
//         // better.
//         cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

//         // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
//         cfg.setLogTemplateExceptions(false);

//         cfg.setWrapUncheckedExceptions(true);

//         cfg.setFallbackOnNullLoopVariable(false);

//         cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
//         return cfg;
//     }

//     public void generateTemplate(Repository repo) {
//         Configuration cfg = setupTemplate();

//         Map<String, Object> root = new HashMap<>();

//         int i = 1, j = 1;
//         for (Map.Entry<Person, List<Document>> entry : repo.getDocuments().entrySet()) {
//             String key = "employee" + i;
//             i++;
//             root.put(key, entry.getKey().name() + "_" + entry.getKey().id());
//             if (entry.getValue().size() != 0) {

//                 for (Document doc : entry.getValue()) {
//                     String key2 = "file" + j;
//                     j++;
//                     root.put(key2, "id :" + doc.id() + " " + doc.name());
//                 }

//             }

//         }

//         Template temp;
//         try {
//             temp = cfg.getTemplate("test.ftlh");

//             BufferedWriter writer = new BufferedWriter(new FileWriter("test.html"));

//             temp.process(root, writer);
//             writer.close();

//             File document = new File("C:\\Users\\alinm\\Desktop\\java_course_fii_2024\\lab5\\homework+bonus\\test.html");

//             Desktop d = Desktop.getDesktop();
//             try {
//                 d.open(document);
//             } catch (IOException ex) {
//                 System.out.println("Failed to open template: " + document.getName());
//             }

//         } catch (IOException | TemplateException e) {
//             e.printStackTrace();
//         }
//     }

//     public void export(Repository repo, String path) throws Exception {

//         ObjectMapper objectMapper = new ObjectMapper();
//         objectMapper.writeValue(
//                 new File(path),
//                 repo);

//     }

// }
