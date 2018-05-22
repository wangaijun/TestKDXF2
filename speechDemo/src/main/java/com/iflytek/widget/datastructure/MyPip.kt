package com.iflytek.widget.datastructure

/**
 * Created by waj on 18-5-18.
 */
class MyPip(private val capacity:Int){
    var head: Node = Node(0)
    var tail: Node =head
    var sizd:Int = 1

    fun enqueue(data: Int) {
        tail.next = Node(data)
        tail = tail.next!!
        if (sizd<capacity) {
            sizd += 1
        }
        else{
            head = head.next!!
        }
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