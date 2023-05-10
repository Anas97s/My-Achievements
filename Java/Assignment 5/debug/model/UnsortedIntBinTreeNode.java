package debug.model;

import java.util.Random;

public class UnsortedIntBinTreeNode extends IntBinTreeNode {
    private static Random rand = new Random(); //1) new random was not written

    public UnsortedIntBinTreeNode(int content, IntBinTreeNode left, IntBinTreeNode right) { // 2)constructor was not implemented
        super(content, left, right);
    }

    /**
     * Randomly inserts to left or right.
     * If left/right is not null, call insert on that child node.
     *
     * @param i Integer to insert.
     */
    @Override
    public void insert(int i) {
        if (UnsortedIntBinTreeNode.rand.nextBoolean()) {
            if (super.left_ == null)
                super.left_ = new UnsortedIntBinTreeNode(i, null, null);
            else
                super.left_.insert(i);
        } else {
            if (super.right_ == null)
                super.right_ = new UnsortedIntBinTreeNode(i, null, null);
            else
                super.right_.insert(i);
        }
    }

    /**
     * Looks if integer is in tree.
     * Looks through both subtrees, as they are not sorted.
     *
     * @param i Integer to search for.
     * @return  True if integer is in tree.
     */
    @Override
    public boolean contains(int i) {
        return super.content_ == i || (super.left_ != null && super.left_.contains(i)) || (super.right_ != null && super.right_.contains(i));
    }
}
