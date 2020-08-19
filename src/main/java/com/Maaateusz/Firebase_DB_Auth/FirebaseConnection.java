package com.Maaateusz.Firebase_DB_Auth;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//https://github.com/firebase/quickstart-java

@Service
public class FirebaseConnection {

    private static final String DATABASE_URL = "https://fir-host-1.firebaseio.com/";

    @PostConstruct
    public void createFirebaseClient() throws IOException {
        //Using an OAuth 2.0 refresh token
        FileInputStream refreshToken  = new FileInputStream("G://Projects/java beckend/fir-host-1-firebase-adminsdk-1xp1i-487f93d63b.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setDatabaseUrl(DATABASE_URL)
                .build();

        FirebaseApp.initializeApp(options);
    }

    /*@PostConstruct
    public void createFirebaseClient2() throws IOException {
        // Use the application default credentials
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build();
        FirebaseApp.initializeApp(options);

    }*/

    //Initialize multiple apps
    /*private void createFirebaseMultipleClient() {
        // Initialize the default app
        FirebaseApp defaultApp = FirebaseApp.initializeApp(); //defaultOptions

        System.out.println(defaultApp.getName());  // "[DEFAULT]"

        // Retrieve services by passing the defaultApp variable...
        FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

        // ... or use the equivalent shorthand notation
        defaultAuth = FirebaseAuth.getInstance();
        defaultDatabase = FirebaseDatabase.getInstance();
    }

    private void createDatabaseClient() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", "Los Angeles");
        docData.put("state", "CA");
        docData.put("country", "USA");
        docData.put("regions", Arrays.asList("west_coast", "socal"));
        ApiFuture<WriteResult> future = db.collection("cities").document("LA").set(docData);
        System.out.println(future.get().getUpdateTime());
    }*/
}
