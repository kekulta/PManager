package com.kekulta.pmanager.shared.utils

fun <T> List<T>.lastNum(num: Int): T = this[this.lastIndex - num]