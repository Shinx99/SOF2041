package Controller;

import Repository.EmployeeRepo;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

public class MainController {

    private Main main;
    private EmployeeRepo empRepo;

    public MainController(Main main) {
        this.main = main;
        this.empRepo = new EmployeeRepo();

        init();
        loadLbState();
    }

    private String getCurrentLoggedInEmployeeId() {
        return LoginController.getLoggedInEmployeeId();
    }

    private void loadLbState() {

        String empId = getCurrentLoggedInEmployeeId();

        String fullname = empRepo.getEmployeeFullName(empId);

        main.getLblState().setText("Author: "+fullname);
    }

    private void init() {
        //System
        main.getMniLogin().addActionListener(e -> MniLoginActionListener());
        main.getMniLogOut().addActionListener(e -> MniLogOutActionListener());
        main.getMniChangePass().addActionListener(e -> MniChangePassActionListener());
        main.getMniExit().addActionListener(e -> MniExitActionListener());

        //Managing
        main.getMniLearners().addActionListener(e -> MniLearnersActionListener());
        main.getMniSubjects().addActionListener(e -> MniSubjectsActionListener());
        main.getMniCourses().addActionListener(e -> MniCoursesActionListener());
        main.getMniStudent().addActionListener(e -> MniStudentActionListener());
        main.getMniEmployee().addActionListener(e -> MniEmployeeActionListener());

        //Statistics
        main.getMniLEY().addActionListener(e -> MniLEYActionListener());
        main.getMniCourseTranscript().addActionListener(e -> MniCourseTranscriptActionListener());
        main.getMniSFC().addActionListener(e -> MniSFCActionListener());
        main.getMniRevenue().addActionListener(e -> MniRevenueActionListener());

        //Help
        main.getMniGuide().addActionListener(e -> MniGuideActionListener());
        main.getMniAbout().addActionListener(e -> MniAboutActionListener());

        //Button
        main.getBtnLogOut().addActionListener(e -> BtnLogOutActionListener());
        main.getBtnExit().addActionListener(e -> BtnExitActionListener());
        main.getBtnSubject().addActionListener(e -> BtnSubjectActionListener());
        main.getBtnLearner().addActionListener(e -> BtnLearnerActionListener());
        main.getBtnCourse().addActionListener(e -> BtnCourseActionListener());
        main.getBtnStudent().addActionListener(e -> BtnStudentActionListener());
        main.getBtnGuide().addActionListener(e -> BtnGuideActionListener());

    }


    //System
    private void MniLoginActionListener() {
        Login login = new Login();
        new LoginController(login);
        main.getFrame().setEnabled(false);

        login.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    private void MniLogOutActionListener() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Do you want to leave?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);
        if (choice == JOptionPane.YES_NO_OPTION) {
            JFrame ExitFrame = (JFrame) SwingUtilities.getWindowAncestor(main.getPainelMain());
            ExitFrame.dispose();
        }

    }

    private void MniChangePassActionListener() {
        ChangePassword changePW = new ChangePassword();
        new ChangePwController(changePW);
        main.getFrame().setEnabled(false);

        changePW.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    private void MniExitActionListener() {
        Login login = new Login();
        new LoginController(login);
        main.getFrame().setEnabled(false);

        login.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });

    }

    //Managing
    private void MniLearnersActionListener() {
        Learner learner = new Learner();
        new LearnerController(learner);
        main.getFrame().setEnabled(false);

        learner.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    private void MniSubjectsActionListener() {
        Subject subject = new Subject();
        new SubjectController(subject);
        main.getFrame().setEnabled(false);

        subject.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    private void MniCoursesActionListener() {
        Course course = new Course();
        new CourseController(course);
        main.getFrame().setEnabled(false);

        course.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });

    }

    private void MniStudentActionListener() {
        Student student = new Student();
        new StudentController(student);
        main.getFrame().setEnabled(false);

        student.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    private void MniEmployeeActionListener() {
        Employee employee = new Employee();
        new EmployeeController(employee);
        main.getFrame().setEnabled(false);

        employee.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    //Statistics
    private void MniStatisticAction(int tabIndex) {
        Statistic statistic = new Statistic();
        statistic.getFrame().setVisible(true);
        statistic.getTabbedPane1().setSelectedIndex(tabIndex);

        new StatisticController(statistic);
        main.getFrame().setEnabled(false);

        statistic.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });

    }

    private void MniCourseTranscriptActionListener() {
        MniStatisticAction(0);
    }

    private void MniLEYActionListener() {
        MniStatisticAction(1);

    }

    private void MniSFCActionListener() {
        MniStatisticAction(2);
    }

    private void MniRevenueActionListener() {
        MniStatisticAction(3);
    }

    //Help
    private void MniAboutActionListener() {
        About about = new About();
        main.getFrame().setEnabled(false);

        about.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.getFrame().setEnabled(true);
                main.getFrame().toFront();
            }
        });
    }

    private void MniGuideActionListener() {
        try {
            Desktop.getDesktop().browse(new URI("https://polyschool.fpt.edu.vn/nganh-hoc/ung-dung-phan-mem-2/"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error opening website!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //Button
    private void BtnLogOutActionListener() {
        MniLogOutActionListener();
    }

    private void BtnGuideActionListener() {
        MniGuideActionListener();
    }

    private void BtnStudentActionListener() {
        MniStudentActionListener();
    }

    private void BtnCourseActionListener() {
        MniCoursesActionListener();
    }

    private void BtnLearnerActionListener() {
        MniLearnersActionListener();
    }

    private void BtnSubjectActionListener() {
        MniSubjectsActionListener();
    }

    private void BtnExitActionListener() {
        MniExitActionListener();
    }

}
