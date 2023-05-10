package debug.model;

public class SortedIntBinTreeNode extends IntBinTreeNode {
    public SortedIntBinTreeNode(int content, SortedIntBinTreeNode left, SortedIntBinTreeNode right) {
        super(content, left, right);
        if ((left != null && left.content_ >= content) || (right != null && right.content_ <= content)) {
            System.err.println("Trying to create invalid sorted tree.");
            System.exit(2);
        }
    }

    /**
     * Inserts integer into the sorted tree.
     * Smaller Integers will be placed into the left subtree, larger ones into
     * the right subtree. Equal ones will be ignored
     *
     * @param i Integer to insert.
     */
    @Override
    public void insert(int i) {
        if (i < super.content_) { //3) small numbers will be placed into left subtree
            if (super.left_ == null)
                super.left_ = new SortedIntBinTreeNode(i, null, null);
            else
                super.left_.insert(i);
        } else if (i > super.content_) { //4) larger numbers will be placed into right subtree
            if (super.right_ == null)
                super.right_ = new SortedIntBinTreeNode(i, null, null);
            else
                super.right_.insert(i);
        }
    }

    //5) contains was not implemented.
    /**
     * Looks up, whether integer is contained within tree.
     * Searching methodology is dictated by derived classes.
     *
     * @param i Integer to search for.
     * @return True if integer is in tree.
     */
    @Override
    public boolean contains(int i) {
        return super.content_ == i || (super.left_ != null && super.left_.contains(i) || (super.right_ != null && super.right_.contains(i)));
    }
}
