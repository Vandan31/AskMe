package com.example.chatbot;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
    ImageButton copy_btn;
RecyclerView recyclerView;
TextView textWelcome;
EditText edtMessage;
ImageButton btnSend;
ImageView btnLogout;
List<Message> messageList;
FirebaseAuth mAuth;
MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
recyclerView= findViewById(R.id.chat_rv);
textWelcome=findViewById(R.id.txtWelcome);
edtMessage=findViewById(R.id.message_edit_text);
btnSend=findViewById(R.id.send_btn);
//copy_btn=findViewById(R.id.btn_copy);
btnLogout= findViewById(R.id.btnLogout);
mAuth= FirebaseAuth.getInstance();
btnLogout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(MainActivity2.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
});
messageList = new ArrayList<>();
messageAdapter= new MessageAdapter(messageList);
recyclerView.setAdapter(messageAdapter);
LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
linearLayoutManager.setStackFromEnd(true);
recyclerView.setLayoutManager(linearLayoutManager);
btnSend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String qts= edtMessage.getText().toString().trim();
        addToChat(qts,Message.SENT_BY_ME);
        edtMessage.setText("");
        CallAPI(qts);
        textWelcome.setVisibility(View.GONE);
    }
});
    }
    void addToChat(String message,String sendby){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sendby));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }
    void CallAPI(String qts){
        messageList.add(new Message("Typing...",Message.SENT_BY_BOT));
        JSONObject jsonObject= new JSONObject();


        try{
            jsonObject.put("model","gpt-3.5-turbo");
            JSONArray messageArr= new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("role","user");
            obj.put("content",qts);
            messageArr.put(obj);
            jsonObject.put("messages",messageArr);
        } catch (JSONException e) {
          throw new RuntimeException(e);

        }
        RequestBody body= RequestBody.create(jsonObject.toString(),JSON);
        Request request= new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("fail to Load Response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
if(response.isSuccessful()){
    try {

        JSONObject jsonObject= new JSONObject(response.body().string());
        JSONArray jsonArray= null;
        jsonArray= jsonObject.getJSONArray("choices");
        String result = jsonArray.getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
        addResponse(result.trim());

    }catch (JSONException e){
  throw new RuntimeException(e);
    }
}else{
    addResponse("Fail to Response  due to "+response.body().string());
}
            }
        });
    }


}