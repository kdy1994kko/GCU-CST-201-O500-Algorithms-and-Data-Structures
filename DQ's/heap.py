def delete_min(heap):
    # Removes and returns the minimum element from a min-heap (binary heap implemented as a list)
    if len(heap) == 0:
        return None  # Return None if the heap is empty
    
    min_element = heap[0]  # The minimum element is always at the root (index 0)
    last_element = heap.pop()  # Remove the last element to shrink the heap

    if heap:
        heap[0] = last_element  # Move the last element to the root
        heapify_down(heap, 0)   # Restore heap property starting from the root

    return min_element

def heapify_down(heap, i):
    # Restores the min-heap property by pushing down the element at index i
    n = len(heap)
    while i < n:
        left = 2 * i + 1       # Index of left child
        right = 2 * i + 2      # Index of right child
        smallest = i

        if left < n and heap[left] < heap[smallest]:
            smallest = left
        if right < n and heap[right] < heap[smallest]:
            smallest = right

        if smallest == i:
            break  # Heap property is satisfied

        # Swap current node with the smaller child
        heap[i], heap[smallest] = heap[smallest], heap[i]
        i = smallest


def delete_min_from_max_heap(heap):
    # Deletes and returns the minimum element from a max-heap (unusual operation)
    if not heap:
        return None

    n = len(heap)
    start = n // 2  # Start index of leaf nodes
    min_index = start

    # Find the minimum among the leaf nodes
    for i in range(start + 1, n):
        if heap[i] < heap[min_index]:
            min_index = i

    min_element = heap[min_index]

    # Swap with last element and remove
    heap[min_index], heap[-1] = heap[-1], heap[min_index]
    heap.pop()

    # Restore max-heap property if needed (overwrites same heapify_down name)
    heapify_down(heap, min_index)

    return min_element

def heapify_down(heap, i):
    # This version assumes max-heap and restores heap from index i downward
    n = len(heap)
    while i < n:
        left = 2 * i + 1
        right = 2 * i + 2
        largest = i

        if left < n and heap[left] > heap[largest]:
            largest = left
        if right < n and heap[right] > heap[largest]:
            largest = right

        if largest == i:
            break

        heap[i], heap[largest] = heap[largest], heap[i]
        i = largest

# ------------------------------------------------------------------------

def delete_value_from_heap(H, v):
    # Deletes a specific value `v` from a min-heap
    n = len(H)
    index = -1

    # Search for the index of the value to be deleted
    for i in range(n):
        if H[i] == v:
            index = i
            break

    if index == -1:
        return  # Value not found in heap

    # Replace with the last element and remove it
    H[index] = H[-1]
    H.pop()

    # Try restoring heap property both directions
    heapify_down(H, index)  # In case the replaced value is too large
    heapify_up(H, index)    # In case the replaced value is too small

def heapify_down(H, i):
    # Push down the element at index i to restore min-heap property
    n = len(H)
    while i < n:
        left = 2 * i + 1
        right = 2 * i + 2
        smallest = i

        if left < n and H[left] < H[smallest]:
            smallest = left
        if right < n and H[right] < H[smallest]:
            smallest = right

        if smallest == i:
            break

        H[i], H[smallest] = H[smallest], H[i]
        i = smallest

def heapify_up(H, i):
    # Move the element at index i up the heap to restore min-heap property
    while i > 0:
        parent = (i - 1) // 2
        if H[i] < H[parent]:
            H[i], H[parent] = H[parent], H[i]
            i = parent
        else:
            break
