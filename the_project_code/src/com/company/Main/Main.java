package com.company.Main;

import com.sun.javafx.geom.Edge;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class Main {


    public static class GraphDraw extends JFrame {
        public GraphDraw() { //Constructor
            nodes = new ArrayList<Node>();
            edges = new ArrayList<edge>();
            width = 50;
            height = 50;
        }

        class Node {
            int x, y;
            String name;

            public Node(String myName, int myX, int myY) {
                x = myX;
                y = myY;
                name = myName;
            }
        }


        class edge {
            int i,j,w;

            public edge(int ii, int jj,int ww) {
                i = ii;
                j = jj;
                w = ww;
            }
        }


        int width = 50 , height = 50 ;

        ArrayList<Node> nodes = new ArrayList<Node>();
        ArrayList<edge> edges = new ArrayList<edge>();




        public void draw(int a[][],int x_start,int x_end,int y_start,int y_end)
        {
            int rows = a.length;
            int cols = a[0].length;


            for (int i = 1 ; i <= rows ; i++)
            {
                ///adds nodes
                int x = (int) ((Math.random()*(x_end-x_start))+x_start);
                int y = (int) ((Math.random()*(y_end-y_start))+y_start);
                nodes.add(new Node(i+"",x,y));
            }


            for (int i = 0 ; i < rows ; i++)
            {
                ///adds edges
                for (int j = i+1 ; j < cols ; j++)
                {
                    if(a[i][j]>0)
                        edges.add(new edge(i,j,a[i][j]));
                }
            }






            this.repaint();
        }

        /*make the program freeze*/
        public void paint(Graphics g) { // draw the nodes and edges
            FontMetrics f = g.getFontMetrics();
            int nodeHeight = Math.max(height, f.getHeight());///max between it and between default


            ///drawing edges
            for (edge e : edges) {
                int nodeWidth = Math.max(width, f.stringWidth(Integer.toString(e.w))+width/2);///max between it and between default

                int xx = (nodes.get(e.i).x+nodes.get(e.j).x)/2 ;
                int yy = (nodes.get(e.i).y+nodes.get(e.j).y)/2 ;

                g.setColor(Color.black);
                g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);

                g.setColor(Color.WHITE);
                g.fillRect(xx, yy, nodeWidth, nodeHeight);

                g.setColor(Color.black);
                g.drawRect(xx, yy, nodeWidth, nodeHeight);


                g.setColor(Color.red);
                g.drawString(Integer.toString(e.w), xx+25-f.stringWidth(Integer.toString(e.w))/2, yy+25+f.getHeight()/2);



            }

            ///drawing nodes
            for (Node n : nodes) {
                int nodeWidth = Math.max(width, f.stringWidth(n.name)+width/2);///max between it and between default

                g.setColor(Color.WHITE);
                g.fillOval(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);

                g.setColor(Color.black);
                g.drawOval(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);

                g.setColor(Color.blue);
                g.drawString(n.name, n.x-f.stringWidth(n.name)/2, n.y+f.getHeight()/2);
            }


        }
        /**/
    }

    /*database*/
    int nodes_Size = 0 ;


    ///###############################################################################################
    public static int V = 0 ;
    static int[] path;
    static ArrayList<ArrayList<Integer>> adj ;
    ///###############################################################################################

    //GraphDraw frame = new GraphDraw();
    JFrame frame = new JFrame();


    Main() {


        /*frame*/
        frame.setTitle("Algorithms_Task");
        frame.setSize(880, 1000);/*set the width , height for frame*/
        frame.setLayout(null);/*the method that you want to use to organize your elements automatic*/
        /*FlowLayout,Gridlayout,BorderLayout*/
        frame.setVisible(true);/*make frame visible or invisible*/

        /*labels*/
        JLabel jLabelSize = new JLabel("enter number of vertices:");
        JLabel jLabelStart = new JLabel("enter start Vertex:");
        JLabel jLabelEnd = new JLabel("enter end Vertex:");
        JLabel jLabelWeigth = new JLabel("enter edge weight:");
        JLabel jLabelSizeStatus = new JLabel("");
        JLabel jLabelTableStatus = new JLabel("");


        jLabelSize.setBounds(10, 50, 200, 20);
        jLabelStart.setBounds(10, 350, 200, 20);
        jLabelEnd.setBounds(10, 380, 200, 20);
        jLabelWeigth.setBounds(10, 410, 200, 20);
        jLabelSizeStatus.setBounds(400, 50, 200, 20);
        jLabelTableStatus.setBounds(10, 500, 400, 20);


        frame.add(jLabelSize);
        frame.add(jLabelStart);
        frame.add(jLabelEnd);
        frame.add(jLabelWeigth);
        frame.add(jLabelSizeStatus);
        frame.add(jLabelTableStatus);

        /*textfields*/
        JTextField textSize = new JTextField();
        textSize.setBounds(160, 50, 60, 25);
        JTextField textStart = new JTextField();
        textStart.setBounds(120, 350, 100, 25);
        JTextField textEnd = new JTextField();
        textEnd.setBounds(120, 380, 100, 25);
        JTextField textWeight = new JTextField();
        textWeight.setBounds(120, 410, 100, 25);


        frame.add(textSize);
        frame.add(textStart);
        frame.add(textEnd);
        frame.add(textWeight);


        /*textarea*/
        JTextArea textTask = new JTextArea();
        textTask.setEditable(false);
        textTask.setBounds(0, 550, 860, 400);


        /*scroll*/
        JScrollPane taScroll = new JScrollPane(textTask); // Adds the scrolls when there are too much text.
        taScroll.setBounds(0, 550, 860, 400);
        taScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        taScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


        /*adding*/
        frame.add(taScroll);





        /*buttons*/
        JButton btnSubmit = new JButton("Submit_size");
        JButton btnAdd = new JButton("Add_Edge");
        JButton btnDelete = new JButton("Delete_Edge");
        JButton btnUpdate = new JButton("Update_Edge");
        JButton btnClear = new JButton("Clear_table");
        JButton btnExecute = new JButton("Execute");

        btnSubmit.setBounds(230, 50, 130, 25);
        btnAdd.setBounds(250, 350, 130, 25);
        btnUpdate.setBounds(250, 380, 130, 25);
        btnDelete.setBounds(250, 410, 130, 25);
        btnClear.setBounds(250, 440, 130, 25);
        btnExecute.setBounds(500, 410, 130, 50);

        frame.add(btnSubmit);
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);
        frame.add(btnClear);
        frame.add(btnExecute);


        /*selectBox*/
        String[] options = {"Adjacency List & Matrices", "Euler & Hamilton", "Sales-Man-Problem" , "Graph Coloring Problem" , "Minimum Spanning Tree"};
        JComboBox jComboBox = new JComboBox(options);
        jComboBox.setBounds(500, 350, 200, 25);


        frame.add(jComboBox);



        /*table*/

        //jtable
        JTable table = new JTable();
        Object[] columns = {"Start","End","Weight"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 100, 880, 200);

        frame.setLayout(null);

        frame.add(pane);

        // create an array of objects to set the row data
        Object[] row = new Object[3];


        /*operations*/

        //sizeStep(#done!!!)


        btnSubmit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isInteger(textSize.getText())) 
                {
                    if(Integer.parseInt(textSize.getText())>0)
                    {
                        nodes_Size = Integer.parseInt(textSize.getText()) ;
                        V = Integer.parseInt(textSize.getText()) ;

                        jLabelSizeStatus.setForeground(Color.blue);
                        jLabelSizeStatus.setText("the size is "+nodes_Size);

                        //clear_table
                        int s = table.getRowCount();
                        for(int i = s-1 ; i >= 0 ; i--){
                            // remove a row from jtable
                            model.removeRow(i);
                        }
                    }
                    else
                    {
                        jLabelSizeStatus.setForeground(Color.red);
                        jLabelSizeStatus.setText("please enter a positive integer");

                    }
                }
                else
                {
                    jLabelSizeStatus.setForeground(Color.red);
                    jLabelSizeStatus.setText("please enter a positive integer");
                }

            }
        });




        //tableStep(#done!!!)




        //for the table

        // button add row
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(nodes_Size>0)
                {
                    if(isInteger(textStart.getText())&&isInteger(textEnd.getText())&&isInteger(textWeight.getText()))
                    {
                        if((Integer.parseInt(textStart.getText())>=1 && Integer.parseInt(textStart.getText())<=nodes_Size)&&(Integer.parseInt(textEnd.getText())>=1 && Integer.parseInt(textEnd.getText())<=nodes_Size))
                        {
                            row[0] = textStart.getText();
                            row[1] = textEnd.getText();
                            row[2] = textWeight.getText();

                            // add row to the model
                            model.addRow(row);
                            jLabelTableStatus.setForeground(Color.blue);
                            jLabelTableStatus.setText("row added!!!");
                        }
                        else
                        {
                            jLabelTableStatus.setForeground(Color.red);
                            jLabelTableStatus.setText("please {for start and end vertex} enter a number in range [1 -> "+nodes_Size+"]");
                        }
                    }
                    else
                    {
                        jLabelTableStatus.setForeground(Color.red);
                        jLabelTableStatus.setText("please enter positive integers");
                    }

                }
                else
                {
                    jLabelTableStatus.setForeground(Color.red);
                    jLabelTableStatus.setText("please enter a valid size first");
                }
            }
        });

        // button remove row
        btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {


                // i = the index of the selected row
                int i = table.getSelectedRow();
                if(i >= 0){
                    // remove a row from jtable
                    model.removeRow(i);
                }
            }
        });

        // button clear table
        btnClear.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int s = table.getRowCount();
                for(int i = s-1 ; i >= 0 ; i--){
                    // remove a row from jtable
                    model.removeRow(i);
                }
            }
        });




        // get selected row data From table to textfields
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = table.getSelectedRow();

                textStart.setText(model.getValueAt(i, 0).toString());
                textEnd.setText(model.getValueAt(i, 1).toString());
                textWeight.setText(model.getValueAt(i, 2).toString());
            }
        });




        // button update row
        btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                if(nodes_Size>0)
                {
                    if(isInteger(textStart.getText())&&isInteger(textEnd.getText())&&isInteger(textWeight.getText()))
                    {
                        if((Integer.parseInt(textStart.getText())>=1 && Integer.parseInt(textStart.getText())<=nodes_Size)&&(Integer.parseInt(textEnd.getText())>=1 && Integer.parseInt(textEnd.getText())<=nodes_Size))
                        {
                            //i = the index of the selected row
                            int i = table.getSelectedRow();

                            if(i >= 0)
                            {
                                model.setValueAt(textStart.getText(), i, 0);
                                model.setValueAt(textEnd.getText(), i, 1);
                                model.setValueAt(textWeight.getText(), i, 2);
                            }


                            jLabelTableStatus.setForeground(Color.blue);
                            jLabelTableStatus.setText("row updated!!!");
                        }
                        else
                        {
                            jLabelTableStatus.setForeground(Color.red);
                            jLabelTableStatus.setText("please {for start and end vertex} enter a number in range [1 -> "+nodes_Size+"]");
                        }
                    }
                    else
                    {
                        jLabelTableStatus.setForeground(Color.red);
                        jLabelTableStatus.setText("please enter positive integers");
                    }

                }
                else
                {
                    jLabelTableStatus.setForeground(Color.red);
                    jLabelTableStatus.setText("please enter a valid size first");
                }

            }
        });






        //executeStep(#done!!!)

        btnExecute.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                if(nodes_Size>0 && table.getRowCount()>0)
                {
                    /*primary task*/

                    int V = nodes_Size ;
                    int E = table.getRowCount();

                    ///define Matrices
                    int [][] a_mat = new int[V][V];
                    int [][] r_mat = new int[V][V];
                    int [][] i_mat = new int[V][E];


                    ///take edges from user[fill the Matrices]
                    for (int i = 0 ; i < E ; i++)
                    {
                        int x , y , z ;

                        x = Integer.parseInt(model.getValueAt(i,0).toString());
                        y = Integer.parseInt(model.getValueAt(i,1).toString());
                        z = Integer.parseInt(model.getValueAt(i,2).toString());

                        ///Adjacent matrix
                        a_mat[x-1][y-1]=z;
                        a_mat[y-1][x-1]=z;

                        ///representation matrix
                        r_mat[x-1][y-1]+=1;
                        r_mat[y-1][x-1]+=1;

                        ///Incidence matrix
                        for (int j = 0 ; j < V ; j++)
                        {
                            if(j == x-1 || j == y-1)
                            {
                                i_mat[j][i]=1;
                            }
                        }

                    }

                    list_element[] list = new list_element[V];

                    for (int i = 0 ; i < V ; i++)
                    {
                        list[i] = new list_element();
                        list[i].head = i+1 ;
                        for (int j = 0 ; j < V ; j++)
                        {
                            if(a_mat[i][j]>0)
                            {
                                list[i].body.add(j+1);
                            }
                        }
                    }





                    GraphDraw frame2 = new GraphDraw();
                    frame2.setSize(1000,1000);
                    frame2.setVisible(true);
                    frame2.draw(a_mat,100,900,100,900);

                    /*secondary task*/
                    convert(get_weight_one(a_mat));
                    String str = "" ;
                    ///task 1 - Adjacency List & Matrices

                    if (jComboBox.getSelectedItem() == "Adjacency List & Matrices")
                    {
                        ///print the Matrices
                        str += printmatrix(a_mat,"Adjacent matrix");
                        str += printmatrix(r_mat,"representation matrix");
                        str += printmatrix(i_mat,"Incidence matrix");


                        str += ("######## adjacency list ########\n");
                        for (int i = 0 ; i < V ; i++)
                        {
                            str += (list[i].head+" -> ");

                            for (int counter = 0; counter < list[i].body.size(); counter++) {
                                str += (list[i].body.get(counter)+" ");
                            }
                            str += ("\n");
                        }


                    }

                    ///task 2 - Euler & Hamilton
                    else if (jComboBox.getSelectedItem() == "Euler & Hamilton")
                    {
                        str += test()+"\n";
                        str += hamCycle(a_mat)+"\n";
                    }

                    ///task 3 - Sales-Man-Problem
                    else if (jComboBox.getSelectedItem() == "Sales-Man-Problem")
                    {
                        str += "the minimum total weight that should be taken to visit all nodes is "+tspp(a_mat)+"\n";
                    }

                    ///task 4 - Graph Coloring Problem
                    else if (jComboBox.getSelectedItem() == "Graph Coloring Problem")
                    {
                        str += greedyColoring()+"\n";
                    }

                    ///task 5 - Minimum Spanning Tree
                    else if (jComboBox.getSelectedItem() == "Minimum Spanning Tree")
                    {
                        str += primMST(a_mat)+"\n";
                    }


                    textTask.setText(str);

                }
                else
                {
                    textTask.setText("please enter the data first!!!!!");
                }


            }
        });

    }

    public static class list_element
    {

        public int head = 0 ;
        public ArrayList<Integer> body = new ArrayList<Integer>();

        list_element()
        {
        }
    }

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    public static String printmatrix(int[][] m,String msg) {
        String str = "" ;
        str += ("//////////////////// "+msg+" ////////////////////\n") ;
        for (int row = 0; row < m.length; row++) {
            for (int column = 0; column < m[row].length; column++) {
                str += (m[row][column]+" ");
            }
            str += ("\n");
        }
        str += ("\n");
        return str ;
    }



    ///##########################################################################################################
    static ArrayList<ArrayList<Integer>> convert(int[][] a)
    {
        // no of vertices
        int l = a[0].length;
        ArrayList<ArrayList<Integer>> adjListArray
                = new ArrayList<ArrayList<Integer>>(l);

        // Create a new list for each
        // vertex such that adjacent
        // nodes can be stored
        for (int i = 0; i < l; i++) {
            adjListArray.add(new ArrayList<Integer>());
        }

        int i, j;
        for (i = 0; i < a[0].length; i++) {
            for (j = 0; j < a.length; j++) {
                if (a[i][j] == 1) {
                    adjListArray.get(i).add(j);
                }
            }
        }

        adj = adjListArray;

        return adjListArray;
    }

   /* static void addEdge(int v, int w)
    {
        adj.get(v).add(w);// Add w to v's list.
        adj.get(w).add(v); //The graph is undirected
    }*/


    static void DFSUtil(int v, boolean visited[])
    {
        // Mark the current node as visited
        visited[v] = true;

        // Recur for all the vertices adjacent to this vertex
        /*Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }*/
        for(int i=0; i<adj.get(v).size(); i++)
        {
            int n = adj.get(v).get(i);
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }


    static boolean isConnected()
    {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        int i;
        for (i = 0; i < V; i++)
            visited[i] = false;

        // Find a vertex with non-zero degree
        for (i = 0; i < V; i++)
            if (adj.get(i).size() != 0)
                break;

        // If there are no edges in the graph, return true
        if (i == V)
            return true;

        // Start DFS traversal from a vertex with non-zero degree
        DFSUtil(i, visited);

        // Check if all non-zero degree vertices are visited
        for (i = 0; i < V; i++)
            if (visited[i] == false && adj.get(i).size() > 0)
                return false;

        return true;
    }


    static int isEulerian()
    {
        // Check if all non-zero degree vertices are connected
        if (isConnected() == false)
            return 0;

        // Count vertices with odd degree
        int odd = 0;
        for (int i = 0; i < V; i++)
            if (adj.get(i).size()%2!=0)
                odd++;

        // If count is more than 2, then graph is not Eulerian
        if (odd > 2)
            return 0;

        // If odd count is 2, then semi-eulerian.
        // If odd count is 0, then eulerian
        // Note that odd count can never be 1 for undirected graph
        return (odd==2)? 1 : 2;
    }


    static String greedyColoring()
    {
        int result[] = new int[V];

        // Initialize all vertices as unassigned
        Arrays.fill(result, -1);

        // Assign the first color to first vertex
        result[0]  = 0;

        // A temporary array to store the available colors. False
        // value of available[cr] would mean that the color cr is
        // assigned to one of its adjacent vertices
        boolean available[] = new boolean[V];

        // Initially, all colors are available
        Arrays.fill(available, true);

        // Assign colors to remaining V-1 vertices
        for (int u = 1; u < V; u++)
        {
            // Process all adjacent vertices and flag their colors
            // as unavailable
            Iterator<Integer> it = adj.get(u).iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            // Find the first available color
            int cr;
            for (cr = 0; cr < V; cr++){
                if (available[cr])
                    break;
            }

            result[u] = cr; // Assign the found color

            // Reset the values back to true for the next iteration
            Arrays.fill(available, true);
        }
        String str = "";
        // print the result
        for (int u = 0; u < V; u++){
            str += "Vertex " + (u+1) + " --->  Color " + result[u] + "\n"; }
        return str;
    }

    static String test()
    {
        String str = "";
        int res = isEulerian();
        if (res == 0)
            str = "graph is not Eulerian";
        else if (res == 1)
            str = "graph has a Euler path";
        else
            str = "graph has a Euler cycle";

        return str;
    }






    // Function to print the adjacency list
    static void printArrayList(ArrayList<ArrayList<Integer>>
                                       adjListArray)
    {
        // Print the adjacency list
        for (int v = 0; v < adjListArray.size(); v++) {
            System.out.print(v);
            for (Integer u : adjListArray.get(v)) {
                System.out.print(" -> " + u);
            }
            System.out.println();
        }
    }




    static int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed MST stored in
    // parent[]
    static String printMST(int parent[], int graph[][])
    {
        String str = "";
        str += "Edge \tWeight \n";
        for (int i = 1; i < V; i++)
            str += (parent[i]+1) + " - " + (i+1) + "\t" + graph[i][parent[i]] + "\n";
        return str;
    }

    // Function to construct and print MST for a graph represented
    // using adjacency matrix representation
    static String primMST(int graph[][])
    {
        // Array to store constructed MST
        int parent[] = new int[V];

        // Key values used to pick minimum weight edge in cut
        int key[] = new int[V];

        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        key[0] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
        String str = "";
        // print the constructed MST
        str = printMST(parent, graph);
        return str;
    }


    static String tspp(int [][]graph)
    {
        boolean[] v = new boolean[V];


        v[0] = true;
        int ans = Integer.MAX_VALUE;
        ans = tsp(graph,v,0,V,1,0,ans);
        String str = Integer.toString(ans);
        return str;
    }

    static int tsp(int[][] graph, boolean[] v,
                   int currPos, int n,
                   int count, int cost, int ans)
    {

        // If last node is reached and it has a link
        // to the starting node i.e the source then
        // keep the minimum value out of the total cost
        // of traversal and "ans"
        // Finally return to check for more possible values
        if (count == n && graph[currPos][0] > 0)
        {
            ans = Math.min(ans, cost + graph[currPos][0]);
            return ans;
        }

        // BACKTRACKING STEP
        // Loop to traverse the adjacency list
        // of currPos node and increasing the count
        // by 1 and cost by graph[currPos,i] value
        for (int i = 0; i < n; i++)
        {
            if (v[i] == false && graph[currPos][i] > 0)
            {

                // Mark as visited
                v[i] = true;
                ans = tsp(graph, v, i, n, count + 1,
                        cost + graph[currPos][i], ans);

                // Mark ith node as unvisited
                v[i] = false;
            }
        }
        return ans;
    }




    /* A utility function to check if the vertex v can be
       added at index 'pos'in the Hamiltonian Cycle
       constructed so far (stored in 'path[]') */
    static boolean isSafe(int v, int graph[][], int path[], int pos)
    {
        /* Check if this vertex is an adjacent vertex of
           the previously added vertex. */
        if (graph[path[pos - 1]][v] == 0)
            return false;

        /* Check if the vertex has already been included.
           This step can be optimized by creating an array
           of size V */
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }

    /* A recursive utility function to solve hamiltonian
       cycle problem */
    static boolean hamCycleUtil(int graph[][], int path[], int pos)
    {
        /* base case: If all vertices are included in
           Hamiltonian Cycle */
        if (pos == V)
        {
            // And if there is an edge from the last included
            // vertex to the first vertex
            if (graph[path[pos - 1]][path[0]] > 0)
                return true;
            else
                return false;
        }

        // Try different vertices as a next candidate in
        // Hamiltonian Cycle. We don't try for 0 as we
        // included 0 as starting point in hamCycle()
        for (int v = 1; v < V; v++)
        {
            /* Check if this vertex can be added to Hamiltonian
               Cycle */
            if (isSafe(v, graph, path, pos))
            {
                path[pos] = v;

                /* recur to construct rest of the path */
                if (hamCycleUtil(graph, path, pos + 1) == true)
                    return true;

                /* If adding vertex v doesn't lead to a solution,
                   then remove it */
                path[pos] = -1;
            }
        }

        /* If no vertex can be added to Hamiltonian Cycle
           constructed so far, then return false */
        return false;
    }

    /* This function solves the Hamiltonian Cycle problem using
       Backtracking. It mainly uses hamCycleUtil() to solve the
       problem. It returns false if there is no Hamiltonian Cycle
       possible, otherwise return true and prints the path.
       Please note that there may be more than one solutions,
       this function prints one of the feasible solutions. */
    static String hamCycle(int graph[][])
    {
        String str = "";
        path = new int[V];
        for (int i = 0; i < V; i++)
            path[i] = -1;

        /* Let us put vertex 0 as the first vertex in the path.
           If there is a Hamiltonian Cycle, then the path can be
           started from any point of the cycle as the graph is
           undirected */
        path[0] = 0;
        if (hamCycleUtil(graph, path, 1) == false)
        {
            str = "\nSolution does not exist";
            return str;
        }

        str = printSolution(path);
        return str;
    }

    /* A utility function to print solution */
    static String printSolution(int path[])
    {
        String str = "" ;
        str += "Solution Exists: Following" +
                " is one Hamiltonian Cycle  ";
        for (int i = 0; i < V; i++)
            str += " " + (path[i]+1) + " ";

        // Let us print the first vertex again to show the
        // complete cycle
        str += " " + (path[0]+1) + " ";
        return str;
    }



    int a[][];//input
    int b[][];//output
    public int[][]  get_weight_one(int a[][])
    {
        int[][]b = new int [a.length][a[0].length];

        for (int i = 0 ; i < a.length ; i++)
        {
            for (int j = 0 ; j < a[0].length ; j++)
            {
              if(a[i][j]!=0)
              {
                  b[i][j]=1;
              }
            }
        }

        return b ;
    }






    ///##########################################################################################################






    public static void main (String[]args) throws IOException {
        new Main();
    }
}



/*
* 1 -> 2 , 4
  2 -> 3 , 4 , 5
  3 -> 5
  4 -> 5
*
*/