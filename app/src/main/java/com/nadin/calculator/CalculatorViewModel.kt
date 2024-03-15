package com.nadin.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
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

    private val _history = MutableLiveData<MutableList<String>>()
    val history: LiveData<List<String>>
        get() = _history.map { it.toList() }

    fun updateArithmeticProcess(keyText: String) {
        _arithmeticProcess.value += keyText
    }

    fun onClear() {
        _arithmeticProcess.value = ""
        _result.value = ""
    }

    private fun calculateNumbersQueue(): Queue<String> {
        val numbersQueue: Queue<String> = LinkedList()
        val operatorsStack = Stack<String>()
        val arithmeticProcessList = convertArithmeticProcess(_arithmeticProcess.value!!)

        for (item in arithmeticProcessList) {
            when {
                item.all { it in "0123456789." } -> numbersQueue.add(item)

                item == "(" -> operatorsStack.push(item)
                item == ")" -> {
                    while (operatorsStack.peek() != "(") {
                        numbersQueue.add(operatorsStack.pop())
                    }
                    operatorsStack.pop()
                    continue
                }

                else -> {
                    if (!operatorsStack.empty() && (operatorsStack.peek() == "x" || operatorsStack.peek() == "/")) {
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
        val calculationStack = Stack<Double>()

        val numbersQueue = calculateNumbersQueue()
        Log.i("Result Queue", numbersQueue.toString())

        while (!numbersQueue.isEmpty()) {
            val item = numbersQueue.poll()
            if (item!!.all { it in "0123456789." }) {
                calculationStack.push(item.toDouble())
            } else {
                val secondNumber = calculationStack.pop()
                val firstNumber = calculationStack.pop()
                when (item) {
                    "+" -> calculationStack.push((firstNumber + secondNumber))
                    "-" -> calculationStack.push((firstNumber - secondNumber))
                    "x" -> calculationStack.push((firstNumber * secondNumber))
                    "/" -> calculationStack.push((firstNumber / secondNumber))
                }
            }
        }
        Log.i("Result", calculationStack.peek().toString())
        _result.value = calculationStack.pop().toString()

        val history = _history.value ?: mutableListOf()
        history.add("${_arithmeticProcess.value} = ${_result.value}")
        _history.value = history
    }

    private fun convertArithmeticProcess(arithmeticProcess: String): List<String> {
        val regex = Regex("""([-+x/()]|\d+(\.\d+)?)""")
        val tokens = regex.findAll(arithmeticProcess).map { it.value }.toList().map { it.trim() }
            .filter { it.isNotBlank() }
        Log.i("Result", tokens.toString())
        return tokens
    }
}