package iw_wip;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Main {
  public static void main(String[] argv) throws Exception {
    JFrame frame = new JFrame();
final JTable table = new JTable(new DefaultTableModel(new String[] {
        "foo", "bar" }, 2));
frame.getContentPane().setLayout(
        new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
frame.getContentPane().add(table.getTableHeader());
frame.getContentPane().add(table);
frame.pack();
frame.setVisible(true);

// listener
table.getTableHeader().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int col = table.columnAtPoint(e.getPoint());
        String name = table.getColumnName(col);
        System.out.println("Column index selected " + col + " " + name);
    }
});
  }
}
class ColumnHeaderListener extends MouseAdapter {
  public void mouseClicked(MouseEvent evt) {
    JTable table = ((JTableHeader) evt.getSource()).getTable();
    TableColumnModel colModel = table.getColumnModel();

    int index = colModel.getColumnIndexAtX(evt.getX());
    if (index == -1) {
      return;
    }
    Rectangle headerRect = table.getTableHeader().getHeaderRect(index);
    if (index == 0) {
      headerRect.width -= 10;
    } else {
      headerRect.grow(-10, 0);
    }
    if (!headerRect.contains(evt.getX(), evt.getY())) {
      int vLeftColIndex = index;
      if (evt.getX() < headerRect.x) {
        vLeftColIndex--;
      }
    }
  }
}