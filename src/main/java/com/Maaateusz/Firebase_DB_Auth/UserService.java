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
//        Map<String, Object> data = new HashMap<>();
//        data.put("name", user.getName());
//        data.put("city", user.getCity());
//        data.put("age", user.getAge());
//        data.put("regions", Arrays.asList("west_coast", "socal"));
        //DocumentReference docRef = db.collection("users").add(data);
        ApiFuture<DocumentReference> result = db.collection(COLLECTION_NAME).add(user);
        //asynchronously write data
        //ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().toString());

        //short version
        //ApiFuture<WriteResult> collectionsApiFuture = db.collection(COLLECTION_NAME).document(user.getName()).set(user);
        return result.get().getId();
    }

    public User getUser(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(id);
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
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        String users = new String();
        for (QueryDocumentSnapshot document : documents) {
            users += "Id: "+document.getId() +"\n";
            for(Object asd: document.getData().keySet()){
                System.out.println(asd.toString());
                users += asd.toString() +": "+ document.get(asd.toString()).toString() +'\n';
            }
        }
        return users;
    }

    public String updateUser(User user) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(user.getName()).update(null);
        //https://firebase.google.com/docs/firestore/manage-data/add-data?authuser=1#java_21
        //ApiFuture<DocumentReference> result = dbFirestore.collection(COLLECTION_NAME).document();
        //ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLLECTION_NAME).document(USER_ID).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUser(String user_id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COLLECTION_NAME).document(user_id).delete();
        return "Document with User ID "+ user_id +" has been deleted\n" + writeResult.get().getUpdateTime();
    }

}
