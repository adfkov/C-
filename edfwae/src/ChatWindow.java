import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChatWindow extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton, copyButton;

    public ChatWindow(String id) {
        setTitle("Chat Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(id);
            }
        });

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(id);
            }
        });

        copyButton = new JButton("Copy");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyFile();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        buttonPanel.add(copyButton);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendMessage(String id) {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            chatArea.append(id + ": " + message + "\n");
            inputField.setText("");
        }
    }

    private void copyFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            JFileChooser destinationChooser = new JFileChooser();
            destinationChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int destinationResult = destinationChooser.showSaveDialog(this);

            if (destinationResult == JFileChooser.APPROVE_OPTION) {
                File destinationDir = destinationChooser.getSelectedFile();
                File dest = new File(destinationDir, selectedFile.getName());

                try {
                    FileInputStream fi = new FileInputStream(selectedFile);
                    FileOutputStream fo = new FileOutputStream(dest);
                    int c;
                    while ((c = fi.read()) != -1) {
                        fo.write((byte) c);
                    }
                    fi.close();
                    fo.close();
                    JOptionPane.showMessageDialog(this, selectedFile.getPath() + "를 " + dest.getPath() + "로 복사하였습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "파일 복사 오류", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new ChatWindow("User1");
    }
}
