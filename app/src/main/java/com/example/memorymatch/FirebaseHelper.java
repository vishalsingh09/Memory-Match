package com.example.memorymatch;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    public final String TAG = "JACKSON";
    private static String uid = null;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private int points;

    private User myUserData;


    public FirebaseHelper() {
        // Get a reference to or the instance of the auth and firestore elements
        // These lines of code establish the connections to the auth and database were linked to
        // based on the json file
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Log.i(TAG, "Firebasehelper constrcutor " + mAuth.getUid());
        if(mAuth.getUid() != null){
            attachReadDataToUser();
        }
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void updateUid(String uid) {

        this.uid = uid;

        Log.i(TAG, "users uid set to: " + uid);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) { this.points = points; }

    public User getUser(){
        return myUserData;
    }

    public void setUser(User newUser){
        this.myUserData = newUser;
    }



    public void attachReadDataToUser() {
        // This is necessary to avoid the issues we ran into with data displaying before it returned
        // from the asynch method calls
        if (mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
            readData(new FirestoreCallback() {
                @Override
                public void onCallback(User myPersonData) {
                    Log.d(TAG, "Inside attachReadDataToUser, onCallback " + myPersonData.toString());
                }
            });
        }
        else {
            Log.d(TAG, "No one logged in");
        }

    }

    public void addUserToFirestore(String firstName, String lastName, String username, String email, String password, String newUID) {

        // Create a new user with their name
        Map<String, Object> user = new HashMap<>();
        user.put("uid", newUID);
        user.put("username", username);
        user.put("email", email);
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("password", password);

        user.put("highscore", 0);
        user.put("points", 0);
        user.put("hints", 0);
        user.put("currentLevel", 1);

        user.put("docID", "");
        //User user = new User(newUID, username, email, firstName, lastName, password);

        db.collection(newUID).add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection(newUID)
                                .document(documentReference.getId()).update("docID", documentReference.getId());
                        Log.i(TAG, "success in addUserToFirestore");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "failure iun addUserToFirestore");
                    }
                });

    }


    public void addData(User u) {
        // add WishListItem w to the database
        // this method is overloaded and incorporates the interface to handle the asynch calls
        addData(u, new FirestoreCallback() {
            @Override
            public void onCallback(User myPersonData) {
                Log.i(TAG, "Inside addData, onCallback :" + myUserData.toString());
            }
        });
    }

    private void addData(User u, FirestoreCallback firestoreCallback) {
        db.collection(uid)
                .add(u)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // This will set the docID key for the WishListItem that was just added.
                        db.collection(uid)
                                .document(documentReference.getId()).update("docID", documentReference.getId());
                        Log.i(TAG, "just added " + u.getUsername());
                        readData(firestoreCallback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error adding document", e);
                    }
                });
    }

    public void editData(User u) {
        // edits current User in the database
        // this method is overloaded and incorporates the interface to handle the asynch calls
        editData(u, new FirestoreCallback() {
            @Override
            public void onCallback(User myPersonData) {
                Log.i(TAG, "Inside editData, onCallback " + myPersonData.toString());
            }
        });
    }



    private void editData(User u, FirestoreCallback firestoreCallback) {
        String docId = u.getDocID();
        // db.collection("users").document(uid).collection("myWishList")
        db.collection(uid)
                .document(docId)
                .set(u)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, "Success updating document");
                        readData(firestoreCallback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error updating document", e);
                    }
                });
    }



    public void deleteData(User u) {
        // delete item w from database
        // this method is overloaded and incorporates the interface to handle the asynch calls
        deleteData(u, new FirestoreCallback() {
            @Override
            public void onCallback(User myPersonData) {
                Log.i(TAG, "Inside deleteData, onCallBack" + myPersonData.toString());
            }
        });
    }

    public void deleteData(User u, FirestoreCallback firestoreCallback) {
        // delete item w from database
        String docId = u.getDocID();
        //db.collection("users").document(uid).collection("myWishList")
        db.collection(uid)
                .document(docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, u.getUsername() + " successfully deleted");
                        readData(firestoreCallback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error deleting document", e);
                    }
                });
    }



    /* https://www.youtube.com/watch?v=0ofkvm97i0s

    This video is good!!!   Basically he talks about what it means for tasks to be asychronous

    and how you can create an interface and then using that interface pass an object of the interface

    type from a callback method and access it after the callback method.  It also allows you to delay

    certain things from occurring until after the onSuccess is finished.

     */


    private void readData(FirestoreCallback firestoreCallback) {
        myUserData = null;        // empties the AL so that it can get a fresh copy of data
        db.collection(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Try to run the line of code instead of the for loop
                            myUserData = task.getResult().getDocuments().get(0).toObject(User.class);
                            Log.i(TAG, "inside readData - userName: "+ task.getResult().getDocuments().get(0).get("userName"));

//                            for (DocumentSnapshot doc: task.getResult()) {
//                                myUserData = doc.toObject(User.class);
//                            }
                            Log.i(TAG, "Success reading data: "+ myUserData.toString());
                            firestoreCallback.onCallback(myUserData);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });
    }


    //https://stackoverflow.com/questions/48499310/how-to-return-a-documentsnapshot-as-a-result-of-a-method/48500679#48500679

    public interface FirestoreCallback {

        void onCallback(User myPersonData);

    }

}