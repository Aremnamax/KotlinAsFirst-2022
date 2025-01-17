@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt
import kotlin.math.pow

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) = when {
    y < 0 -> listOf()
    y == 0.0 -> listOf(0.0)
    else -> {
        val root = sqrt(y)
        // Результат!
        listOf(-root, root)
    }
}

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.lowercase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.map { it * it }.sum())


/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */

fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size


/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> = TODO()

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int = TODO()

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int = p.mapIndexed { idx, value -> value * (x.toDouble().pow(idx)).toInt() }.sum()

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> = TODO()

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> = TODO()

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = TODO()

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var n1 = n
    var digit = ""
    if (n == 0) digit = "0"
    while (n1 != 0) {
        digit = if (n1 % base < 10) (n1 % base).toString() + digit else ('a' + n1 % base - 10) + digit
        n1 /= base
    }
    return digit
}
/*fun main() {
    println('a' + 12 - 10)
}*/

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */


fun decimalFromString(str: String, base: Int): Int {
    var array = 0
    for (i in str.indices) {
        array += if (str[i] in '0'..'9') {
            (str[i] - '0') * (base.toDouble().pow(str.length - i - 1)).toInt()
        } else {
            (str[i] - 'a' + 10) * (base.toDouble().pow(str.length - i - 1)).toInt()
        }
    }
    return array
}

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String = TODO()

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var number = ""
    val nstr = n.toString()
    var n1 = n
    var flag = true
    var flag4 = false
    val arrayto9 = mutableMapOf(
        1 to "один",
        2 to "два",
        3 to "три",
        4 to "четыре",
        5 to "пять",
        6 to "шесть",
        7 to "семь",
        8 to "восемь",
        9 to "девять"
    )
    val arrayto1019 = mutableMapOf(
        10 to "десять",
        11 to "одиннадцать",
        12 to "двенадцать",
        13 to "тринадцать",
        14 to "четырнадцать",
        15 to "пятнадцать",
        16 to "шестнадцать",
        17 to "семнадцать",
        18 to "восемнадцать",
        19 to "девятнадцать"
    )
    val arrayto900 = mutableMapOf(
        1 to "сто",
        2 to "двести",
        3 to "триста",
        4 to "четыреста",
        5 to "пятьсот",
        6 to "шестьсот",
        7 to "семьсот",
        8 to "восемьсот",
        9 to "девятьсот"
    )
    val arrayto1000 = mutableMapOf(
        1 to "одна тысяча",
        2 to "две тысячи",
        3 to "три тысячи",
        4 to "четыре тысячи",
        5 to "пять тысяч",
        6 to "шесть тысяч",
        7 to "семь тысяч",
        8 to "восемь тысяч",
        9 to "девять тысяч"
    )
    val arrayto10001019 = mutableMapOf(
        10 to "тысяч",
        11 to "тысяч",
        12 to "тысяч",
        13 to "тысяч",
        14 to "тысяч",
        15 to "тысяч",
        16 to "тысяч",
        17 to "тысяч",
        18 to "тысяч",
        19 to "тысяч"
    )
    for (i in 1..nstr.length) {
        when {
            i == 1 && n1 % 100 !in 10..19 && n1 % 10 != 0 -> {
                number = arrayto9[n1 % 10] + number
            }

            i == 1 && n1 % 100 in 10..19 -> {
                number = arrayto1019[n1 % 100] + number
                flag = false
            }

            i == 2 && n1 % 10 in 5..8 && flag -> number = arrayto9[n1 % 10] + "десят" + " " + number
            i == 2 && n1 % 10 in 2..3 && flag -> number = arrayto9[n1 % 10] + "дцать" + " " + number
            i == 2 && n1 % 10 == 4 && flag -> number = "сорок $number"
            i == 2 && n1 % 10 == 9 && flag -> number = "девяносто $number"
            i == 3 && n1 % 10 != 0 -> {
                number = arrayto900[n1 % 10] + " " + number
                flag = true
            }

            i == 4 && n1 % 100 !in 10..19 && n1 % 10 != 0 -> {
                number = arrayto1000[n1 % 10] + " " + number
                flag4 = true
                flag = true
            }

            i == 4 && n1 % 100 != 10 && n1 % 10 == 0 -> {
                number = "тысяч $number"
                flag4 = true
                flag = true
            }

            i == 4 && n1 % 100 in 10..19 -> {
                number = arrayto1019[n1 % 100] + " " + arrayto10001019[n1 % 100] + " " + number
                flag = false
                flag4 = true
            }

            i == 5 && n1 % 10 in 5..8 && flag -> {
                number = arrayto9[n1 % 10] + "десят" + " " + number
                flag4 = true
            }

            i == 5 && n1 % 10 in 2..3 && flag -> {
                number = arrayto9[n1 % 10] + "дцать" + " " + number
                flag4 = true
            }

            i == 5 && n1 % 10 == 4 && flag -> {
                number = "сорок $number"
                flag4 = true
            }

            i == 5 && n1 % 10 == 9 && flag -> {
                number = "девяносто $number"
                flag4 = true
            }

            i == 6 -> {
                number = when {
                    !flag4 -> arrayto900[n1 % 10] + " " + "тысяч" + " " + number
                    else -> arrayto900[n1 % 10] + " " + number
                }
            }

            else -> number += ""
        }
        n1 /= 10
    }
    return number.trim()
}

/*fun main() {
    println(russian(51017))
}*/
