package сlient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class ClientWindow extends JFrame implements ActionListener{
    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 8180;
    private Socket clientSocket;
    private DataInputStream inMsg;
    private DataOutputStream outMsg;



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField login = new JTextField("Please input your login");
    private final JTextField fieldPass = new JTextField("Please input your password");
    private final JTextField fieldInput = new JTextField("Please input your msg");
    public final JButton sendMsg = new JButton("Send");
    public JButton auth = new JButton("Auth");


    public ClientWindow(){

        start();

        setBounds(500, 300, 600, 500);
        setTitle("Client of chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        log.setEditable(false);
        log.setLineWrap(true);

        JPanel authPanel = new JPanel(new BorderLayout());
        add(authPanel, BorderLayout.NORTH);
        authPanel.add(login, BorderLayout.WEST);
        authPanel.add(fieldPass, BorderLayout.CENTER);
        authPanel.add(auth, BorderLayout.EAST);

        JScrollPane jScrollPane = new JScrollPane(log);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(sendMsg, BorderLayout.EAST);
        bottomPanel.add(fieldInput, BorderLayout.CENTER);

        login.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                login.setText("");
            }
        });

        fieldPass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldPass.setText("");
            }
        });

        fieldInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldInput.setText("");
            }
        });


        auth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAuthClick();

            }
        });

        sendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String msg = fieldInput.getText();
                    outMsg.writeUTF(msg);
                    fieldInput.setText("");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public void start(){

        try{
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMsg = new DataInputStream(clientSocket.getInputStream());
            outMsg = new DataOutputStream(clientSocket.getOutputStream());
            setAuthorized(false);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String strFromServer = inMsg.readUTF();
                            if(strFromServer.startsWith("/authOk"));{
                                setAuthorized(true);
                                loadHistory();
                                break;
                            }

                        }
                        while (true){
                            String strFromServer = inMsg.readUTF();
                            if(strFromServer.equalsIgnoreCase("/end")){
                                break;
                            }
                            log.append(strFromServer);
                            log.append("\n");
                            logHistory();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            t.setDaemon(true);
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAuthorized(boolean b) {
    }

    public void onAuthClick(){
        try{
            outMsg.writeUTF("/auth " + login.getText() + " " + fieldPass.getText());
            login.setText("");
            fieldPass.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void logHistory() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("History.txt", false))) {
            writer.write(log.getText() + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadHistory(){
        int posHistory = 100;
        ArrayList<String> historyList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("History.txt"))) {
            String temp;
            while ((temp = reader.readLine()) != null){
                historyList.add(temp);
            }

            if(historyList.size() > posHistory){
                for (int i = historyList.size() - posHistory; i <= (historyList.size() - 1); i++){
                    log.append(historyList.get(i) + "\n");
                }
            }else{
                for (int i = 0; i < historyList.size(); i++) {
                    log.append(historyList.get(i) + "\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
