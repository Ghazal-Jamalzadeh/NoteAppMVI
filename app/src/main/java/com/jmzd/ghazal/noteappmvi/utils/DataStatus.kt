package com.jmzd.ghazal.noteappmvi.utils

data class DataStatus<out T>(val status: Status, val data: T? = null, val isEmpty: Boolean) {

    enum class Status {
        SUCCESS
    }

    companion object {
        fun <T> success(data: T?, isEmpty: Boolean): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data, isEmpty)
        }
    }
}

/*
* نمیخوایم با دیتا تایپ مشخصی کار کنیم و میخوایم یک حالت داینامیکی داشته باشه این اکستتنشن
* پس میایم از any استفاده میکنیم
* میشه همه حالت ها رو توی ورودی اکستنشن داد بهش یا اکستنشن بیاد روی اون چیز اصلیه سوار شه
*
*
* */
fun MutableList<out Any>.getIndexFromList(item: Any): Int {
    var index = 0
    for (i in this.indices) {
        if (this[i] == item) {
            index = i
            break
        }
    }
    return index
}