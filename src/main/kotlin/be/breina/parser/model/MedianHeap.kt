package be.breina.parser.model

import java.util.*
import java.util.stream.Stream

class MedianHeap<T : Comparable<T>?> : Collection<T> {
    private val left = PriorityQueue<T>()
    private val right = PriorityQueue(Comparator.reverseOrder<T>())
    fun add(newElement: T) {
        val leftElement = left.peek()
        if (leftElement != null && leftElement < newElement) {
            left.add(newElement)
            if (left.size + 1 > right.size) {
                right.add(left.poll())
            }
        } else {
            right.add(newElement)
            if (right.size > left.size) {
                left.add(right.poll())
            }
        }
    }

    val median: T
        get() = left.peek()

    override val size: Int
        get() = left.size + right.size

    override fun isEmpty(): Boolean = left.isEmpty() && right.isEmpty()

    override fun stream(): Stream<T> = Stream.concat(left.stream(), right.stream())

    override fun iterator(): Iterator<T> = stream().iterator()

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.stream()
            .allMatch(::contains)
    }

    override fun contains(element: T): Boolean = left.contains(element) || right.contains(element)

    fun clear() {
        left.clear()
        right.clear()
    }
}