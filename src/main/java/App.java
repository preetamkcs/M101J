import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import freemarker.template.Configuration;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by preetam on 3/4/17.
 */
public class App {
    public static void main(String[] args) {
        {
            final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setClassForTemplateLoading(App.class, "/freemarker");

            MongoClient client = new MongoClient();

            MongoDatabase database = client.getDatabase("students");
            final MongoCollection<Document> collection = database.getCollection("grades");

            for (int i=0;i<200;i++) {
                ArrayList<Document> studentGrades = collection.find(and(eq("type","homework"),eq("student_id", new Integer(i)))).into(new ArrayList<Document>(4));
//                System.out.println(studentGrades.toString());
//                System.out.println(studentGrades.get((studentGrades.get(0).getDouble("score")<=studentGrades.get(1).getDouble("score"))?0:1));
                collection.deleteOne(studentGrades.get((studentGrades.get(0).getDouble("score")<=studentGrades.get(1).getDouble("score"))?0:1));
            }
            /*Spark.get("/",new Route() {
                public Object handle(final Request request,
                                     final Response response) {
                    StringWriter writer = new StringWriter();
                    try {
                        Template template = configuration.getTemplate("answer.ftl");

                        // Not necessary yet to understand this.  It's just to prove that you
                        // are able to run a command on a mongod server
                        List<Document> results =
                                collection.aggregate(asList(new Document("$group", new Document("_id", "$value")
                                                .append("count", new Document("$sum", 1))),
                                        new Document("$match", new Document("count", new Document("$lte", 2))),
                                        new Document("$sort", new Document("_id", 1))))
                                        .into(new ArrayList<Document>());

                        int answer = 0;
                        for (Document cur : results) {
                            answer += (Double) cur.get("_id");
                        }

                        Map<String, String> answerMap = new HashMap<String, String>();
                        answerMap.put("answer", Integer.toString(answer));

                        template.process(answerMap, writer);
                    } catch (Exception e) {
                        e.printStackTrace();
                        halt(500);
                    }
                    return writer;
                }
            });*/
        }
    }
}