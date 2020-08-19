package com.Maaateusz.Firebase_DB_Auth;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//CRUD operations
@Service
public class UserService {

    public static final String COLLECTION_NAME="users";

    public String saveUser(User user) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        // Add document data  with id "custom_id" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("city", user.getCity());
        data.put("age", user.getAge());
        data.put("regions", Arrays.asList("west_coast", "socal"));
        //DocumentReference docRef = db.collection("users").add(data);
        ApiFuture<DocumentReference> result = db.collection("users").add(data);
        //asynchronously write data
        //ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().toString());

        //short version
        //ApiFuture<WriteResult> collectionsApiFuture = db.collection(COLLECTION_NAME).document(user.getName()).set(user);
        return result.get().getId();
    }

    public User getUser(String name) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document0 = future.get();
        User user = null;

        if(document0.exists()) {
            user = document0.toObject(User.class);
            return user;
        }else {
            return null;
        }
    }

    public String getAllUsers() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        String users = new String();
        for (QueryDocumentSnapshot document : documents) {
            users += "Id: "+document.getId()+", Name: " +document.getString("name") +"\n";
            System.out.println("User: " + document.getId());
            System.out.println("Name: " + document.getString("name"));
            if (document.contains("age")) {
                System.out.println("age: " + document.getLong("age"));
            }
            System.out.println("City: " + document.getString("city"));
        }
        return users;
    }

    public String updateUser(User person) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUser(String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(name).delete();
        return "Document with User ID "+name+" has been deleted";
    }

}
