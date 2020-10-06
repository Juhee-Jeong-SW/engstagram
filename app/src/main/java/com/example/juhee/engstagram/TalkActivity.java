package com.example.juhee.engstagram;

import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class TalkActivity extends AppCompatActivity {

//    private static final int SERVER_TEXT_UPDATE = 100;
//    private static final int CLIENT_TEXT_UPDATE = 200;
//
//    private Button serverCreateButton;//서버열기
//    private Button serverTransButton;//서버텍스트전송
//    private Button serverJoinButton;//서버접속
//    private Button clientTransButton;//클라전송
//
//    private static TextView serverIpText;//서버아이피확인
//    private static TextView serverText;//서버채팅창
//    private static TextView clientText;//클라채팅창
//    private EditText joinIpText;//클라접속아이피
//    private EditText transServerText;
//    private EditText transClientText;
//
//    @SuppressLint("HandlerLeak")
//    private static Handler handler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msgg)
//        {
//            super.handleMessage(msgg);
//            switch(msgg.what)
//            {
//                case SERVER_TEXT_UPDATE: {
//                    serverMsg.append(msg);
//                    serverText.setText(serverMsg.toString());
//                }
//                break;
//                case CLIENT_TEXT_UPDATE:{
//                    clientMsgBuilder.append(clientMsg);
//                    clientText.setText(clientMsgBuilder.toString());
//                }
//                break;
//            }
//        }
//    };
    //서버세팅
//    private ServerSocket serverSocket;
//    private Socket socket;
//    private static String msg;
//    private static StringBuilder serverMsg = new StringBuilder();
//    private static StringBuilder clientMsgBuilder = new StringBuilder();
//    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
//
//    //클라세팅
//    private Socket clientSocket;
//    private DataInputStream clientIn;
//    private DataOutputStream clientOut;
//    private static String clientMsg;
//    private static String nickName;

    String streamMsg = "";
    TextView showText;
    Button connectBtn;
    Button Button_send;
    EditText ip_EditText;
    EditText port_EditText;
    EditText editText_message;
    Handler msgHandler;

    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    Socket socket;

    PipedInputStream sendStream = null;
    PipedOutputStream receiveStream = null;

    LinkedList<SocketClient> threadList;






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        ip_EditText = (EditText) findViewById(R.id.ip_EditText);
        port_EditText = (EditText) findViewById(R.id.port_EditText);
        connectBtn = (Button) findViewById(R.id.conect_Button);
        showText = (TextView) findViewById(R.id.showText_TextView);
        editText_message = (EditText) findViewById(R.id.editText_message);
        Button_send = (Button) findViewById(R.id.Button_send);
        threadList = new LinkedList<SocketClient>();

        ip_EditText.setText("192.168.0.7");
        port_EditText.setText("5001");

        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message hdMsg)
            {
                if(hdMsg.what == 1111)
                {
                    showText.append(hdMsg.obj.toString() + "\n");
                }
            }
        };

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = new SocketClient(ip_EditText.getText().toString(), port_EditText.getText().toString());
                threadList.add(client);
                client.start();
            }
        });

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_message.getText().toString() != null)
                {
                    send = new SendThread(socket);
                    send.start();

                    editText_message.setText("");
                }
            }
        });

    }

    class SocketClient extends Thread
    {
        boolean threadAlive;
        String ip;
        String port;
        String mac;

        OutputStream outputStream = null;
        BufferedReader br = null;

        private DataOutputStream output = null;

        public SocketClient(String ip, String port)
        {
            threadAlive = true;
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run()
        {
            try{
                socket = new Socket(ip, Integer.parseInt(port));
                output = new DataOutputStream(socket.getOutputStream());
                receive = new ReceiveThread(socket);
                receive.start();

                WifiManager mng = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE); //  why error in here?
                WifiInfo info = mng.getConnectionInfo();
                mac = info.getMacAddress();

                output.writeUTF(mac);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    class ReceiveThread extends Thread {
        private Socket socket = null;
        DataInputStream input;

        public ReceiveThread(Socket socket) {
            this.socket = socket;
            try {
                input = new DataInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            try{
                while(input != null)
                {
                    String msg = input.readUTF();
                    if(msg!=null){
                        Log.d(ACTIVITY_SERVICE, "test");

                        Message hdMsg = msgHandler.obtainMessage();
                        hdMsg.what = 1111;
                        hdMsg.obj = msg;
                        msgHandler.sendMessage(hdMsg);
                        Log.d(ACTIVITY_SERVICE,hdMsg.obj.toString());
                    }
                }
            }catch (IOException e)
            { e.printStackTrace();}
        }
    }

    class SendThread extends Thread {
        private Socket socket;
        String sendMsg = editText_message.getText().toString();
        DataOutputStream output;

        public SendThread(Socket socket)
        {
            this.socket = socket;
            try{
                output = new DataOutputStream(socket.getOutputStream());
            }catch (Exception e)
            {}
        }

        public void run()
        {
           try{
               Log.d(ACTIVITY_SERVICE,"11111");
               String mac = null;
               WifiManager mng = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
               WifiInfo info = mng.getConnectionInfo();
               mac = info.getMacAddress();

               if(output!= null)
               {
                   if(sendMsg != null)
                   {
                       output.writeUTF(mac + " : " + sendMsg);
                   }
               }
           }
           catch (IOException e)
           {e.printStackTrace();}
           catch (NullPointerException npe)
           {
               npe.printStackTrace();
           }
        }
    }




}


//        serverCreateButton = (Button) findViewById(R.id.server_create_button);
//        serverTransButton = (Button) findViewById(R.id.trans_server_button);
//        serverJoinButton = (Button) findViewById(R.id.server_join_button);
//        clientTransButton = (Button) findViewById(R.id.trans_client_button);
//        serverIpText = (TextView) findViewById(R.id.server_ip_text);
//        serverText = (TextView) findViewById(R.id.server_text);
//        clientText = (TextView) findViewById(R.id.client_text);
//        joinIpText = (EditText) findViewById(R.id.join_ip_text);
//        transServerText = (EditText) findViewById(R.id.trans_server_text);
//        transClientText = (EditText) findViewById(R.id.trans_client_text);
//
//        serverCreateButton.setOnClickListener(this);
//        serverTransButton.setOnClickListener(this);
//        serverJoinButton.setOnClickListener(this);
//        clientTransButton.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.server_create_button: {
//                serverIpText.setText(getLocalIpAddress() + ":7777");
//                serverCreate();
//            }
//            break;
//            case R.id.trans_server_button: {
//                String msg = "서버 : " + transServerText.getText().toString() + "\n";
//                serverMsg.append(msg);
//                serverText.setText(serverMsg.toString());
//                sendMessage(msg);
//                transServerText.setText("");
//            }
//            break;
//            case R.id.server_join_button: {
//                joinServer();
//            }
//            break;
//            case R.id.trans_client_button: {
//                String msg = nickName + ":" + transClientText.getText() + "\n";
////                clientMsgBuilder.append(msg);
////                clientText.setText(clientMsgBuilder.toString());
//                try {
//                    clientOut.writeUTF(msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                transClientText.setText("");
//            }
//            break;
//
//        }
//    }
//
//    public String getLocalIpAddress() {
//        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
//        int ip = wifiInfo.getIpAddress();
//        String ipAddress = String.format("%d.%d.%d.%d"
//                , (ip & 0xff)
//                , (ip >> 8 & 0xff)
//                , (ip >> 16 & 0xff)
//                , (ip >> 24 & 0xff));
//        return ipAddress;
//    }
//
//    public void joinServer() {
//        if(nickName==null){
//            nickName="스마트폰";
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    clientSocket = new Socket(joinIpText.getText().toString(), 7777);
//                    Log.v("", "클라이언트 : 서버 연결됨.");
//
//                    clientOut = new DataOutputStream(clientSocket.getOutputStream());
//                    clientIn = new DataInputStream(clientSocket.getInputStream());
//
//                    //접속하자마자 닉네임 전송하면. 서버가 이걸 닉네임으로 인식을 하고서 맵에 집어넣겠지요?
//                    clientOut.writeUTF(nickName);
//                    Log.v("", "클라이언트 : 메시지 전송완료");
//
//                    while (clientIn != null) {
//                        try {
//                            clientMsg = clientIn.readUTF();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        handler.sendEmptyMessage(CLIENT_TEXT_UPDATE);
//                    }
//                } catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public void serverCreate() {
//        Collections.synchronizedMap(clientsMap);
//        try {
//            serverSocket = new ServerSocket(7777);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        /** XXX 01. 첫번째. 서버가 할일 분담. 계속 접속받는것. */
//                        Log.v("", "서버 대기중...");
//                        try {
//                            socket = serverSocket.accept();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Log.v("", socket.getInetAddress() + "에서 접속했습니다.");
//                        msg = socket.getInetAddress() + "에서 접속했습니다.\n";
//                        handler.sendEmptyMessage(SERVER_TEXT_UPDATE);
//
//                        new Thread(new Runnable() {
//                            private DataInputStream in;
//                            private DataOutputStream out;
//                            private String nick;
//
//                            @Override
//                            public void run() {
//
//                                try {
//                                    out = new DataOutputStream(socket.getOutputStream());
//                                    in = new DataInputStream(socket.getInputStream());
//                                    nick = in.readUTF();
//                                    addClient(nick, out);
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                try {// 계속 듣기만!!
//                                    while (in != null) {
//                                        msg = in.readUTF();
//                                        sendMessage(msg);
//                                        handler.sendEmptyMessage(SERVER_TEXT_UPDATE);
//                                    }
//                                } catch (IOException e) {
//                                    // 사용접속종료시 여기서 에러 발생. 그럼나간거에요.. 여기서 리무브 클라이언트 처리 해줍니다.
//                                    removeClient(nick);
//                                }
//
//
//                            }
//                        }).start();
//                    }
//                }
//            }).start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void addClient(String nick, DataOutputStream out) throws IOException {
//        sendMessage(nick + "님이 접속하셨습니다.");
//        clientsMap.put(nick, out);
//    }
//
//    public void removeClient(String nick) {
//        sendMessage(nick + "님이 나가셨습니다.");
//        clientsMap.remove(nick);
//    }
//
//    public void sendMessage(String msg) {
//        Iterator<String> it = clientsMap.keySet().iterator();
//        String key = "";
//        while (it.hasNext()) {
//            key = it.next();
//            try {
//                clientsMap.get(key).writeUTF(msg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        clientIn = null;
//    }
