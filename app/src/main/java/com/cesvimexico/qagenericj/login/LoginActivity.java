package com.cesvimexico.qagenericj.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cesvimexico.qagenericj.Camera.CameraActivity;
import com.cesvimexico.qagenericj.MainArea;
import com.cesvimexico.qagenericj.R;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    //Gestionar FirebaseAuth
    //private FirebaseAuth mAuth;
    //Agregar cliente de inicio de sesión de Google
    //private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1;
    //final String TAG = "GoogleSignIn";
//    String web_client_id;
    TextInputEditText txtUsr;
    TextInputEditText txtPwd;

//    private static final String KEY_IS_RESOLVING = "is_resolving";
//    private static final String KEY_CREDENTIAL = "key_credential";
//    private boolean mIsResolving = false;
//    private Credential mCredential;
//    private CredentialsClient mCredentialsClient;
//    private GoogleSignInClient mSignInClient;

    //CredentialsClient mCredentialsClient;

    private SharedPreferences preferences;

    String email = "";
    //private GoogleSignInOptions gso;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 /* El codigo que puse a mi request */: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // aqui ya tengo permisos
                } else {
                    // aqui no tengo permisos
                }
                return;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //ProDialog.show();
        //progressBar.setProgress(0);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
              //public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, /* Este codigo es para identificar tu request */ 1);

        }


//        BeginSignInRequest request = BeginSignInRequest.builder()
//                .setPasswordRequestOptions(
//                        BeginSignInRequest.PasswordRequestOptions.builder()
//                                .setSupported(true)
//                                .build())
//                .setGoogleIdTokenRequestOptions(
//                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                                .setSupported(true)
//                                // Set filterByAuthorizedAccounts = true to avoid duplicated accounts being created
//                                .setFilterByAuthorizedAccounts(true)
//                                .setServerClientId("serverClientID")
//                                .build())
//                .build();

        //FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) { //si no es null el usuario ya esta logueado
//            //mover al usuario a loginActivity
//            Intent loginActivity = new Intent(LoginActivity.this, StartActivity.class);
//            startActivity(loginActivity);
//
//        }


        //////// comando dese consola para obtener SHA1 ya que en algunas versiones de android studion ya no aparecen en las barras lateral de Gradlew
        //  Open Terminal in android studio, type ./gradlew signingReport and press Enter.
        //https://stackoverflow.com/questions/67490537/android-studio-4-2-doesnt-show-signing-report-in-the-gradle-bar


        //super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        //getPermissFineLocation();
    }

//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }


    public void getAccessToken() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        String password = txtPwd.getText().toString().trim();
        String username = txtUsr.getText().toString().trim();

        Call<AccessToken> call = service.getAccessToken("react-levinfocesvi", "password", "JIL9a6LloKmArbSBRFv9J0g1Bohw4lNK", "profile email", username, password);

        //react-levinfocesvi
        //JIL9a6LloKmArbSBRFv9J0g1Bohw4lNK

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    //AccessToken accessToken = response.body();

                    preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
                    //String auditor = preferences.getString("email", "");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", username);
                    editor.apply();


                    BeginSignInRequest request = BeginSignInRequest.builder()
                            .setGoogleIdTokenRequestOptions(
                                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                            .setSupported(true)
                                            .setFilterByAuthorizedAccounts(false)
                                            .setServerClientId("serverClientID")
                                            .build())
                            .build();

                    Intent intent = new Intent(LoginActivity.this, MainArea.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

//        if (savedInstanceState != null) {
//            mIsResolving = savedInstanceState.getBoolean(KEY_IS_RESOLVING, false);
//            mCredential = savedInstanceState.getParcelable(KEY_CREDENTIAL);
//        }





        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
        String auditor = preferences.getString("email", "");
        if (!auditor.equals("")) {
            //Toast.makeText(getApplicationContext(), "No tiene permisos para utilizar esta aplicación", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainArea.class);
            startActivity(intent);
            finish();
            return;
        }

        //web_client_id = getString(R.string.web_client_id);
        txtUsr = findViewById(R.id.txtUsr);
        txtPwd = findViewById(R.id.txtPwd);

        Button boton = (Button) findViewById(R.id.buttonLoginKC);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAccessToken();

            }
        });

        //startService(new Intent( this, FirestoreService.class ) );

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                //.requestIdToken(getString(R.string.default_web_client_id))
//                .requestIdToken(web_client_id)
//                .requestEmail()
//                .build();

        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        mAuth = FirebaseAuth.getInstance();

        //dialogo de permisos
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{
                        //Manifest.permission.ACCESS_FINE_LOCATION,
                        //Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA

                }, /* Este codigo es para identificar tu request */ 1);

//        Button boton = (Button) findViewById(R.id.buttonLogin);
//        boton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                signIn();
//
//            }
//        });
    }

    private void onCredentialRetrieved(Credential credential) {
        String accountType = credential.getAccountType();
        if (accountType == null) {
            // Sign the user in with information from the Credential.
//            signInWithPassword(credential.getId(), credential.getPassword());
        } else if (accountType.equals(IdentityProviders.GOOGLE)) {
            // The user has previously signed in with Google Sign-In. Silently
            // sign in the user with the same ID.
            // See https://developers.google.com/identity/sign-in/android/
            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();

            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
            Task<GoogleSignInAccount> task = signInClient.silentSignIn();
            // ...
        }
    }

//    private void buildClients(String accountName) {
//        GoogleSignInOptions.Builder gsoBuilder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail();
//
//        if (accountName != null) {
//            gsoBuilder.setAccountName(accountName);
//        }
//
//        mCredentialsClient = Credentials.getClient(this);
//        mSignInClient = GoogleSignIn.getClient(this, gsoBuilder.build());
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            if (task.isSuccessful()) {
//                try {
//                    // Google Sign In was successful, authenticate with Firebase
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                    firebaseAuthWithGoogle(account.getIdToken());
//                } catch (ApiException e) {
//                    // Google Sign In failed, GUI
//                    Log.w(TAG, "Google sign in failed", e);
//                }
//            } else {
//                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
//                Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(),
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//    private void firebaseAuthWithGoogle(String idToken) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference usuarios = db.collection("usuarios");
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            //Log.d(TAG, "signInWithCredential:success");
//                            //FirebaseUser user = mAuth.getCurrentUser();
//                            FirebaseUser currentUser = mAuth.getCurrentUser();
//                            email = currentUser.getEmail();
//                            Query reg = db.collection("usrrtmsec").whereEqualTo("email", email);
//                            reg
//                                    .get()
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            List<DocumentSnapshot> total = task.getResult().getDocuments();
//                                            if(total.size()==0){
//                                                Toast.makeText(getApplicationContext(), "No tiene permisos para utilizar esta aplicación", Toast.LENGTH_SHORT).show();
//                                                mAuth.signOut();
//                                                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                        } else {
//                                                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
//                                                                    Toast.LENGTH_LONG).show();
//                                                        }
//                                                    }
//                                                });
//                                                return;
//                                            }
//                                            if (task.isSuccessful()) {
//                                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                                    //if (email.equals(document.get("email").toString())) {
//                                                        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
//                                                        //vin = texto_encontrado.getText().toString();
//                                                        //intent.putExtra("vin", vin);
//                                                        //intent.putExtra("metodo", METODO);
//                                                        startActivity(intent);
//                                                        //LoginActivity.this.finish();
//                                                        finish();
//                                                    //} else {
//
//                                                    //}
//                                                }
//                                            }
//                                        }
//
//                                    });
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                        }
//                    }
//                });
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                //.requestIdToken(getString(R.string.default_web_client_id))
//                .requestIdToken(web_client_id)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}