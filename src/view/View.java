package view;

import controller.Controller;
import model.Listener;
import model.Model;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Christopher Tse on 2/11/2017.
 */
public class View extends JFrame implements Listener {
    public static JList<String> list;
    private static DefaultListModel<String> listModel;
    public static JLabel mainLabel;
    public static ImageIcon imgicon;
    private Model model;

    public View(final Controller controller, final Model model) {
        super("Image Viewer");
        this.model = model;
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model.addListener(this);
        init(controller);

    }

    private void init(final Controller controller) {
        // Set layout of JFrame
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Initialize MenuBar
        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Add File menu and items
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem quitItem = new JMenuItem("Quit");
        final JMenuItem openItem = new JMenuItem("Open...");
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);
        openItem.setActionCommand("2");
        quitItem.setActionCommand("3");
        openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quitItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openItem.addActionListener(controller);
        quitItem.addActionListener(controller);

        // Add Navigate menu and items
        final JMenu navMenu = new JMenu("Navigate");
        final JMenuItem nextItem = new JMenuItem("Next Image");
        final JMenuItem prevItem = new JMenuItem("Previous Image");
        menuBar.add(navMenu);
        navMenu.add(nextItem);
        navMenu.add(prevItem);
        nextItem.setActionCommand("1");
        prevItem.setActionCommand("0");
        nextItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        prevItem.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        nextItem.addActionListener(controller);
        prevItem.addActionListener(controller);

        // Initialize JList with "Empty"
        listModel = new DefaultListModel<>();
        listModel.addElement("Empty");
        list = new JList<>(listModel);
        list.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 50), new BevelBorder(BevelBorder.LOWERED)));
        list.addListSelectionListener(controller);
        contentPane.add(list, BorderLayout.WEST);

        // Initialize JButton and container panel
        final JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        final JButton nextButton = new JButton("Next");
        final JButton prevButton = new JButton("Prev");
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        nextButton.setActionCommand("1");
        prevButton.setActionCommand("0");
        nextButton.addActionListener(controller);
        prevButton.addActionListener(controller);

        mainLabel = new JLabel();
        mainLabel.setText("Go to File > Open... to start");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(mainLabel, BorderLayout.CENTER);
        mainLabel.addComponentListener(controller);
        mainLabel.addMouseListener(controller);
        mainLabel.addMouseWheelListener(controller);

    }

    @Override
    public void imageChanged() {
        String res = model.getRes();
        imgicon = new ImageIcon(res);
        mainLabel.setIcon(new ImageIcon(imgicon.getImage().getScaledInstance(mainLabel.getWidth(), mainLabel.getHeight(), Image.SCALE_FAST)));
        if (imgicon.getIconWidth() < mainLabel.getWidth())
            mainLabel.setIcon(new ImageIcon(imgicon.getImage().getScaledInstance(mainLabel.getWidth(), mainLabel.getHeight(), Image.SCALE_FAST)));
    }

    @Override
    public void folderOpened() {
        listModel.clear();
        String[] filenames = model.getFilenames();
        for (String name : filenames) {
            listModel.addElement(name);
        }
        list.setSelectedIndex(0);
    }

    @Override
    public void listClicked() {

    }
}
