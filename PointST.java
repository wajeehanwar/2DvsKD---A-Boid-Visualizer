public class PointST<Value> implements ST<Value> {
    private RedBlackBST<Point2D, Value> bst; // the symbol table represented 
                                             // as a red-black BST.

    // Construct an empty symbol table of points.
    public PointST() {
        // Initialize BST.
        bst = new RedBlackBST<Point2D, Value>();
    }

    // Return true if the symbol table is empty, and false otherwise.
    public boolean isEmpty() { 
       // Return if BST is empty.
       return bst.isEmpty();
    }

    // Return the number points in the symbol table.
    public int size() {
        // Return size of BST.
        return bst.size();
    }

    // Associate the value val with point p.
    public void put(Point2D p, Value val) {
        // Insert point and value into BST.
        bst.put(p, val);
    }

    // Return the value associated with point p.
    public Value get(Point2D p) {
       // Return value of point from BST.
       return bst.get(p);
    }

    // Return true if the symbol table contains the point p, and false 
    // otherwise.
    public boolean contains(Point2D p) {
        // Return if BST contains point.
        return bst.contains(p);
    }

    // Return all points in the symbol table.
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // Return all points in the symbol table that are inside the rectangle 
    // rect.
    public Iterable<Point2D> range(RectHV rect) {
        // Declare iterable return queue.
        Queue<Point2D> q = new Queue<Point2D>();
        // Load all keys from BST into queue.
        for (Point2D x : bst.keys()) {
            if (rect.contains(x)) {
                q.enqueue(x);
            }
        }
        // Return iterable queue.
        return q;
    }

    // Return a nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p) {
        // Return null if BST is empty.
        if (bst.isEmpty()) {
            return null;
        } else {
            // Initialize return closest point tracker.
            Point2D closestPoint = new Point2D(0.0, 0.0);
            // Initialize closest relative distance tracker.
            double closestDistance = Double.POSITIVE_INFINITY;
            // Compute distance for all points relatove to p.
            for (Point2D x : bst.keys()) {
                // Evaluate closest distances, ommit p against itself.
                if (p.distanceTo(x) < closestDistance && !p.equals(x)) {
                    // Update trackers.
                    closestDistance = p.distanceTo(x);
                    closestPoint = x;
                }
            }
            // Return closest point.
            return closestPoint;
        }
    }
    // Return k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // Initialize MinPQ ordered by distance relative to p.
        MinPQ<Point2D> pq = new MinPQ<Point2D>(p.DISTANCE_TO_ORDER);
        // Compute all points distances.
        for (Point2D x : bst.keys()) {
            // Ommit evaluating p against itself.
            if (p.distanceTo(x) > 0) {
                // Insert point into MinPQ
                pq.insert(x);
            }
        }
        // Initialize return queue.
        Queue<Point2D> kPoints = new Queue<Point2D>();
        // Load k smallest distance points into queue.
        for (int i = 0; i < k; i++) {
            kPoints.enqueue(pq.delMin());
        }
        // Return iterable queue with k closest points to p.
        return kPoints;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        PointST<Integer> st = new PointST<Integer>();
        Point2D query = new Point2D(0.661633, 0.287141);
        Point2D origin = new Point2D(0, 0);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.println("First five values:");
        i = 0;
        for (Point2D p : st.points()) {
            StdOut.println("  " + st.get(p));
            if (i++ == 5) {
                break;
            }
        }
        StdOut.println("st.contains(" + query + ")? " + st.contains(query));
        StdOut.println("st.contains(" + origin + ")? " + st.contains(origin)); 
        StdOut.println("st.range([0.65, 0.68]x[0.28, 0.29]):");
        for (Point2D p : st.range(new RectHV(0.65, 0.28, 0.68, 0.29))) {
            StdOut.println("  " + p);
        }
        StdOut.println("st.nearest(" + query + ") = " + st.nearest(query));
        StdOut.println("st.nearest(" + query + "):"); 
        for (Point2D p : st.nearest(query, 7)) {
            StdOut.println("  " + p);
        }
    }
}
