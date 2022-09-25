@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt


// Урок 2: ветвления (здесь), логический тип (см. 2.2).
// Максимальное количество баллов = 6
// Рекомендуемое количество баллов = 5
// Вместе с предыдущими уроками = 9/12

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}


fun main(){
    println(ageDescription(199))
    println(timeForHalfWay(4.0,3.0,1.0,4.0,1.0,6.0))
    println(whichRookThreatens(1,2,3,4,5,6))
    println(rookOrBishopThreatens(1,6,7,6,3,8))
    println(triangleKind(5.0,3.0,4.0))
    println(segmentLength(1,15,10,14))
}


/**
 * Простая (2 балла)
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    return if (age < 1 || age > 199) "Неправильный ввод" else
        if (age % 100 in 5..20) "$age лет" else
            if (age % 10 == 1) "$age год" else
                if (age % 10 in 2..4) "$age года" else
                    if ((age % 10 in 5..9) || (age % 10 == 0)) "$age лет" else
                        ""

}



/**
 * Простая (2 балла)
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val s1 = t1 * v1
    val s2 = t2 * v2
    val s3 = t3 * v3
    val s = s1 + s2 + s3
    return if (s / 2 < s1) (s / 2 / v1) else
        if (s / 2 < s1 + s2) (t1 + ((s / 2 - s1) / v2)) else (t1 + t2 + (((s / 2) - s1 - s2) / v3))
}

/**
 * Простая (2 балла)
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int = when{
    ((kingX == rookX1 || kingY == rookY1) && (kingX != rookX2 && kingY != rookY2)) -> 1
    ((kingX == rookX2 || kingY == rookY2) && (kingX != rookX1 && kingY != rookY1)) -> 2
    ((kingX == rookX1 && kingY == rookY2) || (kingX == rookX2 && kingY == rookY1)) -> 3
    else -> 0
}


/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int {
    return if ((kingX == rookX && kotlin.math.abs(kingX - bishopX) == kotlin.math.abs(kingY - bishopY)) || (kingY == rookY && kotlin.math.abs(kingX - bishopX) == kotlin.math.abs(kingY - bishopY))) 3 else
    {
        if ((kingX == rookX) || (kingY == rookY)) 1 else
        {
            if (kotlin.math.abs(kingX - bishopX) == kotlin.math.abs(kingY - bishopY)) 2 else 0
        }
    }
}

/**
 * Простая (2 балла)
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */

fun triangleKind(a: Double, b: Double, c: Double): Int = when{
    (a > b + c || c > b + a || b > a + c) -> -1
    (max(max(a, b), c).pow(2) < min(min(a, b), c).pow(2) + min(max(a,b), c).pow(2)) -> 0
    (max(max(a, b), c).pow(2) == min(min(a, b), c).pow(2) + min(max(a,b), c).pow(2)) -> 1
    else -> 2
}


/*{
    return if (a < b + c && c < b + a && b < a + c) {
        if ((a * a + b * b > c * c) || (b * b + c * c > a * a) || (a * a + c * c > b * b)) 2 else {
            if ((a * a + b * b == c * c) || (b * b + c * c == a * a) || (a * a + c * c == b * b)) 1 else {
                if ((a * a + b * b < c * c) || (b * b + c * c < a * a) || (a * a + c * c < b * b)) 0  else -1 }
        }
    } else -1
}*/

/**
 * Средняя (3 балла)
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int{
    return if ((a <= c && c <= b && b <= d) || (c <= a && a <= d && d <= b) || (c <= a && a <= b && b <= d) || (a <= c && c <= d && d <= b)){
        if (a <= c && c <= b && b <= d) (b - c) else{
            if (c <= a && a <= d && d <= b) (d - a) else{
                if (c <= a && a <= b && b <= d) (b - a) else{
                    if (a <= c && c <= d && d <= b) (d - c) else -1
                }
            }
        }
    } else -1
}
