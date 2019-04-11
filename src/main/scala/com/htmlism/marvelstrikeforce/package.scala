package com.htmlism

package object marvelstrikeforce {
  implicit class MapOps[K, V](xs: Map[K, V]) {
    def join[V2](ys: Map[K, V2]): Map[K, (V, V2)] = {
      xs.keys.foreach(k => assert(ys.contains(k)))
      ys.keys.foreach(k => assert(xs.contains(k)))

      xs.keys.toList.map { k =>
        val both = xs(k) -> ys(k)
        k -> both
      }
        .toMap
    }
  }
}
