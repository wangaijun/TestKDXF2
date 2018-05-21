package com.iflytek

/**
 * Created by waj on 18-5-18.
 */
class MyPip(private val size:Int){
    var head: Node = Node(0)
    var tail: Node =head

    init {
        var i = 1
        while (i<size){
            tail.next = Node(0)
            tail = tail.next!!
            i++
        }
    }

    fun enqueue(data: Int) {
        tail.next = Node(data)
        tail = tail.next!!
        head = head.next!!
    }

    fun getDatas():List<Int> {
        val list = arrayListOf<Int>()
        var p: Node = head
        while (p != tail) {
            list.add(p.data)
            p = p.next!!
        }
        list.add(tail.data)
        return list
    }
}