public interface ST<Value> {

    // Return true if the symbol table is empty, and false otherwise.
    public boolean isEmpty();
    
    // Return the number points in the symbol table.
    public int size();
    
    // Associate the value val with point p.
    public void put(Point2D p, Value value);

    // Return the value associated with point p.
    public Value get(Point2D p);

    // Return true if the symbol table contains the point p, and false 
    // otherwise.
    public boolean contains(Point2D p);

    // Return all points in the symbol table.
    public Iterable<Point2D> points();
    
    // Return all points in the symbol table that are inside the rectangle 
    // rect.
    public Iterable<Point2D> range(RectHV rect);

    // Return a nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p);

    // Return k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D p, int k);
}
