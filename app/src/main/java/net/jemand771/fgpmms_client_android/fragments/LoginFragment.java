package net.jemand771.fgpmms_client_android.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.jemand771.fgpmms_client_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void cawoosh();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    Button loginBtn;
    EditText loginUser, loginPass;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        loginBtn = view.findViewById(R.id.login_button);
        loginUser = view.findViewById(R.id.login_user);
        loginPass = view.findViewById(R.id.login_pass);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getView().getContext(), "hey", Toast.LENGTH_LONG).show();

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    doLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // mCallback.cawoosh();
            }
        });

        return view;
    }

    private void doLogin() throws IOException, JSONException {


        //TODO wrong password?
        //TODO nothing entered?
        //TODO input modification (lowercase etc)
        //TODO retrieve ip from somewhere
        //TODO save token
        //TODO make actual UI
        //TODO check if server available

        String username = loginUser.getText().toString();
        String password = loginPass.getText().toString();

        Socket socket = new Socket("192.168.178.155", 8080);

        Scanner sc = new Scanner(socket.getInputStream());
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

        JSONObject obj = new JSONObject();
        obj.put("action", "login");
        JSONObject userinfo = new JSONObject();
        userinfo.put("username", username);
        userinfo.put("password", password);
        obj.put("user", userinfo);

        String sendString = obj.toString();
        pw.println(sendString);
        Log.d("schnitzel", "sending " + sendString);
        String line = sc.nextLine();
        Log.d("schnitzel", "got " + line);
        JSONObject mainresponse = new JSONObject(line);
        String status = (String) mainresponse.get("status");
        if (status.equalsIgnoreCase("ok")) {
            JSONObject response = (JSONObject) mainresponse.get("response");
            String token = (String) response.get("token");
            if (token.length() == 36) {
                Log.d("schnitzel", "token: " + token);
            }

        }
    }

}
