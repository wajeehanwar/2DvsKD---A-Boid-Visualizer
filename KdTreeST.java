//import java.io.*;
public class KdTreeST<Value> implements ST<Value> {
    private Node root; // root of the KdTree
    private int N;     // number of nodes in the KdTree

    // 2d-tree (generalization of a BST in 2d) representation.
    private class Node {
        private Point2D p;   // the point
        private Value val;   // the symbol table maps the point to this value
        private RectHV rect; // the axis-aligned rectangle corresponding to 
                             // this node
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Construct a node given the point, the associated value, and the 
        // axis-aligned rectangle corresponding to the node.
        public Node(Point2D p, Value val, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
        }
    }

    // Construct an empty symbol table of points.
    public KdTreeST() {
        // Initialize table variables.
        root = null;
        N = 0;
    }

    // Return true if the symbol table is empty, and false otherwise.
    public boolean isEmpty() { 
        // Verify if root is null.
        return (root == null);
    }

    // Return the number points in the symbol table.
    public int size() {
        // Return number of nodes.
        return N;
    }

    // Associate the value val with point p.
    public void put(Point2D p, Value val) {
        // Initialize helper variables.
        boolean lr = true;
        double min = Double.NEGATIVE_INFINITY;
        double max = Double.POSITIVE_INFINITY;
        RectHV rect = new RectHV(min, min, max, max);
        // Delegate to Helper method.
        root = put(root, p, val, rect, lr);
    }

    // Helper for put(Point2D p, Value val).
    private Node put(Node x, Point2D p, Value val, RectHV rect, boolean lr) {
        // Traverse in order under null node is found.
        if (x == null) {
            // Insert new node and update node count.
            N++;
            return new Node(p, val, rect);
        }
        // Evaluate comparision for x or y coordinate.
        if (lr) {
            // Compute for x coordinate.
            if (p.x() < x.p.x()) {
                // Set new rectangle with appropriate bounds.
                RectHV rectangle = new RectHV(rect.xmin(), rect.ymin(), 
                                                x.p.x(), rect.ymax());
                // Call put evaluation for left node.
                x.lb  = put(x.lb,  p, val, rectangle, !lr);
            } else {
                // Set new rectangle with appropriate bounds.
                RectHV rectangle = new RectHV(x.p.x(), rect.ymin(), 
                                                rect.xmax(), rect.ymax());
                // Call put evaluation for right node.
                x.rt = put(x.rt, p, val, rectangle, !lr);
            }
        } else {
            // Compute for y coordinate.
            if (p.y() < x.p.y()) {
                // Set new rectangle with appropriate bounds.
                RectHV rectangle = new RectHV(rect.xmin(), rect.ymin(), 
                                                rect.xmax(), x.p.y());
                // Call put evaluation for right node.
                x.lb  = put(x.lb,  p, val, rectangle, !lr);
            } else {
                // Set new rectangle with appropriate bounds.
                RectHV rectangle = new RectHV(rect.xmin(), x.p.y(), 
                                                rect.xmax(), rect.ymax());
                // Call put evaluation for left node.
                x.rt = put(x.rt, p, val, rectangle, !lr);
            }
        }
        // Return current node to caller.
        return x;
    }

    // Return the value associated with point p.
    public Value get(Point2D p) {
        // Initialize level tracker.
        boolean lr = true;
        // Delegate to helper method.
        return get(root, p, lr);
    }

    // Helper for get(Point2D p).
    private Value get(Node x, Point2D p, boolean lr) {
        // Defensive copy.
        Node current = x;
        boolean isX = lr;
        // Compute while evaluated node is not null.
        while (current != null) {
            // Return value if point is found.
            if (p.equals(current.p)) {
                return current.val;
            }
            // Evaluate based on x/y evaluation.
            if (isX) {
                // Set next node to be evaluated in traversal.
                if (p.x() < current.p.x()) {
                    current = current.lb;
                } else {
                    current = current.rt;
                }
            } else {
                // Set next node to be evaluated in traversal.
                if (p.y() < current.p.y()) {
                    current = current.lb;
                } else {
                    current = current.rt;
                }
            }
            // Update comparision value for next iteration.
            isX = !isX;
        }
        // Return null if no match is found.
        return null;
    }

    // Return true if the symbol table contains the point p, and false 
    // otherwise.
    public boolean contains(Point2D p) {
       // Delegate to get method. Return false if no match is found.
       return (get(p) != null);
    }

    // Return all points in the symbol table, in level order.
    public Iterable<Point2D> points() {
        // Initialize current node tracking queue.
        Queue<Node> q = new Queue<Node>();
        // Initialize return iterable queue.
        Queue<Point2D> f = new Queue<Point2D>();
        // Account for empty table.
        if (root == null) {
            return f;
        } else {
            // Load root into node tracker.
            q.enqueue(root);
        }
        // Evaluate while node tracker queue is not empty.
        while (!q.isEmpty()) {
            // Remove node from queue to be evaluated.
            Node current = q.dequeue();
            // Load children into node tracking queue if they are not null.
            if (current.lb != null) {
                q.enqueue(current.lb);
            }
            if (current.rt != null) {
                q.enqueue(current.rt);
            }
            // Load evaluated node point into return queue.
            f.enqueue(current.p);
        }
        // Return queue with points in level order.
        return f;
    }

    // Return all points in the symbol table that are inside the rectangle 
    // rect.
    public Iterable<Point2D> range(RectHV rect) {
        // Initialize return queue.
        Queue<Point2D> q = new Queue<Point2D>();
        // Delegate to helper method.
        range(root, rect, q);
        // Return queue with all points inside rectangle.
        return q;
    }

    // Helper for public range(RectHV rect).
    private void range(Node x, RectHV rect, Queue<Point2D> q) {
        // Return if node is null.
        if (x == null) {
            return;
        }
        // Evaluate if subtree is worth evaluation.
        if (x.rect.intersects(rect)) {
            // Enqueue point contained in rectangle.
            if (rect.contains(x.p)) {
                q.enqueue(x.p);
            }
            // Evaluate both subtrees.
            range(x.lb, rect, q);
            range(x.rt, rect, q);
        } else {
            // Return if subtree is not worth evaluation.
            return;
        }
    }

    // Return a nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p) {
        // Evaluate if symbol tree is empty.
        if (root != null) {
            // Initialize helper variables.
            Point2D nearest = root.p;
            double nearestDistance = p.distanceTo(nearest);
            boolean lr = true;
            // Delegate to helper method.
            return nearest(root, p, nearest, nearestDistance, lr);
        } else {
            // Return null for empty symbol table.
            return null;
        }
    }
    
    // Helper for public nearest(Point2D p).
    private Point2D nearest(Node x, Point2D p, Point2D nearest, 
                            double nearestDistance, boolean lr) {
        // Defensive copy.
        Point2D closest = nearest;
        double distance = nearestDistance;
        // If node is null return to caller.
        if (x == null) {
            return closest;
        }
        // Update trackers if closer, account for query point returning itself.
        if (p.distanceTo(x.p) < distance && !p.equals(x.p)) {
            distance = p.distanceTo(x.p);
            closest = x.p;
        }
        
        // Evaluate if subtree is worth evaluation.
        if (x.rect.distanceTo(p) < distance) {
            // Compute based on x/y coordinates.
            if (lr) {
                // Evaluate children based on level and relative point location.
                if (p.x() < x.p.x()) {
                    closest = nearest(x.lb, p, closest, 
                                      p.distanceTo(closest), !lr);
                    closest = nearest(x.rt, p, closest, 
                                      p.distanceTo(closest), !lr);
                } else {
                    closest = nearest(x.rt, p, closest, 
                                      p.distanceTo(closest), !lr);
                    closest = nearest(x.lb, p, closest, 
                                      p.distanceTo(closest), !lr);
                }
            } else {
                if (p.y() < x.p.y()) {
                    closest = nearest(x.lb, p, closest, 
                                      p.distanceTo(closest), !lr);
                    closest = nearest(x.rt, p, closest, 
                                      p.distanceTo(closest), !lr);
                } else {
                    closest = nearest(x.rt, p, closest, 
                                      p.distanceTo(closest), !lr);
                    closest = nearest(x.lb, p, closest, 
                                      p.distanceTo(closest), !lr);
                }
            }
        }
        // Return closest point.
        return closest;
    }

    // Return k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // Initialize return priority queue sorted distance to order.
        MaxPQ<Point2D> pq = new MaxPQ<Point2D>(k, p.DISTANCE_TO_ORDER);
        // Initialize helper method variables.
        boolean lr = true;
        // Delegate to helper method.
        nearest(root, p, k, pq, lr);
        // Return k closest points.
        return pq;
    }

    // Helper for public nearest(Point2D p, int k).
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, 
                         boolean lr) {
        // Return to caller if node is null.
        if (x == null) {
            return;
        }
        // Account for query point returning itself.
        if (!p.equals(x.p)) {
            // Enqueue current point.
            pq.insert(x.p);
            // Remove furthest point from queue if k points are exceeded.
            if (pq.size() > k) {
                pq.delMax();
            }
        }
        // Evaluate if subtree is worth evaluation.
        if (pq.size() < k || x.rect.distanceTo(p) < p.distanceTo(pq.max())) {
            // Evaluate children based on level and relative point location.
            if (lr) {
                if (p.x() < x.p.x()) {
                    nearest(x.lb, p, k, pq, !lr);
                    nearest(x.rt, p, k, pq, !lr);
                } else {
                    nearest(x.rt, p, k, pq, !lr);
                    nearest(x.lb, p, k, pq, !lr);
                }
            } else {
                if (p.y() < x.p.y()) {
                    nearest(x.lb, p, k, pq, !lr);
                    nearest(x.rt, p, k, pq, !lr);
                } else {
                    nearest(x.rt, p, k, pq, !lr);
                    nearest(x.lb, p, k, pq, !lr);
                }
            }
        }
        // Return to caller.
        return;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreeST<Integer> st = new KdTreeST<Integer>();
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