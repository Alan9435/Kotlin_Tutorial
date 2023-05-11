package com.example.common.Base

interface InterfaceCallback  {
    fun callBackTest(testDataClass: TestDataClass){}

    fun getItem(itemText: String){}
}

data class TestDataClass(val id: String,val name: String) {}