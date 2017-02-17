package controller;

import model.Model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Created by Christopher Tse on 2/11/2017.
 */
public class Controller extends ComponentAdapter implements ActionListener, ListSelectionListener, MouseListener, MouseWheelListener {
    private final Model model;

    public Controller (Model model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            int action = Integer.parseInt(e.getActionCommand());
            switch(action) {
                case 0: model.imgDown(); break;
                case 1: model.imgUp(); break;
            }
            model.notifyImageListener();
        }
        else if (e.getSource() instanceof JMenuItem) {
            int action = Integer.parseInt(e.getActionCommand());
            switch(action) {
                case 0: model.imgDown(); model.notifyImageListener(); break;
                case 1: model.imgUp(); model.notifyImageListener(); break;
                case 2: model.openFolder(); break;
                case 3: System.exit(0);
            }

        }
    }
    @Override
    public void componentResized(ComponentEvent e) {model.notifyImageListener();}

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!((JList)(e.getSource())).getSelectedValue().equals("Empty")) {
            int newIndex = ((JList) e.getSource()).getSelectedIndex();
            model.updateIndex(newIndex);
            System.out.println(model.getCurrentImage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        model.imgUp();
        model.notifyImageListener();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() < 0) model.imgDown();
        if(e.getWheelRotation() > 0) model.imgUp();
        model.notifyImageListener();
    }
}
