public class MemoryPool {
    private byte[] memoryPool;
    private FreeBlock[] freeBlocks;
    private int freeBlockCount;

    public MemoryPool(int initialSize) {
        memoryPool = new byte[initialSize];
        freeBlocks = new FreeBlock[initialSize]; // Max number of free blocks
        freeBlocks[0] = new FreeBlock(0, initialSize);
        freeBlockCount = 1;
    }


    public int allocateMemory(int size) {
        FreeBlock suitableBlock = findSuitableBlock(size);

        if (suitableBlock != null) {
            splitBlock(suitableBlock, size);
            removeFreeBlock(suitableBlock);
            return suitableBlock.getStart();
        }

        return -1; // Allocation failed
    }


    /**
     * Prints the freeblocks of the hash table
     */
    public void printMemoryBlocks() {
        // System.out.println.println("FreeBlock List:");
        // for (int block : freeBlocks) {
        // if (block == -1)
        // continue;
        // else
        // System.out.println.println(block + " ");
        // }
        // System.out.println.println("There are no freeblocks in the memory
        // pool");
    }


    public void deallocateMemory(int handle) {
        FreeBlock blockToDeallocate = findBlockByHandle(handle);

        while (mergeWithBuddy(blockToDeallocate)) {
            FreeBlock buddy = findBuddy(blockToDeallocate);
            removeFreeBlock(buddy);
            blockToDeallocate = mergedBlock(blockToDeallocate);
        }

        addFreeBlock(blockToDeallocate);
    }


    private FreeBlock findBlockByHandle(int handle) {
        for (int i = 0; i < freeBlockCount; i++) {
            if (freeBlocks[i].getStart() == handle) {
                return freeBlocks[i];
            }
        }
        return null; // Block with the given handle not found
    }


    private FreeBlock findSuitableBlock(int size) {
        for (int i = 0; i < freeBlockCount; i++) {
            if (freeBlocks[i].getSize() >= size) {
                return freeBlocks[i];
            }
        }
        return null; // No suitable block found
    }


    private void splitBlock(FreeBlock block, int size) {
        if (block.getSize() > size) {
            FreeBlock newBlock = new FreeBlock(block.getStart() + size, block
                .getSize() - size);
            block.setSize(size);
            addFreeBlock(newBlock);
        }
    }


    private FreeBlock findBuddy(FreeBlock block) {
        int buddyStart;
        if (block.getStart() % (2 * block.getSize()) == 0) {
            buddyStart = block.getStart() + block.getSize();
        }
        else {
            buddyStart = block.getStart() - block.getSize();
        }
        for (int i = 0; i < freeBlockCount; i++) {
            if (freeBlocks[i].getStart() == buddyStart && freeBlocks[i]
                .getSize() == block.getSize()) {
                return freeBlocks[i];
            }
        }
        return null; // Buddy not found
    }


    private boolean mergeWithBuddy(FreeBlock block) {
        FreeBlock buddy = findBuddy(block);
        if (buddy != null) {
            removeFreeBlock(buddy);
            block.merge(buddy);
            return true;
        }
        return false; // No buddy to merge with
    }


    private FreeBlock mergedBlock(FreeBlock block) {
        int mergedStart = Math.min(block.getStart(), findBuddy(block)
            .getStart());
        int mergedSize = 2 * block.getSize();
        return new FreeBlock(mergedStart, mergedSize);
    }


    private void addFreeBlock(FreeBlock block) {
        freeBlocks[freeBlockCount] = block;
        freeBlockCount++;
    }


    private void removeFreeBlock(FreeBlock block) {
        int indexToRemove = -1;
        for (int i = 0; i < freeBlockCount; i++) {
            if (freeBlocks[i] == block) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1) {
            for (int i = indexToRemove; i < freeBlockCount - 1; i++) {
                freeBlocks[i] = freeBlocks[i + 1];
            }
            freeBlocks[freeBlockCount - 1] = null;
            freeBlockCount--;
        }
    }

    private class FreeBlock {
        private int start;
        private int size;

        public FreeBlock(int start, int size) {
            this.start = start;
            this.size = size;
        }


        public int getStart() {
            return start;
        }


        public int getSize() {
            return size;
        }


        public void setSize(int size) {
            this.size = size;
        }


        public void merge(FreeBlock other) {
            this.start = Math.min(this.start, other.getStart());
            this.size = 2 * this.size;
        }
    }
}
