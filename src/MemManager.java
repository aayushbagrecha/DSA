public class MemManager {
    private byte[] memoryPool;
    private int poolSize;
    private int freeBlockStart;
    private int freeBlockSize;

    public MemManager(int initialSize) {
        memoryPool = new byte[initialSize];
        poolSize = initialSize;
        freeBlockStart = 0;
        freeBlockSize = initialSize;
    }


    public Handle insert(byte[] data, int length) {
        if (length > freeBlockSize) {
            expandMemoryPool(length);
        }

        Handle handle = new Handle(freeBlockStart, length);
        System.arraycopy(data, 0, memoryPool, freeBlockStart, length);
        freeBlockStart += length;
        freeBlockSize -= length;

        return handle;
    }


    public void get(byte[] output, Handle handle, int length) {
        if (handle != null && handle.getRecordLength() == length) {
            System.arraycopy(memoryPool, handle.getStartingPosition(), output,
                0, length);
        }
    }


    public void remove(Handle handle) {
        if (handle != null) {
            int blockIndex = handle.getStartingPosition();
            int recordLength = handle.getRecordLength();

            // Fill the memory block with zeros to "delete" the record
            for (int i = blockIndex; i < blockIndex + recordLength; i++) {
                memoryPool[i] = 0;
            }

            // Update free block information
            freeBlockStart = Math.min(freeBlockStart, blockIndex);
            freeBlockSize += recordLength;
        }
    }


    private void expandMemoryPool(int blockSize) {
        int newSize = Math.max(poolSize * 2, poolSize + blockSize);
        // int newSize = poolSize;

        // while (newSize < blockSize) {
        // newSize = 2 * newSize;
        System.out.println("Memory pool expanded to " + newSize + " bytes");

        byte[] newMemoryPool = new byte[newSize];
        System.arraycopy(memoryPool, 0, newMemoryPool, 0, poolSize);
        memoryPool = newMemoryPool;
        poolSize = newSize;
    }


    public void dump() {
        System.out.println("Memory Pool Dump:");
        System.out.println("Pool Size: " + memoryPool.length);
        System.out.println("Free Block Start: " + freeBlockStart);
        System.out.println("Free Block Size: " + freeBlockSize);
    }
}
