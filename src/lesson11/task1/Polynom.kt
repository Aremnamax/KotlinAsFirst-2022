@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import ru.spbstu.wheels.NullableMonad.filter
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {

    private fun cleanCoeffs(vararg lst: Double): DoubleArray {
        var sm = 0
        for (dig in lst) {
            if (dig == 0.0) sm++ else break
        }
        return if (sm == lst.size) doubleArrayOf(0.0)
        else lst.slice(sm until lst.size).toDoubleArray().reversedArray()
    }

    private var workCoeffs = cleanCoeffs(*coeffs)



    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = workCoeffs[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        println(workCoeffs.joinToString())
        var result = 0.0
        for (index in workCoeffs.indices) {
            result += workCoeffs[index] * x.pow(index)
        }
        return result
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = if (workCoeffs.isEmpty()) 0 else workCoeffs.size - 1

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        var thisOne = this.workCoeffs
        var otherOne = other.workCoeffs
        val res = Array(maxOf(thisOne.size, otherOne.size)) { 0.0 }
        if (thisOne.size < otherOne.size) thisOne += Array(otherOne.size - thisOne.size) { 0.0 }.toDoubleArray()
        else otherOne += Array(thisOne.size - otherOne.size) { 0.0 }.toDoubleArray()
        thisOne.indices.forEach { res[it] = thisOne[it] + otherOne[it] }
        return Polynom(*res.toDoubleArray().reversedArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom =
        Polynom(*this.workCoeffs.map { if (it == 0.0) it else -it }.toDoubleArray().reversedArray())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom {
        var thisOne = this.workCoeffs
        var otherOne = other.workCoeffs
        val res = Array(maxOf(thisOne.size, otherOne.size)) { 0.0 }
        /*if (thisOne.contentEquals(otherOne)) return Polynom(0.0)*/
        if (thisOne.size < otherOne.size) thisOne += Array(otherOne.size - thisOne.size) { 0.0 }.toDoubleArray()
        else otherOne += Array(thisOne.size - otherOne.size) { 0.0 }.toDoubleArray()
        thisOne.indices.forEach { res[it] = thisOne[it] - otherOne[it] }
        return Polynom(*res.reversedArray().toDoubleArray())
    }

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val thisOne = this.workCoeffs
        val otherOne = other.workCoeffs
        var res = DoubleArray(otherOne.size) { 0.0 }
        for (thisIndex in thisOne.indices) {
            val part = Array(otherOne.size + thisIndex) { 0.0 }
            for (otherIndex in otherOne.indices) {
                part[otherIndex + thisIndex] = thisOne[thisIndex] * otherOne[otherIndex]
            }
            part.indices.forEach { res[it] = res[it] + part[it] }
            res += 0.0
        }
        return Polynom(*res.reversedArray())
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom {
        val thisOne = this.workCoeffs.reversedArray()
        val otherOne = other.workCoeffs.reversedArray()
        var divPart = thisOne.slice(otherOne.indices)
        val res = DoubleArray(thisOne.size - otherOne.size + 1) { 0.0 }
        var maxIndex = thisOne[0]
        var otherMinus = DoubleArray(otherOne.size) { 0.0 }
        for (index in 0 until (thisOne.size - otherOne.size + 1)) {
            res[index] = maxIndex / otherOne[0]
            if (maxIndex / otherOne[0] != 1.0) {
                for (otherIndex in otherOne.indices) {
                    otherMinus[otherIndex] = otherOne[otherIndex] * maxIndex
                }
            } else otherMinus = otherOne
            /*println(otherMinus.joinToString())*/
            val minus = Polynom(*divPart.toDoubleArray()) - Polynom(*otherMinus)
            /*println(minus.workCoeffs.joinToString())*/
            divPart = cleanCoeffs(*(minus.workCoeffs.toList().reversed() + thisOne[divPart.size]).toDoubleArray()).toList()
            maxIndex = divPart.reversed().first()
            /*println(maxIndex)
            println(divPart)*/
        }
        /*println(100)*/
        /*println(res.joinToString())*/
        return Polynom(*res)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom =
        Polynom(*this.workCoeffs.reversedArray()) - Polynom(*other.workCoeffs.reversedArray()) *
                (Polynom(*this.workCoeffs.reversedArray()) / Polynom(*other.workCoeffs.reversedArray()))


    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Polynom && workCoeffs.contentEquals(other.workCoeffs)

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        return workCoeffs.contentHashCode()
    }

}

