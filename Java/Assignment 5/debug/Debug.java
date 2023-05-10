package debug;

import debug.model.IntBinTreeNode;
import debug.model.SortedIntBinTreeNode;
import debug.model.UnsortedIntBinTreeNode;

public class Debug {
    public static void main(String[] args) {

        IntBinTreeNode treeSorted = new SortedIntBinTreeNode(1, null, null);
        IntBinTreeNode treeUnsorted = new UnsortedIntBinTreeNode(1, null, null);
        int[] toInsert = { 4, 2, 6, 8, 0, 2, 1, 5, 1 };
        // for each i in toInsert
        for (int i : toInsert) {
            if (!treeSorted.contains(i)) { //to avoid getting equals numbers
                treeSorted.insert(i);
            }
            treeUnsorted.insert(i);
        }
        System.out.println(treeSorted);
        System.out.println(treeUnsorted);
    }
}
