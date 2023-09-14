import java.util.ArrayList;
import java.util.List;

public class MemoryPool {
    private byte[] memoryPool;
    private List<Handle> freeBlocks;

    public MemoryPool(int size) {
        this.memoryPool = new byte[size];
        this.freeBlocks = new ArrayList<>();
        this.freeBlocks.add(new Handle(0, size));
    }

    // Initialize memory pool


    public void initializeMemoryPool(int size) {
        this.memoryPool = new byte[size];
        this.freeBlocks.clear();
        this.freeBlocks.add(new Handle(0, size));
    }

    // Insert data into memory pool


    public Handle insertData(byte[] data) {
        // Find a free block to insert data
        Handle freeBlock = getFreeBlock(data.length);

        if (freeBlock != null) {
            int startingPosition = freeBlock.getStartingPosition();
            int recordLength = freeBlock.getRecordLength();

            // Copy data into the memory pool
            System.arraycopy(data, 0, memoryPool, startingPosition,
                data.length);

            // Update freeBlocks list
            int remainingSpace = recordLength - data.length;
            if (remainingSpace > 0) {
                freeBlocks.add(new Handle(startingPosition + data.length,
                    remainingSpace));
            }
            freeBlocks.remove(freeBlock); // Remove the used block from the list

            return new Handle(startingPosition, data.length);
        }

        return null; // No free block available for insertion
    }

    // Delete data from memory pool using a handle


    public void deleteData(Handle handle) {
        int startingPosition = handle.getStartingPosition();
        int recordLength = handle.getRecordLength();

        // Clear the memory block by setting bytes to 0
        for (int i = startingPosition; i < startingPosition
            + recordLength; i++) {
            memoryPool[i] = 0;
        }

        // Update freeBlocks list
        freeBlocks.add(handle);
    }

    // Get a free block from memory pool


    public Handle getFreeBlock(int size) {
        for (Handle block : freeBlocks) {
            if (block.getRecordLength() >= size) {
                return block;
            }
        }
        return null; // No suitable free block found
    }

    // Serialize memory pool data


    public byte[] serialize() {
        return memoryPool;
    }

    // Deserialize memory pool data


    public void deserialize(byte[] data) {
        if (data.length == memoryPool.length) {
            System.arraycopy(data, 0, memoryPool, 0, data.length);
        }
        else {
            // Handle deserialization error (sizes don't match)
            throw new IllegalArgumentException(
                "Deserialization error: Data size doesn't match memory pool size");
        }
    }

}
