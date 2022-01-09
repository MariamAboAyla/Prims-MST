import java.util.*;

public class P1 {

    static class Pair<x extends Integer, y extends Double> extends Vector<Pair<Integer, Double>> {
        Integer x;
        Double y;

        Pair (Integer x,Double y)
        {
            this.x = x;
            this.y = y;
        }

    }

    static class PrimMST
    {
        private final int numOfVertices;
        private Vector<Vector<Pair<Integer, Double>>>adjArray;
        private Boolean [] used;
        private double [] keys;
        private int [] parents;
        private int totalCost;

        PrimMST(int numOfVertices)
        {
            totalCost = 0;
            this.numOfVertices = numOfVertices;
            adjArray = new Vector<> ( numOfVertices+7 ); // as if pair : other node - weight

            for (int i=0; i<numOfVertices+7; i++)
                adjArray.add ( i, new Vector<Pair<Integer,Double>> (  ));

            used = new Boolean[numOfVertices];
            Arrays.fill ( used, false );
            keys = new double[numOfVertices];
            parents = new int[numOfVertices];
            Arrays.fill ( keys , -1); // all are currently roots
        }

        public void addEdge (int start, int end, double weight)
        {
            Vector<Pair<Integer,Double>> vStart = adjArray.elementAt ( start );
            vStart.add ( new Pair<Integer,Double> ( end,weight ) );
            adjArray.setElementAt ( vStart, start );
            Vector<Pair<Integer,Double>> vEnd = adjArray.elementAt ( end );
            vEnd.add ( new Pair<> ( start,weight ) );
            adjArray.setElementAt ( vEnd, end );


        }

        public void solve ()
        {
            Arrays.fill ( keys , Integer.MAX_VALUE);

            int indx = 0;
            PriorityQueue <Pair<Integer,Double>> queue = new PriorityQueue<> ( new Comparator<Pair<Integer, Double>> ( ) {
                @Override
                public int compare ( Pair<Integer, Double> o1 , Pair<Integer, Double> o2 ) {
                    //if(o1.y != o2.y)
                        return (int)(o1.y-o2.y);
                    //else
                       // return o1.x-o2.x;
                }
            } );

            queue.add ( new Pair<Integer,Double> (indx, (double) 0 ) );
            keys[indx] = 0;
            while (!queue.isEmpty ())
            {
                double weight = queue.peek ().y;
                int curr = queue.peek ().x;
                queue.poll ();
                if (used[curr]) continue;
                used[curr] = true;

                Pair <Integer,Double> res = null;
                for (int i=0; i<adjArray.elementAt ( curr ).size (); i++)
                {
                    int vertexIndex = adjArray.elementAt ( curr ).get ( i ).x;
                    double wigt = adjArray.elementAt ( curr ).get ( i ).y;


                    if ( ! used[vertexIndex] && wigt < keys[vertexIndex])
                    {
                        // put parent , push in queue, put weight as key
                        keys[vertexIndex] = wigt;
                        parents[vertexIndex] = curr;
                        res =  new Pair<> ( vertexIndex, wigt) ;
                        queue.add ( res );
                    }
                }


            }
            //getTotalCost();
            print();

        }

//        private void getTotalCost()
//        {
//
//            for (int i=1; i<numOfVertices; i++) totalCost += keys[i];
//        }

        private void print()
        {
            System.out.println ("Prims MST is :" );
            System.out.println ("Parent   Vertex    Weight " );
            // it prints the parent then i
            for (int i=1; i< numOfVertices; i++)
            {
                totalCost += keys[i];
                System.out.println ("  "+parents[i]+"        "+i+"       "+keys[i]);
            }
            System.out.println ("The total cost is: " + totalCost );
        }


    }

    public static void main(String[] args) {

        // to get data from user
        // 0-based
        Scanner scan = new Scanner ( System.in );
        System.out.print ("Enter number of vertices: " );
        int v = scan.nextInt ( );
        PrimMST p = new PrimMST ( v );
        System.out.print ("Enter number of Edges: " );
        int e = scan.nextInt ( );
        System.out.println ("Enter edges in this order:\nstart  end  weight" );
        for (int i=0; i<e; i++)
        {
            int start = scan.nextInt ( );
            int end = scan.nextInt ( );
            double weight = scan.nextDouble ();
            p.addEdge ( start,end,weight );

        }

        p.solve ();  // to call function solve to get (PRIMS MST)


        // doctors assignment example
//        int v =7;
//        PrimMST p = new PrimMST ( v );
//        p.addEdge ( 0,1,2 );
//        p.addEdge (0,3,1);
//        p.addEdge ( 0,2,4);
//        p.addEdge ( 1,4,10);
//        p.addEdge ( 1,2,3);
//        p.addEdge (3,4,7 );
//        p.addEdge ( 4,6,6);
//        p.addEdge ( 3,6,4);
//        p.addEdge (5, 6,1);
//        p.addEdge (3,5,8 );
//        p.addEdge ( 2,5,5 );
//        p.addEdge ( 2,3,2 );
//        p.solve ();


    }
}
