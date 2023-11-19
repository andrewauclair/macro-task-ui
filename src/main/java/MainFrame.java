import ModernDocking.*;
import ModernDocking.exception.DockingLayoutException;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import docking.ui.DockingUI;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private final File layoutFile = new File("layout.xml");
    public MainFrame() {
        RootDockingPanel root = new RootDockingPanel();
        add(root);
setSize(400, 400);
Docking.initialize(this);
        DockingUI.initialize();
        Docking.registerDockingPanel(root, this);
        TasksLists tasks = new TasksLists();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        WindowLayoutBuilder tasks1 = new WindowLayoutBuilder("tasks");

        AppState.setDefaultApplicationLayout(tasks1.buildApplicationLayout());


        JMenuBar menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        JMenu task = new JMenu("Task");
        JMenuItem add = new JMenuItem("Add");
        add.addActionListener(e -> {

        });
        task.add(add);

        menuBar.add(task);

// now that the main frame is set up with the defaults, we can restore the layout
        AppState.setPersistFile(layoutFile);

        try {
            AppState.restore();
        } catch (DockingLayoutException e) {
            // something happened trying to load the layout file, record it here
            e.printStackTrace();
        }

        AppState.setAutoPersist(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configureLookAndFeel();
            new MainFrame().setVisible(true);
        });
    }

    private static void configureLookAndFeel() {
        try {
            FlatLaf.registerCustomDefaultsSource( "docking" );

//            System.setProperty("flatlaf.uiScale", String.valueOf(1));

            String lookAndFeel = "dark";
            switch (lookAndFeel) {
                case "light":
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case "dark":
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case "github-dark":
//                    UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
                    break;
                case "solarized-dark":
//                    UIManager.setLookAndFeel(new FlatSolarizedDarkIJTheme());
                    break;
                default:
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                             UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
            }
            FlatLaf.updateUI();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                   UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
        }
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
        UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

    }
}
