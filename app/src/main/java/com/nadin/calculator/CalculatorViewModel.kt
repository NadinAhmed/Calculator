package com.nadin.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class CalculatorViewModel : ViewModel() {
    private val _arithmeticProcess = MutableLiveData("")
    val arithmeticProcess: LiveData<String>
        get() = _arithmeticProcess

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    fun updateArithmeticProcess(keyText: String) {
        _arithmeticProcess.value += keyText
    }

    fun onClear() {
        _arithmeticProcess.value = ""
        _result.value = ""
    }

    private fun calculateNumbersQueue(): Queue<Char> {
        val numbersQueue: Queue<Char> = LinkedList()
        val operatorsStack = Stack<Char>()
        for (item in _arithmeticProcess.value!!.iterator()) {
            when (item) {
                in "123456789" -> numbersQueue.add(item)

                '(' -> operatorsStack.push(item)
                ')' -> {
                    while (operatorsStack.peek() != '(') {
                        numbersQueue.add(operatorsStack.pop())
                    }
                    operatorsStack.pop()
                    continue
                }

                else -> {
                    if (!operatorsStack.empty() && (operatorsStack.peek() == 'x' || operatorsStack.peek() == '/')) {
                        numbersQueue.add(operatorsStack.pop())
                    }
                    operatorsStack.push(item)

                }
            }
        }

        while (!operatorsStack.empty()) {
            numbersQueue.add(operatorsStack.pop())
        }
        return numbersQueue
    }

    fun calculateResult() {
        val calculationStack = Stack<Int>()

        val numbersQueue = calculateNumbersQueue()
        Log.i("Result Queue", numbersQueue.toString())

        while (!numbersQueue.isEmpty()) {
            val item = numbersQueue.poll()
            if (item!! in "123456789") {
                calculationStack.push(item.digitToInt())
            } else {
                val secondNumber = calculationStack.pop()
                val firstNumber = calculationStack.pop()
                when (item) {
                    '+' -> calculationStack.push((firstNumber + secondNumber))
                    '-' -> calculationStack.push((firstNumber - secondNumber))
                    'x' -> calculationStack.push((firstNumber * secondNumber))
                    '/' -> calculationStack.push((firstNumber / secondNumber))
                }
            }
        }
        Log.i("Result", calculationStack.peek().toString())
        _result.value = calculationStack.pop().toString()
    }
}