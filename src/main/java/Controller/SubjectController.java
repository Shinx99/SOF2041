package Controller;

import Repository.SubjectRepo;
import Model.SubjectMD;
import View.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SubjectController {
    private Subject sub;
    private SubjectRepo subRepo;
    private int current = 0;
    private String imgPath = "D/...";
    private List<SubjectMD> listSub = new ArrayList<>();

    public SubjectController(Subject sub) {
        this.sub = sub;
        this.subRepo = new SubjectRepo();

        init();
        FillTable();
    }

    private void LoadTable() {

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
                "Subject ID",
                "Subject name",
                "Fees",
                "Duration",
                "Picture"
        });

        for (SubjectMD sj : listSub) {
            model.addRow(new Object[]{
                    sj.getSubId(),
                    sj.getSubName(),
                    sj.getFees(),
                    sj.getDuration(),
                    sj.getPicture(),
            });
        }

        sub.getTbSubject().setModel(model);

    }

    private void FillTable() {
        listSub = subRepo.ReadSub();

        if (listSub == null || listSub.isEmpty()) {
            System.out.println("No data found");
            return;
        }
        LoadTable();
        System.out.println("Fill successfully");

    }

    private void Display(int row) {

        if (listSub.isEmpty() || row < 0 || row >= listSub.size()) {
            System.out.println("Invalid row selected.");
            return;
        }

        SubjectMD sb = listSub.get(row);

        sub.getTxtSubID().setText(sb.getSubId());
        sub.getTxtSubName().setText(sb.getSubName());
        sub.getTxtSubDuration().setText(String.valueOf(sb.getDuration()));
        sub.getTxtSubFees().setText(String.valueOf(sb.getFees()));
        sub.getTxtDescription().setText(sb.getDepict());

        //label picture
        String imagePath = sb.getPicture();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                ImageIcon image = new ImageIcon(imagePath);
                Image img = image.getImage().getScaledInstance(
                        sub.getLbPic().getWidth(),
                        sub.getLbPic().getHeight(),
                        Image.SCALE_SMOOTH
                );
                sub.getLbPic().setIcon(new ImageIcon(img));
                sub.getLbPic().setText("");

            } else {
                sub.getLbPic().setIcon(null);
                sub.getLbPic().setText("   Image not found");
            }
        }else{
            sub.getLbPic().setIcon(null);
            sub.getLbPic().setText("   No image");
        }

    }

    private void tableRowDoubleClicked() {
        int selectedRow = sub.getTbSubject().getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String subId = sub.getTbSubject().getValueAt(selectedRow, 0).toString();
        String subName = sub.getTbSubject().getValueAt(selectedRow, 1).toString();
        String feesTxt = sub.getTbSubject().getValueAt(selectedRow, 2).toString();
        String durationTxt = sub.getTbSubject().getValueAt(selectedRow, 3).toString();

        Object pictureObj = sub.getTbSubject().getValueAt(selectedRow, 4);
        String picturePath = (pictureObj != null) ? pictureObj.toString().trim() : "";

        String description = "";
        for(SubjectMD sb: listSub){
            if(sb.getSubId().equals(subId)){
                description = sb.getDepict();
                break;
            }
        }

        sub.getTxtSubID().setText(subId);
        sub.getTxtSubName().setText(subName);
        sub.getTxtSubDuration().setText(durationTxt);
        sub.getTxtSubFees().setText(feesTxt);
        sub.getTxtDescription().setText(description);

        // Kiểm tra đường dẫn ảnh
        if (!picturePath.isEmpty()) {
            File file = new File(picturePath);
            if (file.exists()) {
                ImageIcon image = new ImageIcon(picturePath);
                Image img = image.getImage().getScaledInstance(
                        sub.getLbPic().getWidth(),
                        sub.getLbPic().getHeight(),
                        Image.SCALE_SMOOTH
                );
                sub.getLbPic().setIcon(new ImageIcon(img));
                sub.getLbPic().setText(""); // Xóa văn bản nếu có ảnh
            } else {
                sub.getLbPic().setIcon(null);
                sub.getLbPic().setText("   Image not found");
            }
        } else {
            sub.getLbPic().setIcon(null);
            sub.getLbPic().setText("   No Image");
        }

        current = selectedRow;
        sub.getTabbedPane1().setSelectedIndex(0);
    }


    private void init() {
        sub.getBtnAdd().addActionListener(e -> AddAction());
        sub.getBtnDelete().addActionListener(e -> DeleteAction());
        sub.getBtnNew().addActionListener(e -> NewAction());
        sub.getBtnUpdate().addActionListener(e -> UpdateAction());

        sub.getBtnFirst().addActionListener(e -> FistAction());
        sub.getBtnLast().addActionListener(e -> LastAction());
        sub.getBtnPrevious().addActionListener(e -> PreviousAction());
        sub.getBtnNext().addActionListener(e -> NextAction());

        sub.getLbPic().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose file");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));

                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imgPath = selectedFile.getAbsolutePath(); // Lưu đường dẫn ảnh

                    try {
                        ImageIcon image = new ImageIcon(imgPath);
                        Image img = image.getImage().getScaledInstance(
                                sub.getLbPic().getWidth(),
                                sub.getLbPic().getHeight(),
                                Image.SCALE_SMOOTH
                        );

                        sub.getLbPic().setIcon(new ImageIcon(img));
                        sub.getLbPic().setText("");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Error loading image!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        sub.getTbSubject().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    tableRowDoubleClicked();
                }
            }
        });

    }

    //Left Button
    private void NewAction() {
        sub.getLbPic().setText("   Choose image:");
        sub.getTxtSubID().setText("");
        sub.getTxtSubName().setText("");
        sub.getTxtSubDuration().setText("");
        sub.getTxtSubFees().setText("");
        sub.getTxtDescription().setText("");

    }

    private void AddAction() {

        String subId = sub.getTxtSubID().getText().trim();
        String subName = sub.getTxtSubName().getText().trim();
        String feesTxt = sub.getTxtSubFees().getText().trim();
        String durationTxt = sub.getTxtSubDuration().getText().trim();
        String description = sub.getTxtDescription().getText().trim();

        if (subId.isEmpty() || subName.isEmpty() || feesTxt.isEmpty() || durationTxt.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please fill in all required fields!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (subRepo.isSubjectExist(subId)) {
            JOptionPane.showMessageDialog(null,
                    "Subject already exists!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double fees;
        int duration;

        try {
            fees = Double.parseDouble(feesTxt);
            duration = Integer.parseInt(durationTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Invalid fees or duration format!");
            return;
        }

        String picturePath = imgPath.isEmpty() ? "":imgPath;

        SubjectMD newSub = new SubjectMD(
                subId,
                subName,
                fees,
                duration,
                picturePath,
                description
        );

        if (subRepo.Add(newSub)) {
            JOptionPane.showMessageDialog(null,
                    "Added successfully!");
            FillTable();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Failed to add!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void UpdateAction() {
    }


    private void DeleteAction() {
    }


    //Right Button
    private void PreviousAction() {
        if (!listSub.isEmpty() && current > 0) {
            current--;
            Display(current);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Already at the record.");
        }
    }

    private void NextAction() {
        if (!listSub.isEmpty() && current < listSub.size() - 1) {
            current++;
            Display(current);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Already at the last record.");
        }
    }

    private void LastAction() {
        if (!listSub.isEmpty()) {
            current = listSub.size() - 1;
            Display(current);
        }
    }

    private void FistAction() {
        if (!listSub.isEmpty()) {
            current = 0;
            Display(current);
        }

    }


}
