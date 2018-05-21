package com.example.waj.testkdxf2

/**
 * Created by waj on 18-5-18.
 */
class MyPip(private val size:Int){
    var head:Node=Node(0)
    var tail:Node=head

    init {
        var i = 1
        while (i<size){
            tail.next = Node(0)
            i++
        }
    }

    fun enqueue(data: Int) {
        tail.next = Node(data)

    }

    fun getDatas():List<Int> {
        val list = arrayListOf<Int>()
        head?.let {
            var p:Node = it
            while (p!=tail){
                list.add(p.data)
                p = p.next!!
            }
        }
        return list
    }
}