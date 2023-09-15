public class BuddyBlockManager {
    private int poolSize; // The size of the memory pool
    private int blockSize; // The size of the smallest memory block
    private int levels; // Number of levels in the buddy system tree
    private Block[] buddyTree; // Array representing the buddy system tree

    public BuddyBlockManager(int poolSize) {
        this.poolSize = poolSize;
        this.blockSize = 1;
        this.levels = log2(poolSize);
        this.buddyTree = new Block[2 * poolSize - 1];

        // Initialize the buddy system tree
        initializeTree(0, 0, poolSize - 1);
    }


    // Allocate a memory block of size 'size'
    public int allocate(int size) {
        // Find the smallest block that fits the requested size
        int level = log2(size);
        return allocateBlock(0, level);
    }


    // Deallocate a memory block
    public void deallocate(int blockId) {
        // Mark the block as free
        buddyTree[blockId].setFree(true);

        // Propagate the change up the tree and merge buddies if possible
        int parent = getParent(blockId);
        while (parent >= 0) {
            int buddy = getBuddy(blockId, parent);
            if (buddy >= 0 && buddyTree[buddy].isFree() && buddyTree[parent]
                .isFree()) {
                // Merge the block with its buddy
                buddyTree[parent].setFree(true);
                blockId = parent;
                parent = getParent(blockId);
            }
            else {
                break;
            }
        }
    }


    // Helper method to find the log base 2 of a number
    private int log2(int n) {
        return (int)(Math.log(n) / Math.log(2));
    }


    // Initialize the buddy system tree
    private void initializeTree(int node, int start, int end) {
        if (start == end) {
            // Leaf node representing a memory block
            buddyTree[node] = new Block(start);
        }
        else {
            // Internal node representing a range of memory
            int mid = (start + end) / 2;
            initializeTree(2 * node + 1, start, mid);
            initializeTree(2 * node + 2, mid + 1, end);
            buddyTree[node] = new Block(-1); // Internal nodes are not
                                             // associated with a specific block
        }
    }


    // Allocate a block at the given level of the buddy system tree
    private int allocateBlock(int node, int level) {
        if (level == 0 && !buddyTree[node].isFree()) {
            // Smallest level reached, block already allocated
            return -1;
        }
        if (!buddyTree[node].isFree()) {
            // Block at this level is allocated, descend to children
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;
            int leftResult = allocateBlock(leftChild, level - 1);
            if (leftResult != -1) {
                return leftResult;
            }
            return allocateBlock(rightChild, level - 1);
        }
        else {
            // Block at this level is free, allocate it
            buddyTree[node].setFree(false);
            return node;
        }
    }


    // Get the parent node of a given node
    private int getParent(int node) {
        if (node == 0) {
            return -1; // Root node has no parent
        }
        return (node - 1) / 2;
    }


    // Get the buddy of a given node at a specified parent level
    private int getBuddy(int node, int parent) {
        if (node % 2 == 0) {
            // Node is a right child, buddy is the left child
            return parent * 2 + 1;
        }
        else {
            // Node is a left child, buddy is the right child
            return parent * 2 + 2;
        }
    }

    // Inner class representing a memory block
    private class Block {
        private int blockId;
        private boolean isFree;

        public Block(int blockId) {
            this.blockId = blockId;
            this.isFree = true;
        }


        public boolean isFree() {
            return isFree;
        }


        public void setFree(boolean free) {
            isFree = free;
        }
    }
}
