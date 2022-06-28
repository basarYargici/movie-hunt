package com.basar.moviehunter.util.model

data class Tuple2<T1, T2>(val t1: T1, val t2: T2)

data class Tuple3<T1, T2, T3>(val t1: T1, val t2: T2, val t3: T3)

data class Tuple4<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

data class Tuple5<T1, T2, T3, T4, T5>(val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5)

data class Tuple6<T1, T2, T3, T4, T5, T6>(
    val t1: T1,
    val t2: T2,
    val t3: T3,
    val t4: T4,
    val t5: T5,
    val t6: T6
)

infix fun <T1, T2> T1.then(t2: T2): Tuple2<T1, T2> {
    return Tuple2(this, t2)
}

infix fun <T1, T2, T3> Tuple2<T1, T2>.then(t3: T3): Tuple3<T1, T2, T3> {
    return Tuple3(this.t1, this.t2, t3)
}

infix fun <T1, T2, T3, T4> Tuple3<T1, T2, T3>.then(t4: T4): Tuple4<T1, T2, T3, T4> {
    return Tuple4(this.t1, this.t2, this.t3, t4)
}

infix fun <T1, T2, T3, T4, T5> Tuple4<T1, T2, T3, T4>.then(t5: T5): Tuple5<T1, T2, T3, T4, T5> {
    return Tuple5(this.t1, this.t2, this.t3, this.t4, t5)
}

infix fun <T1, T2, T3, T4, T5, T6> Tuple5<T1, T2, T3, T4, T5>.then(t6: T6): Tuple6<T1, T2, T3, T4, T5, T6> {
    return Tuple6(this.t1, this.t2, this.t3, this.t4, this.t5, t6)
}

