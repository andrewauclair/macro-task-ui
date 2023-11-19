import ModernDocking.Dockable;
import ModernDocking.Docking;

import javax.swing.*;
import java.awt.*;

public class TasksLists extends JPanel implements Dockable {
    public TasksLists() {
        super(new GridBagLayout());

        Docking.registerDockable(this);

        // JTree on left for groups/lists, JTable on right for tasks

        // add ability to popout specific lists/groups as a new instance of this panel in the docking framework

        JTree tree = new JTree();

        JTable table = new JTable();

        JSplitPane split = new JSplitPane();
        split.setLeftComponent(new JScrollPane(tree));
        split.setRightComponent(new JScrollPane(table));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(split, gbc);
    }

    @Override
    public String getPersistentID() {
        return "tasks";
    }

    @Override
    public String getTabText() {
        return "Tasks";
    }

    @Override
    public boolean isWrappableInScrollpane() {
        return false;
    }
}
