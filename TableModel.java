package batboy;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.*;



public class TableModel extends AbstractTableModel {
	  private String[] columnNames = {"mode",
              "x-loc",
              "y-loc",
              "start",
              "finish",
              "x-size",
              "y-size",
            /*  "speed",
              "aspect",
              ---------add this later----------
             */
              "video file name",
};
	  
	  private static final int COLCOUNT=8;
      private Object[][] data =  new Object[1][COLCOUNT];

         private Object[] newRow = null;

		 int    rows = 0;

         public Object[] longValues = null;

         public void out(String s ) {
        	 System.out.println(s);
         }
         
         public TableModel() {
        	 super();
        	 int i = 0;
        	 longValues = new Object[COLCOUNT];
        	 while (i < COLCOUNT) {
        		 data[0][i] = (Object)(new String(" "));   
        		 longValues[i] = (Object)(new String("xxxxxxxx"));
        		 i += 1;
        	 }
        	 longValues[COLCOUNT-1] = (Object)(new String("THIS IS.................................... A LONG VALUE FOR A FILENAME BUT IS IT TOO LONG?..........................................................."));
        	 newRow = new Object[COLCOUNT];
         }
         
         public Class getColumnClass(int c) {
             return getValueAt(0, c).getClass();
         }

         public String getColumnName(int col) {
             return columnNames[col].toString();
         }
         public int getRowCount() { return data.length; }
         public int getColumnCount() { return columnNames.length; }
         public Object getValueAt(int row, int col) {
             return data[row][col];
         }
         public boolean isCellEditable(int row, int col)
             { return false; }
         
         public void setValueAt(Object value, int row, int col) {
        	 
        	 if (row > rows) {
        		 rows += 1;
        		 int rowsplus1 = rows+1;
        		 //out("storing a new row, row number "+row + ", total rows: "+rowsplus1);
        		 
        		 Object oarr[][] = new Object[rowsplus1][COLCOUNT];
        		 int idx=0;
        		 while ( idx < rows) {
        			 int jdx=0;
            		 while (jdx < COLCOUNT) {
        				 //out("Copying row " + idx + " column "+jdx);
        				 oarr[idx][jdx] = data[idx][jdx];
        				 jdx += 1;
        			 }
        			 idx +=1;
        		 }
        		 data = oarr;
        		 int minusvalue = 0; // -1
        		 int rowminus = rows - minusvalue;
        		 //out("doing a fireTableRowsInserted for row "+rowminus);
        		 fireTableRowsInserted(rows-minusvalue, rows-minusvalue);
        	 }
        	 //out ("row count " + getRowCount());
        	 //out ("column count " + getColumnCount());
        	 
        	 //out ("putting value at row " + row + ", column "+col);
             data[row][col] = value;
             fireTableCellUpdated(row, col);
             //this.
         }
      
         public void initColumnSizes(JTable table) {
             TableModel model = (TableModel) table.getModel();
             TableColumn column = null;
             Component   comp   = null;
             int headerWidth = 0;
             int cellWidth   = 0;
             
             Object[] longValues = model.longValues;
             TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
      
             for (int i = 0; i < COLCOUNT; i++) {
                 column = table.getColumnModel().getColumn(i);
      
                 comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
                 headerWidth = comp.getPreferredSize().width;
      
                 comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, longValues[i], false,
                                 false, 0, i);
      
                 cellWidth = comp.getPreferredSize().width;
      
                 //if (DEBUG) {
                     System.out.println("Initializing width of column " + i + ". "
                             + "headerWidth = " + headerWidth + "; cellWidth = "
                             + cellWidth);
                 //}
      
                 // XXX: Before Swing 1.1 Beta 2, use setMinWidth instead.
                 column.setPreferredWidth(Math.max(headerWidth, cellWidth));
             }
         }
         
}
